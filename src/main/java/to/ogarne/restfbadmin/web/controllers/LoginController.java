package to.ogarne.restfbadmin.web.controllers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.ExtendedPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.scope.UserDataPermissions;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import to.ogarne.restfbadmin.model.User;
import to.ogarne.restfbadmin.service.FacebookService;
import to.ogarne.restfbadmin.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private Environment env;
    private UserService userService;
    private FacebookService facebookService;

    public LoginController(Environment env, FacebookService facebookService) {
        this.env = env;
        this.facebookService = facebookService;
    }

    @RequestMapping("/login")
    public String logInForm(Model model, HttpServletRequest request) {
        try {
            Object flash = request.getSession().getAttribute("flash");
            model.addAttribute("flash", flash);
            model.addAttribute("user", new User());
            request.getSession().removeAttribute("flash");
        } catch (Exception ex) {
            // Do nothing
        }

        return "login";
    }

    @RequestMapping("/signin")
    public String signIn(HttpServletRequest request) {



        ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.addPermission(UserDataPermissions.USER_ABOUT_ME);
        scopeBuilder.addPermission(ExtendedPermissions.MANAGE_PAGES);
        scopeBuilder.addPermission(ExtendedPermissions.PUBLISH_PAGES);

        String redirectUri = request.getScheme() + "://" + request.getHeader("host") + "/success";

        String loginDialogUrlString = new DefaultFacebookClient(Version.VERSION_2_10)
                .getLoginDialogUrl(env.getProperty("facebook.appId"), redirectUri, scopeBuilder);

        return "redirect:" + loginDialogUrlString;
    }

    @RequestMapping(value = "/success", params="code")
    public String signedIn(@RequestParam("code") String code,  HttpServletRequest request) {

        String redirectUri = request.getScheme() + "://" + request.getHeader("host") + "/success";

        FacebookClient.AccessToken accessToken = new DefaultFacebookClient(Version.VERSION_2_10)
                .obtainUserAccessToken(env.getProperty("facebook.appId"),
                        env.getProperty("facebook.appSecret"),
                        redirectUri,
                        code);



        FacebookClient fbc = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_10);

        request.getSession().setAttribute("accessToken", accessToken);
        facebookService.initialize(accessToken);


        return "redirect:/";


    }
}
