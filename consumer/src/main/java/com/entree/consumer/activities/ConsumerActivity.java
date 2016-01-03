package com.entree.consumer.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

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

  private void applyWindowProps() {
    Window window = getWindow();

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
    }
  }

  @Override
  public void onActivityCreate(Bundle savedInstanceState) {

    applyWindowProps();

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
