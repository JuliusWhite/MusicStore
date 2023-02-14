package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import db.DBConnection;
import model.*;
import model.Album.FORMAT;
import model.Album.GENRE;
import model.Poster.MATERIAL;

public class ArticleController {

	Scanner scan = new Scanner(System.in);

	private static DBConnection conection;
	private static ArrayList<Article> articles;
	
	public ArticleController() {
		conection = new DBConnection();
		articles = new ArrayList<Article>();
		try {
			recoverArticles();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
	}
	
	// recovers the db info and sets it into java objects

	private static void recoverArticles() throws SQLException {
		Statement s = conection.getConexion().createStatement();
		String query = "select * from article a " + "left join album a2 ON a.COD_ART = a2.COD_CD "
				+ "left join poster p on a.COD_ART = p.COD_POSTER ";
		ResultSet rs = s.executeQuery(query);
		while (rs.next()) {
			if (rs.getString("TITLE") != null) {
				Album a = new Album(rs.getInt("COD_ART"), rs.getInt("STOCK"), rs.getDouble("PRICE"),
						rs.getString("TITLE"), rs.getString("AUTHOR"), Album.checkGenre(rs.getString("GENRE")),
						rs.getInt("CD_LENGTH"), rs.getInt("SONGS"), Album.checkFormat(rs.getString("CD_FORMAT")));
				articles.add(a);
			} else {
				Poster p = new Poster(rs.getInt("COD_ART"), rs.getInt("STOCK"), rs.getDouble("PRICE"),
						rs.getString("BAND"), rs.getInt("HEIGHT"), rs.getInt("WIDTH"),
						Poster.checkMaterial(rs.getString("MATERIAL")));
				articles.add(p);
			}
		}
	}

	// shows all the articles with all their attributes
	
	public void showArticles() {
		for (Article a : articles) {
			System.out.println("\n" + a.toString());
		}
	}
	
	// shows one article with all its attributes

	public String showAnArticle(int i) {
		
		return articles.get(i).toString();
	}
	
	// shows all the articles with the most important attributes

	public void showArticlesShort() {
		for (Article a : articles) {
			System.out.println("\n" + a.toStringShort());
		}
	}
	
	// with the input data from the view sets all attributes to a new album

	public boolean addAlbum(int stock, double price, String title, String author, String genre, int length, int songs,
			String format) {
		int id = getLastID() + 1;
		if (Album.changeGenre(genre) == GENRE.OTHERS) {
			genre = "others";
		}
		if (Album.changeFormat(format) == FORMAT.CD) {
			format = "cd";
		}
		Album auxAlbum = new Album(id, stock, price, title, author, Album.changeGenre(genre), length, songs,
				Album.changeFormat(format));
		articles.add(auxAlbum);
		try {
			PreparedStatement ps = conection.getConexion()
					.prepareStatement("insert into article(COD_ART, STOCK, PRICE)values (?, ?, ?)");
			ps.setInt(1, id);
			ps.setInt(2, stock);
			ps.setDouble(3, price);
			ps.executeUpdate();
			ps.close();
			ps = conection.getConexion().prepareStatement(
					"insert into album (COD_CD, TITLE, AUTHOR, GENRE, CD_LENGTH, SONGS, CD_FORMAT)values (?, ?, ?, ?, ?, ?, ?)");
			ps.setInt(1, id);
			ps.setString(2, title);
			ps.setString(3, author);
			ps.setString(4, genre);
			ps.setInt(5, length);
			ps.setInt(6, songs);
			ps.setString(7, format);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
			try {
			Statement s = conection.getConexion().createStatement();
			s.executeUpdate("delete from article where COD_ART =" + id);
			} catch (SQLException ex) {
				DBConnection.writeErrorXML(ex);
				System.out.println(ex.getMessage());
			}
			return false;
		}
	}
	
	// with the input data from the view sets all attributes to a new poster

	public boolean addPoster(int stock2, double price2, String band, int height, int width, String material) {
		int id = getLastID() + 1;
		material = material.toLowerCase();
		if (Poster.changeMaterial(material) == MATERIAL.OTHERS) {
			material = "others";
		}
		Poster auxPoster = new Poster(id, stock2, price2, band, height, width, Poster.changeMaterial(material));
		articles.add(auxPoster);
		try {
			PreparedStatement ps = conection.getConexion()
					.prepareStatement("insert into article(COD_ART, STOCK, PRICE)values (?, ?, ?)");
			ps.setInt(1, id);
			ps.setInt(2, stock2);
			ps.setDouble(3, price2);
			ps.executeUpdate();
			ps.close();
			ps = conection.getConexion().prepareStatement(
					"insert into poster(COD_POSTER, BAND, HEIGHT, WIDTH, MATERIAL) values (?, ?, ?, ?, ?)");
			ps.setInt(1, id);
			ps.setString(2, band);
			ps.setInt(3, height);
			ps.setInt(4, width);
			ps.setString(5, material);
			ps.executeUpdate();
			ps.close();
			return true;
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	// returns true if the article in the position (pos) is an Album

	public boolean isAlbum(int pos) {
		if (articles.get(pos) instanceof Album) {
			return true;
		} else
			return false;
	}
	
	// method to change the stock of an article

	public boolean modifyStock(int pos, int newStock) {
		Article aux = articles.get(pos);
		System.out.println(
				"\nDo you want to update the album's stock from " + aux.getStock() + " to " + newStock + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setStock(newStock);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update article set stock = " + newStock + " where COD_ART = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the price of an article

	public boolean modifyPrice(int pos, double newPrice) {
		Article aux = articles.get(pos);
		System.out.println(
				"\nDo you want to update the album's price from " + aux.getPrice() + "$ to " + newPrice + "$?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setPrice(newPrice);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update article set price = " + newPrice + " where COD_ART = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the title of an album

	public boolean modifyTitle(int pos, String title) {
		Album aux = (Album) articles.get(pos);
		System.out.println(
				"\nDo you want to update the album's title from " + aux.getTitle() + " to " + title + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setTitle(title);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update album set title = '" + title + "' where COD_CD = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the author/band of an album

	public boolean modifyAuthor(int pos, String band) {
		Album aux = (Album) articles.get(pos);
		System.out
				.println("\nDo you want to update the album's band from " + aux.getAuthor() + " to " + band + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setAuthor(band);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update album set author = '" + band + "' where COD_CD = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the genre of an album

	public boolean modifyGenre(int pos, String genre) {
		Album aux = (Album) articles.get(pos);
		System.out.println("\nDo you want to update the album's genre from " + aux.getGenre() + " to "
				+ Album.genreToString(genre) + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setGenre(Album.changeGenre(genre));
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update album set genre = '" + Album.genreToString(genre) + "' where COD_CD = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the length of an album

	public boolean modifyLength(int pos, int length) {
		Album aux = (Album) articles.get(pos);
		System.out.println("\nDo you want to update the album's length from " + aux.getCdLength() + " min to " + length
				+ " min?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setCdLength(length);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update album set cd_length = " + length + " where COD_CD = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the songs of an album

	public boolean modifySongs(int pos, int songs) {
		Album aux = (Album) articles.get(pos);
		System.out.println("\nDo you want to update the album's number of songs from " + aux.getSongs() + " to " + songs
				+ "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setSongs(songs);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update album set songs = " + songs + " where COD_CD = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the format of an album

	public boolean modifyFormat(int pos, String format) {
		Album aux = (Album) articles.get(pos);
		System.out.println("\nDo you want to update the album's format from " + aux.getFormat() + " to "
				+ Album.formatToString(format) + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setFormat(Album.changeFormat(format));
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update album set cd_format = '" + Album.formatToString(format) + "' where COD_CD = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the band of an poster

	public boolean modifyBand(int pos, String band) {
		Poster aux = (Poster) articles.get(pos);
		System.out
				.println("\nDo you want to update the poster's band from " + aux.getBand() + " to " + band + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setBand(band);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update poster set band = '" + band + "' where COD_POSTER = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the height of an poster

	public boolean modifyHeight(int pos, int height) {
		Poster aux = (Poster) articles.get(pos);
		System.out.println(
				"\nDo you want to update the poster's height from " + aux.getHeight() + " to " + height + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setHeight(height);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update poster set height = " + height + " where COD_POSTER = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the width of an poster

	public boolean modifyWidth(int pos, int width) {
		Poster aux = (Poster) articles.get(pos);
		System.out.println(
				"\nDo you want to update the poster's width from " + aux.getWidth() + " to " + width + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setWidth(width);
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate("update poster set width = " + width + " where COD_POSTER = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to change the material of an poster

	public boolean modifyMaterial(int pos, String material) {
		Poster aux = (Poster) articles.get(pos);
		System.out.println("\nDo you want to update the poster's format from " + aux.getMaterial() + " to "
				+ Poster.materialToString(material) + "?(y/n)");
		String ans = scan.nextLine();
		ans = ans.toLowerCase();
		switch (ans) {
		case "n":
			return false;
		case "y":
			aux.setMaterial(Poster.changeMaterial(material));
			try {
				Statement s = conection.getConexion().createStatement();
				s.executeUpdate(
						"update poster set material = '" + Poster.materialToString(material) + "' where COD_POSTER = " + aux.getCodArt());
				s.close();
				return true;
			} catch (SQLException e) {
				DBConnection.writeErrorXML(e);
				System.out.println(e.getMessage());
			}
		default:
			System.out.println("\nError in the data input.");
			return false;
		}
	}
	
	// method to delete an article from the db

	public boolean deleteArticle(int id, int pos, String aux) {
		if (pos > -1) {
			aux = aux.toLowerCase();
			switch (aux) {
			case "n":
				return false;
			case "y":
				articles.remove(pos);
				try {
					Statement s = conection.getConexion().createStatement();
					s.executeUpdate("delete from article where COD_ART =" + id);
					ResultSet rs = s.executeQuery("select COD_TIC from ticket order by COD_TIC desc limit 1");
					rs.next();
					int x = rs.getInt("COD_TIC");
					for (int i = 1; i <= x; i++) {
						s.executeQuery("call CheckATRows(" + i + ")");
					}
					s.close();
					return true;
				} catch (SQLException e) {
					DBConnection.writeErrorXML(e);
					System.out.println(e.getMessage());
				}
			default:
				System.out.println("\nError in the data input. Returning to the main menu.");
				return false;
			}
		} else
			return false;
	}
	
	// method to see the tickets resume

	public void ticketsResume() {
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select * from AdminViewTickets");
			System.out.print("COD_TIC");
			System.out.print("\t\tCOD_ART_AT");
			System.out.print("\tAMOUNT");
			System.out.print("\t\tDATE_TIME");
			System.out.println();
			System.out.println();
			while (rs.next()) {
				System.out.print(rs.getInt("COD_TIC"));
				System.out.print("\t\t" + rs.getInt("COD_ART_AT"));
				System.out.print("\t\t" + rs.getInt("AMOUNT"));
				System.out.print("\t\t" + rs.getTimestamp("DATE_TIME"));
				System.out.println();
			}
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
	}
	
	// method to see the top 5

	public void top5() {
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select * from Top5Articles");
			System.out.print("COD_ART_AT");
			System.out.print("\tTITLE");
			System.out.print("\t\tP_BAND");
			System.out.print("\t\tSTOCK");
			System.out.print("\t\tPRICE");
			System.out.print("\t\tSOLD_U");
			System.out.println();
			System.out.println();
			while (rs.next()) {
				System.out.print(rs.getInt("COD_ART_AT"));
				System.out.print("\t\t" + rs.getString("TITLE"));
				System.out.print("\t\t" + rs.getString("BAND"));
				System.out.print("\t\t" + rs.getInt("STOCK"));
				System.out.print("\t\t" + rs.getDouble("PRICE"));
				System.out.print("\t\t" + rs.getInt("UD_SOLD"));
				System.out.println();
			}
			rs.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
	}
	
	// method that returns the position in the arraylist of the article with code (id) 

	public int findID(int id) {
		boolean avaliable = false;
		int pos = -1;
		do {
			try {
				for (int i = 0; i < articles.size(); i++) {
					if (articles.get(i).getCodArt() == id) {
						avaliable = true;
						pos = i;
						return pos;
					}
				}
				if (id == 0) {
					System.out.println("\nReturning to the main menu.");
					break;
				}
				if (!avaliable) {
					System.out.println(
							"\nThe selected ID was not found in the database. Please repeat the ID input:\n(0 for returning to the main menu.)");
					id = Integer.parseInt(scan.nextLine());
				}
			} catch (NumberFormatException e) {
				System.out.println("\nYou must enter a number.");
			}
		} while (!avaliable);
		return pos;
	}
	
	// method that returns the last ID in the arraylist

	public int getLastID() {
		int x = -1;
		try {
			Statement s = conection.getConexion().createStatement();
			ResultSet rs = s.executeQuery("select COD_ART from article order by COD_ART desc limit 1");
			rs.next();
			x = rs.getInt("COD_ART");
			s.close();
		} catch (SQLException e) {
			DBConnection.writeErrorXML(e);
			System.out.println(e.getMessage());
		}
		return x;
	}

	public static DBConnection getConection() {
		return conection;
	}

	public static void setConection(DBConnection conection) {
		ArticleController.conection = conection;
	}

	public static ArrayList<Article> getArticles() {
		return articles;
	}

	public static void setArticles(ArrayList<Article> articles) {
		ArticleController.articles = articles;
	}

}
