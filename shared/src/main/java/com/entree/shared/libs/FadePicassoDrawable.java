package com.entree.shared.libs;

/**
 * Created by mkurian on 11/22/15.
 */
/*
 * Copyright (C) 2013 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.SystemClock;
import android.widget.ImageView;

import static android.graphics.Color.WHITE;

public final class FadePicassoDrawable extends BitmapDrawable {
  // Only accessed from main thread.
  private static final Paint DEBUG_PAINT = new Paint();
  private static final float FADE_DURATION = 200f; //ms
  private final boolean debugging;
  private final float density;
  Drawable placeholder;
  long startTimeMillis;
  boolean animating;
  int alpha = 0xFF;
  private float fadeInDuration;

  FadePicassoDrawable(Context context, Bitmap bitmap, Drawable placeholder, float fadeInDuration) {
    super(context.getResources(), bitmap);

    this.debugging = true;
    this.density = context.getResources().getDisplayMetrics().density;
    this.fadeInDuration = fadeInDuration;

    boolean fade = true;
    if (fade) {
      this.placeholder = placeholder;
      animating = true;
      startTimeMillis = SystemClock.uptimeMillis();
    }
  }

  /**
   * Create or update the drawable on the target {@link ImageView} to display the supplied bitmap
   * image.
   */
  public static void setBitmap(ImageView target, Context context, Bitmap bitmap, float fadeInDuration) {
    Drawable placeholder = target.getDrawable();
    if (placeholder instanceof AnimationDrawable) {
      ((AnimationDrawable) placeholder).stop();
    }
    FadePicassoDrawable drawable =
            new FadePicassoDrawable(context, bitmap, placeholder, fadeInDuration);
    target.setImageDrawable(drawable);
  }

  /**
   * Create or update the drawable on the target {@link ImageView} to display the supplied
   * placeholder image.
   */
  static void setPlaceholder(ImageView target, Drawable placeholderDrawable) {
    target.setImageDrawable(placeholderDrawable);
    if (target.getDrawable() instanceof AnimationDrawable) {
      ((AnimationDrawable) target.getDrawable()).start();
    }
  }

  private static Path getTrianglePath(Point p1, int width) {
    Point p2 = new Point(p1.x + width, p1.y);
    Point p3 = new Point(p1.x, p1.y + width);

    Path path = new Path();
    path.moveTo(p1.x, p1.y);
    path.lineTo(p2.x, p2.y);
    path.lineTo(p3.x, p3.y);

    return path;
  }

  public void stop() {
    this.animating = false;
  }

  @Override
  public void draw(Canvas canvas) {
    if (!animating) {
      super.draw(canvas);
    } else {
      float normalized = (SystemClock.uptimeMillis() - startTimeMillis) / fadeInDuration;
      if (normalized >= 1f) {
        animating = false;
        placeholder = null;
        super.draw(canvas);
      } else {
        if (placeholder != null) {
          placeholder.draw(canvas);
        }

        int partialAlpha = (int) (alpha * normalized);
        super.setAlpha(partialAlpha);
        super.draw(canvas);
        super.setAlpha(alpha);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
          invalidateSelf();
        }
      }
    }

    if (debugging) {
      drawDebugIndicator(canvas);
    }
  }

  @Override
  public void setAlpha(int alpha) {
    this.alpha = alpha;
    if (placeholder != null) {
      placeholder.setAlpha(alpha);
    }
    super.setAlpha(alpha);
  }

  @Override
  public void setColorFilter(ColorFilter cf) {
    if (placeholder != null) {
      placeholder.setColorFilter(cf);
    }
    super.setColorFilter(cf);
  }

  @Override
  protected void onBoundsChange(Rect bounds) {
    if (placeholder != null) {
      placeholder.setBounds(bounds);
    }
    super.onBoundsChange(bounds);
  }

  private void drawDebugIndicator(Canvas canvas) {
    DEBUG_PAINT.setColor(WHITE);
    Path path = getTrianglePath(new Point(0, 0), (int) (16 * density));
    canvas.drawPath(path, DEBUG_PAINT);

//        DEBUG_PAINT.setColor(loadedFrom.debugColor);
    path = getTrianglePath(new Point(0, 0), (int) (15 * density));
    canvas.drawPath(path, DEBUG_PAINT);
  }
}