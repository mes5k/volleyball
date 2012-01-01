
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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

public class Schedule {

	private final int gamesPerWeek;
	private final int numWeeks;
	private final int numTeams;
	private final Random rand = new Random(42);
	private final List<Game> currentSchedule; 
	private final Map<Gym,Integer> gymCounts = new HashMap<Gym,Integer>(); 
	private Map<Integer,Set<Team>> divSets = new HashMap<Integer,Set<Team>>();
	private Set<Team> allTeams; 
	private final List<Team> teams; 

	private static Logger logger = Logger.getLogger(Schedule.class.getName());

	public Schedule( List<Team> teams, int numWeeks ) {
		this.teams = teams;
		allTeams = new HashSet<Team>( teams );
		this.numWeeks = numWeeks;
		this.numTeams = teams.size();
		this.gamesPerWeek = teams.size()/2; 
		this.currentSchedule = genGames();
		for ( Game g : currentSchedule )
			gymCounts.put( g.getGym(), 0 );
	}

	private void clearGymCounts() {
		for ( Gym g : gymCounts.keySet() )
			gymCounts.put(g,0);
	}

	private List<Game> genGames() {
		// create a single list of all games for
		// each pairing of teams
		List<Game> games = new ArrayList<Game>();

		for ( Team home : teams ) {
			for ( Team away : teams ) {
				if ( home != away && home.getDivision() == away.getDivision() ) {
					Gym g = home.getHomeGym();
					// if one of the gyms is the Bye gym, make sure we use it
					if ( away.getHomeGym().getName().matches("Bye.*") )
						g = away.getHomeGym();

					games.add( new Game(home,away,g) );
				}
			}
		}
		
		return games;
	}

	private double updateBest(int i1, int i2, List<Game> working, double best, int t) {
		double ret = best;
		swap(working,i1,i2);

		double res = validate(working,false);

		if ( res < best ) {
			logger.info("found better: " + res + "   @" + t);
			Collections.copy(currentSchedule,working);
			ret = res;
		} else if ( res == best ) {
			Collections.copy(currentSchedule,working);
			ret = res;
		} else {
			swap(working,i2,i1);
		}
		
		return ret;
	}
		

	public List<Game> getSchedule() {
		int t = 0;
		int numIterations = 200000;
		ArrayList<Game> working = new ArrayList<Game>(currentSchedule.size());
		for ( Game g : currentSchedule )
			working.add(g);
		double best = validate(working,false);

		// initial naive search
	 	while ( t++ < numIterations ) {
			int i1 = rand.nextInt(working.size());	
			int i2 = rand.nextInt(working.size());	
			best = updateBest(i1,i2,working,best,t);

			if ( best <= 0 )
				return currentSchedule;;	
			if ( t % 100000 == 0 )
				logger.info(" @ " + t);
		}

		// now search only teams found to be playing twice in a week
		logger.info("DUPE search");
		int firstDupe;
		while ( (firstDupe = findFirstDupeGame(working)) >= 0 ) {
			t = 0;
			int firstDupeDivision = working.get(firstDupe).getHomeTeam().getDivision();
	 		while ( t++ < numIterations ) {

				int i1 = firstDupe; 
				int i2 = findNextInDivision(firstDupeDivision,working); 
				best = updateBest(i1,i2,working,best,t);

				if ( best <= 0 )
					return currentSchedule;
				if ( t % 50000 == 0 )
					logger.info(" @ " + t);
			}
		}


		logger.info("OVERSUBSCRIBE search");
		int overSubscribedGame = -1;
		while ( (overSubscribedGame = findOverSubscribedGame(working, overSubscribedGame)) >= 0 ) {
			int firstOverDivision = working.get(overSubscribedGame).getHomeTeam().getDivision();
			logger.info("looking at: " + firstOverDivision + " for oversub: " + overSubscribedGame);
			t = 0;
	 		while ( t++ < 10000 ) {

				int i1 = overSubscribedGame; 
				int i2 = findNextInDivision(firstOverDivision,working); 
				best = updateBest(i1,i2,working,best,t);

				if ( best <= 0 )
					return currentSchedule;
				if ( t % 50000 == 0 )
					logger.info(" @ " + t);
			}
		}

		logger.info("failed to find satisfactory schedule!");

		return currentSchedule;
	}

	private int findNextInDivision(int div, List<Game> working) {
		int i2 = rand.nextInt(working.size());	
		Game g2 = working.get(i2);
		while ( g2.getHomeTeam().getDivision() != div ) {
			i2++;
			if ( i2 == working.size() )
				i2 = 0;
			g2 = working.get(i2);
		}
		return i2;
	}

	private void swap(List<Game> list, int i1, int i2) {
		Game g1 = list.get(i1);	
		Game g2 = list.get(i2);	
		list.set(i1,g2);
		list.set(i2,g1);
	}

	public double validate(List<Game> games) {
		return validate(games,true);
	}

	private int findOverSubscribedGame(List<Game> games, int last) {
		// check each week
		for ( int week = 0; week < numWeeks; week++ ) {
			int beginIndex = week * gamesPerWeek;	
			int endIndex = beginIndex + gamesPerWeek;
			clearGymCounts();
			for ( int i = beginIndex; i < endIndex; i++ ) {
				Game g = games.get(i);  
				Gym gym = g.getGym();
				gymCounts.put(gym, gymCounts.get(gym).intValue() + 1);
				if ( gymCounts.get(gym) > gym.getSlotsPerDay()[week] )
					if ( i > last )
						return i;
			}
		}
		return -1;
	}

	private int findFirstDupeGame(List<Game> games) {
		// check each week
		for ( int week = 0; week < numWeeks; week++ ) {
			int beginIndex = week * gamesPerWeek;	
			int endIndex = beginIndex + gamesPerWeek;

			Set<Team> weekTeams = new HashSet<Team>(); 
			for ( int i = beginIndex; i < endIndex; i++ ) {

				if ( weekTeams.contains(games.get(i).getHomeTeam()) )
					return i;
				else
					weekTeams.add( games.get(i).getHomeTeam() );

				if ( weekTeams.contains(games.get(i).getAwayTeam()) )
					return i;
				else
					weekTeams.add( games.get(i).getAwayTeam() );
			}
		}
		return -1;
	}

	public List<Game> getWeekGames(List<Game> games, int week) {
		int beginIndex = week * gamesPerWeek;	
		int endIndex = beginIndex + gamesPerWeek;
		return games.subList(beginIndex,endIndex);
	}

	private double validate(List<Game> games, boolean print) {
		double cost = 0;

		// check each week
		for ( int week = 0; week < numWeeks; week++ ) {
			if ( print )
				logger.info("Week : " + week + " problems ===========");

			List<Game> weekGames = getWeekGames(games,week); 

			cost += missingTeamPenalty( weekGames, print );

			cost += gymOverSubscriptionPenalty( weekGames, print, week );

			cost += oneVsTwoLastWeekPenalty( weekGames, week );
		}

		// since half of the 1 vs. 2 games necessarily need to
		// happen before the last week, subtract the number
		// of times this will be penalized redundantly, i.e.
		// the number of divisions
		cost -= 8; 

		return cost;
	}

	private Set<Team> genSet(List<Game> games) {
		Set<Team> set = new HashSet<Team>();
		for ( Game g : games ) {
			set.add(g.getHomeTeam());
			set.add(g.getAwayTeam());
		}
		return set;
	}

	// increment cost for each team missing in this week 
	private double missingTeamPenalty( List<Game> weekGames, boolean print ) {
		double cost = 0;
		Set<Team> weekTeams = genSet(weekGames);
		int missing = (numTeams - weekTeams.size());
		cost += missing;
		if ( print ) {
			if ( missing > 0 ) {
				Set<Team> all = new HashSet<Team>(allTeams);
				all.removeAll(weekTeams);
				for ( Team ts : all) 
					logger.info(" missing " + ts);
			}
		}
		return cost;
	}

	// check that gym has appropriate number of games
	private double gymOverSubscriptionPenalty( List<Game> weekGames, boolean print, int week) {
		double cost = 0;
		clearGymCounts();
		for ( Game g : weekGames )
			gymCounts.put(g.getGym(), gymCounts.get(g.getGym()).intValue() + 1);

		for ( Gym g : gymCounts.keySet() ) {
			if ( gymCounts.get(g) > g.getSlotsPerDay()[week] ) {
				cost += 1.0;
				if ( print ) {
					logger.info(" too many users for gym: " + g + " (" + gymCounts.get(g) + ")");
				}
			}
		}
		return cost;
	}

	// penalize if last week doesn't include teams 1 vs. 2
	private double oneVsTwoLastWeekPenalty( List<Game> weekGames, int week ) {
		double cost = 0;
		for ( Game g : weekGames ) {
			int totalRank = g.getHomeTeam().getRank() + g.getAwayTeam().getRank();
			if ( week < (numWeeks - 1) && totalRank == 3 ) {
				cost += 1.0;
			}
		}
		return cost;
	}

}
