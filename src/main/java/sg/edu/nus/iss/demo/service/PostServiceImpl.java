package sg.edu.nus.iss.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.demo.Exception.ResourceNotFoundException;
import sg.edu.nus.iss.demo.model.Post;
import sg.edu.nus.iss.demo.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Page<Post> getPostList(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public Post updatePost(Post post, Long postId) {
        return postRepository.findById(postId).map(pos -> {
            pos.setTitle(post.getTitle());
            pos.setDescription(post.getDescription());
            pos.setContent(post.getContent());
            return postRepository.save(post);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

    @Override
    public Boolean deletePostById(Long postId) {
        return postRepository.findById(postId).map(post -> {
            postRepository.delete(post);
            return true;
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));
    }

}
