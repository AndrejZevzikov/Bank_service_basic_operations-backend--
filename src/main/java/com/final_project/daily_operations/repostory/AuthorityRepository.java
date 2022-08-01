package com.final_project.daily_operations.repostory;

import com.final_project.daily_operations.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthority(String authority);
}