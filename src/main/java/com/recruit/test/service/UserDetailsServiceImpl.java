package com.recruit.test.service;

import com.recruit.test.exception.ErrorCode;
import com.recruit.test.exception.customException;
import com.recruit.test.model.User;
import com.recruit.test.model.UserRepository;
import com.recruit.test.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

    private final UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email) // 이미 db에 있는 이메일이라면 그 유저를 반환
//                .orElseThrow(() -> new customException(ErrorCode.NO_USER)); // 만약 db에 없다면 "해당 유저가 없습니다~" 반환
//        System.out.println("userDetailsServiceImpl user = " + user);
//        return new UserDetailsImpl(user);
//    }

}
