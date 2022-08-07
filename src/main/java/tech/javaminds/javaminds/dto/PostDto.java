package tech.javaminds.javaminds.dto;

import tech.javaminds.javaminds.entity.Post;

import java.util.Date;

public class PostDto {

    private String title;
    private String data;

    public PostDto() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Post getPostFromDto() {
        Post post = new Post();
        post.setTitle(title);
        post.setData(data);
        post.setDate(new Date());
        return post;
    }
}
