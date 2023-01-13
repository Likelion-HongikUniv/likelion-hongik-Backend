package com.recruit.test.service;

import Likelion.model.dto.ReplyDto;

public interface ReplyService {
    void save(Long post_id, Long reply_id, Long comment_id, ReplyDto replyDto);
    void update(Long post_id, Long comments_id, Long reply_id, ReplyDto replyDto);
}
