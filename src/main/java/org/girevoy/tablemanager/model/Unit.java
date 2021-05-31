package org.girevoy.tablemanager.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.LinkedHashMap;
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
public class Unit {
    private long id;

    @NotEmpty
    private String tableName;

    @Schema(description = "LinkedHashMap of entity's attributes (key = column name, value = column value)",
    example = "{ \"name\": \"anyName\", \"count\": 10, \"date\": \"2021-05-31\" }", required = true)
    private final Map<String, Object> attributes;

    public Unit() {
        attributes = new LinkedHashMap<>();
    }
}
