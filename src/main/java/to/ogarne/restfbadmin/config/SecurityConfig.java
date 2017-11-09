package to.ogarne.restfbadmin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import to.ogarne.restfbadmin.service.UserService;
import to.ogarne.restfbadmin.web.FlashMessage;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{


    UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .cacheControl().disable()
                    .and()
                .authorizeRequests()
                    .antMatchers("/**").hasRole("USER")

                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .successHandler(loginSuccessHandler())
                    .failureHandler(loginFailureHandler())
                    .and()
                .logout()
                    .permitAll()
                    .logoutSuccessUrl("/logout");


    }

     private AuthenticationSuccessHandler loginSuccessHandler() {

        return ((request, response, authentication) -> response.sendRedirect("/"));
    }

    private AuthenticationFailureHandler loginFailureHandler() {
        return ((request, response, authentication) -> {
            request.getSession().setAttribute("flash", new FlashMessage("Incorrect username and/or password. Please try again", FlashMessage.Status.FAILURE));
            response.sendRedirect("/login");
        });
    }
}
