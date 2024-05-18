package com.telegram.bot;

import java.io.Serializable;

public class DTOBotUser implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private String username;
  private String firstName;
  private String lastName;

  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }

    if (this.getClass() != obj.getClass()) {
      return false;
    }

    String name = obj.toString();
    return this.toString().equals(name);
  }

  public int hashCode() {
    return this.toString().hashCode();
  }

  public String toString() {
    return new StringBuilder(this.getClass().getName()).append("#")
      .append(this.getId()).toString();
  }

  public String getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getUsername() {
    return username;
  }

  public DTOBotUser setId (String id) {
    this.id = id;
    return this;
  }

  public DTOBotUser setFirstName (String firstName) {
    this.firstName = firstName;
    return this;
  }

  public DTOBotUser setLastName (String lastName) {
    this.lastName = lastName;
    return this;
  }

  public DTOBotUser setUsername (String username) {
    this.username = username;
    return this;
  }
}
