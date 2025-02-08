package com.ariari.ariari.commons.auth.nickname;

import com.ariari.ariari.domain.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class NicknameCreator {

    private final MemberRepository memberRepository;

    private final List<String> adjectives = Arrays.asList(
            "귀여운", "멋진", "빠른", "강한", "밝은", "슬기로운", "용감한", "사랑스러운",
            "친절한", "상냥한", "똑똑한", "예쁜", "행복한", "힘센", "활기찬", "기쁜",
            "조용한", "섬세한", "신나는", "독특한", "푸른", "재미있는", "부드러운", "우아한",
            "매력적인", "따뜻한", "깨끗한", "안정된", "열정적인", "긍정적인",
            "차분한", "깔끔한", "자유로운", "기발한", "소중한", "건강한", "즐거운", "친근한",
            "귀엽고", "기운찬", "정직한", "명랑한", "현명한", "소박한", "상큼한", "창의적인"
    );

    private final List<String> animals = Arrays.asList(
            "쥐", "소", "호랑이", "토끼", "용", "뱀", "말", "양", "원숭이", "닭", "개", "돼지"
    );

    private final Random random = new Random();

    public String createUniqueNickname() {
        while (true) {
            String newNickname = generateNickname();
            if (memberRepository.findByNickName(newNickname).isEmpty()) {
                return newNickname;
            }
        }
    }

    private String generateNickname() {
        String adjective = getRandomElement(adjectives);
        String animal = getRandomElement(animals);
        int number = random.nextInt(900) + 100;
        return adjective + animal + number;
    }

    private String getRandomElement(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

}
