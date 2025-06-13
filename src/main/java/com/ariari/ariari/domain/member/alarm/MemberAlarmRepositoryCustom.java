package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.club.Club;

public interface MemberAlarmRepositoryCustom {

    void deleteAlarmsByClubId(Club club, Long memberId, String keyword);
}
