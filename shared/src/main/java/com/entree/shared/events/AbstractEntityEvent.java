package com.entree.shared.events;

import com.orhanobut.logger.Logger;

public abstract class AbstractEntityEvent<T> {

  private final T mEntity;

  public AbstractEntityEvent(T entity) {
    mEntity = entity;

    try {
      Logger.json(String.valueOf(entity));
    } catch (Exception e) {
      Logger.d(String.valueOf(entity));
    }
  }

  public T getEntity() {
    return mEntity;
  }
}
