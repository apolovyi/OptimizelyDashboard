package com.dashboard.domain.converter;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;

public class DateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate entityValue) {
        if (entityValue != null) return Date.valueOf(entityValue);
        return new Date(121212);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date databaseValue) {
        if (databaseValue != null)
            return databaseValue.toLocalDate();
        return LocalDate.now();

    }
}
