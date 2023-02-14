package view;

import java.util.Scanner;
import controller.ArticleController;

public class ArticleView {

	private static Scanner scan = new Scanner(System.in);

	private static ArticleController controller;

	// menu for the article manager

	public static void articleManager() {
		controller = new ArticleController();
		String op = "";
		while (!op.equals("exit")) {
			System.out.println(
					"\nWelcome to the music store manager system. Enter one of the following options to access one of the menu's:\n"
							+ "\t1. Show all the articles in the database in a short way.\n"
							+ "\t2. Show all the articles in the database in a long way.\n"
							+ "\t3. Add an article to the database.\n"
							+ "\t4. Modify some information from an article.\n"
							+ "\t5. Delete an article from the databse.\n"
							+ "\t6. Resume of the tickets form the database.\n"
							+ "\t7. Show the most sold 5 articles.\n" + "Enter 'exit' to leave the system.\n");
			op = scan.nextLine();
			switch (op) {
			case "1":
				controller.showArticlesShort();
				break;
			case "2":
				controller.showArticles();
				break;
			case "3":
				newArticle();
				break;
			case "4":
				controller.showArticles();
				int id_m = askForID();
				if (id_m == 0) {
					break;
				}
				int pos_m;
				do {
					pos_m = controller.findID(id_m);
				} while (pos_m < -1);
				if (pos_m == -1) {
					break;
				}
				modifyArticle(pos_m);
				break;
			case "5":
				controller.showArticles();
				int id = askForID();
				int pos = controller.findID(id);
				if (pos > -1) {
					System.out.println("\nAre you sure you want to delete the article:\n"
							+ controller.showAnArticle(pos) + "\n?\n(y/n)");
					String aux = scan.nextLine();
					if (controller.deleteArticle(id, pos, aux)) {
						System.out.println("\nThe article was successfully deleted.");
					} else
						System.out.println("\nThe article was not deleted. Returning to the main menu.");
				}
				break;
			case "6":
				controller.ticketsResume();
				break;
			case "7":
				controller.top5();
				break;
			case "exit":
				System.out.println("\nSee you next time!");
				break;
			default:
				System.out.println("\nError in the data input. Please try again.\n");
				break;
			}
		}
		scan.close();
	}

	// method that asks the user for the input data to add a new article

	private static void newArticle() {
		System.out
				.println("\nEnter 1 if the article is an album, enter 2 if it's a poster instead:\nEnter 0 to return.");
		String aux = "";
		do {
			aux = scan.nextLine();
			switch (aux) {
			case "1":
				System.out.println("\nEnter the new album title:");
				String title = scan.nextLine();
				while (title.length() < 2 || title.length() > 50) {
					System.out.println(
							"\nAn error has occurred, the title must be between 2 and 50 characters long. Please repeate the title:");
					title = scan.nextLine();
				}
				System.out.println("\nBy:");
				String author = scan.nextLine();
				while (author.length() < 2 || author.length() > 50) {
					System.out.println(
							"\nAn error has occurred, the author must be between 2 and 50 characters long. Please repeate the author/band:");
					author = scan.nextLine();
				}
				System.out.println("\nEnter the new album's stock:");
				boolean repeat = true;
				int stock = -1;
				do {
					try {
						stock = Integer.parseInt(scan.nextLine());
						repeat = false;
						if (stock < 1) {
							System.out.println("\nThe stock must be greater than 0. Please repeat the stock:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the stock:");
						repeat = true;
					}
				} while (repeat || stock < 1);
				System.out.println("\nEnter the new album's price($):");
				repeat = true;
				double price = -1;
				do {
					try {
						price = Double.parseDouble(scan.nextLine());
						repeat = false;
						if (price < 1 || price >= 1000) {
							System.out.println(
									"\nThe price must be greater than 0 and lower than 1000. Please repeat the price:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the price:");
						repeat = true;
					}
				} while (repeat || price < 1 || price >= 1000);
				System.out.println(
						"\nEnter the number that corresponds to the new album's genre:\n1. ROCK\n2. TRAP\n3. POP\n4. HIPHOP\nTo use default value enter intro:\nDefault genre: OTHERS");
				String genre = scan.nextLine();
				System.out.println("\nEnter the new album's length in minutes:");
				repeat = true;
				int length = -1;
				do {
					try {
						length = Integer.parseInt(scan.nextLine());
						repeat = false;
						if (length < 10 || length > 300) {
							repeat = true;
							System.out.println(
									"\nThe length must be greater than 10 and little than 300 minutes. Please repeat the length:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the length in minutes:");
						repeat = true;
					}
				} while (repeat);
				System.out.println("\nEnter the new album's number of songs:");
				repeat = true;
				int songs = -1;
				do {
					try {
						songs = Integer.parseInt(scan.nextLine());
						repeat = false;
						if (songs < 1 || songs > 50) {
							System.out.println(
									"\nThe number of songs must be greater than 0 and little than 51 minutes. Please repeat the number of tracks:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the number of tracks:");
						repeat = true;
					}
				} while (repeat || songs < 1 || songs > 50);
				System.out.println(
						"\nEnter the number that correspond to the new album's format:\n1. VINYL\n2. CASSETTE\n3. DVD\n4. DIGITAL\n5. BLURAY\nTo use default value enter intro:\nDefault format: CD");
				String format = scan.nextLine();
				if (controller.addAlbum(stock, price, title, author, genre, length, songs, format)) {
					System.out.println("\nThe " + controller.showAnArticle(controller.findID(controller.getLastID()))
					+ "\nwas succsessfully added to the database.");
				} else
					System.out.println("\nThe album wasn't able to be added to the database.");
				break;
			case "2":
				System.out.println("\nEnter the band of the new poster:");
				String band = scan.nextLine();
				while (band.length() < 2 || band.length() > 50) {
					System.out.println(
							"\nAn error has occurred, the band name must be between 2 and 50 characters long. Please repeate the band:");
					band = scan.nextLine();
				}
				System.out.println("\nEnter the new poster's stock:");
				boolean repeat2 = true;
				int stock2 = -1;
				do {
					try {
						stock2 = Integer.parseInt(scan.nextLine());
						repeat2 = false;
						if (stock2 < 1) {
							System.out.println("\nThe stock must be greater than 0. Please repeat the stock:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the stock:");
						repeat2 = true;
					}
				} while (repeat2 || stock2 < 1);
				System.out.println("\nEnter the new poster's price($):");
				repeat2 = true;
				double price2 = -1;
				do {
					try {
						price2 = Double.parseDouble(scan.nextLine());
						repeat2 = false;
						if (price2 < 1 || price2 >= 1000) {
							System.out.println(
									"\nThe price must be greater than 0 and lower than 1000. Please repeat the price:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the price:");
						repeat2 = true;
					}
				} while (repeat2 || price2 < 1 || price2 >= 1000);
				System.out.println("\nEnter the new poster's height in cm:");
				repeat = true;
				int height = -1;
				do {
					try {
						height = Integer.parseInt(scan.nextLine());
						repeat = false;
						if (height < 1 || height >= 1000) {
							System.out.println(
									"\nThe height must be greater than 0 and little than 1000 cm. Please repeat the height:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the height in cm:");
						repeat = true;
					}
				} while (repeat || height < 1 || height >= 1000);
				System.out.println("\nEnter the new poster's width in cm:");
				repeat = true;
				int width = -1;
				do {
					try {
						width = Integer.parseInt(scan.nextLine());
						repeat = false;
						if (width < 1 || width >= 1000) {
							System.out.println(
									"\nThe width must be greater than 0 and little than 1000 cm. Please repeat the width:");
						}
					} catch (NumberFormatException e) {
						System.out.println("\nYou must enter a number. Please repeat the width in cm:");
						repeat = true;
					}
				} while (repeat || width < 1 || width >= 1000);
				System.out.println(
						"\nEnter the number that correspons to the new poster's material\n1. PLASTIC.\n2. FABRIC\n3. METALIC\nTo use default value enter intro:\nDefault material: OTHERS");
				String material = scan.nextLine();
				if (controller.addPoster(stock2, price2, band, height, width, material)) {
					System.out.println("\nThe " + controller.showAnArticle(controller.findID(controller.getLastID()))
							+ "\nwas succsessfully added to the database.");
				} else
					System.out.println("\nThe poster wasn't able to be added to the database.");
				break;
			case "0":
				System.out.println("\nReturning to the main menu.");
				break;
			default:
				System.out.println("\nError in the data input. Please try again:");
				break;
			}
		} while (!aux.equals("1") && !aux.equals("2") && !aux.equals("0"));
	}

	// method that show the menu for changing daata from existing articles and ask
	// for the respective input data

	private static void modifyArticle(int pos) {
		System.out.println("\nYou are about to modify de data from the article:\n" + controller.showAnArticle(pos));
		if (controller.isAlbum(pos)) {
			String op = "";
			while (!op.equals("0")) {
				System.out.println("\nEnter one of the following options to modify some of album's data:\n"
						+ "\t1. Modify stock.\n" + "\t2. Modify price.\n" + "\t3. Modify title.\n"
						+ "\t4. Modify author/band.\n" + "\t5. Modify genre.\n" + "\t6. Modify length.\n"
						+ "\t7. Modify number of songs.\n" + "\t8. Modify format.\n"
						+ "Enter '0' to return to the main menu.\n");
				op = scan.nextLine();
				switch (op) {
				case "0":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nReturning to the main menu.");
					break;
				case "1":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the album's new stock:");
					boolean repeat = true;
					int newStock = -1;
					do {
						try {
							newStock = Integer.parseInt(scan.nextLine());
							repeat = false;
							if (newStock < 0) {
								System.out.println("\nThe stock must be positive. Please repeat the stock:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the stock:");
							repeat = true;
						}
					} while (repeat || newStock < 0);
					if (controller.modifyStock(pos, newStock)) {
						System.out.println("\nThe stock was successfully updated.");
					} else
						System.out.println("\nThe stock was't updated.");
					break;
				case "2":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the album's new price($):");
					repeat = true;
					double newPrice = -1;
					do {
						try {
							newPrice = Double.parseDouble(scan.nextLine());
							repeat = false;
							if (newPrice < 1 || newPrice >= 1000) {
								System.out.println(
										"\nThe price must be greater than 0 and lower than 1000. Please repeat the price:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the price:");
							repeat = true;
						}
					} while (repeat || newPrice < 1 || newPrice >= 1000);
					if (controller.modifyPrice(pos, newPrice)) {
						System.out.println("\nThe price was successfully updated.");
					} else
						System.out.println("\nThe price was't updated.");
					break;
				case "3":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the album's new title:");
					String title = "";
					title = scan.nextLine();
					while (title.length() < 2 || title.length() > 50) {
						System.out.println(
								"\nAn error has occurred, the title must be between 2 and 50 characters long. Please repeat the title:");
						title = scan.nextLine();
					}
					;
					if (controller.modifyTitle(pos, title)) {
						System.out.println("\nThe title was successfully updated.");
					} else
						System.out.println("\nThe title was't updated.");
					break;
				case "4":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the album's new author/band:");
					String band = "";
					band = scan.nextLine();
					while (band.length() < 2 || band.length() > 50) {
						System.out.println(
								"\nAn error has occurred, the band must be between 2 and 50 characters long. Please repeat the band:");
						band = scan.nextLine();
					}
					;
					if (controller.modifyAuthor(pos, band)) {
						System.out.println("\nThe band was successfully updated.");
					} else
						System.out.println("\nThe band was't updated.");
					break;
				case "5":
					System.out.println(controller.showAnArticle(pos));
					System.out.println(
							"\nEnter the number that corresponds to the album's new genre:\n1. ROCK\n2. TRAP\n3. POP\n4. HIPHOP\nTo use default value enter intro:\nDefault genre: OTHERS");
					String genre = scan.nextLine();
					if (controller.modifyGenre(pos, genre)) {
						System.out.println("\nThe genre was successfully updated.");
					} else
						System.out.println("\nThe genre was't updated.");
					break;
				case "6":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the album's new length:");
					repeat = true;
					int length = -1;
					do {
						try {
							length = Integer.parseInt(scan.nextLine());
							repeat = false;
							if (length < 10 || length > 300) {
								repeat = true;
								System.out.println(
										"\nThe length must be greater than 10 and little than 300 minutes. Please repeat the length:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the length in minutes:");
							repeat = true;
						}
					} while (repeat);
					if (controller.modifyLength(pos, length)) {
						System.out.println("\nThe length was successfully updated.");
					} else
						System.out.println("\nThe length was't updated.");
					break;
				case "7":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the album's new number of songs:");
					repeat = true;
					int songs = -1;
					do {
						try {
							songs = Integer.parseInt(scan.nextLine());
							repeat = false;
							if (songs < 1 || songs > 50) {
								System.out.println(
										"\nThe number of songs must be greater than 0 and little than 51. Please repeat the number of tracks:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the number of tracks:");
							repeat = true;
						}
					} while (repeat || songs < 1 || songs > 50);
					if (controller.modifySongs(pos, songs)) {
						System.out.println("\nThe number of songs was successfully updated.");
					} else
						System.out.println("\nThe number of songs was't updated.");
					break;
				case "8":
					System.out.println(controller.showAnArticle(pos));
					System.out.println(
							"\nEnter the number that corresponds to the album's new format:\n1. VINYL\n2. CASSETTE\n3. DVD\n4. DIGITAL\n5. BLURAY\nTo use default value enter intro:\nDefault format: CD");
					String format = scan.nextLine();
					if (controller.modifyFormat(pos, format)) {
						System.out.println("\nThe format was successfully updated.");
					} else
						System.out.println("\nThe format was't updated.");
					break;
				default:
					System.out.println("\nError in the data input. Please try again.\n");
					break;
				}
			}
		} else {
			String op = "";
			while (!op.equals("0")) {
				System.out.println("\nEnter one of the following options to modify some of poster's data:\n"
						+ "\t1. Modify stock.\n" + "\t2. Modify price.\n" + "\t3. Modify band.\n"
						+ "\t4. Modify height.\n" + "\t5. Modify width.\n" + "\t6. Modify material.\n"
						+ "Enter '0' to return to the main menu.\n");
				op = scan.nextLine();
				switch (op) {
				case "0":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nReturning to the main menu.");
					break;
				case "1":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the poster's new stock:");
					boolean repeat = true;
					int newStock = -1;
					do {
						try {
							newStock = Integer.parseInt(scan.nextLine());
							repeat = false;
							if (newStock < 0) {
								System.out.println("\nThe stock must be positive. Please repeat the stock:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the stock:");
							repeat = true;
						}
					} while (repeat || newStock < 0);
					if (controller.modifyStock(pos, newStock)) {
						System.out.println("\nThe stock was successfully updated.");
					} else
						System.out.println("\nThe stock was't updated.");
					break;
				case "2":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the poster's new price($):");
					repeat = true;
					double newPrice = -1;
					do {
						try {
							newPrice = Double.parseDouble(scan.nextLine());
							repeat = false;
							if (newPrice < 1 || newPrice >= 1000) {
								System.out.println(
										"\nThe price must be greater than 0 and lower than 1000. Please repeat the price:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the price:");
							repeat = true;
						}
					} while (repeat || newPrice < 1 || newPrice >= 1000);
					if (controller.modifyPrice(pos, newPrice)) {
						System.out.println("\nThe price was successfully updated.");
					} else
						System.out.println("\nThe price was't updated.");
					break;
				case "3":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the poster's new band:");
					String band = "";
					band = scan.nextLine();
					while (band.length() < 2 || band.length() > 50) {
						System.out.println(
								"\nAn error has occurred, the band must be between 2 and 50 characters long. Please repeat the band:");
						band = scan.nextLine();
					}
					;
					if (controller.modifyBand(pos, band)) {
						System.out.println("\nThe band was successfully updated.");
					} else
						System.out.println("\nThe band was't updated.");
					break;
				case "4":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the poster's new height in cm:");
					repeat = true;
					int height = -1;
					do {
						try {
							height = Integer.parseInt(scan.nextLine());
							repeat = false;
							if (height < 1 || height >= 1000) {
								System.out.println(
										"\nThe height must be greater than 0 and little than 1000 minutes. Please repeat the height in cm:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the height in cm:");
							repeat = true;
						}
					} while (repeat || height < 1 || height >= 1000);
					if (controller.modifyHeight(pos, height)) {
						System.out.println("\nThe height was successfully updated.");
					} else
						System.out.println("\nThe height was't updated.");
					break;
				case "5":
					System.out.println(controller.showAnArticle(pos));
					System.out.println("\nEnter the poster's new width in cm:");
					repeat = true;
					int width = -1;
					do {
						try {
							width = Integer.parseInt(scan.nextLine());
							repeat = false;
							if (width < 1 || width >= 1000) {
								System.out.println(
										"\nThe width must be greater than 0 and little than 1000 minutes. Please repeat the width in cm:");
							}
						} catch (NumberFormatException e) {
							System.out.println("\nYou must enter a number. Please repeat the width in cm:");
							repeat = true;
						}
					} while (repeat || width < 1 || width >= 1000);
					if (controller.modifyWidth(pos, width)) {
						System.out.println("\nThe width was successfully updated.");
					} else
						System.out.println("\nThe width was't updated.");
					break;
				case "6":
					System.out.println(controller.showAnArticle(pos));
					System.out.println(
							"\nEnter the number that corresponds to the poster's new material:\n1. PLASTIC\n2. FABRIC\n3. METALIC\nTo use default value enter intro:\nDefault format: OTHERS");
					String material = scan.nextLine();
					if (controller.modifyMaterial(pos, material)) {
						System.out.println("\nThe material was successfully updated.");
					} else
						System.out.println("\nThe material was't updated.");
					break;
				default:
					System.out.println("\nError in the data input. Please try again.\n");
					break;
				}
			}
		}
	}

	// this method asks for a correct ID

	private static int askForID() {
		boolean repeat = true;
		int id = -1;
		do {
			System.out.println(
					"\nEnter the ID of the article you want to select:\nEnter a 0 if you want to return to the main menu.");
			try {
				id = Integer.parseInt(scan.nextLine());
				repeat = false;
			} catch (NumberFormatException e) {
				System.out.println("\nYou must enter a number. Please repeat the data input:");
				repeat = true;
			}
		} while (repeat);
		return id;
	}
}
