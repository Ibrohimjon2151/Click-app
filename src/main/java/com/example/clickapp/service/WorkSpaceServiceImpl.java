package com.example.clickapp.service;

import com.example.clickapp.entity.*;
import com.example.clickapp.entity.enums.WorkSpacePermissionName;
import com.example.clickapp.entity.enums.WorkspaceRoleName;
import com.example.clickapp.payload.ApiResponse.ApiResponse;
import com.example.clickapp.payload.MemberDto;
import com.example.clickapp.payload.WorkSpaceDto;
import com.example.clickapp.payload.WorkSpaceRoleDto;
import com.example.clickapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class WorkSpaceServiceImpl implements WorkSpaceService {
    @Autowired
    WorkSpaceRepository workSpaceRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    WorkSpaceUserRepository workSpaceUserRepository;

    @Autowired
    WorkSpaceRoleRepository workSpaceRoleRepository;

    @Autowired
    WorkSpacePermissionRepository workSpacePermissionRepository;


    @Override
    public ApiResponse addWorkSpace(WorkSpaceDto workSpaceDto, User user) {
        if (workSpaceRepository.existsByNameAndOwner_Id(workSpaceDto.getName(), user.getId())) {
            return new ApiResponse("Bunday nomli WorkSpace mavjud", false);
        }
        /**
         * WORKSPACE OCHILDI
         */
        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(workSpaceDto.getName());
        workSpace.setColor(workSpaceDto.getColor());
        workSpace.setOwner(user);

        if (setAttachment(workSpaceDto, workSpace)) return new ApiResponse("Avatar topilmadi", false);
        workSpaceRepository.save(workSpace);


        /**
         * WORKSPACE ROLE OCHILDI
         */

        WorkSpaceRole ownerRole = workSpaceRoleRepository.save(
                new WorkSpaceRole(
                        workSpace,
                        WorkspaceRoleName.ROLE_OWNER.name(),
                        null
                )
        );

        WorkSpaceRole adminRole = workSpaceRoleRepository.save(
                new WorkSpaceRole(
                        workSpace,
                        WorkspaceRoleName.ROLE_ADMIN.name(),
                        null
                )
        );

        WorkSpaceRole memberRole = workSpaceRoleRepository.save(
                new WorkSpaceRole(
                        workSpace,
                        WorkspaceRoleName.ROLE_MEMBER.name(),
                        null
                )
        );

        WorkSpaceRole guestRole = workSpaceRoleRepository.save(
                new WorkSpaceRole(
                        workSpace,
                        WorkspaceRoleName.ROLE_GUEST.name(),
                        null
                )
        );


        WorkSpacePermissionName[] workSpacePermissionNames = WorkSpacePermissionName.values();
        List<WorkSpacePermission> workSpacePermissionNamesList = new ArrayList<>();
        for (WorkSpacePermissionName workSpacePermissionName : workSpacePermissionNames) {
            WorkSpacePermission workSpacePermission = new WorkSpacePermission(
                    ownerRole,
                    workSpacePermissionName
            );
            workSpacePermissionNamesList.add(workSpacePermission);

            if (workSpacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workSpacePermissionNamesList.add(new WorkSpacePermission(
                        adminRole,
                        workSpacePermissionName
                ));
            }

            if (workSpacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workSpacePermissionNamesList.add(new WorkSpacePermission(
                        memberRole,
                        workSpacePermissionName
                ));
            }

            if (workSpacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workSpacePermissionNamesList.add(new WorkSpacePermission(
                        guestRole,
                        workSpacePermissionName
                ));
            }


        }


        workSpacePermissionRepository.saveAll(workSpacePermissionNamesList);

        /**
         * WORKSPACE USER OCHILDI
         */
        workSpaceUserRepository.save(
                new WorkSpaceUser(
                        workSpace,
                        user,
                        ownerRole,
                        new Timestamp(System.currentTimeMillis()),
                        new Timestamp(System.currentTimeMillis()),
                        new Timestamp(System.currentTimeMillis())
                )
        );

        return new ApiResponse("WorkSpace yaratildi", true);
    }

    @Override
    public ApiResponse editWorkSpace(Long id, WorkSpaceDto workSpaceDto) {
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(id);
        if (optionalWorkSpace.isEmpty()) {
            return new ApiResponse("Bunday Ishxona topilmadi", false);
        }
        WorkSpace workSpace = optionalWorkSpace.get();
        if (!workSpace.getName().equals(workSpaceDto.getName()) && workSpaceRepository.existsByNameAndOwner_Id(workSpaceDto.getName(), workSpace.getOwner().getId())) {
            return new ApiResponse("Bunday nomli WorkSpace mavjud", false);
        }

        workSpace.setName(workSpaceDto.getName());
        workSpace.setColor(workSpaceDto.getColor());
        if (setAttachment(workSpaceDto, workSpace)) return new ApiResponse("Avatar topilmadi", false);
        return new ApiResponse("Ishxona o'zgartirildi", true);
    }


    @Override
    public ApiResponse ediOwnerWorkSpace(Long workSpaceId, UUID newOwnerId) {
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceId);
        if (optionalWorkSpace.isEmpty()) {
            return new ApiResponse("Bunday Ishxona topilmadi", false);
        }
        WorkSpace workSpace = optionalWorkSpace.get();

        Optional<User> optionalUser = userRepository.findById(newOwnerId);
        if (optionalUser.isEmpty()) {
            return new ApiResponse("Bunday Foydalanuvchi topilmadi", false);
        }
        workSpace.setOwner(optionalUser.get());

        workSpaceRepository.save(workSpace);

        return new ApiResponse("O'gartirildi", true);
    }

    @Override
    public ApiResponse deleteWorkSpace(Long id) {
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(id);
        if (optionalWorkSpace.isEmpty()) {
            return new ApiResponse("Bunday Ishxona topilmadi", false);
        }
        workSpaceRepository.deleteById(id);

        return new ApiResponse("Deleted", true);
    }

    /**
     * MEMBER QOSHISH WORKSPACEGA
     *
     * @param id
     * @param memberDto
     * @return
     */
    @Override
    public ApiResponse addOrEditOrRemove(Long id, MemberDto memberDto) {
        switch (memberDto.getAddType()) {
            case ADD:
                WorkSpaceUser workSpaceUser = new WorkSpaceUser(
                        workSpaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID")),
                        userRepository.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("NOT FOUND USER")),
                        workSpaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("ROLE NOT FOUND")),
                        new Timestamp(System.currentTimeMillis()),
                        new Timestamp(System.currentTimeMillis()),
                        null
                );
                workSpaceUserRepository.save(workSpaceUser);
                break;
            case EDIT:
                Optional<WorkSpaceUser> optionalWorkSpaceUser = workSpaceUserRepository.findByWorkSpaceIdAndUserId(id, memberDto.getId());
                WorkSpaceUser workSpaceUserEdit = optionalWorkSpaceUser.get();
                workSpaceUserEdit.setWorkSpaceRole(workSpaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("ROLE NOT FOUND")));
                workSpaceUserRepository.save(workSpaceUserEdit);
                break;
            case REMOVE:
                workSpaceUserRepository.deleteByWorkSpaceIdAndUserId(id, memberDto.getId());
                break;
        }
        return new ApiResponse("Succassfuly", true);
    }

    /**
     * WORKSPACEGA QO'SHILISH
     *
     * @param id
     * @param user
     * @return
     */
    @Override
    public ApiResponse joinToWorkSpace(Long id, User user) {
        Optional<WorkSpaceUser> optionalWorkSpaceUser = workSpaceUserRepository.findByWorkSpaceIdAndUserId(id, user.getId());

        if (optionalWorkSpaceUser.isPresent()) {
            WorkSpaceUser workSpaceUser = optionalWorkSpaceUser.get();
            workSpaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));

            workSpaceUserRepository.save(workSpaceUser);
            return new ApiResponse("Success", true);
        }
        return new ApiResponse("NOT FOUND", false);
    }

    /**
     * WORKSPACEDAGI MEMBERLARNI KORISH
     *
     * @param id
     * @return
     */
    @Override
    public List<MemberDto> getMemberAndGuest(Long id) {
        List<WorkSpaceUser> allSpaceUserList = workSpaceUserRepository.findAllByWorkSpaceId(id);

        List<MemberDto> collect = allSpaceUserList.stream().map(this::addMemberDtoFromWorkSpaceUser).collect(Collectors.toList());

        return collect;
    }


    @Override
    public List<WorkSpaceDto> getMyWorkSpaces(User user) {
        List<WorkSpaceUser> workSpaceUserList = workSpaceUserRepository.findAllByUserId(user.getId());

        List<WorkSpaceDto> memberDtos = new ArrayList<>();
        for (WorkSpaceUser workSpaceUser : workSpaceUserList) {
            WorkSpaceDto memberDto = addWorkSpaceToWorkSpaceDto(workSpaceUser);
            memberDtos.add(memberDto);
        }

        return memberDtos;
    }

    @Override
    public ApiResponse addOrRemovePermission(WorkSpaceRoleDto workSpaceRoleDto) {
        WorkSpaceRole workSpaceRole = workSpaceRoleRepository.findById(workSpaceRoleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        Optional<WorkSpacePermission> optionalWorkSpacePermission = workSpacePermissionRepository.findByWorkSpaceRoleIdAndWorkSpacePermissionName(workSpaceRole.getId(), workSpaceRoleDto.getWorkSpacePermissionName());
        if (optionalWorkSpacePermission.isPresent()) {
            return new ApiResponse("Allaqochon qo'shilgan", false);
        }
        WorkSpacePermission workSpacePermission = new WorkSpacePermission(workSpaceRole, optionalWorkSpacePermission.   get().getWorkSpacePermissionName());
        workSpacePermissionRepository.save(workSpacePermission);

        return new ApiResponse("Muvaffaqiyatli qo'shildi", true);
    }


    @Override
    public ApiResponse addRole(Long workSpaceId, WorkSpaceRoleDto workSpaceRoleDto, User user) {
        if (workSpaceRoleRepository.existsByWorkSpaceNameAndId(workSpaceRoleDto.getName(), workSpaceId)) {
            return new ApiResponse("ERROR", false);
        }
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceId);
        if (optionalWorkSpace.isEmpty()) {
            return new ApiResponse("NOT FOUND WORKSPACE",true);
        }

        WorkSpaceRole workSpaceRole = workSpaceRoleRepository.save(new WorkSpaceRole(
                optionalWorkSpace.get(),
                workSpaceRoleDto.getName(),
                workSpaceRoleDto.getExtendRole()
        ));

        List<WorkSpacePermission> allByWorkSpaceRoleNameAndWorkSpaceRole_workSpaceId = workSpacePermissionRepository.findAllByWorkSpaceRoleNameAndWorkSpaceRole_WorkSpaceId(workSpaceRoleDto.getExtendRole().name(), workSpaceId);

        List<WorkSpacePermission> permissionList = new ArrayList<>();
        for (WorkSpacePermission workSpacePermission : allByWorkSpaceRoleNameAndWorkSpaceRole_workSpaceId) {
            WorkSpacePermission workSpacePermission1 = new WorkSpacePermission(
                    workSpaceRole,
                    workSpacePermission.getWorkSpacePermissionName()
            );
            permissionList.add(workSpacePermission1);
        }
        workSpacePermissionRepository.saveAll(permissionList);
        return new ApiResponse("SAVED",true);
    }

    /// *********************   MY METHODS   *************************
    public WorkSpaceDto addWorkSpaceToWorkSpaceDto(WorkSpaceUser workSpaceUser) {
        WorkSpaceDto workSpaceDto = new WorkSpaceDto();

        workSpaceDto.setId(workSpaceUser.getWorkSpace().getId());
        workSpaceDto.setColor(workSpaceUser.getWorkSpace().getColor());
        workSpaceDto.setName(workSpaceUser.getWorkSpace().getName());
        if (workSpaceUser.getWorkSpace().getAvatar() != null) {
            workSpaceDto.setAttachmentId(workSpaceUser.getWorkSpace().getAvatar().getId());
        } else {
            workSpaceDto.setAttachmentId(null);
        }
        workSpaceDto.setOwnerId(workSpaceUser.getUser().getId());

        return workSpaceDto;
    }


    /**
     * getMemberAndGuest qo'shimcha method
     *
     * @param workSpaceUser
     * @return
     */
    public MemberDto addMemberDtoFromWorkSpaceUser(WorkSpaceUser workSpaceUser) {
        MemberDto memberDto = new MemberDto();

        memberDto.setId(workSpaceUser.getUser().getId());
        memberDto.setEmail(workSpaceUser.getUser().getEmail());
        memberDto.setFullName(workSpaceUser.getUser().getFullName());
        memberDto.setRoleName(workSpaceUser.getWorkSpaceRole().getName());
        memberDto.setLastActive(workSpaceUser.getLastActive());

        return memberDto;
    }

    /**
     * ATTACHMENTNI SET QILISH PRIVATE METHOD
     *
     * @param workSpaceDto
     * @param workSpace
     * @return
     */
    private boolean setAttachment(WorkSpaceDto workSpaceDto, WorkSpace workSpace) {
        if (workSpaceDto.getAttachmentId() == null) {
            workSpace.setAvatar(null);
        } else {
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(workSpaceDto.getAttachmentId());
            if (optionalAttachment.isPresent()) {
                workSpace.setAvatar(optionalAttachment.get());
            } else {
                return true;
            }
        }
        return false;
    }
}
