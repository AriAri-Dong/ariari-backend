package com.ariari.ariari.domain.club;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom {

}
