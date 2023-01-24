package Likelion.Recruiting.config.auth.dto;

import Likelion.Recruiting.model.enums.Role;
import Likelion.Recruiting.model.User;
import Likelion.Recruiting.model.enums.LType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private LType ltype;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, LType ltype) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.ltype = ltype;
    }


    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }
        else if ("kakao".equals(registrationId)){
            return ofKaKao(userNameAttributeName, attributes);
        }
//        else if ("google".equals(registrationId)){
            return ofGoogle(userNameAttributeName, attributes);
//        }
    }

    private static OAuthAttributes ofKaKao(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .ltype(LType.KAKAO)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .ltype(LType.GOOGLE)
                .build();
    }
    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        /* JSON형태이기 때문에 Map을 통해 데이터를 가져온다. */
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println(response);

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) attributes.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .ltype(LType.NAVER)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .role(Role.GUEST)
                .lType(ltype)
                .profileImage(picture)
                .build();
    }
}
