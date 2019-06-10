package org.experteam.efatura.cloud.oracle.domain;

import lombok.Data;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@XmlRootElement(name = "DATA_DS")
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierDetail {

    @XmlElement(name = "G_1")
    private SupplierDetailItem item;


}
