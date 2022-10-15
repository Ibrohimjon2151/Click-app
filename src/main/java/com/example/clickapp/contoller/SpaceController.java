package com.example.clickapp.contoller;

import com.example.clickapp.entity.User;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.SpaceDto;
import com.example.clickapp.security.CurenntUser;
import com.example.clickapp.service.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("api/space")
public class SpaceController {

    @Autowired
    SpaceService spaceService;

    @PostMapping
    public HttpEntity<?> addSpace(@RequestBody SpaceDto spaceDto, @CurenntUser User user) {
        ApiResponse apiResponse = spaceService.addSpace(spaceDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editSpace(@PathVariable UUID id, @RequestBody SpaceDto spaceDto) {
        ApiResponse apiResponse = spaceService.editSpace(id, spaceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{workSpaceId}")
    public HttpEntity<?> getAllSpaceFromWorkSpace(@PathVariable Long workSpaceId) {
        ApiResponse apiResponse = spaceService.getAllSpaceFromWorkSpace(workSpaceId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }




}
