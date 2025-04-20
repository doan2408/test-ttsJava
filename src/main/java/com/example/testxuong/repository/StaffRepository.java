package com.example.testxuong.repository;

import com.example.testxuong.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {

    Optional<Staff> findByStaffCode(String staffCode);

    Optional<Staff> findByAccountFpt(String accountFpt);

    Optional<Staff> findByAccountFe(String accountFe);



}
