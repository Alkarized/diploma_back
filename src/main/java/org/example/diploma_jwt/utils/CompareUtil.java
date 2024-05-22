package org.example.diploma_jwt.utils;

import org.example.diploma_jwt.models.Item;

import java.util.HashMap;

public class CompareUtil {
    public static Item findSameByField(String condition, HashMap<String, Item> baseToSearch){
        return baseToSearch.getOrDefault(condition, null);
    }



}
