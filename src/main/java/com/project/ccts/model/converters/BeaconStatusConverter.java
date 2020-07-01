package com.project.ccts.model.converters;

import com.project.ccts.model.enums.BeaconStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class BeaconStatusConverter implements AttributeConverter<BeaconStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BeaconStatus beaconStatus) {
        if (beaconStatus == null) {
            return null;
        }
        return beaconStatus.getId();
    }

    @Override
    public BeaconStatus convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(BeaconStatus.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
