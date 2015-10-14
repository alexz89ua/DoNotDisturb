package com.alexz.donotdisturb;

import java.util.ArrayList;

/**
 * Created by alex on 14.10.15.
 */
public class TestData {

    public static ArrayList<TriggerItem> getTestCollection(int count) {
        ArrayList<TriggerItem> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add(new TriggerItem("c 22:00 до 7:00"));
        }
        return items;
    }
}
