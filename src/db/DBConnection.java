package db;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {
	private static Connection conection;

	public DBConnection() {
		startConnection();
	}

	// starts the db connection with the data from the file configuration.props

	public void startConnection() {
		Properties rd = new Properties();
		String user = "";
		String pass = "";
		String rute = "";
		try {
			rd.load(new FileInputStream("configuration.props"));
			user = rd.getProperty("username");
			pass = rd.getProperty("password");
			rute = rd.getProperty("url") + ":" + rd.getProperty("port") + "/" + rd.getProperty("db");
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		conection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conection = DriverManager.getConnection(rute, user, pass);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			writeErrorXML(e);
			System.out.println(e.getMessage());
		}
	}

	public static void endConnection() {
		try {
			if (conection != null) {
				conection.close();
			}
		} catch (SQLException ex) {
			writeErrorXML(ex);
			System.out.println(ex.getMessage());
		}
	}

	public Connection getConexion() {
		return conection;
	}

	// writes an XML file with the SQL exceptions code an message

	public static void writeErrorXML(SQLException e) {
		File f = new File("errors.xml");
		try {
			if (!f.exists())
				f.createNewFile();
			ArrayList<String> lines = new ArrayList<>();
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = br.readLine();
			while (line != null) {
				lines.add(line);
				line = br.readLine();
			}
			br.close();
			BufferedWriter bw = null;
			br = null;
			if (lines.size() < 4) {
				br = new BufferedReader(new FileReader(f));
				bw = new BufferedWriter(new FileWriter(f));
				bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				bw.newLine();
				bw.write("<errors>");
				bw.newLine();
				if (e.getErrorCode() < 0) {
					bw.write("\t<error code=\"" + random(1000) + "\">" + e.getMessage() + "</error>");
					bw.newLine();
				} else {
					bw.write("\t<error code=\"" + e.getErrorCode() + "\">" + e.getMessage() + "</error>");
					bw.newLine();
				}
				bw.write("</errors>");
				bw.newLine();
				bw.flush();
			} else {
				br = new BufferedReader(new FileReader(f));
				lines = new ArrayList<>();
				line = br.readLine();
				while (line != null) {
					lines.add(line);
					line = br.readLine();
				}
				bw = new BufferedWriter(new FileWriter(f));
				for (int i = 0; i < lines.size() - 1; i++) {
					bw.write(lines.get(i));
					bw.newLine();
				}
				if (e.getErrorCode() < 0) {
					bw.write("\t<error code=\"" + random(1000) + "\">" + e.getMessage() + "</error>");
					bw.newLine();
				} else {
					bw.write("\t<error code=\"" + e.getErrorCode() + "\">" + e.getMessage() + "</error>");
					bw.newLine();
				}
				bw.write("</errors>");
				bw.newLine();
				bw.flush();
			}
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private static int random(int n) {
		double toret = Math.random() * n;
		return (int) toret;
	}
}