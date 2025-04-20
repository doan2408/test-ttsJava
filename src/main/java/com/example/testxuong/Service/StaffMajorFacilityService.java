package com.example.testxuong.Service;

import com.example.testxuong.Dto.FacilityDepartmentMajorDto;
import com.example.testxuong.entity.*;
import com.example.testxuong.repository.DepartmentFacilityRepository;
import com.example.testxuong.repository.MajorFacilityRepository;
import com.example.testxuong.repository.StaffMajorFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffMajorFacilityService {
    private final StaffMajorFacilityRepository staffMajorFacilityRepository;
    private final DepartmentFacilityRepository departmentFacilityRepository;
    private final MajorFacilityRepository majorFacilityRepository;

    public List<StaffMajorFacility> findAllStaffMajorFacility() {
        return staffMajorFacilityRepository.findAll();
    }

    public void addStaffMajorFacility(StaffMajorFacility stf) {
        staffMajorFacilityRepository.save(stf);
    }

    public StaffMajorFacility findStaffMajorFacilityById(UUID id) {
        return staffMajorFacilityRepository.findById(id).orElse(null);
    }

    public void deleteStaffMajorFacilityById(UUID id) {
        staffMajorFacilityRepository.deleteById(id);
    }


    public List<FacilityDepartmentMajorDto> findAllStaffMajorFacilityByFacilityId(UUID staffId) {
        return staffMajorFacilityRepository.getFacilityAndDepartmentAndMajorByStaffId(staffId);
    }

    public void addStaffMajorFacility(UUID staffId, UUID facilityId, UUID departmentId, UUID majorId) {
        // Check nếu staff đã có chuyên ngành ở cơ sở này
        boolean exists = staffMajorFacilityRepository.existsByStaffAndFacility(staffId, facilityId);
        if (exists) {
            throw new IllegalArgumentException("Nhân viên đã có chuyên ngành ở cơ sở này!");
        }

        DepartmentFacility departmentFacility = new DepartmentFacility();
        Facility facility = new Facility();
        facility.setId(facilityId);
        Department department = new Department();
        department.setId(departmentId);
        Staff staff = new Staff();
        staff.setId(staffId);

        departmentFacility.setId(UUID.randomUUID());
        departmentFacility.setDepartment(department);
        departmentFacility.setFacility(facility);
        departmentFacility.setStaff(staff);
        departmentFacilityRepository.save(departmentFacility);


        MajorFacility majorFacility = new MajorFacility();
        Major major = new Major();
        major.setId(majorId);
        majorFacility.setId(UUID.randomUUID());
        majorFacility.setMajor(major);
        majorFacility.setDepartmentFacility(departmentFacility);
        majorFacilityRepository.save(majorFacility);


        StaffMajorFacility stf = new StaffMajorFacility();
        stf.setId(UUID.randomUUID());
        stf.setMajorFacility(majorFacility);
        stf.setStaff(staff);
        staffMajorFacilityRepository.save(stf);
    }
}


