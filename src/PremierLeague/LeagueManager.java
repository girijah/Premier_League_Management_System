package PremierLeague;

import java.util.Date;

public interface LeagueManager {

	public abstract void addClub();

	public abstract void removeClub();

	public abstract void addMatch();

	public abstract void updateMatch();

	public abstract void viewClubStatsByName();
	
	public abstract void viewSheduledMatches();
	
	public abstract void viewPlayedMatches();

	public abstract void displayLeagueTable();

	public abstract void viewMatchesPlayedByDate();

}
