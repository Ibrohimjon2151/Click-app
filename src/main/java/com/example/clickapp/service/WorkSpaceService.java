package com.example.clickapp.service;

import com.example.clickapp.entity.User;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.WorkSpaceDto;

import java.util.UUID;

public interface WorkSpaceService {
    ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user);

    ApiResponse editWorkSpace(Long id, WorkSpaceDto workSpaceDto);

    ApiResponse ediOwnerWorkSpace(Long workSpaceId, UUID newOwnerId);

    ApiResponse deleteWorkSpace(Long id);
}
