package org.example.diploma_jwt.playload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataRequest {
    private List<LocalItem> items;

    @Getter
    @Setter
    public static class LocalItem {
        private double price;
        private String article;
        private String code;
        private String company;
        private String title;
    }


}
