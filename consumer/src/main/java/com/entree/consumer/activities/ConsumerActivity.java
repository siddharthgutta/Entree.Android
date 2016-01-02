package com.entree.consumer.activities;

import android.os.Bundle;

import com.entree.consumer.R;
import com.entree.consumer.dispatcher.Dispatcher;
import com.entree.consumer.fragments.ItemFocusFragment;
import com.entree.consumer.stores.ItemStore;
import com.github.bluejamesbond.fluid.AbstractFluidFragmentManager;
import com.github.bluejamesbond.fluid.FluidActivity;
import com.github.bluejamesbond.fluid.FluidFragment;
import com.github.bluejamesbond.fluid.FluidFragmentManager;
import com.github.bluejamesbond.fluid.FluidPersistence;
import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;

public class ConsumerActivity extends FluidActivity {

  static {
    Logger.init(ConsumerActivity.class.getSimpleName());
  }

  public static final String FRAGMENT_CONTEXT_BASE = "FRAGMENT_CONTEXT_BASE";

  private FluidFragmentManager<ConsumerActivity> mMainFragmentManager;

  @Override
  public void onActivityRegisterPersistence() {
    FluidPersistence.register(this, Dispatcher.class, ItemStore.class);
  }

  @Override
  public void performBackPressAction() {
    if (mMainFragmentManager.hasActiveTransaction()) {
      return;
    }

    mMainFragmentManager.toPreviousFragment();
  }

  @Override
  public void onActivityCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_base);

    try {
      ButterKnife.bind(this);
    } catch (Exception e) {
      e.printStackTrace();
    }

    mMainFragmentManager = new FluidFragmentManager<>(this, R.id.base_content_inset, true);
    mMainFragmentManager.toFragment(ItemFocusFragment.newInstance(), FRAGMENT_CONTEXT_BASE);

  }

  @Override
  public void onFragmentChange(AbstractFluidFragmentManager fluidFragmentManager, FluidFragment from, FluidFragment to, String context, Bundle args) {

  }
}
