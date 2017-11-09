package to.ogarne.restfbadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import to.ogarne.restfbadmin.web.FbLoginHandlerInterceptor;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    FbLoginHandlerInterceptor fbLoginHandlerInterceptor;



    public MvcConfig(FbLoginHandlerInterceptor fbLoginHandlerInterceptor) {
        this.fbLoginHandlerInterceptor = fbLoginHandlerInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fbLoginHandlerInterceptor);
    }


}
