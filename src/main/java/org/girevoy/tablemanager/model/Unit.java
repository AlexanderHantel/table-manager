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
public class Unit {
    private long id;
    private String tableName;
    private final Map<String, Object> attributes;

    public Unit() {
        attributes = new LinkedHashMap<>();
    }
}
