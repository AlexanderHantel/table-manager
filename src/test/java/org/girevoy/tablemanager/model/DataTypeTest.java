package org.girevoy.tablemanager.model;

import org.girevoy.tablemanager.model.table.enums.DataType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataTypeTest {

    @Test
    void getType_ShouldReturnINT_IfInteger() {
        assertEquals("INT", DataType.INT.name());
    }

    @Test
    void getType_ShouldReturnTEXT_IfString() {
        assertEquals("TEXT", DataType.TEXT.name());
    }

    @Test
    void getType_ShouldReturnDATE_IfLocalDate() {
        assertEquals("DATE", DataType.DATE.name());
    }
}