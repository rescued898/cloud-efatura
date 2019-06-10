package org.experteam.efatura.domain.converter;

import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

import java.math.BigDecimal;

public class LongToBigDecimalConverter extends CustomConverter<Long, BigDecimal> {

    @Override
    public BigDecimal convert(Long aLong, Type<? extends BigDecimal> type, MappingContext mappingContext) {
        return BigDecimal.valueOf(aLong);
    }
}
