package com.entree.consumer.stores;

import android.content.Context;

import com.entree.consumer.dispatcher.Dispatcher;
import com.entree.shared.events.AbstractEntityEvent;
import com.entree.shared.models.Item;
import com.github.bluejamesbond.fluid.FluidStore;
import com.github.bluejamesbond.fluid.annotations.FluidPersistent;
import com.google.common.eventbus.Subscribe;
import com.orhanobut.logger.Logger;

public class ItemStore extends FluidStore {

  public static final class Event {
    public static final class NewItemReceived extends AbstractEntityEvent<Item> {
      public NewItemReceived(Item entity) {
        super(entity);
      }
    }

  }

  @FluidPersistent Dispatcher mDispatcher;

  public ItemStore(Context applicationContext) {
    super(applicationContext);
  }

  @Override
  public void onInitialize() {
    super.onInitialize();
  }

  @Subscribe
  public void onItemsUpdateRequest(Dispatcher.Event.ItemsUpdateRequest event) {
    refreshItems();
  }

  public void refreshItems() {
    Logger.d("Refreshing!!!");
  }
}
