package com.ariari.ariari.domain.recruitment.apply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplyRepository extends JpaRepository<Apply, Long>, ApplyRepositoryCustom {

}
