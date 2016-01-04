package com.entree.shared.libs;

import com.entree.shared.utils.Lodash;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

public class JSONObjectConverterFactory extends Converter.Factory {
  static final MediaType MEDIA_TYPE = MediaType.parse("application/json");

  @Override
  public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
    if (Lodash.JSONObject.class.equals(type)) {
      return value -> {
        try {
          return new Lodash.JSONObject(value.string());
        } catch (JSONException e) {
          e.printStackTrace();
        }

        return Lodash.json();
      };
    }

    return null;
  }

  @Override
  public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
    if (Lodash.JSONObject.class.equals(type)) {
      return new Converter<Lodash.JSONObject, RequestBody>() {
        @Override
        public RequestBody convert(Lodash.JSONObject value) throws IOException {
          return RequestBody.create(MEDIA_TYPE, value.toString());
        }
      };
    }

    return null;
  }
}