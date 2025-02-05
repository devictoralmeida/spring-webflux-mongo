package com.devictoralmeida.mongoreactive.dto;

import com.devictoralmeida.mongoreactive.entities.Post;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class PostDTO {

  private String id;
  private Instant date;
  private String title;
  private String body;

  private AuthorDTO author;

  private List<CommentDTO> comments = new ArrayList<>();

  public PostDTO() {
  }

  public PostDTO(String id, Instant date, String title, String body, String authorId, String authorName,
                 AuthorDTO author) {
    this.id = id;
    this.date = date;
    this.title = title;
    this.body = body;
    author = new AuthorDTO(authorId, authorName);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public PostDTO(Post post) {
    id = post.getId();
    date = post.getDate();
    title = post.getTitle();
    body = post.getBody();
    author = new AuthorDTO(post.getAuthorId(), post.getAuthorName());

    List result = List.copyOf(post.getComments());
    comments.addAll(result);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public AuthorDTO getAuthor() {
    return author;
  }

  public void setAuthor(AuthorDTO author) {
    this.author = author;
  }

  public List<CommentDTO> getComments() {
    return comments;
  }

  public void addComments() {

  }
}
