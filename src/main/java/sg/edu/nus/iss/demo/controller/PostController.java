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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import sg.edu.nus.iss.demo.Exception.ResourceNotFoundException;
import sg.edu.nus.iss.demo.model.Post;
import sg.edu.nus.iss.demo.service.PostService;

@Tag(description = "Post resources that provides access to availabe post data", name = "Post Resource")
@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;

    
    @GetMapping("/posts")
    public Page<Post> getAllPosts(Pageable pageable) {
        return postService.getPostList(pageable);
    }

    @PostMapping("/posts")
    public Post createPost(@Valid @RequestBody Post post) {
        return postService.savePost(post);
    }

    @PutMapping("/posts/{postId}")
    public Post updatePost(@PathVariable Long postId, @Valid @RequestBody Post postRequest) {
        return postService.updatePost(postRequest, postId);
    }


    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {

        Boolean isDeleted = postService.deletePostById(postId);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            throw new ResourceNotFoundException("PostId " + postId + " not found");
        }
    }
}
