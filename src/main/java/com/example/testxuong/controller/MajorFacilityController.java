package com.example.testxuong.controller;


import com.example.testxuong.Dto.DepartmentDto;
import com.example.testxuong.Dto.StaffDto;
import com.example.testxuong.Service.*;
import com.example.testxuong.entity.*;
import com.example.testxuong.repository.DepartmentFacilityRepository;
import com.example.testxuong.repository.StaffMajorFacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/major")
public class MajorFacilityController {
    private final StaffService staffService;
    private final FacilityService facilityService;
    private final MajorService majorService;
    private final DepartmentService departmentService;
    private final StaffMajorFacilityService staffMajorFacilityService;
    private final StaffMajorFacilityRepository staffMajorFacilityRepository;
    private final DepartmentFacilityService departmentFacilityService;
    private final MajorFacilityService majorFacilityService;

    @GetMapping("/infoStaff/{id}")
    public String infoStaff(Model model, @PathVariable UUID id) {
        StaffDto staff = staffService.findStaffByIdDto(id);
        model.addAttribute("s", staff);
        model.addAttribute("smf", staffMajorFacilityRepository.getFacilityAndDepartmentAndMajorByStaffId(id));

        return "major";
    }

    @GetMapping("/view-themMajor/{id}")
    public String viewThemMajor(@PathVariable UUID id, Model model) {
        List<Facility> facilities = facilityService.getAvailableFacilitiesForStaff(id);
        model.addAttribute("facilities", facilities);
        model.addAttribute("idstaff", id);
        return "them-department-major";
    }

    @PostMapping("/themFacilityDepartmentMajor")
    public String themMajor(
            @RequestParam("facilityId") UUID facilityId,
            @RequestParam("departmentId") UUID departmentId,
            @RequestParam("majorId") UUID majorId,
            @RequestParam("staffId") UUID staffId
    ) {

        staffMajorFacilityService.addStaffMajorFacility(staffId, facilityId, departmentId, majorId);
        return "redirect:/major/infoStaff/" + staffId;

    }

    @GetMapping("/departments")
    @ResponseBody
    public List<DepartmentDto> getDepartments(@RequestParam("facilityId") UUID facilityId) {
        List<Department> deps = departmentService.getDepartmentsByFacility(facilityId);
        return deps.stream().map(d -> new DepartmentDto(d.getId(), d.getName())).collect(Collectors.toList());
    }


    @GetMapping("/deleteMajor/{majorName}/{departmentName}/{facilityName}/{staffId}")
    public String deleteMajor(@PathVariable("majorName") String mName,
                              @PathVariable("departmentName") String dName,
                              @PathVariable("facilityName") String fName,
                              @PathVariable("staffId") UUID staffid
    ) {
        UUID idMajor = majorService.getMajorByName(mName).getId();
        UUID idDepartment = departmentService.getDepartmentByName(dName).getId();
        UUID idFacility = facilityService.getFacilityByName(fName).getId();
        System.out.println("idFacility: "+ idFacility );

        UUID idDepartmentFacility = departmentFacilityService.getDmfByFidAndDidAndSid(idFacility, idDepartment, staffid).getId();
        System.out.println("idDepartmentFacility: " + idDepartmentFacility);

        UUID idMajorFacility = majorFacilityService.getMajorFacilityByIdMajorAndIdDmf(idMajor, idDepartmentFacility).getId();

        System.out.println("major facility: " +idMajorFacility);

        UUID idStaffMajorFacility = staffMajorFacilityService.getSmfBySidAndMfId(staffid, idMajorFacility).getId();

        staffMajorFacilityService.deleteStaffMajorFacilityById(idStaffMajorFacility);
        majorFacilityService.deleteMajorFacilityById(idMajorFacility);
        departmentFacilityService.delteDepartmentFacilities(idDepartmentFacility);

        return "redirect:/major/infoStaff/" + staffid;

    }


//    public List<Department> getDepartments(@RequestParam("facilityId") UUID facilityId) {
//        List<Department> departments = departmentService.getDepartmentsByFacility(facilityId);
//        System.out.println("Có " + departments.size() + " bộ môn");
//        return departments;
//    }


//    public List<Department> getDepartments(@RequestParam("facilityId") UUID facilityId) {
//        return departmentService.getDepartmentsByFacility(facilityId);
//    }

    @GetMapping("/majors")
    @ResponseBody
    public List<Major> getMajors(@RequestParam("facilityId") UUID facilityId,
                                 @RequestParam("departmentId") UUID departmentId) {
        return majorService.getMajorsByFacilityIdAndDepartmentId(facilityId, departmentId);
    }

//    public boolean isStaffAssignedToMajor(UUID staffId, UUID facilityId) {
    // Kiểm tra xem nhân viên đã có bộ môn chuyên ngành nào trong cơ sở này chưa
//        List<DepartmentFacility> existingAssignments = departmentFacilityRepository.findByStaffIdAndFacilityId(staffId, facilityId);
//        return !existingAssignments.isEmpty();  // Nếu có ít nhất một kết quả thì nhân viên đã được gán
//    }

//    public void addMajorForStaff(UUID staffId, UUID facilityId, UUID departmentId, UUID majorId) {
//        // Kiểm tra nếu nhân viên đã có bộ môn chuyên ngành trong cơ sở này
//        if (isStaffAssignedToMajor(staffId, facilityId)) {
//            throw new IllegalStateException("Nhân viên này đã có bộ môn chuyên ngành trong cơ sở này.");
//        }
//
//        // Nếu chưa có, tiến hành thêm bộ môn chuyên ngành mới
//        DepartmentFacility departmentFacility = new DepartmentFacility();
//
//        departmentFacilityRepository.save(departmentFacility);
//    }


}
