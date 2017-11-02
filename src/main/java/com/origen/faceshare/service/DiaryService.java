
package com.origen.faceshare.service;

import org.springframework.data.domain.Page;

import com.origen.faceshare.model.entity.Diary;
import com.origen.faceshare.model.entity.User;

public interface DiaryService {

	public void addDiary(Diary diary, User user);

	public Page<Diary> getAllDiary(User user, Integer pageNo);

	public Diary findById(Integer id);

	public void updateDiary(Diary diary);

	public void deleteDiary(Integer id);
}
