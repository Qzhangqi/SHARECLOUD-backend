package ji.groupcloud.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SomeTest {
    public static void main(String[] args) throws Exception {
        String str = "我操你妈你妈死了123123123123";

        md5("我操你妈你妈死了123123123123");
        md5("我操妈死了1231123");
        md5("1231123我操妈死了");
    }

    public static void md5(String str) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        System.out.println(new BigInteger(1, md.digest()).toString(16));
    }
}
