package com.entree.shared.api;

import com.entree.shared.utils.Lodash;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface EntreeAPI {
  @POST("/api/user/create")
  Call<Lodash.JSONObject> createUser(@Body Lodash.JSONObject user);
}
