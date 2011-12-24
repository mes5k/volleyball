
package com.aescon.volleyball;

public class Team {

	private final Gym homeGym;
	private final String name;
	private int division;
	private int rank;

	public Team(String name, Gym homeGym, int division, int rank) {
		this.name = name;
		this.homeGym = homeGym;
		this.division = division;
		this.rank = rank;
	}

	public Gym getHomeGym() {
		return homeGym;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return "(" + division + ": " + rank + ") " + name;
	}

	public int getDivision() {
		return division;
	}

	public int getRank() {
		return rank;
	}
}
