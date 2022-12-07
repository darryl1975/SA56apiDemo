package sg.edu.nus.iss.demo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import sg.edu.nus.iss.demo.service.CommentService;
import sg.edu.nus.iss.demo.Exception.ResourceNotFoundException;
import sg.edu.nus.iss.demo.model.Comment;

@Tag(description = "Comment resources that provides access to availabe post data", name = "Comment Resource")
@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/posts/{postId}/comments")
    public Page<Comment> getAllCommentsByPostId(@PathVariable(value = "postId") Long postId,
            Pageable pageable) {
        return commentService.getAllCommentsByPostId(postId, pageable);
    }

    @PostMapping("/posts/{postId}/comments")
    public Comment createComment(@PathVariable(value = "postId") Long postId,
            @Valid @RequestBody Comment comment) {
        return commentService.saveComment(postId, comment);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody Comment commentRequest) {
        return commentService.updateComment(postId, commentId, commentRequest);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId) {

        Boolean isDeleted = commentService.deleteComment(postId, commentId);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException(
                    "Comment not found with id " + commentId + " and postId " + postId);
        }
    }
}
