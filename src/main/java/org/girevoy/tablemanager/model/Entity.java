package org.girevoy.tablemanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
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
public class Entity {
    private long id;

    @NotEmpty
    private String tableName;

    @Schema(description = "Map of entity's attributes (key = column name, value = column value)",
    example = "{ \"name\": \"anyName\", \"count\": 10, \"date\": \"2021-05-31\" }", required = true)
    private Map<String, Object> attributes;

    public Entity() {
        attributes = new HashMap<>();
    }
}
