package com.ariari.ariari.domain.member.point;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update PointHistory ph set ph.member= null where ph.member= :member")
    void updateMemberNull(Member member);

}
