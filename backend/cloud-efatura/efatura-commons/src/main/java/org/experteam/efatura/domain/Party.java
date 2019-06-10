package org.experteam.efatura.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Party {
    protected List<Identification> partyIdentification;
    protected String partyName;
    protected PostalAddress postalAddress;
    protected List<String> taxSchemeName;

    public List<Identification> getPartyIdentification() {
        if (partyIdentification == null)
            partyIdentification = new ArrayList<>();

        return partyIdentification;
    }

    public List<String> getTaxSchemeName() {
        if (taxSchemeName == null)
            taxSchemeName = new ArrayList<>();

        return taxSchemeName;
    }
}
