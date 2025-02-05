package com.entree.shared.modules;

import com.entree.shared.api.EntreeAPI;
import com.entree.shared.libs.JSONObjectConverterFactory;

import retrofit.Retrofit;

// FIXME possibly leverage dagger2 later
public class WebModule {
  private static String mBaseUrl = "http://ec2-52-26-163-35.us-west-2.compute.amazonaws.com:3000";
  private static EntreeAPI mEntreeAPIInstance;

  static {
    mEntreeAPIInstance = new Retrofit.Builder()
            .baseUrl(mBaseUrl)
            .addConverterFactory(new JSONObjectConverterFactory())
            .build()
            .create(EntreeAPI.class);
  }


  public static EntreeAPI getEntreeAPIInstance() {
    return mEntreeAPIInstance;
  }
}
