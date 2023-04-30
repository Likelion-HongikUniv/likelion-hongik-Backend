package Likelion.Recruiting.common.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public interface CustomOauthUser extends OAuth2User {
    @Override
    default <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    Map<String, Object> getAttributes();

    @Override
    Collection<? extends GrantedAuthority> getAuthorities();

    @Override
    String getName();
}
