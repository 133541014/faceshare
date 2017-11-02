
package com.origen.faceshare.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.model.enums.MessageType;
import com.origen.faceshare.model.vo.UserVO;
import com.origen.faceshare.service.UserMessageService;
import com.origen.faceshare.service.UserService;
import com.origen.faceshare.utils.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class UserController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserMessageService userMessageService;

	/**
	 * 用户注册
	 */
	@RequestMapping("/register")
	public void userRegister(@ModelAttribute User user, HttpServletResponse response, HttpSession session) {

		try
		{
			boolean isOk = userService.addUser(user);

			if ( isOk )
			{
				this.writeJson(response, "success");
				// 查询完整用户信息，保存session
				User queryUser = userService.queryUserInfo(user.getUsername());
				session.setAttribute("normalUser", queryUser);
				session.setMaxInactiveInterval(30 * 60);

			} else
			{
				this.writeJson(response, "failed");
			}

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);

		}

	}

	/**
	 * 用户登录
	 */
	@RequestMapping("/login")
	public void userLogin(@ModelAttribute User user, HttpServletResponse response, HttpSession session, String remember) {

		try
		{
			boolean isLogin = userService.userLogin(user);

			if ( isLogin )
			{
				this.writeJson(response, "success");
				// 查询完整用户信息，保存session
				User queryUser = userService.queryUserInfo(user.getUsername());
				session.setAttribute("normalUser", queryUser);
				session.setMaxInactiveInterval(30 * 60);
				// 是否保存cookie
				if ( "1".equals(remember) )
				{
					Cookie usernameCookie = new Cookie("username", user.getUsername());
					Cookie passwordCookie = new Cookie("password", user.getPassword());
					// 设置过期时间3天
					usernameCookie.setMaxAge(60 * 60 * 24 * 3);
					passwordCookie.setMaxAge(60 * 60 * 24 * 3);
					response.addCookie(usernameCookie);
					response.addCookie(passwordCookie);
				}
			} else
			{
				this.writeJson(response, "failed");
			}
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);

		}
	}

	/**
	 * 填写用户信息
	 */
	@RequestMapping("/addUserInfo")
	public ModelAndView addUserInfo(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "headImage", required = false) MultipartFile file, String nickname, String description) {

		try
		{

			String headImage = "";
			if ( !file.isEmpty() )
			{
				// 上传的文件不为空
				String path = request.getSession().getServletContext().getRealPath("upload/images");

				// 生成当前时间 加5位随机数的文件名
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

				String dateString = sdf.format(new Date());

				// 生成5位随机数
				Random random = new Random();
				Integer number = random.nextInt(99999 - 10000 + 1) + 10000;

				headImage = number + dateString + ".jpg";

				// 保存文件
				file.transferTo(new File(path, headImage));

			}

			User user = (User) session.getAttribute("normalUser");

			if ( user != null )
			{
				String username = user.getUsername();

				userService.addUserInfo(nickname, headImage, description, username);

				// 更新session中用户信息
				if ( StringUtils.isNotBlank(nickname) )
				{

					user.setNickname(nickname);
				}

				if ( StringUtils.isNotBlank(description) )
				{

					user.setDescription(description);
				}

				session.setAttribute("normalUser", user);

			}
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");

		}

		return new ModelAndView("index");

	}

	/**
	 * 用户注销
	 */
	@RequestMapping("/loginout")
	public ModelAndView loginout(HttpSession session) {

		session.removeAttribute("normalUser");
		return new ModelAndView("login");
	}

	/**
	 * 用户信息条件检索
	 */
	@RequestMapping("/searchUser")
	public void searchUser(String username, String nickname, String email, HttpServletResponse response, HttpSession session) {

		List<User> userList = null;

		List<UserVO> userVOList = new ArrayList<UserVO>();

		JSONArray jsonArray = null;

		try
		{
			// User实体结构嵌套复杂,无法转换成json，故转换成简单结构的VO进行转换
			userList = userService.queryUserByParam(username, nickname, email);

			User loginUser = (User) session.getAttribute("normalUser");

			String loginUsername = loginUser.getUsername();

			UserVO userVO = null;

			for ( User user : userList )
			{
				userVO = new UserVO();
				userVO.setCreateDate(user.getCreateDate());
				userVO.setDescription(user.getDescription());
				userVO.setEmail(user.getEmail());
				userVO.setHeadImage(user.getHeadImage());
				userVO.setLevel(user.getLevel());
				userVO.setNickname(user.getNickname());
				userVO.setPassword(user.getPassword());
				userVO.setUsername(user.getUsername());

				if ( user.getUsername().equals(loginUsername) )
				{
					userVO.setIsYourSelf(true);
				} else
				{
					userVO.setIsYourSelf(false);
				}

				userVOList.add(userVO);
			}

			jsonArray = JSONArray.fromObject(userVOList);

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);

		}

		this.writeJson(response, jsonArray.toString());

	}

	/**
	 * 好友申请
	 * 
	 * @param nickname 接收人用户名
	 */
	@RequestMapping("/addFriend")
	public void addFriend(HttpSession session, String nickname, HttpServletResponse response) {

		try
		{
			User sendUser = (User) session.getAttribute("normalUser");

			userMessageService.AddFriendMessage(sendUser, nickname);
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);

		} finally
		{
			this.writeJson(response, "ok");
		}

	}

	/**
	 * 用户发送消息
	 * 
	 * @param acceptId 接收人id
	 * @param content 消息内容
	 */
	@RequestMapping("/userSendMessage")
	public void userSendMessage(Integer acceptId, String content, HttpSession session, HttpServletResponse response) {

		try
		{
			User createBy = (User) session.getAttribute("normalUser");

			userMessageService.sendUserMessage(createBy, acceptId, content);

			this.writeJson(response, "success");
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			this.writeJson(response, "failed");
		}
	}

	/**
	 * 处理用户消息
	 * 
	 * @param createBy 消息发送人
	 * @param messageId 信息编号
	 * @param messageType 信息类型
	 * @param confirmState 信息确认状态
	 */
	@RequestMapping("/userMessageDeal")
	public void userMessageDeal(Integer createBy, Integer messageId, String messageType, HttpSession session, Integer confirmState, HttpServletResponse response) {

		try
		{
			// 判断消息类型
			if ( MessageType.ADD_FRIEND_MESSAGE_STR.getValue().equals(messageType) )
			{

				if ( confirmState == 1 )
				{

					User acceptBy = (User) session.getAttribute("normalUser");
					userService.passAddFriendRequest(createBy, acceptBy, messageId);
				} else
				{
					userMessageService.updateMessageState(messageId);
				}

			} else
			{
				userMessageService.updateMessageState(messageId);
			}

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
		} finally
		{
			// ajax请求必须有响应信息，否则算请求失败
			this.writeJson(response, "ok");
		}
	}

	/**
	 * 查看用户详细信息
	 * 
	 * @param userId 用户id
	 */
	@RequestMapping("/showUserInfo")
	public void showUserInfo(@RequestParam(value = "userId", required = true) Integer userId, HttpServletResponse response) {

		JSONObject jb = null;
		try
		{
			User user = userService.queryUserDetail(userId);

			// User对象属性嵌套复杂，故转换成VO再转JSON
			UserVO userVO = new UserVO();

			userVO.setId(user.getId());

			userVO.setUsername(user.getUsername());

			userVO.setNickname(user.getNickname());

			userVO.setLevel(user.getLevel());

			userVO.setDescription(user.getDescription());

			userVO.setHeadImage(user.getHeadImage());

			jb = JSONObject.fromObject(userVO);

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
		}

		this.writeJson(response, jb.toString());
	}

	/**
	 * 删除好友
	 * 
	 * @param friendId 删除的好友id
	 */
	@RequestMapping("/deleteFriend")
	public ModelAndView deleteFriend(@RequestParam(value = "friendId", required = true) Integer friendId, HttpSession session) {

		try
		{
			User user = (User) session.getAttribute("normalUser");

			userService.removeUserFriend(friendId, user.getId());

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("/error");
		}

		return new ModelAndView("redirect:/toMyFriends");

	}

}
