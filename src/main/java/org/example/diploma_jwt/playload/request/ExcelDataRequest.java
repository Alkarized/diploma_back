package org.example.diploma_jwt.playload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class ExcelDataRequest {
    List<List<String>> data;
}
