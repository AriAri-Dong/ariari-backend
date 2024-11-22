package com.ariari.ariari.commons.entitydelete;

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
    public void deleteEntity(Object entity) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        // 자식 엔티티들 삭제
        List<Object> childEntities = entityRelationManager.getChildEntities(entity);
        for (Object childEntity : childEntities) {
            deleteEntity(childEntity);
        }

        // 엔티티 삭제 -> 논리 삭제 여부 판단
        Class<?> entityClass = entity.getClass();
        try { // 논리 삭제
            Method deleteMethod = entityClass.getMethod("deleteEntity");
            deleteMethod.invoke(entity);
        } catch (NoSuchMethodException e) { // 물리 삭제
            String key = resolveMapKey(entityClass);
            JpaRepository<?, ?> jpaRepository = repositoryMap.get(key);
            jpaRepository.getClass().getMethod("delete", Object.class).invoke(jpaRepository, entity);
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
