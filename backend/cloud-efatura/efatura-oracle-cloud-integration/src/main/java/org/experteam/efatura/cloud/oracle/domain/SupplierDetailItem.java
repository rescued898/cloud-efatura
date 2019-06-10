package org.experteam.efatura.cloud.oracle.domain;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierDetailItem {

    @XmlElement(name = "VENDOR_ID")
    private Long supplierId;

    @XmlElement(name = "PARTY_NAME")
    private String partyName;

    @XmlElement(name = "REP_REGISTRATION_NUMBER")
    private String taxRegNumber;

    @XmlElement(name = "PARTY_ID")
    private Long partyId;

    @XmlElement(name = "PARTY_SITE_ID")
    private Long partySiteId;

    @XmlElement(name = "IBAN")
    private String iban;

    @XmlElement(name = "PAYMENT_METHOD")
    private String paymentMethod;
}
