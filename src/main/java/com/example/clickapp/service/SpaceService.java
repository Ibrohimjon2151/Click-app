package com.example.clickapp.service;

import com.example.clickapp.entity.User;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.SpaceDto;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface SpaceService {
    ApiResponse addSpace(SpaceDto spaceDto , User user);

    ApiResponse editSpace(UUID id, SpaceDto spaceDto);

    ApiResponse getAllSpaceFromWorkSpace(Long workSpaceId);

}
