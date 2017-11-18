package PremierLeague;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FootballClubTable implements Serializable{
	private static final long serialVersionUID = 6709871463046696679L;
	Connection conn = null;
	DataConnection connection = new DataConnection();
	ObjectSerialization ser = new ObjectSerialization();
	Encryption encryption = new Encryption();

	private String queryByName = "SELECT * FROM FOOTBALLCLUB WHERE NAME=?";
	private String queryInsert = "INSERT INTO FootballClub(Name,\"Value\")VALUES (?,?)";
	private String queryDelete = "DELETE FROM FOOTBALLCLUB where Name=?";

//	public FootballClubTable() {
//		try {
//			conn = connection.getOracleConnection();
//		} catch (Exception exc) {
//			System.out.println("Connection error!");
//		}
//	}
	

	private byte[] prepareSerEncrptedObject(FootballClub ft) {
				// Serialise object
				byte[] byt = ser.serialise(ft);

				// Encrypt serialised object
				byte[] serialisedEncrypted = encryption.encrypt(byt);

				return serialisedEncrypted;
	}

//	private byte[] prepareSerEncrptedObject(FootballClub ft) {
//		// Serialise FootballClub object
//		byte[] byt = ser.serialise(ft);
//
//		// Encrypt serialised FootballClub object
//		byte[] serialisedEncrypted = encryption.encrypt(byt);
//
//		return serialisedEncrypted;
//	}

	public FootballClub addClub(FootballClub ft) {
		FootballClub obj = null;
	
		//serialise & encrypt footballClub object
		byte[] serialisedEncrypted = prepareSerEncrptedObject(ft);

		// check club already stored
		ResultSet rs = null;
					
			try {
				conn = connection.getOracleConnection();
				PreparedStatement pstmt = conn.prepareStatement(queryByName);
				pstmt.setString(1, ft.getName());
				rs = pstmt.executeQuery();
			} catch (Exception ex) {

			}
			try {
				if (rs.next()) {
					System.out.println("Club already exists!");
				} else {
					// store serialised and encrypted FootballClub object to
					// database

					try {						
						PreparedStatement prepareStatement = conn
								.prepareStatement(queryInsert);
						prepareStatement.setString(1, ft.getName());
						prepareStatement.setBinaryStream(2,
								new ByteArrayInputStream(serialisedEncrypted),
								serialisedEncrypted.length);

						prepareStatement.execute();
						conn.commit();

						PreparedStatement pstmt1 = conn
								.prepareStatement(queryByName);
						pstmt1.setString(1, ft.getName());
						ResultSet res = pstmt1.executeQuery();
						res.next();

						Blob blob = res.getBlob(2);
						// System.out.println("Read " + blob.length()
						// +" bytes ");
						byte[] array = blob.getBytes(1, (int) blob.length());

						byte[] decrypted = encryption.decrypt(array);

						obj = (FootballClub) ser.deSerialise(decrypted);

					} catch (Exception exc) {
						System.out.println(exc);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		return obj;
	}

	public boolean removeClub(String name) {
		boolean bool = false;
		// delete from FootballClub database table
		try {
			conn = connection.getOracleConnection();
			PreparedStatement pstmt = conn.prepareStatement(queryDelete);
			pstmt.setString(1, name);
			bool = pstmt.execute();

		} catch (Exception e) {
			System.out.println(e);
		}
		return bool;
	}

}
