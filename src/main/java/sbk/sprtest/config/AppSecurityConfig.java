package sbk.sprtest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.AntPathRequestMatcher;
import sbk.sprtest.filters.STUsernamePasswordAuthFilter;
import sbk.sprtest.service.STUserDetailsService;

@Configuration
@EnableWebSecurity
@Import(DataBaseConfig.class)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private STUserDetailsService userDetailsService;

    @Autowired
    private DataBaseConfig dataBaseConfig;

    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint(){
        LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint("/login");
        return entryPoint;
    }

    @Bean
    public STUsernamePasswordAuthFilter authenticationFilter() throws Exception {
        STUsernamePasswordAuthFilter authFilter = new STUsernamePasswordAuthFilter();
        return authFilter;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataBaseConfig.dataSource())
                .usersByUsernameQuery("select login, password, enabled from " +
                        "principal where login=?")
                .authoritiesByUsernameQuery("select p.login, r.ROLE_NAME from principal p \n" +
                        "  join PRINCIPAL_ROLE pr on  p.PRINCIPAL_ID = pr.PRINCIPAL_ID\n" +
                        "  join role r on r.ROLE_ID = pr.ROLE_ID where p.LOGIN=?")
                .passwordEncoder(passwordEncoder())
        ;

    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
        web.ignoring().antMatchers("/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/protected/**").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/confidential/**").access("hasRole('ROLE_SUPERADMIN')")
                .and()
                    .formLogin()
                        .loginPage("/loginForm")
                        .failureUrl("/error")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", false)
                .and()
                    .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                .and()
                    .exceptionHandling().accessDeniedPage("/403")
                .and()
                    .addFilter(authenticationFilter())
                ;

        http.sessionManagement().invalidSessionUrl("/loginForm");
    }

    @Bean
    public Md5PasswordEncoder passwordEncoder() throws Exception {
        return new Md5PasswordEncoder();
    }

}
