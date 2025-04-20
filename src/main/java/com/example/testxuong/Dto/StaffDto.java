package com.example.testxuong.Dto;

import com.example.testxuong.entity.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDto {
    private UUID id;

    private String staffCode;

    private String name;

    private String accountFpt;

    private String accountFe;

    private Boolean status = true;


    public static StaffDto convertDto(Staff staff) {
        StaffDto staffDto = new StaffDto();
        staffDto.setId(staff.getId());
        staffDto.setStaffCode(staff.getStaffCode());
        staffDto.setName(staff.getName());
        staffDto.setAccountFpt(staff.getAccountFpt());
        staffDto.setAccountFe(staff.getAccountFe());
        staffDto.setStatus(staff.getStatus());
        return staffDto;
    }
}
