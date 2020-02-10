package ji.groupcloud.controller;

import ji.groupcloud.authorization.InviteToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {
    @GetMapping("/signup")
    public String getSignUpHtml(String token) {
        Subject subject = SecurityUtils.getSubject();
        subject.login(new InviteToken(token));

        if (subject.isAuthenticated()) {
            return "signup";
        } else {
            return "error";
        }
    }
}
