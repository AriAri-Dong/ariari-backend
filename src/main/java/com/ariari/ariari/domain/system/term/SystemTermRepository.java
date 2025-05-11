package com.ariari.ariari.domain.system.term;

import com.ariari.ariari.domain.system.SystemTerm;
import com.ariari.ariari.domain.system.enums.TermType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemTermRepository extends JpaRepository<SystemTerm, Long> {

    Optional<SystemTerm> findByTermType(TermType termType);
}
