package com.example.testxuong.repository;

import com.example.testxuong.entity.Department;
import com.example.testxuong.entity.DepartmentFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentFacilityRepository extends JpaRepository<DepartmentFacility, UUID> {

    List<DepartmentFacility> findByFacilityId(UUID facilityId);

    @Query("SELECT dmf.department FROM DepartmentFacility dmf WHERE dmf.facility.id = :facilityId")
    List<Department> findDepartmentByFacilityId(UUID facilityId);


    DepartmentFacility findByFacilityIdAndDepartmentId(UUID facilityId, UUID departmentId);


}
