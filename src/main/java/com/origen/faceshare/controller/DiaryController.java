
package com.origen.faceshare.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.origen.faceshare.model.entity.Diary;
import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.service.DiaryService;

@Controller
public class DiaryController extends BaseController {

	private static Logger log = LoggerFactory.getLogger(DiaryController.class);

	@Autowired
	private DiaryService diaryService;

	/**
	 * 发日记
	 * 
	 * @param diary
	 * @param session
	 * @return
	 */
	@RequestMapping("/sendDiary")
	public ModelAndView sendDiary(@ModelAttribute Diary diary, HttpSession session) {

		try
		{
			User user = (User) session.getAttribute("normalUser");
			diaryService.addDiary(diary, user);
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("redirect:/toMyDiary");
	}

	/**
	 * 查看日记详情
	 */
	@RequestMapping("/diaryDetail")
	public ModelAndView diaryDetail(@RequestParam(name = "id", required = true) Integer id) {

		Diary diary = null;
		try
		{
			diary = diaryService.findById(id);
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("diary_detail").addObject("diary", diary);
	}

	/**
	 * 修改日记
	 */
	@RequestMapping("/updateDiary")
	public ModelAndView updateDiary(Diary diary) {

		try
		{
			diaryService.updateDiary(diary);
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("redirect:/toMyDiary");
	}

	/**
	 * 删除日记
	 */
	@RequestMapping("/deleteDiary")
	public ModelAndView deleteDiary(@RequestParam("id") Integer id) {

		try
		{
			diaryService.deleteDiary(id);
		}
		catch ( Exception e )
		{
			log.error(e.getMessage(), e);
			return new ModelAndView("error");
		}

		return new ModelAndView("redirect:/toMyDiary");
	}

}
