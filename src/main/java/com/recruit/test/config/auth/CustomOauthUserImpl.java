package com.recruit.test.config.auth;

import com.recruit.test.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOauthUserImpl implements CustomOauthUser{

    private final User user;
    private Map<String, Object> attributes;

    public CustomOauthUserImpl(User user) {
        this.user = user;
    }

    public CustomOauthUserImpl(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();

        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }

        });

        return collect;
    }

    @Override
    public String getName() {
        return user.getName();
    }
}
