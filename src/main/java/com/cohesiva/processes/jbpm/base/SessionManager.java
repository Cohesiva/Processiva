package com.cohesiva.processes.jbpm.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class SessionManager implements ISessionManager {

	private final String filePath = "session";
	private File file;

	public SessionManager() {
		try {
			file = new File(filePath);

			if (!file.exists()) {

				file.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getLatestSessionId() {
		int sessionId = -1;

		try {
			FileInputStream fis = new FileInputStream(file);
			DataInputStream dis = new DataInputStream(fis);
			
			sessionId = dis.readInt();
			
			dis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return sessionId;
	}

	public void saveLatestSessionId(int sessionId) {
		try {			
			FileOutputStream fos = new FileOutputStream(file, false);
			DataOutputStream dos = new DataOutputStream(fos);
			
			dos.writeInt(sessionId);
			
			dos.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
