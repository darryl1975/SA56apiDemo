package sg.edu.nus.iss.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sg.edu.nus.iss.demo.model.Post;

public interface PostService {
    Post savePost(Post post);
    
    Page<Post> getPostList(Pageable pagaeable);

    Post updatePost(Post post, Long postId);

    Boolean deletePostById(Long postId);
}
