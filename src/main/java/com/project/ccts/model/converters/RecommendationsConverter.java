package com.project.ccts.model.converters;

import com.project.ccts.model.enums.Recommendations;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class RecommendationsConverter implements AttributeConverter<Recommendations, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Recommendations recommendations) {
        if (recommendations == null) {
            return null;
        }
        return recommendations.getId();
    }

    @Override
    public Recommendations convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(Recommendations.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
