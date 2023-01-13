package Likelion.Service;

import Likelion.Repository.CommentRepository;
import Likelion.Repository.PostRepository;
import Likelion.model.Comment;
import Likelion.model.User;
import Likelion.model.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

   @Transactional
    public CommentDto writeComment(int postId, CommentDto commentDto, User user){
       Comment comment=new Comment();

   }


}
