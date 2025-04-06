package com.ariari.ariari.domain.recruitment.applyform;

import java.util.Set;

public class SpecialApplyQuestionManager {

    private static final Set<String> SPECIAL_QUESTIONS = Set.of(
            "gender", "birthday", "phone", "email", "education",
            "major", "occupation", "mbti", "availablePeriod", "preferredActivityField",
            "hobby", "sns", "motivation", "activityExperience", "skill",
            "aspiration", "availableTime", "referralSource"
    );

    public static boolean isSpecialQuestion(String question) {
        return SPECIAL_QUESTIONS.contains(question);
    }
    
}
