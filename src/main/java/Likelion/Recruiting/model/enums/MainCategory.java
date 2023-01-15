package Likelion.Recruiting.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MainCategory {
    BOARD("MAINCATEGORY_BOARD", "게시판"),
    HOMEWORK("MAINCATEGORY_HOMEWORK", "과제관리"),
    PROJECT("MAINCATEGORY_PROJECT", "프로젝트");


    private final String key;
    private final String title;
}
