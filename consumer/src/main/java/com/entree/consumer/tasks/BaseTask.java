package com.entree.consumer.tasks;

import android.os.AsyncTask;

import com.entree.shared.utils.Lodash;
import com.github.bluejamesbond.fluid.FluidPersistence;

public abstract class BaseTask<A, B, C> extends AsyncTask<A, B, C> {
  private Lodash.ResultCallback<C> mResultCallback;

  public BaseTask() {
    super();

    FluidPersistence.bind(this);
  }

  public void setCallback(Lodash.ResultCallback<C> resultCallback) {
    mResultCallback = resultCallback;
  }

  public Lodash.ResultCallback<C> getCallback() {
    return mResultCallback;
  }

  @Override
  protected void onPostExecute(C c) {
    if (mResultCallback != null) {
      mResultCallback.onResult(c);
    }
  }
}
