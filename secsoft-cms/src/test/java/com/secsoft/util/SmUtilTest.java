package com.secsoft.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.junit.Test;

import java.security.KeyPair;

/**
 * 测试SM国密算法工具类
 *
 * @author luhf
 * @since 2019/07/30 0:05
 */
public class SmUtilTest {

    @Test
    public void testSm2() {
        String text = "我是一段测试aaaa";

        KeyPair pair = SecureUtil.generateKeyPair("SM2");
        byte[] privateKey = pair.getPrivate().getEncoded();
        byte[] publicKey = pair.getPublic().getEncoded();

        SM2 sm2 = SmUtil.sm2(privateKey, publicKey);
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(text, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
    }

    @Test
    public void testSm3() {
        String digestHex = SmUtil.sm3("aaaaa");

        String realPass = "Secsoft@0123";
        String salt = RandomUtil.randomString(
            RandomUtil.BASE_CHAR_NUMBER + RandomUtil.BASE_CHAR.toUpperCase() + "~!@#$%^&*()-_=+\\|[{}];:'\",<.>/?", 16);
        String secPass = SmUtil.sm3(SmUtil.sm3(realPass) + salt);
        System.out.println(secPass);

        salt = "av)g\\8+j0S^.!iuR";
        secPass = "ca5bff2110dda0c3a1be27e8099d078f44e572f903bc70c7205e8837b12a83c4";
        System.out.println(secPass.equals(SmUtil.sm3(SmUtil.sm3(realPass) + salt)));
    }

    @Test
    public void testSm4() {
        String content = "test中文";
        SymmetricCrypto sm4 = SmUtil.sm4();

        String encryptHex = sm4.encryptHex(content);
        String decryptStr = sm4.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
    }

}
