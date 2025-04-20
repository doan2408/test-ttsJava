package com.example.testxuong.repository;

import com.example.testxuong.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, UUID> {

    @Query("SELECT f FROM Facility f WHERE f.id NOT IN :excludedIds")
    List<Facility> findAvailableFacilities(@Param("excludedIds") List<UUID> excludedIds);

}
