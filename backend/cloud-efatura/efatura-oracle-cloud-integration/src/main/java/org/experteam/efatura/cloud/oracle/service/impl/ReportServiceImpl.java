package org.experteam.efatura.cloud.oracle.service.impl;

import com.oracle.xmlns.oxp.service.v2.*;
import lombok.extern.slf4j.Slf4j;
import org.experteam.efatura.cloud.oracle.domain.SupplierDetail;
import org.experteam.efatura.cloud.oracle.domain.SupplierDetailItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import javax.xml.bind.JAXB;
import java.io.StringReader;

@Slf4j
public class ReportServiceImpl extends WebServiceGatewaySupport {

    @Value("${client.report-service.supplier-dtl-path}")
    private String SUPPLIER_DTL_REPORT_PATH;

    @Value("${client.user.name}")
    private String userName;

    @Value("${client.user.password}")
    private String userPassword;

    public SupplierDetailItem getSupplierDetail(String endpointUrl, String supplierId, String currencyCode) {
        RunReport runReport = new RunReport();

        runReport.setUserID(userName);
        runReport.setPassword(userPassword);

        ReportRequest reportRequest = new ReportRequest();

        reportRequest.setReportAbsolutePath(SUPPLIER_DTL_REPORT_PATH);

        ObjectFactory objectFactory = new ObjectFactory();

        ParamNameValues paramNameValues = objectFactory.createParamNameValues();

        ArrayOfParamNameValue arrayOfParamNameValue = objectFactory.createArrayOfParamNameValue();

        ParamNameValue currencyCodeParam = objectFactory.createParamNameValue();
        currencyCodeParam.setName("CURRENCY_CODE");

        ArrayOfString currencyCodeParamValue = objectFactory.createArrayOfString();
        currencyCodeParamValue.getItem().add(currencyCode);
        currencyCodeParam.setValues(currencyCodeParamValue);

        arrayOfParamNameValue.getItem().add(currencyCodeParam);

        ParamNameValue supplierIdParam = objectFactory.createParamNameValue();
        supplierIdParam.setName("SUPPLIER_ID");

        ArrayOfString supplierIdParamValue = objectFactory.createArrayOfString();
        supplierIdParamValue.getItem().add(supplierId);
        supplierIdParam.setValues(supplierIdParamValue);

        arrayOfParamNameValue.getItem().add(supplierIdParam);

        paramNameValues.setListOfParamNameValues(arrayOfParamNameValue);

        reportRequest.setParameterNameValues(paramNameValues);
        reportRequest.setSizeOfDataChunkDownload(-1);

        runReport.setReportRequest(reportRequest);

        RunReportResponse response = (RunReportResponse) getWebServiceTemplate()
                .marshalSendAndReceive(endpointUrl, runReport,
                        new SoapActionCallback("http://xmlns.oracle.com/oxp/service/v2/runReport"));

        log.info(new String(response.getRunReportReturn().getReportBytes()));

        SupplierDetail supplierDetail = JAXB.unmarshal(new StringReader(new String(response.getRunReportReturn().getReportBytes())), SupplierDetail.class);

        log.info(supplierDetail.toString());

        return supplierDetail.getItem();

    }

}
