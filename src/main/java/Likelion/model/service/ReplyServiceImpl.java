package Likelion.model.service;


import Likelion.model.Reply;
import Likelion.model.User;
import Likelion.model.dto.ReplyDto;
import Likelion.model.repository.CommentsRepository;
import Likelion.model.repository.PostRepository;
import Likelion.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public abstract class ReplyServiceImpl implements ReplyService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentsRepository commentsRepository;

    @Transactional
    public Long save(Long post_id, Long reply_id, Long comment_id, ReplyDto replyDto){

    }
}
