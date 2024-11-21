package com.logrex.jobSeeker.jpa;

import com.logrex.jobSeeker.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String roleJobseeker);
}
