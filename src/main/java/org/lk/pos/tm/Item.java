package org.lk.pos.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item implements Serializable {

    private String code;
    private String description;
    private int qty;
    private BigDecimal unitPrice;
}
