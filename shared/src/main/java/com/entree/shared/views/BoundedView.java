package com.entree.shared.views;


import android.content.Context;
import android.view.View;

import com.github.bluejamesbond.fluid.FluidPersistence;

import butterknife.ButterKnife;

public abstract class BoundedView {
  private View mView;

  public View getView() {
    return mView;
  }

  public BoundedView(View view) {
    mView = view;

    try {
      ButterKnife.bind(this, mView);
    } catch (Exception e) {
      e.printStackTrace();
    }

    FluidPersistence.bind(this);
  }

  public Context getContext() {
    return mView.getContext();
  }
}