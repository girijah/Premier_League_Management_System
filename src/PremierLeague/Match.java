package PremierLeague;

import java.io.Serializable;

public class Match implements Serializable {
	private static final long serialVersionUID = 4247887663462947379L;
	MatchTable tab = new MatchTable();

	private String matchDate;
	private String teamA;
	private String teamB;
	private int teamAGoal;
	private int teamBGoal;
	
	public Match(){
		
	}

	public Match(String matchDate, String teamA, String teamB) {
		this.matchDate = matchDate;
		this.teamA = teamA;
		this.teamB = teamB;
	}

	public Match(String matchDate, String teamA, String teamB, int teamAGoal,
			int teamBGoal) {
		this.matchDate = matchDate;
		this.teamA = teamA;
		this.teamB = teamB;
		this.teamAGoal = teamAGoal;
		this.teamBGoal = teamBGoal;
	}

	public String getWinningClub(int teamAg, int teamBg) {
		if (teamAg > teamBg) {
			return teamA;	
		}else if(teamAg < teamBg);
			return teamB;
	}
	
	public String getLostClub(int teamAg, int teamBg){
		if (teamAg < teamBg) {
			return teamA;	
		}else if(teamAg > teamBg);
			return teamB;
	}
	
	public String getDrawMatch(int teamAg, int teamBg){	
		if(teamAg==teamBg){
			return "draw";
		}
		return "0";	
	}

	public String getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}

	public String getTeamA() {
		return teamA;
	}

	public void setTeamA(String teamA) {
		this.teamA = teamA;
	}

	public String getTeamB() {
		return teamB;
	}

	public void setTeamB(String teamB) {
		this.teamB = teamB;
	}

	public int getTeamAGoal() {
		return teamAGoal;
	}

	public void setTeamAGoal(int teamAGoal) {
		this.teamAGoal = teamAGoal;
	}

	public int getTeamBGoal() {
		return teamBGoal;
	}

	public void setTeamBGoal(int teamBGoal) {
		this.teamBGoal = teamBGoal;
	}
	
	 
	public boolean addMatch(Match match) {
		boolean bool = tab.addMatch(match);		
			return bool;
		
	}
	
	public boolean updateMatch(Match match){
		//int test=11; 
		boolean bool = false;
		Match objt = tab.updateMatch(match);
		int chkAgoal = objt.getTeamAGoal();
		int chkBgoal = objt.getTeamBGoal();
		//System.out.println(chkAgoal + " " + chkBgoal);

		if (chkAgoal == match.getTeamAGoal()
				&& chkBgoal == match.getTeamBGoal()) {
			//test = 999;			
			bool=true;
		}else{
			bool=false;
		}
//		if (test == 11) {			
//			bool=false;
//		}
		return bool;
	}
	
	public void deleteMatch(Match match){
		int recordCount = tab.deleteMatch(match);
		
		if(recordCount>0){
			System.out.println(recordCount+" record deleted successfully!");	
		}else if(recordCount==0){
			System.out.println("No match get deleted!");
		}
	}

}
