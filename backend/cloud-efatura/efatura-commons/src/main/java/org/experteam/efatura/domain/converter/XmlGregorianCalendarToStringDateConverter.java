package org.experteam.efatura.domain.converter;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class XmlGregorianCalendarToStringDateConverter extends CustomConverter<XMLGregorianCalendar, String> {
    @Override
    public String convert(XMLGregorianCalendar xmlCalendar, Type<? extends String> type, MappingContext mappingContext) {
        Calendar calendar = xmlCalendar.toGregorianCalendar();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(calendar.getTimeZone());
        return formatter.format(calendar.getTime());
    }
}
