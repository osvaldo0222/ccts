package com.project.ccts.model.converters;

import com.project.ccts.model.enums.CivilStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CivilStatusConverter implements AttributeConverter<CivilStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CivilStatus civilStatus) {
        if (civilStatus == null) {
            return null;
        }
        return civilStatus.getId();
    }

    @Override
    public CivilStatus convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(CivilStatus.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
