package ch.bbw.pr.sospri.other;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @class: WebSecurityConfig
 * @author: Anes Hodza
 * @version: 06.04.2022
 **/

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}password").roles("user");
        auth.inMemoryAuthentication().withUser("supervisor").password("{noop}password").roles("user", "supervisor");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}password").roles("user","supervisor", "admin");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fragments/**").permitAll()
                .antMatchers("/get-register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/contact.html").permitAll()
                .antMatchers("/get-members").hasAuthority("admin")
                .antMatchers("/get-channel").hasAuthority("member")
                .and().exceptionHandling().accessDeniedPage("/403.html")
                .and().formLogin().loginPage("/login").failureUrl("/login?error=true").permitAll();

        http.csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();

        http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
