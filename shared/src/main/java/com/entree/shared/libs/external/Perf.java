package com.entree.shared.libs.external;

/*
 * Copyright (C) 2007 The Android Open Source Project
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

import android.os.SystemClock;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * A utility class to help log timings splits throughout a method call.
 * Typical usage is:
 * <p>
 * <pre>
 *     Perf timings = new Perf(TAG, "methodA");
 *     // ... do some work A ...
 *     timings.addSplit("work A");
 *     // ... do some work B ...
 *     timings.addSplit("work B");
 *     // ... do some work C ...
 *     timings.addSplit("work C");
 *     timings.dump();
 * </pre>
 * <p>
 * <p>The dump call would add the following to the log:</p>
 * <p>
 * <pre>
 *     D/TAG     ( 3459): methodA: begin
 *     D/TAG     ( 3459): methodA:      9 ms, work A
 *     D/TAG     ( 3459): methodA:      1 ms, work B
 *     D/TAG     ( 3459): methodA:      6 ms, work C
 *     D/TAG     ( 3459): methodA: end, 16 ms
 * </pre>
 */
public class Perf {

  /**
   * A label to be included in every log.
   */
  private String mLabel;

  /**
   * Used to track whether Log.isLoggable was enabled at reset time.
   */
  private boolean mDisabled;

  /**
   * Stores the time of each split.
   */
  ArrayList<Long> mSplits;

  /**
   * Stores the labels for each split.
   */
  ArrayList<String> mSplitLabels;

  /**
   * Create and initialize a Perf object that will log using
   * the specific tag. If the Log.isLoggable is not enabled to at
   * least the Log.VERBOSE level for that tag at creation time then
   * the addSplit and dump call will do nothing.
   *
   * @param label a string to be displayed with each log
   */
  public Perf(String label) {
    reset(label);
  }

  /**
   * Clear and initialize a Perf object that will log using
   * the specific tag. If the Log.isLoggable is not enabled to at
   * least the Log.VERBOSE level for that tag at creation time then
   * the addSplit and dump call will do nothing.
   *
   * @param label a string to be displayed with each log
   */
  public void reset(String label) {
    mLabel = label;
    reset();
  }

  /**
   * Clear and initialize a Perf object that will log using
   * the tag and label that was specified previously, either via
   * the constructor or a call to reset(tag, label). If the
   * Log.isLoggable is not enabled to at least the Log.VERBOSE
   * level for that tag at creation time then the addSplit and
   * dump call will do nothing.
   */
  public void reset() {
    if (mDisabled) return;
    if (mSplits == null) {
      mSplits = new ArrayList<Long>();
      mSplitLabels = new ArrayList<String>();
    } else {
      mSplits.clear();
      mSplitLabels.clear();
    }
    addSplit(null);
  }

  /**
   * Add a split for the current time, labeled with splitLabel. If
   * Log.isLoggable was not enabled to at least the Log.VERBOSE for
   * the specified tag at construction or reset() time then this
   * call does nothing.
   *
   * @param splitLabel a label to associate with this split.
   */
  public void addSplit(String splitLabel) {
    long now = SystemClock.elapsedRealtime();
    mSplits.add(now);
    mSplitLabels.add(splitLabel);
  }

  /**
   * Dumps the timings to the log using Log.d(). If Log.isLoggable was
   * not enabled to at least the Log.VERBOSE for the specified tag at
   * construction or reset() time then this call does nothing.
   */
  public void dump() {
    StringBuilder smb = new StringBuilder();
    smb.append("begin\n");
    final long first = mSplits.get(0);
    long now = first;
    for (int i = 1; i < mSplits.size(); i++) {
      now = mSplits.get(i);
      final String splitLabel = mSplitLabels.get(i);
      final long prev = mSplits.get(i - 1);

      smb.append("     ").append(now - prev).append(" ms, ").append(splitLabel).append("\n");
    }
    smb.append("end, ").append(now - first).append(" ms");
    Logger.d(smb.toString());
  }
}