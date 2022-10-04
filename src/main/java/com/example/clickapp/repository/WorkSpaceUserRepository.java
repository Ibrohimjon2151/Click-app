package com.example.clickapp.repository;

import com.example.clickapp.entity.WorkSpaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkSpaceUserRepository extends JpaRepository<WorkSpaceUser, Long > {
    Optional <WorkSpaceUser> findByWorkSpaceIdAndUserId(Long workSpace_id, UUID user_id);

    List<WorkSpaceUser> findAllByWorkSpaceId(Long workSpace_id);

    @Transactional
    @Modifying
    void deleteByWorkSpaceIdAndUserId(Long workSpace_id, UUID user_id);

    List<WorkSpaceUser> findAllByUserId(UUID user_id);
}
