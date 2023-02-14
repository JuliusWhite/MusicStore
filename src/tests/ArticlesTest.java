package tests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import controller.*;
import db.DBConnection;

class ArticlesTest {

	// this JUnit test is intended to be ran from the begging to the end, before
	// making any change in the db, deleting the articles you'll be asked to.

	static Scanner scan;
	private static ArticleController aC;
	private static DBConnection conection;

	@BeforeAll
	static void scanner() {
		scan = new Scanner(System.in);
	}

	@BeforeEach
	void generateObjects() {
		aC = new ArticleController();
		conection = new DBConnection();
	}

	@Test
	@DisplayName("Correct article add.")
	@Tag("add")
	void test1AddArticle() {
		int actualArray = ArticleController.getArticles().size();
		int actualDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			actualDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		int stock = 31;
		double price = 24.99;
		String title = "Unlimited Love";
		String author = "Red Hot Chili Peppers";
		String genre = "1";
		int length = 73;
		int songs = 17;
		String format = "1";
		aC.addAlbum(stock, price, title, author, genre, length, songs, format);
		int expectedArray = ArticleController.getArticles().size();
		int expectedDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			expectedDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		assertEquals(actualArray + actualDB, expectedArray + expectedDB - 2);
	}

	@Test
	@DisplayName("Incorrect article add.")
	@Tag("add")
	void test2AddArticleFail() {
		int actualArray = ArticleController.getArticles().size();
		int actualDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			actualDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		int stock = 25;
		double price = 14.99;
		String title = "This Is All Yours";
		String author = "Alt-J";
		String genre = "cd";
		int length = 63;
		int songs = 13;
		String format = "1";
		aC.addAlbum(stock, price, title, author, genre, length, songs, format);
		int expectedArray = ArticleController.getArticles().size();
		int expectedDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			expectedDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		assertNotEquals(actualArray + actualDB, expectedArray + expectedDB - 2);
	}

	@Test
	@DisplayName("Correct article delete.")
	@Tag("delete")
	// an article must be deleted from the database
	void test3DeleteArticle() {
		int actualArray = ArticleController.getArticles().size();
		int actualDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			actualDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		int pos = aC.findID(21);
		if (pos > -1) {
			System.out
					.println("\nAre you sure you want to delete the article:\n" + aC.showAnArticle(pos) + "\n?\n(y/n)");
			String aux = scan.nextLine();
			if (aC.deleteArticle(21, pos, aux)) {
				System.out.println("\nThe article was successfully deleted.");
			} else
				System.out.println("\nThe article was not deleted. Returning to the main menu.");
		}
		int expectedArray = ArticleController.getArticles().size();
		int expectedDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			expectedDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		assertEquals(actualArray + actualDB, expectedArray + expectedDB + 2);
	}

	@Test
	@DisplayName("Incorrect article delete.")
	@Tag("delete")
	// an article must be deleted from the database
	void test4DeleteArticleFail() {
		int actualArray = ArticleController.getArticles().size();
		int actualDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			actualDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		int pos = aC.findID(22);
		if (pos > -1) {
			System.out
					.println("\nAre you sure you want to delete the article:\n" + aC.showAnArticle(pos) + "\n?\n(y/n)");
			String aux = scan.nextLine();
			if (aC.deleteArticle(22, pos, aux)) {
				System.out.println("\nThe article was successfully deleted.");
			} else
				System.out.println("\nThe article was not deleted. Returning to the main menu.");
		}
		int expectedArray = ArticleController.getArticles().size();
		int expectedDB = 0;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select count(*) from article a");
			rs.next();
			expectedDB = rs.getInt("count(*)");
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		assertNotEquals(actualArray + actualDB, expectedArray + expectedDB + 2);
	}

	@AfterAll
	static void scannerClose() {
		scan.close();
	}

}
