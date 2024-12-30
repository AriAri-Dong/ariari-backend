package com.ariari.ariari.domain.recruitment.apply;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long>, ApplyRepositoryCustom {

}
