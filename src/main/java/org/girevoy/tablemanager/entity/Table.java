package org.girevoy.tablemanager.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class Table {
    private String name;
    private List<Column> columns;
    private List<Row> rows;

    public Table() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }
}
