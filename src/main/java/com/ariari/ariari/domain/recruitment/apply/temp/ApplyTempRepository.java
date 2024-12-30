package com.ariari.ariari.domain.recruitment.apply.temp;

import com.ariari.ariari.domain.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyTempRepository extends JpaRepository<ApplyTemp, Long>, ApplyTempRepositoryCustom {

    Page<ApplyTemp> searchByMember(Member reqMember, Pageable pageable);

}
