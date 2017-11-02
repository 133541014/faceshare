
package com.origen.faceshare.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.origen.faceshare.model.entity.User;

public interface UserService {

	/**
	 * 用户注册
	 */
	public boolean addUser(User user);

	/**
	 * 用户登录
	 */
	public boolean userLogin(User user);

	/**
	 * 填写用户信息
	 */
	public void addUserInfo(String nickname, String headImage, String description, String username);

	/**
	 * 查询用户信息
	 */
	public User queryUserInfo(String username);

	/**
	 * 条件检索用户信息
	 */
	public List<User> queryUserByParam(String username, String nickname, String email);

	/**
	 * 通过添加好友请求
	 */
	public void passAddFriendRequest(Integer createBy, User acceptBy, Integer messageId);

	/**
	 * 查询用户好友列表
	 */
	public Page<User> queryUserFriends(Integer userId, Integer pageNo);

	/**
	 * 查询用户详细信息
	 */
	public User queryUserDetail(Integer id);

	/**
	 * 删除用户好友
	 */
	public void removeUserFriend(Integer friendId, Integer userId);

}
