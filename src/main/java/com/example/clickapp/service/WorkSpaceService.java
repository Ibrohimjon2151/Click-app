package com.example.clickapp.service;

import com.example.clickapp.entity.User;
import com.example.clickapp.entity.WorkSpace;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.MemberDto;
import com.example.clickapp.payload.WorkSpaceDto;
import com.example.clickapp.payload.WorkSpaceRoleDto;

import java.util.List;
import java.util.UUID;

public interface WorkSpaceService {
    ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user);

    ApiResponse editWorkSpace(Long id, WorkSpaceDto workSpaceDto);

    ApiResponse ediOwnerWorkSpace(Long workSpaceId, UUID newOwnerId);

    ApiResponse deleteWorkSpace(Long id);

    ApiResponse addOrEditOrRemove(Long id, MemberDto memberDto);

    ApiResponse joinToWorkSpace(Long id, User user);

    List<MemberDto> getMemberAndGuest(Long id);

    List<WorkSpaceDto> getMyWorkSpaces(User user);

    ApiResponse addOrRemovePermission(WorkSpaceRoleDto workSpaceRoleDto);

    ApiResponse addRole(Long workSpaceId, WorkSpaceRoleDto workSpaceRoleDto, User user);
}
