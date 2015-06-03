package sbk.sprtest.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.AntPathRequestMatcher;
import sbk.sprtest.security.service.STUserDetailsService;

@Configuration
@EnableWebSecurity
//@EnableGlobalAuthentication()
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(STGlobalMethodSecurityConfiguration.class)
public class AclSecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private STUserDetailsService userDetailsService;

    @Autowired
    private STGlobalMethodSecurityConfiguration globalCfg;

    @Override
    public void configure(WebSecurity web) throws Exception {
    //    web.expressionHandler((SecurityExpressionHandler) globalCfg.createExpressionHandler());
    }
//
//
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/protected/**").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/confidential/**").access("hasRole('ROLE_SUPERADMIN')")
                    .antMatchers("/auth/**").permitAll()
                    .antMatchers("/bulletin/**").access("hasRole('ROLE_VISITOR')")
                    .antMatchers("/role/admin/**").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/role/user/**").access("hasRole('ROLE_USER')")
                    .antMatchers("/role/visitor/**").access("hasRole('ROLE_VISITOR')")
                .and()
                    .formLogin()
                    .loginPage("/loginForm")
                    .failureUrl("/error")
                    .loginProcessingUrl("/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                .   defaultSuccessUrl("/", false)
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
        ;
        http.sessionManagement().invalidSessionUrl("/loginForm");
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationManager authenticationManager = new ProviderManager(
                Lists.newArrayList(authenticationProvider()));
        return authenticationManager;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setSaltSource(saltSource());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.afterPropertiesSet();
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() throws Exception {
        return new ShaPasswordEncoder(256);
    }

    @Bean
    public SaltSource saltSource() throws Exception {
        ReflectionSaltSource saltSource = new ReflectionSaltSource();
        saltSource.setUserPropertyToUse("salt");
        saltSource.afterPropertiesSet();
        return saltSource;
    }

}
