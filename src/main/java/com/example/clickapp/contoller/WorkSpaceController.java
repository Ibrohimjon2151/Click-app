package com.example.clickapp.contoller;

import com.example.clickapp.entity.User;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.WorkSpaceDto;
import com.example.clickapp.security.CurenntUser;
import com.example.clickapp.service.WorkSpaceService;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkSpaceController {

    WorkSpaceService workSpaceService;

    @PostMapping()
    public HttpEntity<?> addWorkSpace(@RequestBody WorkSpaceDto workSpaceDto , @CurenntUser User user) {
        ApiResponse apiResponse = workSpaceService.addWorkSpace(workSpaceDto , user);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
    @PutMapping("/id")
    public HttpEntity<?> editWorkSpace(@PathVariable Long id , @RequestBody WorkSpaceDto workSpaceDto) {

        ApiResponse apiResponse = workSpaceService.editWorkSpace(id , workSpaceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/changeOwner/workSpaceId")
    public HttpEntity<?> editOwnerWorkSpace(@PathVariable Long workSpaceId , @RequestParam UUID newOwnerId) {

        ApiResponse apiResponse = workSpaceService.ediOwnerWorkSpace(workSpaceId , newOwnerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkSpace(@PathVariable Long id ){
        ApiResponse apiResponse = workSpaceService.deleteWorkSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
