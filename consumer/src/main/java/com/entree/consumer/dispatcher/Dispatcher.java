package com.entree.consumer.dispatcher;

import android.content.Context;

import com.github.bluejamesbond.fluid.FluidDispatcher;

public class Dispatcher extends FluidDispatcher {

  public static final class Event {
    public static final class ItemsUpdateRequest {

    }
  }

  public Dispatcher(Context applicationContext) {
    super(applicationContext);
  }
}