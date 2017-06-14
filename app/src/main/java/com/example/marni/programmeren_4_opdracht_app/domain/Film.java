package com.example.marni.programmeren_4_opdracht_app.domain;

public class Film {
	
	private int filmId;
	private String title;
	private String description;
	private int releaseYear;
	private int languageId;
	private int originalLanguageId;
	private int rentalDuration;
	private int rentalRate;
	private int length;
	private int replacementCost;
	private String rating;
	private String specialFeatures;
	private String lastUpdate;

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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getReleaseYear() {
		return releaseYear;
	}
	
	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}
	
	public int getLanguageId() {
		return languageId;
	}
	
	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}
	
	public int getOriginalLanguageId() {
		return originalLanguageId;
	}
	
	public void setOriginalLanguageId(int originalLanguageId) {
		this.originalLanguageId = originalLanguageId;
	}
	
	public int getRentalDuration() {
		return rentalDuration;
	}
	
	public void setRentalDuration(int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}
	
	public int getRentalRate() {
		return rentalRate;
	}
	
	public void setRentalRate(int rentalRate) {
		this.rentalRate = rentalRate;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getReplacementCost() {
		return replacementCost;
	}
	
	public void setReplacementCost(int replacementCost) {
		this.replacementCost = replacementCost;
	}
	
	public String getRating() {
		return rating;
	}
	
	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public String getSpecialFeatures() {
		return specialFeatures;
	}
	
	public void setSpecialFeatures(String specialFeatures) {
		this.specialFeatures = specialFeatures;
	}
	
	public String getLastUpdate() {
		return lastUpdate;
	}
	
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
