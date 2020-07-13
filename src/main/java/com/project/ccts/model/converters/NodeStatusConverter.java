package com.project.ccts.model.converters;

import com.project.ccts.model.enums.NodeStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class NodeStatusConverter implements AttributeConverter<NodeStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(NodeStatus nodeStatus) {
        if (nodeStatus == null) {
            return null;
        }
        return nodeStatus.getId();
    }

    @Override
    public NodeStatus convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }

        return Stream.of(NodeStatus.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
