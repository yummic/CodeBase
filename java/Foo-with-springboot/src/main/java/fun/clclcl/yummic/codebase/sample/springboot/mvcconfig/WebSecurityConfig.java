package fun.clclcl.yummic.codebase.sample.springboot.mvcconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/css/**").permitAll()
                .anyRequest().access("isAuthenticated() or hasIpAddress('127.0.0.1')").and().formLogin().loginPage("/")
                .loginProcessingUrl("/").defaultSuccessUrl("/console").failureUrl("/?error").and().logout()
                .logoutSuccessUrl("/");
        http.httpBasic();
        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin").password(new BCryptPasswordEncoder().encode("admin")).roles("USER");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService result = null;
        try {
            result = super.userDetailsServiceBean();
        } catch (Exception e) {
            LOGGER.error("Unable to retrieve user details service bean", e);
        }
        return result;
    }
}