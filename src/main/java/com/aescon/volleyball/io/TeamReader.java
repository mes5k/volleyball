
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

package com.aescon.volleyball.io;

import java.io.FileReader;
import java.io.BufferedReader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import com.aescon.volleyball.Gym;
import com.aescon.volleyball.Team;

public class TeamReader {

	public List<Team> getTeams(String fileName,Map<String,Gym> gymMap) {
		List<Team> teams = new ArrayList<Team>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] l = line.split(",");
				int div = Integer.valueOf(l[0].trim());
				int rank = Integer.valueOf(l[1].trim());
				String name = l[3].trim();
				Gym gym = gymMap.get(l[4].trim());

				Team t = new Team(name,gym,div,rank);

				teams.add(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return teams;
	}
}
