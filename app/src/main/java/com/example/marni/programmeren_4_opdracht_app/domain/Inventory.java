package com.example.marni.programmeren_4_opdracht_app.domain;

import org.joda.time.DateTime;

public class Inventory {

    private int inventoryId;
    private String returnDate = "";
    private String rentalDate = "";

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public enum Status {AVAILABLE, MINE, NOT_AVAILABLE}
    private Status status = Status.AVAILABLE;
    private boolean available = true;
    private boolean mine = false;

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isMine() {
        return mine;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }
}
