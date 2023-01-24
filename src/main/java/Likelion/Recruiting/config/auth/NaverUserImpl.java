package Likelion.Recruiting.config.auth;

import Likelion.Recruiting.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class NaverUserImpl implements CustomOauthUser {
    private final User user;

    private Map<String, Object> attributes;

    public NaverUserImpl(User user) {
        this.user = user;
    }

    public NaverUserImpl(User user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = (Map<String, Object>) attributes.get("response");;
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
        return attributes.get("name").toString();
    }


    public String getEmail() {
        return attributes.get("email").toString();
    }

}
