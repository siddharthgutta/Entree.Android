package com.entree.consumer.fragments;

import android.os.Bundle;

import com.entree.consumer.R;
import com.entree.consumer.activities.ConsumerActivity;
import com.github.bluejamesbond.fluid.FluidFragment;
import com.github.bluejamesbond.fluid.annotations.FluidLayout;

@FluidLayout(R.layout.fragment_item_focus)
public class ItemFocusFragment extends FluidFragment<ConsumerActivity> {
  public static ItemFocusFragment newInstance() {
    Bundle args = new Bundle();
    ItemFocusFragment fragment = new ItemFocusFragment();
    fragment.setArguments(args);
    return fragment;
  }
}
