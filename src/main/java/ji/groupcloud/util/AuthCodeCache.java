package ji.groupcloud.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import ji.groupcloud.exception.SMSException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
public class AuthCodeCache {
    @Data
    private static class AuthCode {
        private String code;
        private String phoneNumber;
        private Date creatDate;

        public AuthCode(String code, String phoneNumber, Date creatDate) {
            this.code = code;
            this.phoneNumber = phoneNumber;
            this.creatDate = creatDate;
        }
    }

    private static ConcurrentHashMap<String, AuthCodeCache.AuthCode> authCodeMap = new ConcurrentHashMap<>();
    private static ConcurrentLinkedDeque<AuthCodeCache.AuthCode> authCodeDeque = new ConcurrentLinkedDeque<>();
    private static CopyOnWriteArraySet<String> phoneNumberSet = new CopyOnWriteArraySet<>();

    /**
     * 发生验证码
     */
    public static void getAuthCode(String phoneNumber) throws SMSException {
        //检查是否少于 1 分钟获取验证码验证码
        String authStr = null;

        synchronized (AuthCode.class) {
            if (phoneNumberSet.contains(phoneNumber)) {
                throw new SMSException("手机号请求频繁");
            }

            authStr = RandomStringUtils.random(6, false, true);
            AuthCode authCode = new AuthCode(authStr, phoneNumber, Calendar.getInstance().getTime());
            authCodeMap.put(phoneNumber, authCode);
            authCodeDeque.addLast(authCode);
            phoneNumberSet.add(phoneNumber);
        }


        // 通过第三方服务发送短信
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                "LTAI4GGiPz9TTrmrama7g6Ug",
                "LecGK8zhPGRQTnugkuYZ3DfBwXvatT");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", "SCLOUD快捷网盘");
        request.putQueryParameter("TemplateCode", "SMS_187954769");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+ authStr +"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String resStr = response.getData();
            log.info("短信服务： " + resStr);
            Map resMap = JSON.parseObject(resStr);
            if (!resMap.get("Code").equals("OK")) {
                throw new SMSException("手机号或短信服务出现异常");
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证验证码
     */
    public static boolean verifyAuthCode(String phoneNumber, String authCode) throws SMSException {
        if (authCodeMap.containsKey(phoneNumber)) {
            return authCode.equals(authCodeMap.get(phoneNumber).code);
        } else {
            throw new SMSException("验证码失效或还未发送");
        }
    }

    /**
     * 清空过期验证码
     */
    public static void clearAuthCode(Integer keepTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -keepTime);
        Date beforeDate = calendar.getTime();

        while (!authCodeDeque.isEmpty()) {
            AuthCodeCache.AuthCode authCode = authCodeDeque.peekFirst();
            Date codeCreatDate = authCode.getCreatDate();
            if (codeCreatDate.before(beforeDate)) {
                authCodeDeque.removeFirst();
                authCodeMap.remove(authCode.getPhoneNumber());
            } else {
                break;
            }
        }
    }

    /**
     * 释放到期手机号
     */
    public static void clearPhoneNumber() {
        synchronized (AuthCode.class) {
            phoneNumberSet = new CopyOnWriteArraySet<>();
        }
    }

}