package com.wms.models;

public class Warehouse {
    private int warehouseId;
    private String warehouseName;
    private String location;

    public Warehouse(int warehouseId, String warehouseName, String location) {
        this.warehouseId = warehouseId;
        this.warehouseName = warehouseName;
        this.location = location;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getLocation() {
        return location;
    }
}
