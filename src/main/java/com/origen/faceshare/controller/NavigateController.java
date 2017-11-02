
package com.origen.faceshare.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.origen.faceshare.model.entity.Diary;
import com.origen.faceshare.model.entity.Mood;
import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.model.entity.UserMessage;
import com.origen.faceshare.service.DiaryService;
import com.origen.faceshare.service.MoodService;
import com.origen.faceshare.service.UserMessageService;
import com.origen.faceshare.service.UserService;
import com.origen.faceshare.utils.PageUtils;

/**
 * 页面跳转controller
 * 
 * @author: origen.wang
 * @date: 2017年7月27日
 */
@Controller
public class NavigateController extends BaseController {

	// 使用slf4j兼容spring boot内置logback进行日志记录
	private static Logger log = LoggerFactory.getLogger(NavigateController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private UserMessageService userMessageService;

	@Autowired
	private MoodService moodService;

	@Autowired
	private DiaryService diaryService;

	/**
	 * 访问首页
	 */
	@RequestMapping("/")
	public ModelAndView toLogin(HttpServletRequest request) {

		String username = "";
		String password = "";

		try
		{

			Cookie[] cookies = request.getCookies();

			for ( Cookie cookie : cookies )
			{
				if ( "username".equals(cookie.getName()) )
				{
					username = cookie.getValue();
				} else if ( "password".equals(cookie.getName()) )
				{
					password = cookie.getValue();
				}
			}

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");

		}

		return new ModelAndView("login")
						.addObject("username", username)
						.addObject("password", password);
	}

	/**
	 * 跳转到首页
	 */
	@RequestMapping("/toIndex")
	public ModelAndView toIndex(HttpServletRequest request) {

		return new ModelAndView("index");
	}

	/**
	 * 跳转个人信息设置页面
	 */
	@RequestMapping("/toAddUserInfo")
	public ModelAndView toAddUserInfo(HttpSession session) {

		User loginUser = null;
		try
		{

			User user = (User) session.getAttribute("normalUser");

			loginUser = userService.queryUserInfo(user.getUsername());
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("add_userinfo").addObject("loginUser", loginUser);
	}

	/**
	 * 跳转到心情分享页面
	 */
	@RequestMapping("/toFaceShare")
	public ModelAndView toFaceShare() {

		return new ModelAndView("mood_share");
	}

	/**
	 * 添加好友页面
	 */
	@RequestMapping("/toAddFriend")
	public ModelAndView toAddFriend() {

		return new ModelAndView("add_friend");
	}

	/**
	 * 跳转到用户消息界面
	 */
	@RequestMapping("/toUserMessage")
	public ModelAndView toUserMessage(Integer pageNumber, HttpSession session) {

		Page<UserMessage> page = null;
		// 使用分页工具类记录分页信息
		PageUtils<UserMessage> pageUtil = new PageUtils<UserMessage>();
		try
		{
			User user = (User) session.getAttribute("normalUser");

			int accpetBy = user.getId().intValue();

			if ( pageNumber == null || pageNumber < 0 )
			{
				pageNumber = 0;
			}

			page = userMessageService.queryUserMessage(accpetBy, pageNumber);

			pageUtil.setTotalPages(page.getTotalPages());

			pageUtil.setContent(page.getContent());

			pageUtil.setPageNo(pageNumber);

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");

		}

		return new ModelAndView("user_message").addObject("page", pageUtil);
	}

	/**
	 * 跳转到博客页面
	 */
	@RequestMapping("/toDiary")
	public ModelAndView toBlog() {

		return new ModelAndView("add_diary");
	}

	/**
	 * 跳转到好友界面
	 */
	@RequestMapping("/toMyFriends")
	public ModelAndView toMyFriends(HttpSession session, Integer pageNo) {

		PageUtils<User> pageUtils = new PageUtils<User>();
		try
		{
			if ( pageNo == null || pageNo < 0 )
			{
				pageNo = 0;
			}
			User loginUser = (User) session.getAttribute("normalUser");
			Page<User> page = userService.queryUserFriends(loginUser.getId(), pageNo);
			pageUtils.setContent(page.getContent());
			pageUtils.setTotalPages(page.getTotalPages());
			pageUtils.setPageNo(page.getNumber());

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("my_friends").addObject("page", pageUtils);
	}

	/**
	 * 好友动态页面
	 * 
	 * @param pageNo 页码
	 */
	@RequestMapping("/toFriendDynamic")
	public ModelAndView toFriendDynamic(HttpSession session, Integer pageNo) {

		PageUtils<Mood> pageUtils = new PageUtils<Mood>();
		try
		{
			if ( pageNo == null || pageNo < 0 )
			{
				pageNo = 0;
			}
			User loginUser = (User) session.getAttribute("normalUser");
			Page<Mood> page = moodService.queryUserFriendMoods(pageNo, loginUser.getId());
			pageUtils.setContent(page.getContent());
			pageUtils.setTotalPages(page.getTotalPages());
			pageUtils.setPageNo(page.getNumber());

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("friend_dynamic").addObject("page", pageUtils);

	}

	/**
	 * 我的动态页面
	 */
	@RequestMapping("/toMyDynamic")
	public ModelAndView toMyDynamic(HttpSession session, Integer pageNo) {

		PageUtils<Mood> pageUtils = new PageUtils<Mood>();
		try
		{
			if ( pageNo == null || pageNo < 0 )
			{
				pageNo = 0;
			}
			User loginUser = (User) session.getAttribute("normalUser");
			Page<Mood> page = moodService.queryUserMoods(pageNo, loginUser.getId());
			pageUtils.setContent(page.getContent());
			pageUtils.setTotalPages(page.getTotalPages());
			pageUtils.setPageNo(page.getNumber());

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("my_dynamic").addObject("page", pageUtils);

	}

	/**
	 * 跳转到我的日记页面
	 */
	@RequestMapping("/toMyDiary")
	public ModelAndView toMyDiary(HttpSession session, Integer pageNo) {

		PageUtils<Diary> pageUtils = new PageUtils<Diary>();
		try
		{
			if ( pageNo == null || pageNo < 0 )
			{
				pageNo = 0;
			}
			User loginUser = (User) session.getAttribute("normalUser");
			Page<Diary> page = diaryService.getAllDiary(loginUser, pageNo);
			List<Diary> list = page.getContent();
			for ( Diary diary : list )
			{
				diary.setContent(diary.getContent().substring(0, 40) + "...");
			}
			pageUtils.setContent(list);
			pageUtils.setTotalPages(page.getTotalPages());
			pageUtils.setPageNo(page.getNumber());

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("my_diary").addObject("page", pageUtils);
	}

}
