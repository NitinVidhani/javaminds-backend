package tech.javaminds.javaminds.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tech.javaminds.javaminds.dto.PostDto;
import tech.javaminds.javaminds.entity.Category;
import tech.javaminds.javaminds.entity.Post;
import tech.javaminds.javaminds.entity.User;
import tech.javaminds.javaminds.exception.ResourceAlreadyExistsException;
import tech.javaminds.javaminds.exception.ResourceNotFoundException;
import tech.javaminds.javaminds.repository.CategoryRepository;
import tech.javaminds.javaminds.repository.PostRepository;
import tech.javaminds.javaminds.repository.UserRepository;
import tech.javaminds.javaminds.service.PostService;

import java.util.ArrayList;
import java.util.List;

@Service(value = "postService")
public class PostServiceImpl implements PostService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Create new post
    @Override
    public Post createPost(long categoryId, PostDto postDto) {

        Post existingPost = postRepository.findPostByTitle(postDto.getTitle());
        if(existingPost != null) {
            throw new ResourceAlreadyExistsException("Post with title already exists");
        }

        existingPost = postRepository.findPostByData(postDto.getData());

        if(existingPost != null) {
            throw new ResourceAlreadyExistsException("Post with this content already exists");
        }

        Post post = postDto.getPostFromDto();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + categoryId + "doesn't exists"));
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());
        post.setCategory(category);
        post.setUser(user);
        return postRepository.save(post);

    }

    // Returns a post if id is present otherwise null
    @Override
    public Post findPostById(long id) {
        return postRepository.findById(id).orElse(null);
    }


    // Returns a list of all the posts
    @Override
    public List<Post> getAllPosts() {
        List<Post> allPost = new ArrayList<>();
        postRepository.findAll().iterator().forEachRemaining(allPost::add);
        return allPost;
    }

    // Updates the post if id is present
    // Returns the updated post or the recieved post
    @Override
    public Post updatePost(long id, PostDto postDto, long categoryId) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with Id: " + id + " doesn't exists"));

        post.setTitle(postDto.getTitle());
        post.setData(postDto.getData());
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + categoryId + " doesn't exists"));

        post.setCategory(category);
        postRepository.save(post);

        return post;
    }

    // Deletes the post using it's id
    @Override
    public void deletePost(long id) {
        postRepository.delete(findPostById(id));
    }

    @Override
    public List<Post> getPostsByCategoryId(long id) {
        return postRepository.findByCategory(categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with Id: "+ id + " doesn't exists")));
    }

    @Override
    public void changeCategory(long postId, long categoryId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with Id: " + postId + " doesn't exists"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with Id: " + categoryId + " doesn't exists"));

        post.setCategory(category);
        postRepository.save(post);
    }

    @Override
    public List<Post> findRecentPosts() {
        return postRepository.findFirst5ByOrderByDateDesc();
    }

    public List<Post> getPostsByUserId(long id) {
        return postRepository.findByUser(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id: " + id + " doesn't exists")));
    }
}
