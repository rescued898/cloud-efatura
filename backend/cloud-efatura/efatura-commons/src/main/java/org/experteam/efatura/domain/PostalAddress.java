package org.experteam.efatura.domain;

import lombok.Data;

@Data
public class PostalAddress {
    protected String streetName;
    protected String blockName;
    protected String buildingName;
    protected String buildingNumber;
    protected String citySubdivisionName;
    protected String cityName;
    protected String postalZone;
    protected String district;
    protected String country;
}
