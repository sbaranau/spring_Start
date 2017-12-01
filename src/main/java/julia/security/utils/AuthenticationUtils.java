package julia.security.utils;


import julia.entity.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * Утилитный класс на тему аутентификации
 * @author Artiom_Buevich
 * @author vandronov
 */
public class AuthenticationUtils {

    private UserDetailsService userDetailsService;

    /**
     * Получить авторизированного пользователя.
     * @return объект SecurityUser
     */
    public static SecurityUser getAuthentication() {
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * Авторизоваться пользователем "Система"
     */
    public void authenticateAsSystem() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("system");
        SecurityContextHolder.getContext().setAuthentication(new Authentication() {

            @Override
            public String getName() {
                return null;
            }

            @Override
            public void setAuthenticated(boolean arg0) throws IllegalArgumentException {
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public Object getPrincipal() {
                return userDetails;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Collection<GrantedAuthority> getAuthorities() {
                return null;
            }
        });
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

}
