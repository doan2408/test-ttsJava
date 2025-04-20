package com.example.testxuong.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "status", columnDefinition = "tinyint")
    private Boolean status;

    @Column(name = "created_date")
    private Long createdDate;

    @Column(name = "last_modified_date")
    private Long lastModifiedDate;

    @Size(max = 255)
    @Column(name = "account_fe")
    private String accountFe;

    @Size(max = 255)
    @Column(name = "account_fpt")
    private String accountFpt;

    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @Size(max = 255)
    @Column(name = "staff_code")
    private String staffCode;

    @OneToMany(mappedBy = "staff")
    private Set<DepartmentFacility> departmentFacilities = new LinkedHashSet<>();

    @OneToMany(mappedBy = "staff")
    private Set<StaffMajorFacility> staffMajorFacilities = new LinkedHashSet<>();

}