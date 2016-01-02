package com.entree.shared.models;

import org.json.JSONObject;

public class Item {

  public static Item from(JSONObject obj) {
    return new Item();
  }
}

