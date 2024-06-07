package com.harkesh.letterbox.repository;

import com.harkesh.letterbox.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
