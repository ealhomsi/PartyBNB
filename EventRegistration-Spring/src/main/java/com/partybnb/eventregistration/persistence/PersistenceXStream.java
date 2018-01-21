package com.partybnb.eventregistration.persistence;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Repository;

import com.partybnb.eventregistration.model.Event;
import com.partybnb.eventregistration.model.Participant;
import com.partybnb.eventregistration.model.Registration;
import com.partybnb.eventregistration.model.RegistrationManager;
import com.thoughtworks.xstream.XStream;

// The first type parameter is the domain type for wich we are creating the repository.
// The second is the key type that is used to look it up. This example will not use it.
@Repository
public class PersistenceXStream {

	private static XStream xstream = new XStream();
	private static String filename = "data.xml";

	// TODO create the RegistrationManager instance here (replace the void return
	// value as well)
	public static RegistrationManager initializeModelManager(String fileName) {
		// Initialization for persistence
		RegistrationManager rm;
		setFilename(fileName);
		setAlias("event", Event.class);
		setAlias("participant", Participant.class);
		setAlias("registration", Registration.class);
		setAlias("manager", RegistrationManager.class);

		// load model if exists, create otherwise
		File file = new File(fileName);
		if (file.exists()) {
			rm = (RegistrationManager) loadFromXMLwithXStream();
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			rm = new RegistrationManager();
			saveToXMLwithXStream(rm);
		}
		return rm;
	}

	public static boolean saveToXMLwithXStream(Object obj) {
		xstream.setMode(XStream.ID_REFERENCES);
		String xml = xstream.toXML(obj); // save our xml file

		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(xml);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Object loadFromXMLwithXStream() {
		xstream.setMode(XStream.ID_REFERENCES);
		try {
			FileReader fileReader = new FileReader(filename); // load our xml file
			return xstream.fromXML(fileReader);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void setAlias(String xmlTagName, Class<?> className) {
		xstream.alias(xmlTagName, className);
	}

	public static void setFilename(String fn) {
		filename = fn;
	}

	public static String getFilename() {
		return filename;
	}

	public static void clearData() {
		File myFoo = new File(filename);
		FileWriter fooWriter;
		try {
			fooWriter = new FileWriter(myFoo, false);
			fooWriter.write("");
			fooWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
