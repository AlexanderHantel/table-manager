package org.girevoy.tablemanager.model;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class EntityRow {
    private int id;
    private String tableName;
    private Map<String, Object> attributes;

    public EntityRow() {
        attributes = new LinkedHashMap<>();
    }
}
