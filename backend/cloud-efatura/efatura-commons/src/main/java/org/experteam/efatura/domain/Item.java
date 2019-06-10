package org.experteam.efatura.domain;

import lombok.Data;

@Data
public class Item {
    private String name;
    private String sellersId;
    private Amount price;
}
