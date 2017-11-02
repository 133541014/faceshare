
package com.origen.faceshare.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.origen.faceshare.model.entity.User;

/**
 * 用户操作持久层
 * JpaSpecificationExecutor:动态查询接口
 * 
 * @author origen.wang
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User>, PagingAndSortingRepository<User, Integer> {

	/**
	 * 检验用户名和邮箱是否存在
	 */
	@Query(value = "select u from User u where u.username = :username or u.email = :email")
	public List<User> queryUserByParam(@Param("username") String username, @Param("email") String email);

	/**
	 * 用户登录
	 */
	@Query(value = "select u from User u where (u.username = :username and u.password = :password) or (u.email = :username and u.password = :password)")
	public List<User> userLogin(@Param("username") String username, @Param("password") String password);

	/**
	 * 根据用户名查询用户
	 */
	public List<User> findByUsername(String username);

	/**
	 * 根据用户名或邮箱
	 */
	public List<User> findByEmail(String email);

	/**
	 * 根据昵称查询用户
	 */
	public List<User> findByNickname(String nickname);

	/**
	 * 根据用户编号查询用户
	 */
	public User findById(Integer id);

	/**
	 * 查询用户的好友列表
	 */
	@Query(value = "select f from User u join u.friends f where u.id = :id", countQuery = "select count(f.id) from User u join u.friends f where u.id = :id")
	public Page<User> findUserFriends(Pageable pageable, @Param("id") Integer id);

}
