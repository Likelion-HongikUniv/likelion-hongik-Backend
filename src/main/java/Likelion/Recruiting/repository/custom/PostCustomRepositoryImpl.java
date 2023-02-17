package Likelion.Recruiting.repository.custom;

import Likelion.Recruiting.model.Post;
import Likelion.Recruiting.model.QUser;
import Likelion.Recruiting.model.enums.MainCategory;
import Likelion.Recruiting.model.enums.SubCategory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static Likelion.Recruiting.model.QPost.post;
import static Likelion.Recruiting.model.QUser.user;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Post> searchPost(MainCategory mainCategory, SubCategory subCategory, String search, Pageable pageable){

        List<Post> posts =
                queryFactory
                        .select(post)
                        .from(post)
                        .where(post.mainCategory.eq(mainCategory)
                                .and(post.subCategory.eq(subCategory))
                                .and(post.title.contains(search)
                                        .or(post.body.contains(search))))
                        .offset(pageable.getOffset())
                        .orderBy(post.id.desc())
                        .limit(pageable.getPageSize())
                        .fetch();

        long count = queryFactory
                .select(post)
                .from(post)
                .where(post.mainCategory.eq(mainCategory)
                        .and(post.subCategory.eq(subCategory))
                        .and(post.title.contains(search)
                                .or(post.body.contains(search))))
                .fetch().size();
        return new PageImpl<>(posts, pageable, count);
    };

    @Override
    public Page<Post> searchProject(MainCategory mainCategory, SubCategory subCategory, Long teamId,String search, Pageable pageable){

        List<Post> projects =
                queryFactory
                        .select(post)
                        .from(post)
                        .where(post.mainCategory.eq(mainCategory)
                                .and(post.subCategory.eq(subCategory))
                                .and(post.author.id.eq(teamId))
                                .and(post.title.contains(search)
                                        .or(post.body.contains(search))))
                        .offset(pageable.getOffset())
                        .orderBy(post.id.desc())
                        .limit(pageable.getPageSize())
                        .fetch();

        long count = queryFactory
                .select(post)
                .from(post)
                .where(post.mainCategory.eq(mainCategory)
                                .and(post.subCategory.eq(subCategory))
                                .and(post.author.id.eq(teamId))
                                .and(post.title.contains(search)
                                        .or(post.body.contains(search))))
                .fetch().size();
        return new PageImpl<>(projects, pageable, count);
    }
}
