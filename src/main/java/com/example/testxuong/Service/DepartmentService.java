package com.example.testxuong.Service;

import com.example.testxuong.entity.Department;
import com.example.testxuong.repository.DepartmentFacilityRepository;
import com.example.testxuong.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentFacilityRepository departmentFacilityRepository;

    public List<Department> findAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department findDepartmentById(UUID id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public void addDepartment(Department department) {
        departmentRepository.save(department);
    }

    public void deleteDepartment(UUID id) {
        departmentRepository.deleteById(id);
    }

    public List<Department> getDepartmentsByFacility(UUID facilityId) {
        return departmentFacilityRepository.findDepartmentByFacilityId(facilityId);
    }

}
