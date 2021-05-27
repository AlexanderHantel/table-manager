package org.girevoy.tablemanager.entity;

public enum DataType {
    INT,
    TEXT,
    DATE;

    public static DataType getDataType(String dataTypeInString) {
        for (DataType type : DataType.values()) {
            if (type.name().equals(dataTypeInString)) {
                return type;
            }
        }
        return null;
    }
}
