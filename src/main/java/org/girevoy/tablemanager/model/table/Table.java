package org.girevoy.tablemanager.model.table;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
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
public class Table {
    @Schema(description = "Table name",
            example = "testTable",
            required = true)
    private String name;
    @Schema(description = "List of columns",
            example = "[\n" +
                      "    {\n" +
                      "      \"name\": \"string1\",\n" +
                      "      \"tableName\": \"testTable\",\n" +
                      "      \"dataType\": \"INT\"\n" +
                      "    },\n" +
                      "    {\n" +
                      "      \"name\": \"string2\",\n" +
                      "      \"tableName\": \"testTable\",\n" +
                      "      \"dataType\": \"TEXT\"\n" +
                      "    },\n" +
                      "    {\n" +
                      "      \"name\": \"string3\",\n" +
                      "      \"tableName\": \"testTable\",\n" +
                      "      \"dataType\": \"DATE\"\n" +
                      "    }\n" +
                      "]",
            required = true)
    private List<Column> columns;

    public Table() {
        columns = new ArrayList<>();
    }
}
