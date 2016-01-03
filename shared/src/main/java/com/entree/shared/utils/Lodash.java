package com.entree.shared.utils;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * This class is reserved for enabling functional programming
 * Add methods accordingly
 *
 * @bluejamesbond externalize in the future
 */
public class Lodash {


  public static <T> T select(T value, T defaultValue) {
    if (value == null) return defaultValue;
    return value;
  }

  public static <T> String join(String delimiter, T... obj) {
    return Joiner.on(delimiter).skipNulls().join(obj);
  }

  public static <T> String join(T[] iterator, String delimiter) {
    return Joiner.on(delimiter).skipNulls().join(iterator);
  }

  public static String join(Iterator<?> iterator, String delimiter) {
    return Joiner.on(delimiter).skipNulls().join(iterator);
  }

  public static String join(Iterable<?> iterator, String delimiter) {
    return Joiner.on(delimiter).skipNulls().join(iterator);
  }

  // uses default value to generify; passing in null screws it up
  public static <T> T select(JSONObject object, String key, @NonNull T def) {
    if (object.has(key)) {
      try {

        Object t = null;

        if (def instanceof Integer) {
          t = object.getInt(key);
        } else if (def instanceof Float) {
          t = object.getDouble(key);
        } else if (def instanceof Double) {
          t = object.getDouble(key);
        } else if (def instanceof Long) {
          t = object.getLong(key);
        } else if (def instanceof String) {
          t = object.getString(key);
        } else if (def instanceof JSONObject) {
          t = object.getJSONObject(key);
        } else if (def instanceof JSONArray) {
          t = object.getJSONArray(key);
        } else if (def instanceof Boolean) {
          t = object.getBoolean(key);
        } else if (def instanceof Date) {
          t = new Date(object.getLong(key));
        }

        return (T) t;
      } catch (Exception e) {
        e.printStackTrace();
        return def;
      }
    } else {
      return def;
    }
  }

  public static String toFixed(double num, int offset) {
    BigDecimal numberBigDecimal = new BigDecimal(num);
    numberBigDecimal = numberBigDecimal.setScale(offset, BigDecimal.ROUND_HALF_UP);
    return numberBigDecimal.toString();
  }

  public static <T, V> V select(List<T> t, Function<T, V> get, V defaultValue, int specificIndex) {
    if (t == null) {
      return defaultValue;
    }

    if (t.size() > specificIndex) {
      return get.apply(t.get(specificIndex));
    }

    return defaultValue;
  }

  public static <T> List<T> emptyList() {
    return new ArrayList<T>(0);
  }

  public static <T, V> V select(List<T> t, Function<T, V> transform, V defaultValue) {
    return select(t, transform, defaultValue, 0);
  }

  public static <T, V> V select(List<T> t, Function<T, V> transform) {
    return select(t, transform, null);
  }

  public static String fromNow(Date date) {
    String res = (String) DateUtils.getRelativeTimeSpanString(date.getTime(), new Date().getTime(), DateUtils.SECOND_IN_MILLIS);
    // Make some stuff shorter
    res = res.replace("minutes", "mins");
    res = res.replace("minute", "min");
    res = res.replace("seconds", "secs");
    res = res.replace("second", "sec");
    return res;
  }

  public static <T> List<T> map(JSONArray array, Function<Object, T> get) {
    List<T> res = new ArrayList<>(array.length());

    for (int i = 0; i < array.length(); i++) {
      try {
        if (get == null) {
          res.add((T) array.get(i));
          continue;
        }
        res.add(get.apply(array.get(i)));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    return res;
  }

  public static <T> List<T> map(JSONArray array) {
    return map(array, null);
  }

  public static <A, B, C> List<C> map(Collection<A> a, Collection<B> b, Mapper<A, B, C> abMap, Function<A, C> aMap, Function<B, C> bMap) {
    List<C> ret = new ArrayList<>();
    Iterator<A> t = a.iterator();
    Iterator<B> g = b.iterator();

    while (t.hasNext() && g.hasNext()) {
      ret.add(abMap.apply(t.next(), g.next()));
    }

    if (aMap != null) {
      while (t.hasNext()) {
        ret.add(aMap.apply(t.next()));
      }
    }

    if (bMap != null) {
      while (g.hasNext()) {
        ret.add(bMap.apply(g.next()));
      }
    }

    return ret;
  }

  public static <A, B, C> List<C> map(Collection<A> a, Collection<B> b, Mapper<A, B, C> abMap, Function<A, C> aMap) {
    return map(a, b, abMap, aMap, null);
  }

  public static <A, C> List<C> map(Collection<A> a, Function<A, C> aMap) {
    List<C> lists = new ArrayList<>(a.size());
    Lodash.each(a, t -> lists.add(aMap.apply(t)));
    return lists;
  }

  public static <A, C> List<C> map(Iterable<A> a, Function<A, C> aMap) {
    List<C> lists = new ArrayList<>();
    Lodash.each(a, t -> lists.add(aMap.apply(t)));
    return lists;
  }

  public static <T> boolean contains(Iterable<T> iterable, Object t) {
    return contains(iterable, m -> {
      if (m == null && t == null) {
        return true;
      } else if (m == null) {
        return t.equals(m);
      } else {
        return m.equals(t);
      }
    });
  }

  public static <T> boolean contains(T[] items, Object t) {
    return contains(items, m -> {
      if (m == null && t == null) {
        return true;
      } else if (m == null) {
        return t.equals(m);
      } else {
        return m.equals(t);
      }
    });
  }

  public static <T> boolean contains(Collection<T> items, Object t) {
    return contains(items, m -> {
      if (m == null && t == null) {
        return true;
      } else if (m == null) {
        return t.equals(m);
      } else {
        return m.equals(t);
      }
    });
  }

  public static <T> boolean contains(Collection<T> items, Predicate<T> t) {
    for (T item : items) {
      if (t.apply(item)) {
        return true;
      }
    }

    return false;
  }

  public static <T> boolean contains(T[] items, Predicate<T> t) {
    return contains(Iterators.forArray(items), t);
  }

  public static <T> boolean contains(Iterator<T> items, Predicate<T> t) {
    while (items.hasNext()) {
      if (t.apply(items.next())) {
        return true;
      }
    }

    return false;
  }

  public static <T> boolean contains(Iterable<T> items, Predicate<T> t) {
    for (T i : items) {
      if (t.apply(i)) {
        return true;
      }
    }

    return false;
  }

  public static <A, C> C reduce(Iterable<A> a, Mapper<C, A, C> reducer, C start) {
    final Object[] m = {start};
    Lodash.each(a, t -> m[0] = (C) reducer.apply((C) m[0], t));
    return (C) m[0];
  }


  public static <A> A find(Collection<A> collection, Predicate<A> predicate) {
    return find(collection, predicate, null);
  }

  public static <A> A find(Iterable<A> collection, Predicate<A> predicate) {
    return find(collection, predicate, null);
  }

  public static <A> A find(A[] collection, Predicate<A> predicate) {
    return find(collection, predicate, null);
  }

  public static <A> A find(Collection<A> collection, Predicate<A> predicate, A defaultValue) {
    return Iterables.find(collection, predicate, defaultValue);
  }

  public static <A> A find(Iterable<A> collection, Predicate<A> predicate, A defaultValue) {
    return Iterables.find(collection, predicate, defaultValue);
  }

  public static <A> A find(A[] collection, Predicate<A> predicate, A defaultValue) {
    return Iterators.find(Iterators.forArray(collection), predicate, defaultValue);
  }

  public static <A> List<A> filter(Collection<A> collection, Predicate<A> predicate) {
    return Lists.newArrayList(Collections2.filter(collection, predicate));
  }

  public static <A> List<A> filter(Iterable<A> collection, Predicate<A> predicate) {
    return Lists.newArrayList(Iterables.filter(collection, predicate));
  }

  public static <A> List<A> filter(A[] c, Predicate<A> predicate) {
    return Lists.newArrayList(Iterators.filter(Iterators.forArray(c), predicate));
  }

  public static <A> List<A> filter(Collection<?> collection, Object t) {
    List<A> list = new ArrayList<>();
    for (Object m : collection) {
      if (m == null && t == null) {
        list.add((A) m);
      } else if (m == null) {
        if (t.equals(m)) {
          list.add((A) m);
        }
      } else if (m.equals(t)) {
        list.add((A) m);
      }
    }

    return list;
  }

  public static <A> List<A> filter(Iterable<?> iterable, Object t) {
    return filter(Lists.newArrayList(iterable), t);
  }

  public static <A> List<A> filter(A[] c, Object t) {
    return filter(Lists.newArrayList(c), t);
  }

  public static <A, C> List<C> map(A[] a, Function<A, C> aMap) {
    List<C> lists = new ArrayList<>(a.length);
    Lodash.each(a, t -> lists.add(aMap.apply(t)));
    return lists;
  }

  public static <A, B, C> List<C> map(Collection<A> a, Collection<B> b, Mapper<A, B, C> abMap) {
    return map(a, b, abMap, null);
  }

  public static <A, B> void each(Collection<A> a, Collection<B> b, ResultCallback2<A, B> abCb, ResultCallback<A> aCb, ResultCallback<B> bCb) {
    Iterator<A> t = a.iterator();
    Iterator<B> g = b.iterator();

    while (t.hasNext() && g.hasNext()) {
      abCb.onResult(t.next(), g.next());
    }

    if (aCb != null) {
      while (t.hasNext()) {
        aCb.onResult(t.next());
      }
    }

    if (bCb != null) {
      while (g.hasNext()) {
        bCb.onResult(g.next());
      }
    }
  }

  public static <A, B> void each(Collection<A> a, Collection<B> b, ResultCallback2<A, B> abCb, ResultCallback<A> aCb) {
    each(a, b, abCb, aCb, null);
  }

  public static <A, B> void each(Collection<A> a, Collection<B> b, ResultCallback2<A, B> abCb) {
    each(a, b, abCb, null);
  }

  public static <A> void each(Collection<A> collection, ResultCallback<A> callback) {
    for (A a : collection) {
      callback.onResult(a);
    }
  }

  public static <A> void each(A[] collection, ResultCallback<A> callback) {
    for (A a : collection) {
      callback.onResult(a);
    }
  }

  public static <A> void each(Iterable<A> iterable, ResultCallback<A> callback) {
    for (A a : iterable) {
      callback.onResult(a);
    }
  }

  public static Object setTimeout(Runnable runnable, long delay) {
    return new TimeoutEvent(runnable, delay);
  }

  public static void clearTimeout(Object timeoutEvent) {
    if (timeoutEvent != null && timeoutEvent instanceof TimeoutEvent) {
      ((TimeoutEvent) timeoutEvent).cancelTimeout();
    }
  }

  public static List<Object> getStaticFields(Class<?> base) {
    return getStaticFields(base, null);
  }

  public static <T> List<T> getStaticFields(Class<?> base, Class<T> type) {
    List<T> list = new ArrayList<>();
    Field[] fields = base.getDeclaredFields();

    for (Field f : fields) {
      f.setAccessible(true); // for private fields
      if (Modifier.isStatic(f.getModifiers())) {
        try {
          if (type != null && type.equals(f.getType())) {
            list.add((T) f.get(null));
          }
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }

    return list;
  }

  public interface Mapper<A, B, C> {
    public C apply(A a, B b);
  }

  public static interface ResultCallback2<T, V> {
    public void onResult(T t, V v);
  }

  public static interface ResultCallback<T> {
    public void onResult(T t);
  }

  private static class TimeoutEvent {
    private static Handler handler = new Handler();
    private volatile Runnable runnable;

    private TimeoutEvent(Runnable task, long delay) {
      runnable = task;
      handler.postDelayed(() -> {
        if (runnable != null) {
          runnable.run();
        }
      }, delay);
    }

    private void cancelTimeout() {
      runnable = null;
    }
  }

  public static <T> String toJSON(T object) {
    return ReflectionToStringBuilder.toString(object, ToStringStyle.JSON_STYLE);
  }
}
