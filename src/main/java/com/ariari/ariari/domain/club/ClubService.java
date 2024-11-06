package com.ariari.ariari.domain.club;

import com.ariari.ariari.commons.exception.exceptions.NotAuthenticatedException;
import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.commons.manager.views.ViewsManager;
import com.ariari.ariari.domain.club.dto.ClubMiniData;
import com.ariari.ariari.domain.club.enums.ClubAffiliationType;
import com.ariari.ariari.domain.club.enums.ClubCategoryType;
import com.ariari.ariari.domain.member.Member;
import com.ariari.ariari.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubService {

    private final MemberRepository memberRepository;
    private final ClubRepository clubRepository;
    private final ViewsManager viewsManager;

    public List<ClubMiniData> findClubRankingList(Long memberId, ClubAffiliationType clubAffiliationType, ClubCategoryType clubCategoryType) {

        List<Club> clubs;

        if (clubAffiliationType.equals(ClubAffiliationType.EXTERNAL)) {
            if (clubCategoryType == null) {
                clubs = clubRepository.findRanking();
            } else {
                clubs = clubRepository.findRankingByClubCategoryType(clubCategoryType);
            }
        } else {
            if (memberId == null) {
                throw new NotAuthenticatedException();
            }

            Member member = memberRepository.findById(memberId).orElseThrow(NotFoundEntityException::new);

            if (clubCategoryType == null) {
                clubs = clubRepository.findRankingBySchool(member.getSchool());
            } else {
                clubs = clubRepository.findRankingBySchoolAndClubCategoryType(member.getSchool(), clubCategoryType);
            }
        }

        return ClubMiniData.fromEntities(clubs);
    }

}
