package com.example.testxuong.repository;

import com.example.testxuong.Dto.FacilityDepartmentMajorDto;
import com.example.testxuong.entity.StaffMajorFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StaffMajorFacilityRepository extends JpaRepository<StaffMajorFacility, UUID> {

    boolean existsByStaffIdAndMajorFacilityId(UUID staffId, UUID majorFacilityId);

    @Query("SELECT mf.departmentFacility.facility.id " +
            "FROM StaffMajorFacility smf " +
            "JOIN smf.majorFacility mf " +
            "WHERE smf.staff.id = :staffId")
    List<UUID> findFacilityIdsByStaffId(@Param("staffId") UUID staffId);


    //bảng major
    @Query(value = "SELECT f.name AS facilityName, d.name AS departmentName, m.name AS majorName " +
            "FROM staff_major_facility smf " +
            "JOIN staff s ON smf.id_staff = s.id " +
            "JOIN major_facility mf ON smf.id_major_facility = mf.id " +
            "JOIN major m ON mf.id_major = m.id " +
            "JOIN department_facility dmf ON mf.id_department_facility = dmf.id " +
            "JOIN department d ON dmf.id_department = d.id " +
            "JOIN facility f ON dmf.id_facility = f.id " +
            "WHERE s.id = ?1", nativeQuery = true)
    List<FacilityDepartmentMajorDto> getFacilityAndDepartmentAndMajorByStaffId(UUID staffId);


    @Query("SELECT COUNT(smf) > 0 FROM StaffMajorFacility smf " +
            "WHERE smf.staff.id = :staffId " +
            "AND smf.majorFacility.departmentFacility.facility.id = :facilityId")
    boolean existsByStaffAndFacility(@Param("staffId") UUID staffId,
                                     @Param("facilityId") UUID facilityId);


    StaffMajorFacility findByStaffIdAndMajorFacilityId(UUID staffId, UUID majorFacilityId);

}
