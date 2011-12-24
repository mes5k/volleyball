
package com.aescon.volleyball;

public class Game {

	private final Team home;
	private final Team away;
	private final Gym gym;

	public Game(Team home, Team away, Gym gym) {
		this.home = home;
		this.away = away;
		this.gym = gym;
	}

	public Team getHomeTeam() {
		return home;
	}

	public Team getAwayTeam() {
		return away;
	}

	public Gym getGym() {
		return gym;
	}

	public String toString() {
		return home + " vs " + away + "  at  " + gym; 
	}
}
