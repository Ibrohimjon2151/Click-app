package com.example.clickapp.contoller;

import com.example.clickapp.entity.User;
import com.example.clickapp.entity.WorkSpace;
import com.example.clickapp.entity.WorkSpaceUser;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.MemberDto;
import com.example.clickapp.payload.WorkSpaceDto;
import com.example.clickapp.payload.WorkSpaceRoleDto;
import com.example.clickapp.security.CurenntUser;
import com.example.clickapp.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkSpaceController {

    @Autowired
    WorkSpaceService workSpaceService;

    @PostMapping()
    public HttpEntity<?> addWorkSpace(@RequestBody WorkSpaceDto workSpaceDto, @CurenntUser User user) {
        ApiResponse apiResponse = workSpaceService.addWorkSpace(workSpaceDto, user);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editWorkSpace(@PathVariable Long id, @RequestBody WorkSpaceDto workSpaceDto) {
        ApiResponse apiResponse = workSpaceService.editWorkSpace(id, workSpaceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/changeOwner/{workSpaceId}")
    public HttpEntity<?> editOwnerWorkSpace(@PathVariable Long workSpaceId, @RequestParam UUID newOwnerId) {

        ApiResponse apiResponse = workSpaceService.ediOwnerWorkSpace(workSpaceId, newOwnerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkSpace(@PathVariable Long id) {
        ApiResponse apiResponse = workSpaceService.deleteWorkSpace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemove(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        ApiResponse apiResponse = workSpaceService.addOrEditOrRemove(id, memberDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/join")
    public HttpEntity<?> joinToWorkSpace(@RequestParam Long id, @CurenntUser User user) {
        ApiResponse apiResponse = workSpaceService.joinToWorkSpace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/member/{id}")
    public HttpEntity<?> getMemberAndGuest(@PathVariable Long id) {
        List<MemberDto> members = workSpaceService.getMemberAndGuest(id);
        return ResponseEntity.ok(members);
    }

    @GetMapping
    public HttpEntity<?> getMyWorkSpaces(@CurenntUser User user) {
        List<WorkSpaceDto> myWorkSpaces = workSpaceService.getMyWorkSpaces(user);
        return ResponseEntity.ok(myWorkSpaces);
    }

    @PutMapping("/addOrRemovePermission")
    public HttpEntity<?> addOrRemovePermission(@RequestBody WorkSpaceRoleDto workSpaceRoleDto) {
        ApiResponse apiResponse = workSpaceService.addOrRemovePermission(workSpaceRoleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/role")
    public HttpEntity<?> addRole(@RequestParam Long workSpaceId,
                                 @RequestBody WorkSpaceRoleDto workSpaceRoleDto,
                                 @CurenntUser User user) {
        ApiResponse apiResponse = workSpaceService.addRole(workSpaceId, workSpaceRoleDto, user);

        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
