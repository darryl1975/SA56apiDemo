package sg.edu.nus.iss.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sg.edu.nus.iss.demo.model.Comment;

public interface CommentService {
    Page<Comment> getAllCommentsByPostId(Long postId, Pageable pagaeable);

    Comment saveComment(Long postId, Comment comment);

    Comment updateComment(Long postId, Long commentId, Comment commentRequest);

    Boolean deleteComment(Long postId, Long commentId);
}
