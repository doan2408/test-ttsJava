package com.example.testxuong.controller;

import com.example.testxuong.Dto.StaffDto;
import com.example.testxuong.Service.StaffService;
import com.example.testxuong.entity.Staff;
import com.example.testxuong.repository.StaffRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class NhanVienController {
    private final StaffService staffService;
    private final StaffRepository staffRepository;

    @ModelAttribute("staffList")
    public List<Staff> getStaffList() {
        return staffService.findAllStaff();
    }

    @GetMapping("/hien-thi")
    public String hienThi(Model model) {
        List<Staff> listStaff = staffService.findAllStaff();
        model.addAttribute("staffList", listStaff);
        return "nhan-vien";
    }


    @GetMapping("/view-add")
    public String viewAddStaff(@ModelAttribute("staffListDto") StaffDto staffDto) {
        return "them-nhan-vien";
    }

    @PostMapping("/addStaff")
    public String addStaff(Model model,
                           @ModelAttribute("staffListDto") StaffDto staffDto,
                           @RequestParam("staffCode") String staffCode,
                           @RequestParam("accountFpt") String accountFpt,
                           @RequestParam("accountFe") String accountFe) {
        String message = staffService.checkUnique(staffCode, accountFpt, accountFe);

        if (message.equals("valid")) {
            staffService.saveStaff(staffDto);
            return "redirect:/staff/hien-thi";
        } else {
            if (message.equals("staff")) {
                model.addAttribute("staffCodeError", "Mã nhân viên đã tồn tại");
            }
            if (message.equals("fpt")) {
                model.addAttribute("accountFptError", "Email FPT đã tồn tại");
            }
            if (message.equals("fe")) {
                model.addAttribute("accountFeError", "Email FE đã tồn tại");
            }
            return "them-nhan-vien";
        }
    }

    @GetMapping("/viewUpdate-staff/{id}")
    public String viewUpdatStaff(Model model,
                                 @PathVariable("id") UUID id) {
        StaffDto staffDto = staffService.findStaffByIdDto(id);
        model.addAttribute("staffListDto", staffDto);
        return "view-update-staff";
    }

    @PostMapping("/updateStaff")
    public String updateStaff(Model model,
                              @ModelAttribute("staffListDto") StaffDto staffDto) {

        // Check thay đổi và validate duy nhất
        String message = staffService.checkUpdateConflict(
                staffDto.getId(),
                staffDto.getStaffCode(),
                staffDto.getAccountFpt(),
                staffDto.getAccountFe()
        );

        if (message.equals("valid")) {
            staffService.updateStaff(staffDto.getId(), staffDto);
            return "redirect:/staff/hien-thi";
        }

        if (message.equals("staff")) {
            model.addAttribute("staffCodeError", "Mã nhân viên đã tồn tại");
        }
        if (message.equals("fpt")) {
            model.addAttribute("accountFptError", "Email FPT đã tồn tại");
        }
        if (message.equals("fe")) {
            model.addAttribute("accountFeError", "Email FE đã tồn tại");
        }

        model.addAttribute("staffListDto", staffDto);
        return "view-update-staff";
    }

    @GetMapping("/updateStatus/{id}")
    public String updateStatus(@PathVariable UUID id) {
        staffService.toggleStaffStatus(id);
        return "redirect:/staff/hien-thi";
    }



    @GetMapping("/template")
    public void downloadStaffTemplate(HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Staff Template");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("STT");
        header.createCell(1).setCellValue("Mã nhân viên");
        header.createCell(2).setCellValue("Tên nhân viên");
        header.createCell(3).setCellValue("Email FPT");
        header.createCell(4).setCellValue("Email FE");
        header.createCell(5).setCellValue("Trạng thái");

        // Định dạng file trả về
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=staff_template.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/import-view")
    public String importView() {
        return "/importExcel";
    }

    @PostMapping("/import")
    public String importStaffTemplate(@RequestParam("fileExcel") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Vui lòng chọn file để import.");
            return "redirect:/staff/import-view";
        }

        try (InputStream inputStream = file.getInputStream(); Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            int successCount = 0;
            int failCount = 0;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Bỏ dòng tiêu đề
                Row row = sheet.getRow(i);
                if (row == null) continue;

                try {
                    String maNV = row.getCell(1).getStringCellValue().trim();
                    String tenNV = row.getCell(2).getStringCellValue().trim();
                    String emailFpt = row.getCell(3).getStringCellValue().trim();
                    String emailFe = row.getCell(4).getStringCellValue().trim();
                    String trangThai = row.getCell(5).getStringCellValue().trim();

                    boolean status = trangThai.equalsIgnoreCase("Đang hoạt động");

                    Staff staff = new Staff();
                    staff.setId(UUID.randomUUID());
                    staff.setStaffCode(maNV);
                    staff.setName(tenNV);
                    staff.setAccountFpt(emailFpt);
                    staff.setAccountFe(emailFe);
                    staff.setStatus(status);

                    staffRepository.save(staff);
                    successCount++;

                } catch (Exception e) {
                    failCount++;
                }
            }

            redirectAttributes.addFlashAttribute("success", "Import thành công " + successCount + " bản ghi, thất bại " + failCount + " bản ghi.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Đã xảy ra lỗi khi đọc file.");
        }

        return "redirect:/staff/hien-thi";
    }



}
