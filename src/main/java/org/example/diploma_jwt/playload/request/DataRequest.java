package org.example.diploma_jwt.playload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataRequest {
    private List<LocalItem> items;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LocalItem {
        private double price;
        private String article;
        private String code;
        private String company;
        private String title;


    }




}
