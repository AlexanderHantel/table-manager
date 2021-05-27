package org.girevoy.tablemanager.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Column {
    private String name;
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
