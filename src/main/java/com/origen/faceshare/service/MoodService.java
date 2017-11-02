
package com.origen.faceshare.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.origen.faceshare.model.entity.Mood;
import com.origen.faceshare.model.entity.MoodImage;
import com.origen.faceshare.model.entity.User;

public interface MoodService {

	/**
	 * 保存用户心情
	 */
	public void saveUserMood(Set<Mood> moods, Set<MoodImage> images);

	/**
	 * 查询用户好友的所有心情
	 * 
	 */
	public Page<Mood> queryUserFriendMoods(Integer PageNo, Integer userId);

	/**
	 * 查询我的所有心情
	 */
	public Page<Mood> queryUserMoods(Integer PageNo, Integer userId);

	/**
	 * 删除心情
	 */
	public void deleteMood(Integer moodId);

	/**
	 * 添加心情评论
	 */
	public void addMoodComment(User createBy, String content, Integer moodId);

	/**
	 * 回复评论
	 */
	public void replyComment(Integer commentId, Integer acceptUserId, String content, User createBy);
}
