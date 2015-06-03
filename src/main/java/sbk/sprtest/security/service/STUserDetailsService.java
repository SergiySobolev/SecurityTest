package sbk.sprtest.security.service;

import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sbk.sprtest.entity.PermissionEntity;
import sbk.sprtest.entity.PrincipalEntity;
import sbk.sprtest.entity.RoleEntity;
import sbk.sprtest.repo.PrincipalRepository;
import sbk.sprtest.security.domain.STUserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Sets.newHashSet;

@Service
public class STUserDetailsService implements UserDetailsService {

    @Autowired
    private PrincipalRepository principalRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PrincipalEntity principal = principalRepository.findPrincipalByLogin(username);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        UserDetails userDetails =
                new STUserDetails(
                        principal.getLogin().trim(),
                        principal.getPassword().trim(),
                        enabled,
                        accountNonExpired,
                        credentialsNonExpired,
                        accountNonLocked,
                        getAuthorities(principal),
                        principal.getSalt()
                );
        return userDetails;
    }

    private  Collection<? extends GrantedAuthority> getAuthorities(PrincipalEntity principal) {
        Set<GrantedAuthority> authList = newHashSet();
        for(RoleEntity role : principal.getRoles()) {
             authList.addAll(from(role.getPermissions())
                     .transform(new Function<PermissionEntity, GrantedAuthority>() {
                         @Override
                         public GrantedAuthority apply(PermissionEntity permission) {
                             return new SimpleGrantedAuthority(permission.getName());
                         }
                     }).toSet());
        }
        return authList;
    }


}
