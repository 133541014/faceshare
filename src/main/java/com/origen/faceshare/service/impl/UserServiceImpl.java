
package com.origen.faceshare.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.model.enums.Pagination;
import com.origen.faceshare.model.enums.UserLevel;
import com.origen.faceshare.repository.UserMessageRepository;
import com.origen.faceshare.repository.UserRepository;
import com.origen.faceshare.service.UserService;
import com.origen.faceshare.utils.StringUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMessageRepository userMessageRepository;

	@Override
	public boolean addUser(User user) {

		if ( StringUtils.isBlank(user.getUsername()) )
		{
			return false;
		}

		if ( StringUtils.isBlank(user.getPassword()) )
		{
			return false;
		}

		if ( StringUtils.isBlank(user.getEmail()) )
		{
			return false;
		}

		if ( StringUtils.specialCharCheck(user.getUsername()) )
		{
			return false;
		}

		// 验证用户名邮箱是否已经存在
		String username = user.getUsername();
		String email = user.getEmail();

		List<User> userList = userRepository.queryUserByParam(username, email);

		if ( !userList.isEmpty() )
		{
			return false;
		}
		// 设定用户等级
		user.setLevel(UserLevel.PRIMARY.toString());
		// 取当前年月日作为创建时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String nowTime = sdf.format(new Date());
		user.setCreateDate(nowTime);
		// 修改用户信息
		userRepository.save(user);
		return true;

	}

	/**
	 * 用户登录
	 */
	@Override
	public boolean userLogin(User user) {

		if ( StringUtils.isBlank(user.getUsername()) )
		{
			return false;
		}

		if ( StringUtils.isBlank(user.getPassword()) )
		{
			return false;
		}

		List<User> loginUser = userRepository.userLogin(user.getUsername(), user.getPassword());

		if ( loginUser.isEmpty() )
		{
			return false;
		}

		return true;
	}

	/**
	 * 填写用户信息
	 */
	@Override
	public void addUserInfo(String nickname, String headImage, String description, String username) {

		User user = userRepository.findByUsername(username).get(0);

		if ( StringUtils.isNotBlank(nickname) )
		{
			user.setNickname(nickname);
		}

		if ( StringUtils.isNotBlank(headImage) )
		{
			user.setHeadImage(headImage);
		}

		if ( StringUtils.isNotBlank(description) )
		{
			user.setDescription(description);
		}

		userRepository.save(user);
	}

	/**
	 * 查询用户信息
	 */
	@Override
	public User queryUserInfo(String username) {

		List<User> list = null;

		// 判断是否有可能是邮箱登录
		if ( username.contains("@") )
		{

			list = userRepository.findByEmail(username);

		} else
		{

			list = userRepository.findByUsername(username);
		}

		if ( !list.isEmpty() )
		{
			return list.get(0);
		} else
		{
			return null;
		}

	}

	/**
	 * 条件检索用户信息
	 */
	@Override
	public List<User> queryUserByParam(String username, String nickname, String email) {

		List<User> userList = null;

		Specification<User> specification = new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

				List<Predicate> predicates = new ArrayList<Predicate>();
				if ( StringUtils.isNotBlank(username) )
				{
					predicates.add(criteriaBuilder.equal(root.get("username"), username));
				}
				if ( StringUtils.isNotBlank(nickname) )
				{
					predicates.add(criteriaBuilder.like(root.get("nickname"), "%" + nickname + "%"));
				}
				if ( StringUtils.isNotBlank(email) )
				{
					predicates.add(criteriaBuilder.equal(root.get("email"), email));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};

		userList = userRepository.findAll(specification);

		return userList;
	}

	/**
	 * 通过添加好友请求
	 */
	@Override
	public void passAddFriendRequest(Integer createBy, User acceptBy, Integer messageId) {

		// 查询出请求发送人
		User createUser = userRepository.findById(createBy);

		// 查询出请求接收人
		User acceptUser = userRepository.findById(acceptBy.getId());

		createUser.getFriends().remove(acceptUser);

		createUser.getFriends().add(acceptUser);

		userRepository.save(createUser);

		acceptUser.getFriends().remove(createUser);

		acceptUser.getFriends().add(createUser);

		userRepository.save(acceptUser);

		// 修改信息状态
		userMessageRepository.changeMessageState(messageId);
	}

	/**
	 * 查询用户好友列表
	 */
	@Override
	public Page<User> queryUserFriends(Integer userId, Integer pageNo) {

		Pageable pageable = new PageRequest(pageNo, Pagination.PAGE_SIZE_DEFAULT.getSize());

		Page<User> page = userRepository.findUserFriends(pageable, userId);

		return page;
	}

	/**
	 * 查询用户详细信息
	 */
	@Override
	public User queryUserDetail(Integer id) {

		User user = userRepository.findById(id);

		return user;
	}

	@Override
	public void removeUserFriend(Integer friendId, Integer userId) {

		User friend = userRepository.findById(friendId);

		User user = userRepository.findById(userId);

		List<User> friends = user.getFriends();

		friends.remove(friend);

		user.setFriends(friends);// ?

		userRepository.save(user);

	}

}
