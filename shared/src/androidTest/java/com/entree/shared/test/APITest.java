package com.entree.shared.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.entree.shared.api.EntreeAPI;
import com.entree.shared.modules.WebModule;
import com.entree.shared.utils.Lodash;
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
    Lodash.JSONObject req = Lodash.json().puts("username", "jim", "password", "halpert", "email", "yolocat");
    Call<Lodash.JSONObject> responseCall = rest.createUser(req);
    Response<Lodash.JSONObject> response = responseCall.execute();
    Logger.json(response.body().toString());
    Assert.assertEquals("jim", response.body().get("data", Lodash.json()).get("username", "none"));
  }
}
