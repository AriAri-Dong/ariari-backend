package com.ariari.ariari.test;

import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class TestDataSetter {
    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initTestData() {
        Member member1 = Member.createMember(null);
        member1.addAuthority(new SimpleGrantedAuthority("ROLE_MANAGER"));
        member1.addAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        Member member2 = Member.createMember(null);
        member2.addAuthority(new SimpleGrantedAuthority("ROLE_MANAGER"));
        Member member3 = Member.createMember(null);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

}
