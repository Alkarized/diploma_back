package org.example.diploma_jwt.interfaces;

import org.example.diploma_jwt.models.Item;
import org.example.diploma_jwt.models.Settings;
import org.example.diploma_jwt.models.usable.ParsedExcelData;

public interface ItemComparable {
    public boolean compareItems(Item item, ParsedExcelData data, Settings settings);
}
