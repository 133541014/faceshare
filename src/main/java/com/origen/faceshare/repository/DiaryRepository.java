
package com.origen.faceshare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.origen.faceshare.model.entity.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Integer> {

	@Query(value = "select d from Diary d where createBy.id = :userId order by createDate desc", countQuery = "select count(d) from Diary d where createBy.id = :userId")
	public Page<Diary> findByUserId(Pageable pageable, @Param("userId") Integer userId);

}
