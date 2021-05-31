package org.girevoy.tablemanager.model.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.girevoy.tablemanager.model.table.enums.DataType;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Column {
    @Schema(description = "Column name",
            example = "parameter",
            required = true)
    private String name;
    @Schema(description = "Table name",
            example = "testTable",
            required = true)
    private String tableName;
    @Schema(description = "Data type",
            example = "INT",
            required = true)
    private DataType dataType;

    public void setDataType(String dataType) {
        switch (dataType) {
            case "INT":
                this.dataType = DataType.INT;
                break;
            case "TEXT":
                this.dataType = DataType.TEXT;
                break;
            case "DATE":
                this.dataType = DataType.DATE;
                break;
        }
    }
}
