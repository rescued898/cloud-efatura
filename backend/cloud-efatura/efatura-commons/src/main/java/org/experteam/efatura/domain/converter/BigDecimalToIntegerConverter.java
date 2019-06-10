package org.experteam.efatura.domain.converter;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

import java.math.BigDecimal;

public class BigDecimalToIntegerConverter extends CustomConverter<BigDecimal, Integer> {
    @Override
    public Integer convert(BigDecimal bigDecimal, Type<? extends Integer> type, MappingContext mappingContext) {
        return bigDecimal.intValue();
    }
}
