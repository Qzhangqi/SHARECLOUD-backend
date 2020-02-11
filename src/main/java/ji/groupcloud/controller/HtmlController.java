package ji.groupcloud.controller;

import ji.groupcloud.authorization.InviteToken;
import ji.groupcloud.util.InviteTokenCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class HtmlController {

    @RequiresGuest
    @GetMapping("/signup")
    public String getSignUpHtml(String token, HttpSession session) {
        log.info(token);

        Subject subject = SecurityUtils.getSubject();
        subject.login(new InviteToken(token));

        if (subject.isAuthenticated()) {
            session.setAttribute("inviter", InviteTokenCache.verifyInviteToken(token));
            return "/signuppage.html";
        } else {
            return "/errorpage.html";
        }
    }
}
