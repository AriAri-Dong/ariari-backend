package com.ariari.ariari.commons.auth.springsecurity;

import com.ariari.ariari.commons.auth.springsecurity.domain.CustomUserDetails;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByIdWithAuthorities(Long.valueOf(memberId)).orElseThrow(NoSuchElementException::new);
        return new CustomUserDetails(member.getId(), member.getAuthorities());
    }

}