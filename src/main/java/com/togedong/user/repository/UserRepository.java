package com.togedong.user.repository;

import com.togedong.user.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

    boolean existsByUserId(final String userId);

    Optional<Member> findByUserId(final String userId);

    Optional<Member> findByUserName(final String userName);
}
