package com.entree.shared.libs;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.entree.shared.utils.Lodash;

public class OnKeyEnterListener implements View.OnKeyListener {

  private final Lodash.ResultCallback<CharSequence> mEnter;
  private final Lodash.ResultCallback<Void> mEmpty;

  public OnKeyEnterListener(Lodash.ResultCallback<CharSequence> enter, Lodash.ResultCallback<Void> empty) {
    mEmpty = empty;
    mEnter = enter;
  }

  @Override
  public boolean onKey(View v, int keyCode, KeyEvent event) {
    CharSequence s = ((EditText) (v)).getText();
    if (event.getAction() == KeyEvent.ACTION_DOWN) {
      switch (keyCode) {
        case KeyEvent.KEYCODE_DPAD_CENTER:
        case KeyEvent.KEYCODE_ENTER:
          mEnter.onResult(s);
          return true;
        default:
          break;
      }
    } else if (s.toString().trim().length() == 0) {
      mEmpty.onResult(null);
      return true;
    }

    return false;
  }
}
