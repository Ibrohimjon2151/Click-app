package com.example.clickapp.repository;

import com.example.clickapp.entity.WorkSpacePermission;
import com.example.clickapp.entity.enums.WorkSpacePermissionName;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkSpacePermissionRepository extends JpaRepository<WorkSpacePermission , Long > {

    Optional<WorkSpacePermission> findByWorkSpaceRoleIdAndWorkSpacePermissionName(Long workSpaceRole_id, WorkSpacePermissionName workSpacePermissionName);

    List<WorkSpacePermission> findAllByWorkSpaceRoleNameAndWorkSpaceRole_WorkSpaceId(String workSpaceRole_name, Long workSpaceRole_workSpace_id);
}
