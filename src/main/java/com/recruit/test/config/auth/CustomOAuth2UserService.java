package com.recruit.test.config.auth;

import com.recruit.test.config.auth.dto.OAuthAttributes;
import com.recruit.test.config.auth.dto.SessionUser;
import com.recruit.test.exception.ErrorCode;
import com.recruit.test.exception.customException;
import com.recruit.test.model.User;
import com.recruit.test.model.UserRepository;
import com.recruit.test.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        /*
             OAuth2UserService는 loadUser만 선언된 인터페이스고 DefaultOAuthUserService는 구현체
             -> 이를 이용해서 userRequest 안의 있는 정보를 뻬 낼 수 있다.
         */
        OAuth2UserService<OAuth2UserRequest,OAuth2User> delegate = new DefaultOAuth2UserService(); // 구현체
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 어떤 소셜로그인을 사용했는지
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 로그인을 위한 키
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();


        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        DefaultOAuth2User defaultOAuth2User = new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
        System.out.println("defaultOAuth2User = " + defaultOAuth2User);
        return defaultOAuth2User;
//        return userDetails;
//        return oAuth2User;
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }

    public CustomOauthUser loadUserByEmail(String email) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new customException(ErrorCode.NO_USER));
//        System.out.println("custom user = " + user);

        return new CustomOauthUserImpl(user);
    }


}
