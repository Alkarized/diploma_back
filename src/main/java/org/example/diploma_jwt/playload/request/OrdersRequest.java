package org.example.diploma_jwt.playload.request;

import lombok.Getter;
import lombok.Setter;
import org.example.diploma_jwt.models.usable.CheckOrder;
import org.example.diploma_jwt.models.usable.ManyType;

import java.util.List;

@Getter
@Setter
public class OrdersRequest {
    List<CheckOrder> orders;
 }
