
package com.origen.faceshare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.origen.faceshare.model.entity.Mood;

public interface MoodRepository extends JpaRepository<Mood, Integer> {

	/**
	 * 查询用户好友的所有心情
	 * 
	 * @param id 用户id
	 */
	@Query(value = "select m from Mood m where m.user.id in (select u.id from User u join u.users s where s.id = :id) order by m.createDate desc", countQuery = "select count(m.id) from Mood m where m.user.id in (select u.id from User u join u.users s where s.id = :id)")
	Page<Mood> findUserFriendMoods(Pageable pageable, @Param("id") Integer id);

	/**
	 * 查询我的所有心情
	 * 
	 * @param id 用户id
	 */
	@Query(value = "select m from Mood m where m.user.id = :id order by m.createDate desc", countQuery = "select count(m.id) from Mood m where m.user.id = :id")
	Page<Mood> findUserMoods(Pageable pageable, @Param("id") Integer id);

	Mood findById(Integer id);

}
