package org.experteam.efatura.cloud.oracle.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Suppliers {
    private List<Supplier> items;
}
