package tech.javaminds.javaminds.service;

import tech.javaminds.javaminds.dto.PostDto;
import tech.javaminds.javaminds.entity.Post;

import java.util.List;

public interface PostService {

    Post createPost(long categoryId, PostDto postDto);

    Post findPostById(long id);

    List<Post> getAllPosts();

    Post updatePost(long id, PostDto postDto, long categoryId);

    void deletePost(long id);

    List<Post> getPostsByCategoryId(long id);

    void changeCategory(long postId, long categoryId);

    List<Post> findRecentPosts();

}
