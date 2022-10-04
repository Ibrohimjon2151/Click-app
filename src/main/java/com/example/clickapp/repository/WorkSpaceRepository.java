package com.example.clickapp.repository;

import com.example.clickapp.entity.WorkSpace;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkSpaceRepository extends JpaRepository<WorkSpace , Long > {
    boolean existsByNameAndOwner_Id(String name, UUID owner_id);
}
