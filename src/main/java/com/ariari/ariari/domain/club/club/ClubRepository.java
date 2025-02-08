package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom {

}
