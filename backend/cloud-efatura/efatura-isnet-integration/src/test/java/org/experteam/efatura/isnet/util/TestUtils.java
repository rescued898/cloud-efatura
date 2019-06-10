package org.experteam.efatura.isnet.util;

import javax.xml.namespace.QName;

public class TestUtils {
    public static String ISBANK_ENTEGRATOR_INVOICE_SERVICE_WSDL = "http://einvoiceservicetest.isnet.net.tr/InvoiceService/ServiceContract/InvoiceService.svc?wsdl";
    public static String ISBANK_ENTEGRATOR_INVOICE_SERVICE_ENDPOINT = "http://einvoiceservicetest.isnet.net.tr/InvoiceService/ServiceContract/InvoiceService.svc";
    public static final QName IE_QNAME = new QName("http://tempuri.org/", "InvoiceService");
    public static final QName _InvoiceResultSet_QNAME = new QName("http://schemas.datacontract.org/2004/07/EInvoice.Service.Model", "ResultSet");
    public static final String ISBANK_TEST_VERGI_KODU = "1234567805";
    public static final String UBL_INVOICE_EXAMPLE =
            "<Invoice xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\" xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\" xsi:schemaLocation=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2 ..\\xsd\\maindoc\\UBL-Invoice-2.1.xsd.xsd\" xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\">\n" +
                    "  <cbc:UBLVersionID>2.1</cbc:UBLVersionID>\n" +
                    "  <cbc:CustomizationID>TR1.2</cbc:CustomizationID>\n" +
                    "  <cbc:ProfileID>TICARIFATURA</cbc:ProfileID>\n" +
                    "  <cbc:ID>ATR2019000000218</cbc:ID>\n" +
                    "  <cbc:UUID>8de84df7-2585-47cb-b1f4-cf2b6d372d39</cbc:UUID>\n" +
                    "</Invoice>";
}
