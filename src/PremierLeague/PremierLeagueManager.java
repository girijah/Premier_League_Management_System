package PremierLeague;

import java.io.ByteArrayInputStream;
import java.io.InvalidClassException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class PremierLeagueManager implements LeagueManager, Comparator<Integer> {

	DataConnection connection = new DataConnection();
	ObjectSerialization ser = new ObjectSerialization();
	Encryption encryption = new Encryption();
	Scanner scan = new Scanner(System.in);

	List<FootballClub> clubs = new ArrayList<FootballClub>();
	List<Match> matches = new ArrayList<Match>();
	List<FootballClub> footballList = new ArrayList<FootballClub>();
	Connection conn=null;
	FootballClub ftClub=null;
	Match match=null;
	
	public PremierLeagueManager() {		
		ftClub = new FootballClub();
		match = new Match();

		try {
			// load all objects from FootballClub table to clubs arraylist
			conn = connection.getOracleConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT \"Value\" FROM FOOTBALLCLUB");
			ResultSet res = pstmt.executeQuery();

			while (res.next()) {
				Blob blob = res.getBlob(1);
				byte[] arrayy = blob.getBytes(1, (int) blob.length());
				byte[] decryptedd = encryption.decrypt(arrayy);

				FootballClub obj = (FootballClub) ser.deSerialise(decryptedd);
				clubs.add(obj);
			}

		} catch (InvalidClassException exx) {
			System.out.println(exx);
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e);
		}

		try {
			// load all objects from FootballMatch table to matches arraylist
			conn = connection.getOracleConnection();
			PreparedStatement pstmt = conn
					.prepareStatement("SELECT \"Value\" FROM FootballMatch");
			ResultSet res = pstmt.executeQuery();

			while (res.next()) {
				Blob blob = res.getBlob(1);
				byte[] array = blob.getBytes(1, (int) blob.length());
				byte[] decrypted = encryption.decrypt(array);
				Match obj = (Match) ser.deSerialise(decrypted);
				matches.add(obj);
			}

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e);
		}

	}

	public static void main(String[] args) {
		PremierLeagueManager league = new PremierLeagueManager();
		do {
			league.displayMenu(league);
		} while (true);

	}

	@Override
	public void addClub() {

		// prompt for FootballClub attributes
		System.out.println("\nAdd new club");
		System.out.print("Club Name: ");
		String clubName = scan.nextLine();
		System.out.print("Club location: ");
		String clubLoc = scan.nextLine();
		System.out.print("Club foundedYear: ");
		String clubYr = scan.nextLine();
		
		if(clubName.equals("")||clubLoc.equals("")||clubYr.equals("")){
			System.out.println("Please don't leave empty spaces! ");
		}else{
		// create FootballClub object
		FootballClub ft = new FootballClub(clubName, clubLoc, clubYr);
		clubs.add(ft);
		boolean bool = ft.addClub(ft);
		if(bool==true){
			System.out.println("Club added successfully!");
		}else {
			System.out.println("Club is not added!");
		}
			
		}		
	}
	

	@Override
	public void removeClub() {
		System.out.println("\nRemove Club");
		System.out.print("Club Name: ");
		String clubName = scan.nextLine();
		int tester = 10;
		if(clubName.equals("")){
			System.out.println("Please don't leave empty spaces! ");
		}else{

		// delete from clubs arraylist
		for (int i = 0; i < clubs.size(); i++) {
			if (clubs.get(i).getName().equals(clubName)) {
				clubs.remove(i);
				tester = 100;
			}
		}
		
		if (tester != 100) {
			System.out.println("Club not exists!");
		}
		if(ftClub.removeClub(clubName)){
			System.out.println("Club deleted successfully!");	
		}else{
			System.out.println("Club is not deleted!");
		}			
		}
	}

	@Override
	public void addMatch() {
		// prompt for Match attributes
		System.out.println("\nAdd new match");
		System.out.print("Match date dd-mm-yyyy: ");
		String matchDate = scan.nextLine();
		System.out.print("Team A: ");
		String teamA = scan.nextLine();
		System.out.print("Team B: ");
		String teamB = scan.nextLine();

		if(teamA.equalsIgnoreCase(teamB)){
		System.out.println("Both teams are same!");	
		}else{
		// create Match object
		Match match = new Match(matchDate, teamA, teamB);
		matches.add(match);

		if(!match.addMatch(match)){
			System.out.println("Match added successfully!");
		}else{
			System.out.println("Match is not added!");
		}
		}

	}

	@Override
	public void updateMatch() {
		// prompt for Match attributes
		System.out.println("\nUpdate match result");
		System.out.print("Match date dd-mm-yyyy: ");
		String matchDate = scan.nextLine();
		System.out.print("Team A: ");
		String teamA = scan.nextLine();
		System.out.print("Team B: ");
		String teamB = scan.nextLine();
		System.out.println("TeamA goals: ");
		String teamAGoals = scan.nextLine();
		System.out.println("TeamB goals: ");
		String teamBGoals = scan.nextLine();
		
		if(matchDate.equals("")||teamA.equals("")||teamB.equals("")||teamAGoals.equals("")||teamBGoals.equals("")){
			System.out.println("Please don't leave blank spaces! ");
		}else{
			try{
				 Integer.parseInt(teamAGoals);
				 Integer.parseInt(teamBGoals);
				 
			}catch(NumberFormatException e){
				System.out.println("Please enter correct goals-count!");
			}
		Match match = new Match(matchDate, teamA.trim(), teamB.trim(),
				Integer.parseInt(teamAGoals), Integer.parseInt(teamBGoals));
		
		if(match.updateMatch(match)){
			System.out.println("Result is updated!");	
		}else{
			System.out.println("No such match result found!");
		}
		
		}
	}

	@Override
	public void viewClubStatsByName() {
		int totGoal = 0; int test =90;
		int matchCount = 0;
		int winCount = 0;
		int lostCount = 0;

		// prompt for Club name
		System.out.println(" Club Statistics");
		System.out.print("Club name: ");
		String clubName = scan.nextLine();
		System.out.println();
		for (int i = 0; i < clubs.size(); i++) {
			if(clubs.get(i).getName().equalsIgnoreCase(clubName.trim())){
			test=111;
			}	
		}
	
		if(test==111){
			for (int i = 0; i < matches.size(); i++) {
				if (matches.get(i).getTeamA().trim().equalsIgnoreCase(clubName.trim())
						&& matches.get(i).getTeamAGoal() > 0) {
					totGoal = totGoal + matches.get(i).getTeamAGoal();
					matchCount = matchCount + 1;
				}
				if (matches.get(i).getTeamB().trim().equalsIgnoreCase(clubName.trim())
						&& matches.get(i).getTeamBGoal() > 0) {
					totGoal = totGoal + matches.get(i).getTeamAGoal();
					matchCount = matchCount + 1;
				}
				if (matches
						.get(i)
						.getWinningClub(matches.get(i).getTeamAGoal(),
								matches.get(i).getTeamBGoal()).equalsIgnoreCase(clubName.trim())
						&& matches.get(i).getTeamAGoal() > 0
						&& matches.get(i).getTeamBGoal() > 0) {
					winCount = winCount + 1;
				}else if (matches
						.get(i)
						.getLostClub(matches.get(i).getTeamAGoal(),
								matches.get(i).getTeamBGoal()).equalsIgnoreCase(clubName.trim())
						&& matches.get(i).getTeamAGoal() > 0
						&& matches.get(i).getTeamBGoal() > 0) {
					lostCount = lostCount + 1;
				}

			}
			int drawCount = matchCount - (winCount + lostCount);
			System.out.printf(" %4s %4s %4s %4s %4s %4s", "pnts", "goal", "plyd",
					"wins", "loss", "draw");
			System.out.println();
			System.out.printf(" %4s %4s %4s %4s %4s %4s", (winCount * 3)
					+ drawCount, totGoal, matchCount, winCount, lostCount,
					drawCount);	
		}else if(test==90){
			System.out.println("The club name you entered doesn't exist!");	
		
		}
	}

	@Override
	public void displayLeagueTable() {

		int i; int drawCount=0;
		for (int x = 0; x < clubs.size(); x++) {
			int totGoal = 0;int matchCount = 0;			
			int winCount = 0;int lostCount = 0;			

			for (i = 0; i < matches.size(); i++) {
				
				if (matches.get(i).getTeamA().equals(clubs.get(x).getName())
						&& matches.get(i).getTeamAGoal() > 0) {
					totGoal = totGoal + matches.get(i).getTeamAGoal();
					matchCount = matchCount + 1;
				}
				if (matches.get(i).getTeamB().equals(clubs.get(x).getName())
						&& matches.get(i).getTeamBGoal() > 0) {
					totGoal = totGoal + matches.get(i).getTeamBGoal();
					matchCount = matchCount + 1;
				}
				if (matches
						.get(i)
						.getWinningClub(matches.get(i).getTeamAGoal(),
								matches.get(i).getTeamBGoal())
						.equals(clubs.get(x).getName())
						&& matches.get(i).getTeamAGoal() > 0
						&& matches.get(i).getTeamBGoal() > 0) {
					winCount = winCount + 1;
					//System.out.println("club name: "+clubs.get(x).getName()+" winning team: "+matches.get(i).getWinningClub(matches.get(i).getTeamAGoal(),
									//matches.get(i).getTeamBGoal()));
				} else if (matches
						.get(i)
						.getLostClub(matches.get(i).getTeamAGoal(),
								matches.get(i).getTeamBGoal())
						.equals(clubs.get(x).getName())
						&& matches.get(i).getTeamAGoal() > 0
						&& matches.get(i).getTeamBGoal() > 0) {
					lostCount = lostCount + 1;
				}

			}
			int nonDraws=winCount+lostCount;
			drawCount = matchCount - nonDraws;
			FootballClub footballClub = new FootballClub(
					clubs.get(x).getName(), clubs.get(x).getLocation(), clubs
							.get(x).getFoundedYr());
			footballClub.setGoalReceived(totGoal);
			footballClub.setPoints((winCount * 3) + drawCount);
			footballClub.setMatchPlayed(matchCount);
			footballClub.setWin(winCount);
			footballClub.setDraw(drawCount);
			footballClub.setDefeat(lostCount);
			footballList.add(footballClub);
		}

		for (int a = 0; a < footballList.size(); a++) {
			for (int b = a + 1; b < footballList.size(); b++) {
				if (compare(footballList.get(a).getPoints(), footballList
						.get(b).getPoints()) < 0) {
					swap(a, b);
				} else if (compare(footballList.get(a).getPoints(),
						footballList.get(b).getPoints()) == 0) {
					if (compare(footballList.get(a).getGoalReceived(),
							footballList.get(b).getGoalReceived()) < 0) {
						swap(a, b);
					}
				}
			}
		}

		System.out.println("           Premier League Table");
		System.out.println("-------------------------------------------");

		System.out.printf(" %12s %4s %4s %4s %4s %4s %4s", "Name ", "pnts",
				"goal", "plyd", "wins", "loss", "draw");
		System.out.println();
		for (int b = 0; b < footballList.size(); b++) {
			System.out.printf("%12s %4d %4d %4d %4d %4s %4s",
					footballList.get(b).getName(), footballList.get(b)
							.getPoints(),
					footballList.get(b).getGoalReceived(), footballList.get(b)
							.getMatchPlayed(), footballList.get(b).getWin(),
					footballList.get(b).getDefeat(), footballList.get(b)
							.getDraw());
			System.out.println();
		}

	}

	@Override
	public void viewMatchesPlayedByDate() {
		int monthCount = 20;
		int check = 10;String str=null;
		
		System.out.print(" Please enter month space year: ");
		String month = scan.next();
		String year = scan.next();

		System.out.println();
		String[] months = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		for (int index = 0; index < 12; index++) {
			if (months[index].equalsIgnoreCase(month)) {
				monthCount = index;
			}
		}
		if (monthCount == 20) {
			System.err.print("please re-enter correctly!");
			System.out.println();
			viewMatchesPlayedByDate();
		}
		String[] date = { Integer.toString(++monthCount), year };
		//Calendar cal = new Calendar();
		Calendar.main(date);
		System.out.print("\nEnter date: ");
		String day = scan.next();
		if(Integer.parseInt(day)<0 || Integer.parseInt(day)>31){
		System.out.println("The date should be between 1 to 31");	
		}else{
		String matchDate = day + "-" + monthCount + "-" + year;
		// System.out.println(matchDate);

		for (int i = 0; i < matches.size(); i++) {
			if (matches.get(i).getMatchDate().equals(matchDate)) {
				check=100;
				System.out.println("A: " + matches.get(i).getTeamA() + " B: "
						+ matches.get(i).getTeamB());
				if (matches.get(i).getTeamAGoal() > 0) {
					System.out.print("A: " + matches.get(i).getTeamAGoal());
				}
				if (matches.get(i).getTeamBGoal() > 0) {
					System.out
							.println("\t B: " + matches.get(i).getTeamBGoal());
				}
			} 
		}if(check==10){
		System.out.println("No match played this day!");	
		}
		}

		}

	private void displayMenu(PremierLeagueManager league) {

		System.out
				.println("\n-------------------------------------------------------Premier League Management System---------------------------------------------------------------------------------------------------- \n");

		System.out.printf("%50s %35s %45s ", " 1. Add Match              ",
				"5. Played Matches       "," 9. Add Club                     ");
		System.out.println();
		System.out.printf("%50s %35s %45s ", " 2. Update Match           ",
				"6. UnUpdated Match      ","10. Remove Club                  ");
		System.out.println();
		System.out.printf("%50s %35s %45s ", " 3. Delete Match           ",
				"7. All Matches          ","11. Club With Name               ");
		System.out.println();
		System.out.printf("%50s %35s %45s ", " 4. Match In Calendar      ",
				"8. Score Table          ","12. Exit                         ");
		System.out.println();
		
		System.out.print("\n Enter required choice: ");

		Scanner scanner = new Scanner(System.in);

		try {
			int choice = scanner.nextInt();
			System.out.println();
			if (choice >= 1 && choice <= 12) {

				switch (choice) {
				case 1:
					league.addMatch();					
					break;
				case 2:
					league.updateMatch();					
					break;
				case 3:
					league.deleteMatch();										
					break;
				case 4:
					league.viewMatchesPlayedByDate();										
					break;
				case 5:
					league.viewPlayedMatches();
					break;
				case 6:					
					league.viewSheduledMatches();
					break;
				case 7:					
					league.viewAllMatches();
					break;
				case 8:					
					league.displayLeagueTable();
					break;
				case 9:
					league.addClub();					
					break;
				case 10:
					league.removeClub();					
					break;
				case 11:
					league.viewClubStatsByName();
					break;
				case 12:
					System.exit(0);
					break;
				}
			} else {
				System.err.println("Invalid choice! Try again ");
			}
		} catch (InputMismatchException inputEx) {
			// System.out.println(inputEx);
			System.err.println("Not expected! Try again!");
			displayMenu(league);
		} catch (Exception ex) {
			// System.out.println(ex);
			System.out.println("\n Maintain the system correctly. Thanks!");
			displayMenu(league);
		}
	}

	@Override
	public void viewSheduledMatches() {
		int match = 0;
		for (int i = 0; i < matches.size(); i++) {

			if (matches.get(i).getTeamAGoal() == 0
					&& matches.get(i).getTeamBGoal() == 0) {
				match++;
				System.out.printf("%2s # %12s %12s %4s %12s %4s ", match, matches.get(i).getMatchDate(), matches.get(i)
						.getTeamA(), matches.get(i).getTeamAGoal(), matches
						.get(i).getTeamB(), matches.get(i).getTeamBGoal());
				System.out.println();
			
			}
		}
		System.out.println("\n Total scheduled matches: " + match);

	}

	@Override
	public void viewPlayedMatches() {
		int matchPlayed = 0;
		for (int i = 0; i < matches.size(); i++) {
			 int s=i+1;
			if (matches.get(i).getTeamAGoal() > 0
					&& matches.get(i).getTeamBGoal() > 0) {
				System.out.printf("%2s # %12s %12s %4s %12s %4s ", s, matches.get(i).getMatchDate(), matches.get(i)
						.getTeamA(), matches.get(i).getTeamAGoal(), matches
						.get(i).getTeamB(), matches.get(i).getTeamBGoal());
				System.out.println();
				matchPlayed++;
			}
		}
		System.out.println("\n Total match played: " + matchPlayed);

	}

	@Override
	public int compare(Integer o1, Integer o2) {
		return o1.compareTo(o2);
	}

	private void swap(int a, int b) {
		FootballClub aClub = footballList.get(a);
		footballList.set(a, footballList.get(b));
		footballList.set(b, aClub);
	}
	

	private void deleteMatch() {
		int test=12;
		// prompt for Match attributes
				System.out.println("\nDelete Match");
				System.out.print("Match date dd-mm-yyyy: ");
				String matchDate = scan.nextLine();
				System.out.print("Team A: ");
				String teamA = scan.nextLine();
				System.out.print("Team B: ");
				String teamB = scan.nextLine();
				
				Match match = new Match(matchDate, teamA, teamB);	
				match.deleteMatch(match);
	}
	
	
	private void viewAllMatches(){
		 //System.out.println(matches);
		 for(int i=0; i<matches.size(); i++){
			 int s=i+1;
		// System.out.println(s + ". "+matches.get(i).getTeamA()+" "+matches.get(i).getTeamAGoal()+" "+matches.get(i).getTeamB()+" "+matches.get(i).getTeamBGoal());
		 System.out.printf("%2s # %12s %12s %4s %12s %4s ", s, matches.get(i).getMatchDate(),matches.get(i)
					.getTeamA(), matches.get(i).getTeamAGoal(), matches
					.get(i).getTeamB(), matches.get(i).getTeamBGoal());
			System.out.println();
		 }
	}

}
