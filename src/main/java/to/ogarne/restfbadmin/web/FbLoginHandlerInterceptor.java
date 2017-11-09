package to.ogarne.restfbadmin.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import to.ogarne.restfbadmin.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class FbLoginHandlerInterceptor extends HandlerInterceptorAdapter {

    private UserService userService;

    public FbLoginHandlerInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if (request.getUserPrincipal() == null || request.getRequestURI().equals("/signin") || request.getRequestURI().equals("/success")) {
            return true;
        }

        if (request.getSession().getAttribute("accessToken") != null) {
            return true;
        } else {
            return requireSignIn(request, response);
        }



    }

    private boolean requireSignIn(HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.sendRedirect("/signin");
        return false;
    }
}
