package com.entree.shared.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class ListenedScrollView extends ScrollView {
  private OnScrollViewListener mOnScrollViewListener;

  public ListenedScrollView(Context context) {
    super(context);
  }

  public ListenedScrollView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ListenedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public ListenedScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  public void setOnScrollViewListener(OnScrollViewListener l) {
    this.mOnScrollViewListener = l;
  }

  @Override
  protected void onScrollChanged(int l, int t, int oldl, int oldt) {
    if (mOnScrollViewListener != null) {
      mOnScrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
    }
    super.onScrollChanged(l, t, oldl, oldt);
  }

  public interface OnScrollViewListener {
    void onScrollChanged(ListenedScrollView v, int l, int t, int oldl, int oldt);
  }
}
