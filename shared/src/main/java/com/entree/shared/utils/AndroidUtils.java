package com.entree.shared.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.entree.shared.libs.FadePicassoDrawable;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Set;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class AndroidUtils {
  public static void applyBlurTransform(final ImageView destView, String url, final float fadeInDuration, int width, int height) {

    Context context = destView.getContext();

    // / TODO performance impacts; fix as necessary - pause on scroll perhaps
    Target fadeInTarget = new Target() {
      @Override
      public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        FadePicassoDrawable.setBitmap(destView, destView.getContext(), bitmap, fadeInDuration);
      }

      @Override
      public void onBitmapFailed(Drawable errorDrawable) {
      }

      @Override
      public void onPrepareLoad(Drawable placeHolderDrawable) {
      }
    };

    destView.setTag(fadeInTarget);
    destView.setScaleType(ImageView.ScaleType.FIT_XY);

    Picasso picasso = Picasso.with(destView.getContext());

    picasso.setIndicatorsEnabled(false);
    picasso.setDebugging(false);
    picasso.load(url)
            .resize(width, height)
            .centerCrop()
            .priority(Picasso.Priority.LOW)
            .config(Bitmap.Config.RGB_565)
            .transform(new BlurTransformation(context, 25))
            .noFade()
            .into(fadeInTarget);
  }

  public static void setViewBackgroundWithoutResettingPadding(View v, int backgroundResource) {
    int paddingBottom = v.getPaddingBottom();
    int paddingTop = v.getPaddingTop();
    int paddingLeft = v.getPaddingLeft();
    int paddingRight = v.getPaddingRight();

    v.setBackgroundResource(backgroundResource);
    v.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
  }

  public static <T extends View> T inflate(Context context, int layout) {
    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    return (T) inflater.inflate(layout, null);
  }

  public static int getScreenWidth(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  public static int getScreenHeight(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  public static float getScreenDensity(Context context) {
    return context.getResources().getDisplayMetrics().density;
  }

  public static int dpToInt(int dp, Context context) {
    return (int) (dp + getScreenDensity(context) + 0.5f);
  }

  public static void fadeInImageView(final ImageView destView, String url, final float fadeInDuration) {
    destView.setAlpha(0f);
    destView.setScaleType(ImageView.ScaleType.CENTER_CROP);

    Picasso picasso = Picasso.with(destView.getContext());

    picasso.setIndicatorsEnabled(false);
    picasso.setDebugging(false);
    picasso.load(url)
            .priority(Picasso.Priority.LOW)
            .config(Bitmap.Config.RGB_565)
            .fit()
            .centerCrop()
            .noFade()
            .into(destView, new Callback() {
              @Override
              public void onSuccess() {
                if (fadeInDuration <= 0) {
                  destView.setAlpha(1f);
                  return;
                }

                destView.animate().cancel();
                destView.animate().alpha(1f).start();
              }

              @Override
              public void onError() {
                if (fadeInDuration <= 0) {
                  destView.setAlpha(1f);
                  return;
                }

                destView.animate().cancel();
                destView.animate().alpha(1f).start();
              }
            });
  }

  public static void applyBlurTransform(final ImageView destView, String url, final float fadeInDuration) {
    applyBlurTransform(destView, url, fadeInDuration, 300, 500);
  }

  public static int getColorFromTheme(Activity activity, int attr) {
    TypedValue typedValue = new TypedValue();
    Resources.Theme theme = activity.getTheme();
    theme.resolveAttribute(attr, typedValue, true);
    return typedValue.data;
  }

  public static void expand(final View v) {
    if ("EXPANDING".equals(v.getTag()) && v.getVisibility() != View.VISIBLE) return;
    v.setTag("EXPANDING");

    v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    final int targetHeight = v.getMeasuredHeight();

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    v.getLayoutParams().height = 1;
    v.setVisibility(View.VISIBLE);
    Animation a = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime == 1) {
          v.setTag("");
        }

        v.getLayoutParams().height = interpolatedTime == 1
                ? ViewGroup.LayoutParams.WRAP_CONTENT
                : (int) (targetHeight * interpolatedTime);
        v.requestLayout();
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };

    // 1dp/ms
    a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
    v.clearAnimation();
    v.startAnimation(a);
  }

  public static void collapse(final View v) {
    if ("COLLAPSING".equals(v.getTag()) && v.getVisibility() != View.GONE) return;
    v.setTag("COLLAPSING");

    final int initialHeight = v.getMeasuredHeight();
    Animation a = new Animation() {
      @Override
      protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime == 1) {
          v.setVisibility(View.GONE);
          v.setTag("");
        } else {
          v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
          v.requestLayout();
        }
      }

      @Override
      public boolean willChangeBounds() {
        return true;
      }
    };

    // 1dp/ms
    a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density) * 2);
    v.clearAnimation();
    v.startAnimation(a);
  }

  public static void vibrate(Activity activity, int ms) {
    Vibrator vibe = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
    vibe.vibrate(ms);
  }

  public static void vibrate(Activity activity) {
    vibrate(activity, 50);
  }

  public static <T> void putStore(Context context, String prefName, String key, T value) {
    SharedPreferences.Editor editor = context.getSharedPreferences(prefName, Context.MODE_PRIVATE).edit();

    if (value == null) {
      editor.putString(key, "");
    } else if (value instanceof Integer) {
      editor.putInt(key, (Integer) value);
    } else if (value instanceof String) {
      editor.putString(key, (String) value);
    } else if (value instanceof Boolean) {
      editor.putBoolean(key, (Boolean) value);
    } else if (value instanceof Long) {
      editor.putLong(key, (Long) value);
    } else if (value instanceof Set) {
      editor.putStringSet(key, (Set<String>) value);
    }

    editor.apply();
  }

  @SuppressWarnings("unchecked")
  public static <T> T getStore(Context context, String prefName, String key, @NonNull T def) {

    SharedPreferences editor = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);

    if (def == null) {
      String str = editor.getString(key, "");
      return str == null ? null : (T) str;
    } else if (def instanceof Integer) {
      Integer str = editor.getInt(key, (Integer) def);
      return (T) (str);
    } else if (def instanceof String) {
      String str = editor.getString(key, (String) def);
      return str == null ? null : (T) (str);
    } else if (def instanceof Boolean) {
      Boolean str = editor.getBoolean(key, (Boolean) def);
      return (T) (str);
    } else if (def instanceof Long) {
      Long str = editor.getLong(key, (Long) def);
      return (T) (str);
    } else if (def instanceof Set) {
      Set<String> str = editor.getStringSet(key, (Set<String>) def);
      return (T) (str);
    }

    return null;
  }
}
