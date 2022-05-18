package ch.bbw.pr.sospri.other;

import ch.bbw.pr.sospri.member.Member;
import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @class: WebSecurityConfig
 * @author: Anes Hodza
 * @version: 06.04.2022
 **/

@Configuration
@EnableWebSecurity
@ComponentScan(basePackageClasses = MemberService.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.memberService);
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/fragments/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/get-register").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/404.html").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/h2-console").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/contact.html").permitAll()
                .antMatchers("/get-members").hasAuthority("admin")
                .antMatchers("/get-channel").hasAnyAuthority("member", "supervisor", "admin", "ROLE_USER")
                .antMatchers("/h2-console").permitAll()
                .and().exceptionHandling().accessDeniedPage("/403.html")
                .and().formLogin().loginPage("/login").failureUrl("/login?error=true").permitAll()
                .and().logout().permitAll()
                .and().oauth2Login().defaultSuccessUrl("/get-channel?google=true");

        http.csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();

        http.httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
}
