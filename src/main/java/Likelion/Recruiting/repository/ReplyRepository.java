package Likelion.Recruiting.repository;

import Likelion.Recruiting.model.Reply;

import Likelion.Recruiting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

}


