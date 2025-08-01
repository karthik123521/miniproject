package com.project.login.EntityRepo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.login.Entity.LoginEntity;

@Repository
public interface LoginEntityRepo extends JpaRepository<LoginEntity, Integer>{

	@Query(value = "SELECT * FROM login_entity WHERE email_id = :email", nativeQuery = true)
	Optional<LoginEntity> findByEmail(@Param("email") String email);

}
