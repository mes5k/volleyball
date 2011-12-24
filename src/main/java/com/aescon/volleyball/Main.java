
package com.aescon.volleyball;

import java.util.Map;
import java.util.List;

import com.aescon.volleyball.io.GymReader;
import com.aescon.volleyball.io.TeamReader;

public class Main {

	private static final int numWeeks = 10;
	private static final int numDivisions = 8;
	private static final int teamsPerDivision = 6;

	public static void main(String[] args) {
		
		validateArgs(args);

		GymReader gr = new GymReader();
		Map<String,Gym> gyms = gr.getGyms(args[0]);

		TeamReader tr = new TeamReader();
		List<Team> teams = tr.getTeams(args[1],gyms);

		Schedule schedule = new Schedule(teams,numWeeks);
		List<Game> games = schedule.getSchedule();

		int numGamesPerWeek = numDivisions * teamsPerDivision / 2;
		for ( int week = 0; week < numWeeks; week++ ) {
			int begin = numGamesPerWeek * week;
			System.out.println("---------- week: " + week + " -----------");
			for ( int game = 0; game < numGamesPerWeek; game++)
				System.out.println("(week " + week + "): " + games.get(begin + game) );
		}

		double res = schedule.validate(games);
		System.out.println("validation result: " + res);
	}

	private static void validateArgs(String[] args) {
		if (args.length != 2) {
			System.err.println("Error: incorrect arguments specified!");
			System.err.println("");
			System.err.println("USAGE: java -jar volleyball.jar <gyms.csv> <teams.csv>");
			System.err.println("");
			System.exit(1);
		}
	}
}
