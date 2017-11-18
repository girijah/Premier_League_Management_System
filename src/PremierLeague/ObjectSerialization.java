package PremierLeague;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectSerialization implements Serializable{

	public ObjectSerialization() {

	}

	public byte[] serialise(Object obj) {
		ByteArrayOutputStream outputByteArray = new ByteArrayOutputStream();
		ObjectOutput objOutput = null;
		byte[] myBytes = null;

		try {

			objOutput = new ObjectOutputStream(outputByteArray);
			objOutput.writeObject(obj);

			myBytes = outputByteArray.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objOutput != null) {
					objOutput.close();
					outputByteArray.close();
				}
			} catch (IOException exc) {
				exc.printStackTrace();
			}

		}
		return myBytes;
	}

	public Object deSerialise(byte[] myBytes) {

		ByteArrayInputStream inputByteArray = new ByteArrayInputStream(myBytes);
		ObjectInput objInput = null;
		Object o = null;

		try {
			objInput = new ObjectInputStream(inputByteArray);
			o = objInput.readObject();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			try {
				inputByteArray.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (objInput != null) {
				try {
					objInput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return o;
	}

}
