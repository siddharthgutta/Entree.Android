package com.entree.shared.libs;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
  public interface OnBroadcastReceiver {
    void onReceive(Context context, Intent intent);
  }

  private OnBroadcastReceiver mOnBroadcastReceiver;

  public BroadcastReceiver(@NonNull OnBroadcastReceiver onBroadcastReceiver) {
    super();
    mOnBroadcastReceiver = onBroadcastReceiver;
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    mOnBroadcastReceiver.onReceive(context, intent);
  }
}
