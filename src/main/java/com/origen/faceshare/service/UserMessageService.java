
package com.origen.faceshare.service;

import org.springframework.data.domain.Page;

import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.model.entity.UserMessage;

public interface UserMessageService {

	/**
	 * 发送好友申请消息
	 * 
	 * @param content 消息内容
	 * @param sendUser 信息发送人
	 * @param username 信息接收人
	 */
	public void AddFriendMessage(User sendUser, String username);

	/**
	 * 查看消息
	 */
	public Page<UserMessage> queryUserMessage(Integer acceptBy, Integer pageNumber);

	/**
	 * 修改信息状态
	 */
	public void updateMessageState(Integer messageId);

	/**
	 * 发送普通好友消息
	 */
	public void sendUserMessage(User createBy, Integer acceptId, String content);

	/**
	 * 发送用户评论消息通知
	 */
	public void sendUserCommentNotice(User createBy, Integer moodId, String content);
}
