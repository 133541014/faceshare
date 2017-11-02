
package com.origen.faceshare.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.origen.faceshare.model.entity.Mood;
import com.origen.faceshare.model.entity.MoodImage;
import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.service.MoodService;
import com.origen.faceshare.service.UserMessageService;
import com.origen.faceshare.utils.StringUtils;

/**
 * 心情相关控制器
 * 
 * @author: origen.wang
 * @date: 2017年8月24日
 */
@Controller
public class MoodsController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(MoodsController.class);

	@Autowired
	private MoodService moodService;

	@Autowired
	private UserMessageService messageService;

	/**
	 * 发布心情
	 */
	@RequestMapping("/moodShare")
	public ModelAndView moodShare(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam("files[]") MultipartFile[] files, String content) {

		User user = null;
		Set<MoodImage> images = null;
		Set<Mood> moods = null;

		try
		{

			if ( StringUtils.isNotBlank(content) )
			{

				user = (User) session.getAttribute("normalUser");
				images = new HashSet<MoodImage>();
				moods = new HashSet<Mood>();

				// 上传图片
				if ( !files[0].isEmpty() )
				{
					// 取出数组file文件
					for ( int i = 0; i < files.length; i++ )
					{
						MultipartFile file = files[i];

						String path = request.getSession().getServletContext().getRealPath("upload/images");
						String headImage = "";

						// 生成当前时间 加5位随机数的文件名
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

						String dateString = sdf.format(new Date());

						// 生成5位随机数
						Random random = new Random();
						Integer number = random.nextInt(99999 - 10000 + 1) + 10000;

						headImage = number + dateString + ".jpg";

						file.transferTo(new File(path, headImage));

						MoodImage moodImage = new MoodImage();

						moodImage.setImageUrl(headImage);

						moodImage.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

						images.add(moodImage);
					}

				}

				Mood mood = new Mood();

				mood.setUser(user);

				mood.setContent(content);

				// 保存心情发布的具体时间，用于计算心情发布具体时间
				mood.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

				moods.add(mood);

				// 保存用户心情
				moodService.saveUserMood(moods, images);

			}

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");

		}

		return new ModelAndView("index");

	}

	@RequestMapping("/deleteMood")
	public ModelAndView deleteMood(@RequestParam(name = "moodId", required = true) Integer moodId) {

		try
		{
			moodService.deleteMood(moodId);
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("redirect:/toMyDynamic");

	}

	@RequestMapping("/commentMood")
	public ModelAndView commentMood(@RequestParam(name = "moodId", required = true) Integer moodId, @RequestParam(name = "content", required = true) String content, HttpSession session) {

		try
		{
			User createBy = (User) session.getAttribute("normalUser");

			moodService.addMoodComment(createBy, content, moodId);

			messageService.sendUserCommentNotice(createBy, moodId, content);

			return new ModelAndView("redirect:/toFriendDynamic");

		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}
	}

	@RequestMapping("/replyComment")
	public ModelAndView replyComment(Integer commentId, Integer acceptUserId, String content, HttpSession session, String replyType) {

		try
		{
			User createBy = (User) session.getAttribute("normalUser");
			moodService.replyComment(commentId, acceptUserId, content, createBy);
			if ( "comment".equals(replyType) )
			{

				return new ModelAndView("redirect:/toMyDynamic");
			} else
			{
				return new ModelAndView("redirect:/toFriendDynamic");
			}
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}
	}

}
