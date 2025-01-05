package com.ariari.ariari.commons.entitydelete;

import com.ariari.ariari.commons.exception.exceptions.UnexpectedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * v2 : 논리 삭제 처리 도입
 */
@Slf4j
//@Component
@Transactional
@RequiredArgsConstructor
public class EntityDeleteManagerV1 implements EntityDeleteManager {

    private final Map<String, JpaRepository<?, ?>> repositoryMap;
    private final EntityRelationManager entityRelationManager;

    @Override
    public void deleteEntity(Object entity) {
        // 자식 엔티티들 삭제
        List<Object> childEntities = entityRelationManager.getChildEntities(entity);
        for (Object childEntity : childEntities) {
            deleteEntity(childEntity);
        }

        if (entity instanceof LogicalDeleteEntity) { // 논리 삭제
            ((LogicalDeleteEntity) entity).deleteLogically();
        } else { // 물리 삭제
            String key = resolveMapKey(entity.getClass());
            JpaRepository<?, ?> jpaRepository = repositoryMap.get(key);
            try {
                jpaRepository.getClass().getMethod("delete", Object.class).invoke(jpaRepository, entity);
            } catch (Exception e) {
                log.info("!!! {} {}", entity, entity.getClass());
                log.error("엔티티 삭제 중 리플렉션 에러 발생", e);
                throw new UnexpectedException();
            }
        }

    }

    private String resolveMapKey(Class<?> clazz) {
        String simpleName = clazz.getSimpleName();
        String entityName = String.valueOf(simpleName.charAt(0)).toLowerCase() + simpleName.substring(1);
        return entityName + "Repository";
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void init() {
//        for (String key : repositoryMap.keySet()) {
//            log.info("key : {}, value : {}", key, repositoryMap.get(key));
//        }
//    }

}
