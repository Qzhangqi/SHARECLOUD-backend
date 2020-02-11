package ji.groupcloud.controller;

import ji.groupcloud.authorization.InviteToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class HtmlController {
    @GetMapping("/signup")
    public String getSignUpHtml(String token) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new InviteToken(token));

        if (subject.isAuthenticated()) {
            return "signuppage";
        } else {
            return "errorpage";
        }
    }

    @GetMapping("/test")
    public String test(String token) {
        log.info(token);
        return "/errorpage.html";
    }
}
