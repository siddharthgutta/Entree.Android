package com.entree.shared.api;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface EntreeAPI {

  /**
   * Try to minimize the use of these classes without losing readability
   */

  public static class UserRequest {
    public String username;
    public String email;
    public String password;

    public UserRequest(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
    }
  }

  public static class UserResponse {
    public String username;
    public String email;
    public String password;
    public String id;

    public UserResponse(String username, String email, String password) {
      this.username = username;
      this.email = email;
      this.password = password;
    }
  }

  @POST("/api/user/create")
  Call<UserResponse> createUser(@Body UserRequest user);
}
