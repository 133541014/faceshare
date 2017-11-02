
package com.origen.faceshare.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.origen.faceshare.model.entity.Diary;
import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.model.enums.Pagination;
import com.origen.faceshare.repository.DiaryRepository;
import com.origen.faceshare.repository.UserRepository;
import com.origen.faceshare.service.DiaryService;
import com.origen.faceshare.utils.StringUtils;

@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private DiaryRepository diaryRepository;

	@Autowired
	private UserRepository userRepository;

	/**
	 * 写日记
	 */
	@Override
	public void addDiary(Diary diary, User user) {

		// 校验日记标题和内容
		if ( StringUtils.isBlank(diary.getTitle()) || StringUtils.isBlank(diary.getContent()) )
		{
			return;
		}

		User sendUser = userRepository.findOne(user.getId());

		diary.setCreateBy(sendUser);

		diary.setCreateDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

		diaryRepository.save(diary);

	}

	/**
	 * 获取用户所有日记信息
	 * 
	 * @param user
	 * @param PageNo 页码
	 * @return
	 */
	@Override
	public Page<Diary> getAllDiary(User user, Integer PageNo) {

		Pageable pageable = new PageRequest(PageNo, Pagination.PAGE_SIZE_DEFAULT.getSize());

		Page<Diary> page = diaryRepository.findByUserId(pageable, user.getId());

		return page;
	}

	@Override
	public Diary findById(Integer id) {

		Diary diary = diaryRepository.findOne(id);
		return diary;
	}

	@Override
	public void updateDiary(Diary diary) {

		Diary updateDiary = diaryRepository.findOne(diary.getId());

		if ( StringUtils.isNotBlank(diary.getTitle()) && StringUtils.isNotBlank(diary.getContent()) )
		{
			updateDiary.setTitle(diary.getTitle());
			updateDiary.setContent(diary.getContent());
			diaryRepository.save(updateDiary);
		}

	}

	@Override
	public void deleteDiary(Integer id) {

		diaryRepository.delete(id);

	}

}
