package com.example.testxuong.Service;

import com.example.testxuong.entity.Facility;
import com.example.testxuong.repository.FacilityRepository;
import com.example.testxuong.repository.StaffMajorFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final StaffMajorFacilityRepository staffMajorFacilityRepository;

    public List<Facility> findAllFacilities() {
        return facilityRepository.findAll();
    }

    public Facility findFacilityById(UUID id) {
        return facilityRepository.findById(id).orElse(null);
    }

    public void addFacility(Facility facility) {
        facilityRepository.save(facility);
    }

    public void deleteFacility(UUID id) {
        facilityRepository.deleteById(id);
    }


    public List<Facility> getAvailableFacilitiesForStaff(UUID staffId) {
        List<UUID> used = staffMajorFacilityRepository.findFacilityIdsByStaffId(staffId);
        return used.isEmpty() ? facilityRepository.findAll() : facilityRepository.findAvailableFacilities(used);
    }

    public Facility getFacilityByName(String name) {
        return facilityRepository.findByName(name);
    }

}
