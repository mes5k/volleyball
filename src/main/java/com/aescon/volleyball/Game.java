// ----------------------------------------------------------------------
//  This file is part of volleyball.
//
//  volleyball is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  volleyball is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.
//
//  You should have received a copy of the GNU General Public License
//  along with volleyball.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright (C) 2011 Michael E. Smoot
//-----------------------------------------------------------------------

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
