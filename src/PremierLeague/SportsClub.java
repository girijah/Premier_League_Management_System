package PremierLeague;

import java.util.Date;

public abstract class SportsClub {

	protected String name;
	protected String location;
	protected String foundedYr;
	protected int win;
	protected int draw;
	protected int defeat;
	protected int goalReceived;
	protected int points;
	protected int matchPlayed;

	public SportsClub() {

	}

	public SportsClub(String name, String location, String year) {
		this.name = name;
		this.location = location;
		this.foundedYr = year;
	}

	public abstract boolean addClub(FootballClub ft);

	public abstract boolean removeClub(String name);

	public abstract void viewStatisticsByClubName(String name);

	public abstract int getGoalReceived();

	public abstract int getPoints();

	public abstract int getMatchPlayed();

	public abstract int getWin();

	public abstract int getDraw();

	public abstract int getDefeat();

}
