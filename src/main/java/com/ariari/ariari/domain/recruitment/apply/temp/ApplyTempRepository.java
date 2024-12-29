package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ApplyTempRepository extends JpaRepository<ApplyTemp, Long>, ApplyTempRepositoryCustom {

    @Query("select a from ApplyTemp a where a.member= :member order by a.createdDateTime desc")
    Page<ApplyTemp> findByMember(Member member, Pageable pageable);

}
