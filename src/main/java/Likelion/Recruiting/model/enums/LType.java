package Likelion.Recruiting.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LType {
    KAKAO("LTYPE_KAKAO", "카카오"),
    NAVER("LTYPE_NAVER", "네이버"),
    GOOGLE("LTYPE_GOOGLE", "구글"),
    GITHUB("LTYPE_GITHUB", "깃허브");


    private final String key;
    private final String title;
}