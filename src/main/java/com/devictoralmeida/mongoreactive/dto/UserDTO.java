package com.devictoralmeida.mongoreactive.dto;

import com.devictoralmeida.mongoreactive.entities.User;

public class UserDTO {

  private String id;
  private String name;
  private String email;

  public UserDTO() {
  }

  public UserDTO(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public UserDTO(User user) {
    id = user.getId();
    name = user.getName();
    email = user.getEmail();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public User toEntity(String id, String name, String email) {
    return new User(id, name, email);
  }
}
