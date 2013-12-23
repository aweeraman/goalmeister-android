package com.goalmeister.model;

public class UserToken {

  public String _id;
  public String email;
  public String access_token;
  public int expires_in;

  public UserToken() {
    // empty - needed for serialization
  }

  public UserToken(String email, String token) {
    this.email = email;
    this.access_token = token;
  }

}
