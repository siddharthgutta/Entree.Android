package com.entree.consumer.tasks;

import com.entree.shared.libs.external.Perf;
import com.entree.shared.models.Item;
import com.entree.shared.utils.Lodash;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

// FIXME - use retrofit!
public class GetItems extends BaseTask<String, Void, List<Item>> {
  private static OkHttpClient mClient = new OkHttpClient();

  @Override
  protected List<Item> doInBackground(String... params) {
    Request request = new Request.Builder()
            .url(Lodash.join(params, ""))
            .cacheControl(CacheControl.FORCE_NETWORK) // disable cache
            .build();

    Perf perf = new Perf("doInBackground");

    try {
      Response response = mClient.newCall(request).execute();
      String body = response.body().string();
      perf.addSplit("Received response");
      JSONObject json = new JSONArray(body).getJSONObject(1).getJSONObject("data");
      List<Item> items = Lodash.map(Lodash.select(json, "children", new JSONArray()), input -> Item.from((JSONObject) input));
      perf.addSplit("Parsed " + json.length() + " posts");
      return items;
    } catch (IOException | JSONException e) {
      e.printStackTrace();
    } finally {
      perf.dump();
    }

    return Lodash.emptyList();
  }
}
