
package com.origen.faceshare.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.origen.faceshare.model.entity.Mood;
import com.origen.faceshare.model.entity.MoodComment;
import com.origen.faceshare.model.entity.MoodCommentReply;
import com.origen.faceshare.model.entity.MoodImage;
import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.model.enums.Pagination;
import com.origen.faceshare.repository.MoodCommentReplyRepository;
import com.origen.faceshare.repository.MoodCommentRepository;
import com.origen.faceshare.repository.MoodRepository;
import com.origen.faceshare.repository.UserRepository;
import com.origen.faceshare.service.MoodService;
import com.origen.faceshare.utils.StringUtils;

@Service
public class MoodServiceImpl implements MoodService {

	@Autowired
	private MoodRepository moodRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MoodCommentRepository moodCommentRepository;

	@Autowired
	private MoodCommentReplyRepository moodCommentReplyRepository;

	/**
	 * 保存用户心情
	 */
	@Override
	public void saveUserMood(Set<Mood> moods, Set<MoodImage> images) {

		for ( Mood mood : moods )
		{
			for ( MoodImage moodImage : images )
			{
				moodImage.setMood(mood);
			}
			mood.setImages(images);
		}

		moodRepository.save(moods);

	}

	@Override
	public Page<Mood> queryUserFriendMoods(Integer PageNo, Integer userId) {

		Pageable pageable = new PageRequest(PageNo, Pagination.PAGE_SIZE_MOOD.getSize());

		Page<Mood> page = moodRepository.findUserFriendMoods(pageable, userId);

		return page;
	}

	@Override
	public Page<Mood> queryUserMoods(Integer PageNo, Integer userId) {

		Pageable pageable = new PageRequest(PageNo, Pagination.PAGE_SIZE_MOOD.getSize());

		Page<Mood> page = moodRepository.findUserMoods(pageable, userId);

		return page;
	}

	@Override
	public void deleteMood(Integer moodId) {

		moodRepository.delete(moodId);

	}

	/**
	 * 添加心情评论
	 * 
	 * @param createBy 创建人
	 * @param content 评论内容
	 * @param moodId 心情id
	 */
	@Override
	public void addMoodComment(User createBy, String content, Integer moodId) {

		if ( moodId != null && StringUtils.isNotBlank(content) && createBy != null )
		{

			Mood mood = moodRepository.findById(moodId);
			// 防止session中保存的用户信息与数据库不符
			User user = userRepository.findById(createBy.getId());
			MoodComment comment = new MoodComment();
			comment.setContent(content);
			comment.setCreateBy(user);
			comment.setMoodId(mood);
			comment.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

			// 保存评论
			moodCommentRepository.save(comment);
		}

	}

	/**
	 * 回复评论
	 */
	@Override
	public void replyComment(Integer commentId, Integer acceptUserId, String content, User createBy) {

		if ( StringUtils.isNotBlank(content) )
		{
			User acceptBy = userRepository.findById(acceptUserId);
			MoodComment comment = moodCommentRepository.findOne(commentId);
			MoodCommentReply reply = new MoodCommentReply();
			reply.setContent(content);
			reply.setCreateBy(createBy);
			reply.setAcceptBy(acceptBy);
			reply.setMoodComment(comment);
			reply.setCreateDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			moodCommentReplyRepository.save(reply);
		}

	}

}
