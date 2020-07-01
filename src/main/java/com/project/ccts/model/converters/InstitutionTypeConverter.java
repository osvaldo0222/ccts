package com.project.ccts.model.converters;

import com.project.ccts.model.enums.InstitutionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class InstitutionTypeConverter implements AttributeConverter<InstitutionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InstitutionType institutionType) {
        if (institutionType == null) {
            return null;
        }
        return institutionType.getId();
    }

    @Override
    public InstitutionType convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(InstitutionType.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
