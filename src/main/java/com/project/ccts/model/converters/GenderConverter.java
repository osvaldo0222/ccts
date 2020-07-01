package com.project.ccts.model.converters;

import com.project.ccts.model.enums.Gender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<Gender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }
        return gender.getId();
    }

    @Override
    public Gender convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Gender.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
