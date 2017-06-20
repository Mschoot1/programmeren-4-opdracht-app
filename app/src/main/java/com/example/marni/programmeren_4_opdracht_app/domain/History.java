package com.example.marni.programmeren_4_opdracht_app.domain;

public class History {

	private int inventoryId;
	private String rentalDate = "";
	private int filmId;
	private String title;

	public int getFilmId() {
		return filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Inventory.Status getStatus() {
		return status;
	}

	public void setStatus(Inventory.Status status) {
		this.status = status;
	}

	public enum Status {AVAILABLE, MINE, NOT_AVAILABLE}
	private Inventory.Status status = Inventory.Status.AVAILABLE;
	private boolean mine = false;

	public String getRentalDate() {
		return rentalDate;
	}

	public void setRentalDate(String rentalDate) {
		this.rentalDate = rentalDate;
	}

	public boolean isMine() {
		return mine;
	}

	public void setMine(boolean mine) {
		this.mine = mine;
	}
}
