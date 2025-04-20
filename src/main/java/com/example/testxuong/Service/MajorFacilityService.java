package com.example.testxuong.Service;

import com.example.testxuong.entity.MajorFacility;
import com.example.testxuong.repository.MajorFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MajorFacilityService {
    private final MajorFacilityRepository majorFacilityRepository;

    public List<MajorFacility> findAllMajorFacilities() {
        return majorFacilityRepository.findAll();
    }

    public void addMajorFacility(MajorFacility majorFacility) {
        majorFacilityRepository.save(majorFacility);
    }

    public MajorFacility getMajorFacilityById(UUID id) {
        return majorFacilityRepository.findById(id).orElse(null);
    }

    public void deleteMajorFacilityById(UUID id) {
        majorFacilityRepository.deleteById(id);
    }
}
