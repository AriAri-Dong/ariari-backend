package com.ariari.ariari.configs;

import com.ariari.ariari.commons.entitydelete.EntityDeleteManager;
import com.ariari.ariari.commons.entitydelete.EntityDeleteManagerV1;
import com.ariari.ariari.commons.entitydelete.EntityRelationManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EntityDeleteConfig {

    private final Map<String, JpaRepository<?, ?>> repositoryMap;

    @Bean
    public EntityRelationManager entityRelations() {
        return new EntityRelationManager();
    }

    @Bean
    public EntityDeleteManager entityDeleteManager() {
        return new EntityDeleteManagerV1(
                repositoryMap,
                entityRelations());
    }

}
