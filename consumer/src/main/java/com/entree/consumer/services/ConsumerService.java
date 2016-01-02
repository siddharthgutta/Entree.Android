package com.entree.consumer.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.entree.shared.libs.BroadcastReceiver;
import com.entree.shared.utils.Lodash;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.socket.client.Socket;

public class ConsumerService extends Service {

  static {
    Logger.init(ConsumerService.class.getSimpleName());
  }

  private static final String ACTION_SOCKET = "ACTION_SOCKET";
  private static final String EXTRA_SOCKET_ENABLED = "EXTRA_SOCKET_ENABLED";

  private Socket mSocket;
  private List<BroadcastReceiver> mReceivers;

  @Override
  public void onCreate() {
    super.onCreate();

    register(new BroadcastReceiver((context, intent) -> {
      setActionSocket(intent.getBooleanExtra(EXTRA_SOCKET_ENABLED, false));
    }), ACTION_SOCKET);
  }

  private void setActionSocket(boolean enabled) {
    if (enabled) {
      // do something
    } else {
      // do something else
    }
  }

  @Override
  public void onDestroy() {
    Lodash.each(mReceivers, this::unregisterReceiver);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  public static void setSystemNotificationEnabled(Context context, boolean enabled) {
    Intent intent = new Intent();
    intent.putExtra(EXTRA_SOCKET_ENABLED, enabled);
    intent.setAction(ACTION_SOCKET);
    intent.setClass(context, ConsumerService.class);
    context.sendBroadcast(intent);
  }

  public void register(BroadcastReceiver receiver, String... actions) {
    IntentFilter filter = new IntentFilter();
    Lodash.each(actions, filter::addAction);

    registerReceiver(receiver, filter);
    mReceivers.add(receiver);
  }
}
