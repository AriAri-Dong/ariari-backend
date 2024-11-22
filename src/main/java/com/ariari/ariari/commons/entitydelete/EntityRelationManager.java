package com.ariari.ariari.commons.entitydelete;

import com.ariari.ariari.domain.alarm.Alarm;
import com.ariari.ariari.domain.apply.Apply;
import com.ariari.ariari.domain.apply.answer.Answer;
import com.ariari.ariari.domain.block.Block;
import com.ariari.ariari.domain.club.Club;
import com.ariari.ariari.domain.club.clubbookmark.ClubBookmark;
import com.ariari.ariari.domain.clubmember.ClubMember;
import com.ariari.ariari.domain.clubpost.ClubPost;
import com.ariari.ariari.domain.clubpost.comment.ClubPostComment;
import com.ariari.ariari.domain.clubpost.image.ClubPostImage;
import com.ariari.ariari.domain.policy.Policy;
import com.ariari.ariari.domain.recruitment.Recruitment;
import com.ariari.ariari.domain.recruitment.image.RecruitmentImage;
import com.ariari.ariari.domain.recruitment.question.Question;
import com.ariari.ariari.domain.school.School;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * v1 : 연관관계 class 를 map 으로 지정 후 getEntity 메서드를 리플렉션으로 동적 호출
 */
@Slf4j
//@Component
@RequiredArgsConstructor
public class EntityRelationManager {

    private final Map<Class<?>, List<Class<?>>> CHILD_ENTITY_MAP = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void initChildEntityMap() {
        CHILD_ENTITY_MAP.put(Member.class, List.of(ClubMember.class, ClubPost.class, ClubPostComment.class, Apply.class, Alarm.class, ClubBookmark.class));
        CHILD_ENTITY_MAP.put(Club.class, List.of(ClubMember.class, ClubBookmark.class, Recruitment.class, ClubPost.class));
        CHILD_ENTITY_MAP.put(Recruitment.class, List.of(Apply.class, Question.class, RecruitmentImage.class));
        CHILD_ENTITY_MAP.put(Apply.class, List.of(Answer.class));
        CHILD_ENTITY_MAP.put(Question.class, List.of(Answer.class));
        CHILD_ENTITY_MAP.put(ClubPost.class, List.of(ClubPostComment.class, ClubPostImage.class));
        CHILD_ENTITY_MAP.put(ClubPostComment.class, List.of());
        CHILD_ENTITY_MAP.put(School.class, List.of(Club.class, Member.class));
    }

    public List<Object> getChildEntities(Object entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Object> childEntities = new ArrayList<>();

        List<Class<?>> childClasses = getChildClasses(entity);
        for (Class<?> childClass : childClasses) {
            Method method = entity.getClass().getMethod(resolveMethodName(childClass));
            List<?> result = (List<?>) method.invoke(entity);

            for (Object o : result) {
                log.info("child : {}", o);
            }

            childEntities.addAll(result);
        }

        return childEntities;
    }

    private List<Class<?>> getChildClasses(Object entity) {
        if (CHILD_ENTITY_MAP.containsKey(entity.getClass())) {
            return CHILD_ENTITY_MAP.get(entity.getClass());
        } else {
            return List.of();
        }
    }

    private String resolveMethodName(Class<?> clazz) {
        return "get" + clazz.getSimpleName() + "s";
    }

}
