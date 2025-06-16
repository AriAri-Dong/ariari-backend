package com.ariari.ariari.commons.auth.security;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class AESUtil {

    // 암호화 알고리즘 설정
    private static final String AES = "AES";
    private static final String AES_ALGORITHM = "AES/GCM/NoPadding";

    // GCM 세부 설정
    private static final int GCM_TAG_LENGTH = 128; //bit
    private static final int GCM_IV_LENGTH = 12;

    // 대칭키 (고정 키)
    private static final String SECRET_KEY = "MYSecretKey63489";

    private static final SecureRandom secureRandom = new SecureRandom();

    //암호화
    public String encrypt(String plainText) throws Exception {
        byte[] iv = new byte[GCM_IV_LENGTH];
        secureRandom.nextBytes(iv); // IV 무작위 생성

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), AES);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        // [IV (12바이트)] + [실제 암호화된 데이터]
        // IV + 암호문 합치기
        byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
        // iv를 배열의 앞부분에 복사
        System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
        // 암호문을 iv 뒤에 붙임
        System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(encryptedWithIv);
    }

    //복호화
    public String decrypt(String cipherText) throws Exception {
        byte[] decoded = Base64.getUrlDecoder().decode(cipherText);

        byte[] iv = new byte[GCM_IV_LENGTH];
        byte[] encrypted = new byte[decoded.length - GCM_IV_LENGTH];

        System.arraycopy(decoded, 0, iv, 0, GCM_IV_LENGTH);
        System.arraycopy(decoded, GCM_IV_LENGTH, encrypted, 0, encrypted.length);

        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), AES);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted, StandardCharsets.UTF_8);
    }

}
