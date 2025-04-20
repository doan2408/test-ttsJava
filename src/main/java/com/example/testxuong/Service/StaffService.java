package com.example.testxuong.Service;

import com.example.testxuong.Dto.StaffDto;
import com.example.testxuong.entity.Major;
import com.example.testxuong.entity.Staff;
import com.example.testxuong.repository.MajorRepository;
import com.example.testxuong.repository.StaffRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final StaffRepository staffRepository;
    private final MajorRepository majorRepository;

    public List<Staff> findAllStaff() {
        return staffRepository.findAll();
    }

    public StaffDto findStaffByIdDto(UUID id) {
        Staff staff = staffRepository.findById(id).orElse(null);
        StaffDto staffDto = StaffDto.convertDto(staff);
        return staffDto;
    }

    public Staff findStaffById(UUID id) {
        return staffRepository.findById(id).orElse(null);
    }

    public void saveStaff(StaffDto staffDto) {
        Staff staff = new Staff();
        BeanUtils.copyProperties(staffDto, staff);
        staff.setId(UUID.randomUUID());
        staffRepository.save(staff);
    }

    public void updateStaff(UUID id, StaffDto staffDto) {
        Staff staff = findStaffById(id);
        BeanUtils.copyProperties(staffDto, staff);
        staffRepository.save(staff);
    }

    public void deleteStaffById(UUID Idstaff) {
        staffRepository.deleteById(Idstaff);
    }

    public String checkUnique(String staffCode, String accountFpt, String accountFe) {
        if (staffRepository.findByStaffCode(staffCode).isPresent()) {
            return "staff";
        }
        if (staffRepository.findByAccountFpt(accountFpt).isPresent()) {
            return "fpt";
        }
        if (staffRepository.findByAccountFe(accountFe).isPresent()) {
            return "fe";
        }
        return "valid";
    }

    public String checkUpdateConflict(UUID id, String staffCode, String accountFpt, String accountFe) {
        Optional<Staff> existingByCode = staffRepository.findByStaffCode(staffCode);
        if (existingByCode.isPresent() && !existingByCode.get().getId().equals(id)) {
            return "staff";
        }

        Optional<Staff> existingByFpt = staffRepository.findByAccountFpt(accountFpt);
        if (existingByFpt.isPresent() && !existingByFpt.get().getId().equals(id)) {
            return "fpt";
        }

        Optional<Staff> existingByFe = staffRepository.findByAccountFe(accountFe);
        if (existingByFe.isPresent() && !existingByFe.get().getId().equals(id)) {
            return "fe";
        }

        return "valid";
    }

    public void toggleStaffStatus(UUID id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        staff.setStatus(!staff.getStatus());
        staffRepository.save(staff);
    }


}
