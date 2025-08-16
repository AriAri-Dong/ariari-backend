package com.ariari.ariari.domain.club.club;

import com.ariari.ariari.commons.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long>, ClubRepositoryCustom {

    @Query("select c from Club as c left join fetch c.school where c.id = :id")
    Optional<Club> findByIdWithSchool(Long id);

}
