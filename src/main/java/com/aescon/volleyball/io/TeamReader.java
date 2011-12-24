
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
