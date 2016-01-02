package com.entree.shared.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.entree.shared.R;

public class FontTextView extends TextView {

  public FontTextView(Context context) {
    super(context);
  }

  public FontTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setCustomFont(context, attrs);
  }

  public FontTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setCustomFont(context, attrs);
  }

  private void setCustomFont(Context ctx, AttributeSet attrs) {
    if (isInEditMode()) {
      return;
    }

    TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.FontTextView);
    String customFont = a.getString(R.styleable.FontTextView_ft_customFont);
    setCustomFont(ctx, customFont);
    a.recycle();
  }

  public boolean setCustomFont(Context ctx, String asset) {
    Typeface tf;
    try {
      tf = Typeface.createFromAsset(ctx.getAssets(), asset);
    } catch (Exception e) {
//      Log.e(TAG, "Could not get typeface: " + asset, e);
      return false;
    }

    setTypeface(tf);
    return true;
  }

}