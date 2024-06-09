/*
 * MIT License
 *
 * Copyright (c) 2021 苗锦洲
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package tech.ordinaryroad.commons.core.utils.openid;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.AES;
import jakarta.validation.constraints.NotNull;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.SecureRandom;

/**
 * Openid生成器
 *
 * @author mjz
 * @date 2021/11/2
 */
public class OpenidUtil {

    public static void main(String[] args) {
//        openidTest1();
//        openidTest2();
//        openidTest3();
        ClassPathResource aesKeyClassPathResource = new ClassPathResource("keys/openid_aes_key.txt");
        String aesKey = null;
        try {
            aesKey = IoUtil.read(FileUtil.getUtf8Reader(aesKeyClassPathResource.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AES aes = SecureUtil.aes(aesKey.getBytes());

        // base64
        System.out.println(Base64.encode("ordinaryroad"));
        System.out.println(Base64.encode("ordinaryroad-ordinaryroad"));
        System.out.println(Base64.encode("123456789012345"));

        // aes 15个字符以内得到的只有24位长
        System.out.println(aes.encryptBase64("a"));
        System.out.println(aes.encryptBase64("aa"));
        System.out.println(aes.encryptBase64("ordinaryroad"));
        System.out.println(aes.encryptBase64("1234567890"));
        System.out.println(aes.encryptBase64("12345678901"));
        System.out.println(aes.encryptBase64("123456789012"));
        System.out.println(aes.encryptBase64("1234567890123"));
        System.out.println(aes.encryptBase64("12345678901234"));
        System.out.println(aes.encryptBase64("123456789012345"));

    }

    private static void openidTest1() {
        System.out.println(generateRandom("ordinaryroad", "123456789"));
        System.out.println(generateRandom("ordinaryroad", "12345678"));
        System.out.println(generateRandom("ordinaryroad", "123456789"));

        System.out.println(generateRandom("qq", "123456789"));
    }

    private static void openidTest2() {
        ClassPathResource privateKeyClassPathResource = new ClassPathResource("keys/openid_private_key.txt");
        ClassPathResource publicClassPathResource = new ClassPathResource("keys/openid_public_key.txt");
        String privateKeyBase64 = null;
        String publicKeyBase64 = null;
        try {
            privateKeyBase64 = IoUtil.read(FileUtil.getUtf8Reader(privateKeyClassPathResource.getFile()));
            publicKeyBase64 = IoUtil.read(FileUtil.getUtf8Reader(publicClassPathResource.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RSA rsa = SecureUtil.rsa(privateKeyBase64, publicKeyBase64);

        // 公钥加密，私钥解密
        String a = rsa.encryptBase64("a".getBytes(), KeyType.PublicKey);
        System.out.println(a);
        String aD = rsa.decryptStr(a, KeyType.PrivateKey);
        System.out.println(aD);

        // 私钥签名，公钥验证
        String b = rsa.encryptBase64("b".getBytes(), KeyType.PrivateKey);
        System.out.println(b);
        String bD = rsa.decryptStr(b, KeyType.PublicKey);
        System.out.println(bD);
    }

    private static void openidTest3() {
        System.out.println(generate("ordinaryroad", "123456789"));
        System.out.println(generate("ordinaryroad", "12345678"));
        System.out.println(generate("ordinaryroad", "123456789"));

        System.out.println(generate("qq", "123456789"));
    }

    public static String generateRandom(@NotNull String clientId, @NotNull String orNumber) {
        SecureRandom secureRandom = RandomUtil.getSecureRandom((clientId + orNumber).getBytes());

        StringBuilder sb = new StringBuilder(36);
        for (int i = 0; i < 2; i++) {
            int number = secureRandom.nextInt(RandomUtil.BASE_CHAR_NUMBER.length());
            sb.append(RandomUtil.BASE_CHAR_NUMBER.charAt(number));
        }

        sb.append("_");

        for (int i = 0; i < 14; i++) {
            int number = secureRandom.nextInt(RandomUtil.BASE_CHAR_NUMBER.length());
            sb.append(RandomUtil.BASE_CHAR_NUMBER.charAt(number));
        }

        sb.append("_");

        for (int i = 0; i < 16; i++) {
            int number = secureRandom.nextInt(RandomUtil.BASE_CHAR_NUMBER.length());
            sb.append(RandomUtil.BASE_CHAR_NUMBER.charAt(number));
        }

        sb.append("_");
        sb.append("_");

        return sb.toString();
    }

    /**
     * 1. basicString = ${clientId},${orNumber}
     * <br>
     * 2. rsaString = rsa(basicString)
     * <br>
     * 3. aesString = aes(rsaString)
     * <br>
     * 4. openid
     * <br>
     *
     * @param clientId 客户端ID
     * @param orNumber or帐号
     * @return openid
     * @deprecated 使用 {@link #generateRandom(String, String)}
     */
    @Deprecated
    public static String generate(String clientId, String orNumber) {
        ClassPathResource privateKeyClassPathResource = new ClassPathResource("keys/openid_private_key.txt");
        ClassPathResource publicKeyClassPathResource = new ClassPathResource("keys/openid_public_key.txt");
        ClassPathResource aesKeyClassPathResource = new ClassPathResource("keys/openid_aes_key.txt");
        String privateKeyBase64 = null;
        String publicKeyBase64 = null;
        String aesKey = null;
        try {
            privateKeyBase64 = IoUtil.read(FileUtil.getUtf8Reader(privateKeyClassPathResource.getFile()));
            publicKeyBase64 = IoUtil.read(FileUtil.getUtf8Reader(publicKeyClassPathResource.getFile()));
            aesKey = IoUtil.read(FileUtil.getUtf8Reader(aesKeyClassPathResource.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AES aes = SecureUtil.aes(aesKey.getBytes());
        RSA rsa = SecureUtil.rsa(privateKeyBase64, publicKeyBase64);

        // clientId和or帐号
        String basicString = clientId + "," + orNumber;

        // rsa
        String rsaString = rsa.encryptBase64(basicString.getBytes(), KeyType.PublicKey);

        // aes
        String aesString = aes.encryptBase64(rsaString);

        return aesString;
    }

}
