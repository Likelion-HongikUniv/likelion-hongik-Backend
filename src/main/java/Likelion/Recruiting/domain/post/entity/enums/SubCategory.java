package Likelion.Recruiting.domain.post.entity.enums;
//public enum SubCategory {
//    , , , , , DESIGN
//}

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubCategory {
     NOTICE("SUBCATEGORY_NOTICE", "공지"),
     QNA("SUBCATEGORY_QNA", "질의응답"),
     FREEBOARD("SUBCATEGORY_FREEBOARD", "자유게시판"),
     FRONTEND("SUBCATEGORY_FRONTEND", "프론트"),
     BACKEND("SUBCATEGORY_BACKEND", "백엔드"),
     DESIGN("SUBCATEGORY_DESIGN", "디자인"),
     PROJECT("SUBCATEGORY_PROJECT", "프로젝트");


     private final String key;
     private final String title;

}

