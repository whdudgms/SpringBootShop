package com.example.shoptry.repository;

import com.example.shoptry.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Member findByEmail(String email);

}
