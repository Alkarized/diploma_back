package org.example.diploma_jwt.playload.request;


import lombok.Getter;
import lombok.Setter;
import org.example.diploma_jwt.models.Excel;

@Getter
@Setter
public class ExcelFieldsRequest {
    private String article;
    private String price;
    private String code;
    private String company;
    private String title;
}
