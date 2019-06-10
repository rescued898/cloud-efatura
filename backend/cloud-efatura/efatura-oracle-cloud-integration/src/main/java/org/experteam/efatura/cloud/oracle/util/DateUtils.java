package org.experteam.efatura.cloud.oracle.util;

import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Slf4j
public class DateUtils {
    public static XMLGregorianCalendar toXmlCalendar(LocalDate date) {
        try {
            GregorianCalendar gcal = GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault()));
            XMLGregorianCalendar xcal = DatatypeFactory.newInstance().newXMLGregorianCalendar(gcal);
            xcal.setTimezone(DatatypeConstants.FIELD_UNDEFINED);

            return xcal;
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            return null;
        }
    }

}
