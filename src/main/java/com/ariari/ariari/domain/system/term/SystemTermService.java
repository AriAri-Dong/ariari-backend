package com.ariari.ariari.domain.system.term;

import com.ariari.ariari.commons.exception.exceptions.NotFoundEntityException;
import com.ariari.ariari.domain.system.SystemTerm;
import com.ariari.ariari.domain.system.enums.TermType;
import com.ariari.ariari.domain.system.term.dto.res.SystemTermDetailRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SystemTermService {
    private final SystemTermRepository systemTermRepository;

    @Transactional(readOnly = true)
    public SystemTermDetailRes getSystemTermByTermType(TermType termType) {
        SystemTerm systemTerm = systemTermRepository.findByTermType(termType).orElseThrow(NotFoundEntityException::new);
        return SystemTermDetailRes.fromEntity(systemTerm);
    }


}
