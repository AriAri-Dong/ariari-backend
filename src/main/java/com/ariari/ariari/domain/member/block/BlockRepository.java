package com.ariari.ariari.domain.member.block;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByBlockedMemberAndBlockingMember(Member member, Member reqMember);
}
