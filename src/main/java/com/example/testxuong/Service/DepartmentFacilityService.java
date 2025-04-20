package com.example.testxuong.Service;

import com.example.testxuong.entity.Department;
import com.example.testxuong.entity.DepartmentFacility;
import com.example.testxuong.entity.Facility;
import com.example.testxuong.repository.DepartmentFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentFacilityService {
    private final DepartmentFacilityRepository departmentFacilityRepository;

    public List<DepartmentFacility> findAllDepartmentFacilities() {
        return departmentFacilityRepository.findAll();
    }

    public DepartmentFacility findDepartmentFacilityById(UUID id) {
        return departmentFacilityRepository.findById(id).orElse(null);
    }

    public void addDepartmentFacilities(DepartmentFacility departmentFacility) {
        departmentFacilityRepository.save(departmentFacility);
    }

    public void delteDepartmentFacilities(UUID id) {
        departmentFacilityRepository.deleteById(id);
    }

    public void addDEpartmentFacility(UUID facilityId, UUID departmentId) {
        DepartmentFacility departmentFacility = new DepartmentFacility();
        Facility facility = new Facility();
        facility.setId(facilityId);
        Department department = new Department();
        department.setId(departmentId);
        departmentFacility.setId(UUID.randomUUID());
        departmentFacility.setFacility(facility);
        departmentFacility.setDepartment(department);

        departmentFacilityRepository.save(departmentFacility);
    }

    public void deleteDepartmentFacility() {}
}
