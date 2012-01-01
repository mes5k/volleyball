
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

import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.logging.*;
import java.io.IOException;

import com.aescon.volleyball.io.GymReader;
import com.aescon.volleyball.io.TeamReader;

public class Main {

	private static final int numWeeks = 10;
	private static final int numDivisions = 8;
	private static final int teamsPerDivision = 6;

	public static void main(String[] args) {
		configLogging();
		
		validateArgs(args);

		GymReader gr = new GymReader();
		Map<String,Gym> gyms = gr.getGyms(args[0]);

		TeamReader tr = new TeamReader();
		List<Team> teams = tr.getTeams(args[1],gyms);

		Schedule schedule = new Schedule(teams,numWeeks);
		List<Game> games = schedule.getSchedule();

		for ( int week = 0; week < numWeeks; week++ ) {
			System.out.println("---------- week: " + (week+1) + " -----------");
			List<Game> wg = schedule.getWeekGames(games,week);
			for ( Game g : wg ) {
				Gym gym = g.getGym();
				Set<String> slots = gym.getSlotSets().get(week);
				Iterator<String> sit = slots.iterator();
				if ( sit.hasNext() ) {
					String s = sit.next();
					System.out.println(g + ": " + s);
					slots.remove(s);
				} else {
					System.out.println(g + ": NO SPACE!");
				}
			}
		}
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

	private static void configLogging() {
		//Logger globalLogger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		Logger globalLogger = Logger.getLogger("");
		try {
			for ( Handler h : globalLogger.getHandlers() )
				globalLogger.removeHandler(h);
			FileHandler fh = new FileHandler("schedule-creation-log.txt"); 
			fh.setFormatter(new CleanFormatter());
			globalLogger.addHandler(fh);
			globalLogger.setLevel(Level.ALL);
		} catch (IOException ioe) { ioe.printStackTrace(); }
	}

	private static class CleanFormatter extends Formatter {
		private static final String newline = System.getProperty("line.separator");
		CleanFormatter() {
			super();
		}
		public String format(LogRecord rec) {
			return rec.getLevel().toString() + ": " + rec.getMessage() + newline;
		}
	}
}
