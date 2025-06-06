package com.example.testxuong.repository;

import com.example.testxuong.entity.Major;
import com.example.testxuong.entity.MajorFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MajorFacilityRepository extends JpaRepository<MajorFacility, UUID> {

    List<MajorFacility> findByDepartmentFacilityId(UUID facilityId);

    @Query("select mf.major from MajorFacility mf where mf.departmentFacility.facility.id = :facilityId " +
            "and mf.departmentFacility.department.id = :departmentId")
    public List<Major>findMajorsByFacilityIdAndDepartmentId(UUID facilityId, UUID departmentId);



    @Query("select mf from MajorFacility mf " +
            "where mf.major.id = :idMajor and mf.departmentFacility.id = :idDmf")
    MajorFacility getMajorFacilityByIdMajorAndIdDmf(@Param("idMajor") UUID idMajor, @Param("idDmf") UUID idDmf);
}
