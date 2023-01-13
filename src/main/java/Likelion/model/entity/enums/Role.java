package Likelion.model.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "멤버"),
    EXECUTIVE("ROLE_EXECUTIVE", "운영진"),
    ADMIN("ROLE_ADMIN", "관리자");


    private final String key;
    private final String title;
}