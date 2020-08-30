package ji.groupcloud.util;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class InviteTokenCache {

    @Data
    private static class InviteToken {
        private String token;
        private String inviter;
        private Date creatDate;

        public InviteToken(String token, String inviter, Date creatDate) {
            this.token = token;
            this.inviter = inviter;
            this.creatDate = creatDate;
        }
    }

    //邀请注册码
    private static ConcurrentHashMap<String, InviteToken> inviteTokenMap = new ConcurrentHashMap<>();
    private static ConcurrentLinkedDeque<InviteToken> inviteTokenDeque = new ConcurrentLinkedDeque<>();

    public static String creatInviteToken(String username) {
        String tokenStr = RandomStringUtils.random(20, true, true);
        InviteToken inviteToken = new InviteToken(tokenStr, username, Calendar.getInstance().getTime());
        inviteTokenMap.put(tokenStr, inviteToken);
        inviteTokenDeque.addLast(inviteToken);
        return tokenStr;
    }

    public static String verifyInviteToken(String tokenStr) {
        if (inviteTokenMap.containsKey(tokenStr)) {
            return inviteTokenMap.get(tokenStr).getInviter();
        } else {
            return null;
        }
    }

    public static void clearInviteToken(Integer keepTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -keepTime);
        Date beforeTime = calendar.getTime();

        while (!inviteTokenDeque.isEmpty()) {
            InviteToken inviteToken = inviteTokenDeque.peekFirst();
            Date tokenTime = inviteToken.getCreatDate();
            if (tokenTime.before(beforeTime)) {
                inviteTokenDeque.removeFirst();
                inviteTokenMap.remove(inviteToken.getToken());
            } else {
                break;
            }
        }
    }


}
