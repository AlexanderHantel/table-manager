package org.girevoy.tablemanager.model.table;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.girevoy.tablemanager.model.EntityRow;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@EqualsAndHashCode
public class Table {
    private String name;
    private List<Column> columns;
    private List<EntityRow> rows;

    public Table() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }
}
