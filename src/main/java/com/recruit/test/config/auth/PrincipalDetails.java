package com.recruit.test.config.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {

        this.user = user;
        this.attributes = attributes;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    // 해당 유저의 권한을 리턴하는 곳
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
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // service에서 해당 이메일이 있는지 판별할 용도
    }

    // 계정이 만료되지 않았는지 리턴한다.(true : 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겨있지 않았는지 리턴한다.(true : 만료안됨)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는지 리턴한다.(true : 만료안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 계정이 활성화(사용가능)인지 리턴한다. (true : 활성화)
    @Override
    public boolean isEnabled() {
        /*
            만약 사이트에서 1년동안 회원이 로그인을 안하면 휴먼 계정으로 돌리려고 한다.
            -> 그러면 현재시간 - 로그인시간 -> 1년을 초과하면 return false;를 해주면 된다.
        */
        return true;
    }

    @Override
    public String getName() {
        return null;
    }
}
