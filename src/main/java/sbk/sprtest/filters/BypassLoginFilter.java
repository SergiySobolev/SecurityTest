package sbk.sprtest.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class BypassLoginFilter extends AbstractAuthenticationProcessingFilter{

    private static String HEADER_IS_ADMIN = "isAdmin";

    public BypassLoginFilter()
    {
        super("/*");
    }

    //Never gets executed
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException,
            IOException, ServletException {

        boolean isAdmin = Boolean.valueOf(request.getHeader(HEADER_IS_ADMIN));

        PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken("","",getAuthorities(isAdmin));
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));

        return getAuthenticationManager().authenticate(authRequest);
    }

    private List<GrantedAuthority> getAuthorities(boolean isAdmin)
    {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(isAdmin){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return authorities;
    }
}
