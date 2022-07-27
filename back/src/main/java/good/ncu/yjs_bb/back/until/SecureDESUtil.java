package good.ncu.yjs_bb.back.until;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * DES加密助手
 */
public class SecureDESUtil {
    /**
     * 注意：通过方法 SecureDESUtil.generateSecretKey() 生产一个秘钥Key
     */
    private static final String secretKey = "GOOD@work114";

//    /**
//     * 生成秘钥Key
//     */
//    public static String generateSecretKey() {
//        byte[] keyBytes = SecureUtil.generateKey(SymmetricAlgorithm.DES.getValue()).getEncoded();
//        return new BASE64Encoder().encodeBuffer(keyBytes);
//    }

    /**
     * 获取秘钥Key的byte[]数组
     */



    private byte[] getKeyBytes() {
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = new BASE64Decoder().decodeBuffer(secretKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keyBytes;
    }

    /**
     * 加密字符串
     */
    public String encrypt(String info) {
        DES des = SecureUtil.des(getKeyBytes());
        return des.encryptHex(info);
    }

    /**
     * 解密字符串
     */
    public String decrypt(String encrypt) {
        DES des = SecureUtil.des(getKeyBytes());
        return des.decryptStr(encrypt);
    }

    //加解密测试
    @Test
    public void test(){
        String res = this.encrypt("root");
        System.out.println(res);
        String des = this.decrypt("43025fcc1901a57a");
        System.out.println(des);
    }
}
