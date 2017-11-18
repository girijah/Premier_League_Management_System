package PremierLeague;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FootballClub extends SportsClub implements Serializable {
	private static final long serialVersionUID = 609210926450544001L;
	FootballClubTable tab = new FootballClubTable();

	private String name;
	private String location;
	private String foundedYr;
	private int win;
	private int draw;
	private int defeat;
	private int goalReceived;
	private int points;
	private int matchPlayed;

	public FootballClub() {

	}

	public FootballClub(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFoundedYr() {
		return foundedYr;
	}

	public void setFoundedYr(String foundedYr) {
		this.foundedYr = foundedYr;
	}

	public FootballClub(String name, String location, String year) {
		this.name = name;
		this.location = location;
		this.foundedYr = year;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getDefeat() {
		return defeat;
	}

	public void setDefeat(int defeat) {
		this.defeat = defeat;
	}

	public int getGoalReceived() {
		return goalReceived;
	}

	public void setGoalReceived(int goalReceived) {
		this.goalReceived = goalReceived;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getMatchPlayed() {
		return matchPlayed;
	}

	public void setMatchPlayed(int matchPlayed) {
		this.matchPlayed = matchPlayed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean addClub(FootballClub ft) {
		
		FootballClub addedClub = tab.addClub(ft);
		if (addedClub.getName().equals(ft.getName())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean removeClub(String name) {

		if (tab.removeClub(name)) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void viewStatisticsByClubName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getPoints() {
		return points;
	}

}
