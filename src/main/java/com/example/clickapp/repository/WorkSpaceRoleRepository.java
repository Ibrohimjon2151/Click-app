package com.example.clickapp.repository;

import com.example.clickapp.entity.WorkSpaceRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkSpaceRoleRepository extends JpaRepository<WorkSpaceRole , Long> {

    boolean existsByWorkSpaceNameAndId(String workSpace_name, Long id);
}
