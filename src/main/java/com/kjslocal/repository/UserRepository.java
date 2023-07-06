package com.kjslocal.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kjslocal.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	@Modifying
	@Transactional
	@Query(value = "UPDATE TBL_USER SET FIRST_NAME = :fname, LAST_NAME = :lname, "
			+ "EMAIL = :email WHERE USER_ID = :id", nativeQuery = true)
	int update(@Param("fname") String fname, @Param("lname") String lname, @Param("email") String email,
			@Param("id") int id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM TBL_USER WHERE USER_ID = :id", nativeQuery = true)
	int delete(@Param("id") int id);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM TBL_USER WHERE USER_ID IN (:ids)", nativeQuery = true)
	void deleteMultiple(@Param("ids") List<Integer> ids);

}
