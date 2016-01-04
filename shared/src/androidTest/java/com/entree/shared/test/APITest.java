package com.entree.shared.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.entree.shared.api.EntreeAPI;
import com.entree.shared.modules.WebModule;
import com.orhanobut.logger.Logger;

import junit.framework.Assert;

import org.junit.Test;

import java.io.IOException;

import retrofit.Call;
import retrofit.Response;

public class APITest extends ActivityInstrumentationTestCase2<Activity> {
  public APITest() {
    super(Activity.class);
  }

  static {
    Logger.init(APITest.class.getSimpleName());
  }

  @Test
  public void testCreateUser() throws IOException {
    EntreeAPI rest = WebModule.getEntreeAPIInstance();
    Call<EntreeAPI.UserResponse> responseCall = rest.createUser(new EntreeAPI.UserRequest("jim", "halpert@gsdf.com", "halpert"));
    Response<EntreeAPI.UserResponse> response = responseCall.execute();
    Logger.json(response.message());
    Assert.assertTrue(true);
  }
}
