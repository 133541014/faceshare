
package com.origen.faceshare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.origen.faceshare.model.entity.UserMessage;

public interface UserMessageRepository extends JpaRepository<UserMessage, Integer>, PagingAndSortingRepository<UserMessage, Integer> {

	/**
	 * 查询用户下所有消息
	 * 
	 * @param Pageable分页信息
	 */
	@Query(value = "select m from UserMessage m where m.acceptBy.id = :acceptBy order by m.state asc,m.messageType asc", countQuery = "select count(m.id) from UserMessage m where m.acceptBy.id = :acceptBy")
	public Page<UserMessage> findUserMessage(@Param("acceptBy") Integer acceptBy, Pageable pageable);

	/**
	 * 查询用户下消息数量
	 */
	@Query(value = "select count(id) from t_user_message where accept_by = :acceptBy and state = '0'", nativeQuery = true)
	public int findUserMessageCount(@Param("acceptBy") Integer acceptBy);

	/**
	 * 修改信息状态
	 */
	@Modifying
	@Query(value = "update t_user_message set state = '1' where id = :messageId", nativeQuery = true)
	public void changeMessageState(@Param("messageId") Integer messageId);

}
