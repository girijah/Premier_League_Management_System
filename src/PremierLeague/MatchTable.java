package PremierLeague;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MatchTable implements Serializable{
	private static final long serialVersionUID = 6709871463046696679L;
	Connection conn = null;
	DataConnection connection = new DataConnection();
	ObjectSerialization ser = new ObjectSerialization();
	Encryption encryption = new Encryption();

	private String querySelect = "SELECT * FROM FootballMatch";
	private String queryById = "SELECT \"Value\" FROM FootballMatch WHERE id =?";	
	private String queryInsert = "INSERT INTO FootballMatch(Id,\"Value\")VALUES (SQ_FootballMatch_Id.nextval,?)";
	private String queryUpdate = "UPDATE FootballMatch SET \"Value\"= ? WHERE id =?";
	private String queryDelete = "DELETE FROM FootballMatch WHERE id =?";


	private byte[] prepareSerEncrptedObject(Match ft) {
		// byte[] serialisedEncrypted =
		// encryption.encrypt(ser.serialise(match));

		// Serialise object
		byte[] byt = ser.serialise(ft);

		// Encrypt serialised object
		byte[] serialisedEncrypted = encryption.encrypt(byt);

		return serialisedEncrypted;
	}

	public boolean addMatch(Match match) {
		boolean bool = false;
		// serialise & encrypt footballClub object
		byte[] serialisedEncrypted = prepareSerEncrptedObject(match);

		// store serialised and encrypted Match object to database
		try {
			conn = connection.getOracleConnection();
			PreparedStatement prepareStatement = conn
					.prepareStatement(queryInsert);
			prepareStatement.setBinaryStream(1, new ByteArrayInputStream(
					serialisedEncrypted), serialisedEncrypted.length);
			prepareStatement.execute();
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return (bool);
	}

	public Match updateMatch(Match match) {
		Match objt=null;
		// serialise & encrypt footballClub object
				byte[] serialisedEncrypted = prepareSerEncrptedObject(match);
		try {
			conn = connection.getOracleConnection();
			PreparedStatement pstmt1 = conn
					.prepareStatement(querySelect);
			ResultSet resSet = null;
			ResultSet res = pstmt1.executeQuery();
			while (res.next()) {

				Blob blob = res.getBlob(2);
				byte[] array = blob.getBytes(1, (int) blob.length());
				byte[] decrypted = encryption.decrypt(array);

				Match obj = (Match) ser.deSerialise(decrypted);

				if (obj.getMatchDate().equals(match.getMatchDate())
						&& obj.getTeamA().equals(match.getTeamA())
						&& obj.getTeamB().equals(match.getTeamB())) {
					String id = res.getString(1);
					// System.out.println("id: " + id);

					PreparedStatement prepareStatement = conn
							.prepareStatement(queryUpdate);

					prepareStatement.setBinaryStream(1,
							new ByteArrayInputStream(serialisedEncrypted),
							serialisedEncrypted.length);
					prepareStatement.setInt(2, Integer.parseInt(id));
					prepareStatement.execute();

					PreparedStatement statement = conn.prepareStatement(queryById);
					statement.setString(1, id);
					resSet = statement.executeQuery();

					while (resSet.next()) {
						Blob blb = resSet.getBlob(1);
						//System.out.println("blob: " + blb);
						byte[] ary = blob.getBytes(1, (int) blb.length());
						byte[] decrptd = encryption.decrypt(ary);
						objt = (Match) ser.deSerialise(decrptd);
						
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return objt;
	}
	
	public int deleteMatch(Match match){
		int recordCount=0; 
		try {	
			conn = connection.getOracleConnection();
			PreparedStatement pstmt1 = conn
					.prepareStatement(querySelect);

			ResultSet res = pstmt1.executeQuery();
			
			while (res.next()) {

				Blob blob = res.getBlob(2);
				byte[] array = blob.getBytes(1, (int) blob.length());
				byte[] decrypted = encryption.decrypt(array);

				Match obj = (Match) ser.deSerialise(decrypted);

				if (obj.getMatchDate().equals(match.getMatchDate())
						&& obj.getTeamA().equals(match.getTeamA())
						&& obj.getTeamB().equals(match.getTeamB())) {
					String id = res.getString(1);
					// System.out.println("id: " + id);
					
					PreparedStatement prepareStatement = conn
							.prepareStatement(queryDelete);
					prepareStatement.setString(1, id);
					prepareStatement.execute();
					recordCount = recordCount+1;
				}
			}			
		}catch(Exception ex){
			System.out.println(ex);
		}		
		return recordCount;
	}
	
	
}
