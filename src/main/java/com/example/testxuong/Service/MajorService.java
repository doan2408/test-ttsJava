package com.example.testxuong.Service;

import com.example.testxuong.entity.Major;
import com.example.testxuong.repository.MajorFacilityRepository;
import com.example.testxuong.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MajorService {
    private final MajorRepository mRepository;

    private final MajorFacilityRepository majorFacilityRepository;

    public List<Major> findAllMajor() {
        return mRepository.findAll();
    }

    public Major findMajorById(UUID id) {
        return mRepository.findById(id).orElse(null);
    }

    public void addMajor(Major major) {
        mRepository.save(major);
    }

    public void deleteMajor(UUID id) {
        mRepository.deleteById(id);
    }

    public List<Major> getMajorsByFacilityIdAndDepartmentId(UUID facilityId, UUID departmentId) {
        return majorFacilityRepository.findMajorsByFacilityIdAndDepartmentId(facilityId, departmentId);
    }


}
