
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
//  along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
//-----------------------------------------------------------------------

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
