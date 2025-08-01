package com.ar.repository;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ar.entity.CitizenApiEntity;

@Repository
public interface CitizenAppRepository extends JpaRepository<CitizenApiEntity, Integer> {

	@Query(value = "SELECT dob FROM citizen_api_entity WHERE app_id = :id", nativeQuery = true)
    public LocalDate findDobById(@Param("id")Integer id);
}

