package com.ariari.ariari.domain.member.alarm;

import com.ariari.ariari.domain.club.Club;

public interface MemberAlarmRepositoryCustom {

    void deleteAlarmsByClubId(Long reqMemberId, Club club, String keyword);
}
