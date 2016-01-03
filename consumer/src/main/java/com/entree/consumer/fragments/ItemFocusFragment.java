package com.entree.consumer.fragments;

import android.os.Bundle;
import android.view.View;

import com.entree.consumer.R;
import com.entree.consumer.activities.ConsumerActivity;
import com.github.bluejamesbond.fluid.FluidFragment;
import com.github.bluejamesbond.fluid.FluidPostpone;
import com.github.bluejamesbond.fluid.annotations.FluidFragmentDidMount;
import com.github.bluejamesbond.fluid.annotations.FluidLayout;
import com.orhanobut.logger.Logger;

@FluidLayout(R.layout.fragment_item_focus)
public class ItemFocusFragment extends FluidFragment<ConsumerActivity> {
  public static ItemFocusFragment newInstance() {
    Bundle args = new Bundle();
    ItemFocusFragment fragment = new ItemFocusFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onFragmentDidMount(ConsumerActivity activity, View view) {
    Logger.d("Mounted");
  }

  @Override
  public boolean onFragmentWillDismount(ConsumerActivity activity, View view, FluidPostpone postpone) {
    Logger.d("Destroyed; return true if you will manually invoke postpone.run()");
    return false;
  }

  @Override
  public void onFragmentUpdate(ConsumerActivity activity, View view, Bundle prevArgs) {
    Logger.d("Updated with new arguments");
  }

  @FluidFragmentDidMount
  public void printHello() {
    Logger.d("Hello!");
  }
}
