package ru.lesson.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.lesson.models.Appuser;
import ru.lesson.store.Storages;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс аутентификации пользователей
 */
@Service("provider")
public class CustomAuthProvider implements AuthenticationProvider {

        @Autowired
        private Storages storages;

        @Override
        @Transactional
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            String login = authentication.getName();
            String password = authentication.getCredentials().toString();
            final Appuser user = storages.userStorage.findOnAuth(login, password);

            if (user != null) {
                List<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority(user.getRole().getName()));
                return new UsernamePasswordAuthenticationToken(login, password, grantedAuths);
            } else {
                return null;
            }
        }

        @Override
        public boolean supports(Class<?> authentication) {
//            return authentication.equals(UsernamePasswordAuthenticationToken.class);
            return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
        }

}
