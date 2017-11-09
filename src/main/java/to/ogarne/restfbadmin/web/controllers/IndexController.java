package to.ogarne.restfbadmin.web.controllers;

import com.restfb.FacebookClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import to.ogarne.restfbadmin.service.FacebookService;

import javax.servlet.http.HttpServletRequest;
@Controller
public class IndexController {

    FacebookService facebookService;

    public IndexController(FacebookService facebookService) {
        this.facebookService = facebookService;
    }


    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request) {


        facebookService.initialize((FacebookClient.AccessToken)request.getSession().getAttribute("accessToken"));


        return "index";
    }

}
