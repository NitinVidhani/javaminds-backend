package tech.javaminds.javaminds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.javaminds.javaminds.dto.PostDto;
import tech.javaminds.javaminds.entity.Post;
import tech.javaminds.javaminds.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping
    public List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable("postId") long id) {
        return postService.findPostById(id);
    }

    @PutMapping("/{postId}/{categoryId}")
    public Post updatePost(@PathVariable("postId") long id, @RequestBody PostDto postDto, @PathVariable long categoryId) {
        return postService.updatePost(id, postDto, categoryId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable("postId") long id) {
        postService.deletePost(id);
    }

    @PatchMapping("/change/{postId}/{categoryId}")
    public void changeCategory(@PathVariable("postId") long postId, @PathVariable("categoryId") long categoryId) {
        postService.changeCategory(postId, categoryId);
    }

    @GetMapping("/recent")
    public List<Post> recentPosts() {
        return postService.findRecentPosts();
    }

}
