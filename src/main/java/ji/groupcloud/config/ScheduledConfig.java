package ji.groupcloud.config;

import ji.groupcloud.constant.SystemConfig;
import ji.groupcloud.util.AuthCodeCache;
import ji.groupcloud.util.InviteTokenCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 定时任务配置
 */
@Slf4j
@Component
@Configurable
@EnableScheduling
public class ScheduledConfig {
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void clearInviteTokenScheduled() {
        log.info("清除过期邀请码 {}", Calendar.getInstance().getTime());
        InviteTokenCache.clearInviteToken(SystemConfig.INVITE_TOKEN_LIMIT);
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void clearAuthCodeScheduled() {
        log.info("清除过期验证码 {}", Calendar.getInstance().getTime());
        //同时还会对手机一分钟解封
        AuthCodeCache.clearAuthCode(SystemConfig.AUTH_CODE_LIMIT);
        AuthCodeCache.clearPhoneNumber();
    }
}
