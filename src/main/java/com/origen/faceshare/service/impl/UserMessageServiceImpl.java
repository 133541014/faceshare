
package com.origen.faceshare.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.origen.faceshare.model.entity.Mood;
import com.origen.faceshare.model.entity.User;
import com.origen.faceshare.model.entity.UserMessage;
import com.origen.faceshare.model.enums.MessageState;
import com.origen.faceshare.model.enums.MessageType;
import com.origen.faceshare.model.enums.Pagination;
import com.origen.faceshare.repository.MoodRepository;
import com.origen.faceshare.repository.UserMessageRepository;
import com.origen.faceshare.repository.UserRepository;
import com.origen.faceshare.service.UserMessageService;
import com.origen.faceshare.utils.StringUtils;

@Service
public class UserMessageServiceImpl implements UserMessageService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMessageRepository userMessageRepository;

	@Autowired
	private MoodRepository moodRepository;

	/**
	 * 好友申请信息
	 * 
	 * @param content 消息内容
	 * @param sendUser 信息发送人
	 * @param nickname 信息接收人
	 */
	@Override
	public void AddFriendMessage(User sendUser, String nickname) {

		User acceptUser = userRepository.findByNickname(nickname).get(0);

		UserMessage userMessage = new UserMessage();

		String content = null;

		if ( StringUtils.isBlank(sendUser.getNickname()) )
		{
			content = sendUser.getUsername() + " 用户申请加你为好友";
		} else
		{
			content = sendUser.getNickname() + " 用户申请加你为好友";
		}

		userMessage.setContent(content);

		userMessage.setCreateBy(sendUser);

		userMessage.setAcceptBy(acceptUser);

		userMessage.setMessageType(MessageType.ADD_FRIEND_MESSAGE.getValue());

		userMessage.setState(MessageState.NOT_READ.getState());

		userMessage.setCreateDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

		userMessageRepository.save(userMessage);

	}

	/**
	 * 分页查询用户消息
	 * 
	 * @param pageNumer页码(从0开始)
	 */
	@Override
	public Page<UserMessage> queryUserMessage(Integer acceptBy, Integer pageNumber) {

		Pageable pageable = new PageRequest(pageNumber, Pagination.PAGE_SIZE_DEFAULT.getSize());

		Page<UserMessage> pageInfo = userMessageRepository.findUserMessage(acceptBy, pageable);

		return pageInfo;
	}

	@Override
	public void updateMessageState(Integer messageId) {

		userMessageRepository.changeMessageState(messageId);
	}

	@Override
	public void sendUserMessage(User createBy, Integer acceptId, String content) {

		if ( StringUtils.isNotBlank(content) && createBy != null && acceptId != null )
		{

			User accpetBy = userRepository.findById(acceptId);

			UserMessage message = new UserMessage();

			message.setCreateBy(createBy);

			message.setAcceptBy(accpetBy);

			message.setContent(content);

			message.setMessageType(MessageType.USER_MESSAGE.getValue());

			message.setState(MessageState.NOT_READ.getState());

			message.setCreateDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

			userMessageRepository.save(message);
		}

	}

	/**
	 * 向用户发送心情评论提醒
	 */
	@Override
	public void sendUserCommentNotice(User createBy, Integer moodId, String content) {

		// 查询消息接收人
		Mood mood = moodRepository.findOne(moodId);
		User acceptBy = mood.getUser();

		UserMessage message = new UserMessage();
		message.setCreateBy(createBy);
		message.setAcceptBy(acceptBy);
		StringBuilder messageContent = new StringBuilder();

		String name = null;
		if ( createBy.getNickname() == null )
		{
			name = createBy.getUsername();
		} else
		{
			name = createBy.getNickname();
		}
		messageContent.append(name);
		messageContent.append(" ");
		messageContent.append("评论了你的心情");
		messageContent.append("\"");
		messageContent.append(mood.getContent().substring(0, 10));
		messageContent.append("...");
		messageContent.append("\"");
		messageContent.append(" ");
		messageContent.append(":");
		messageContent.append(content);
		message.setContent(messageContent.toString());
		message.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		message.setMessageType(MessageType.SYSTEM_MESSAGE.getValue());
		message.setState(MessageState.NOT_READ.getState());

		userMessageRepository.save(message);

	}

}
