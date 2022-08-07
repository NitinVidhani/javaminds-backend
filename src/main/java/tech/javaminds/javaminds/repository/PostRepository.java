package tech.javaminds.javaminds.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.javaminds.javaminds.entity.Category;
import tech.javaminds.javaminds.entity.Post;
import tech.javaminds.javaminds.entity.User;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {

    List<Post> findByCategory(Category category);

    List<Post> findByUser(User user);

    Post findPostByTitle(String title);

    Post findPostByData(String data);

    List<Post> findFirst5ByOrderByDateDesc();

}
