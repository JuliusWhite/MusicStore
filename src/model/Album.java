package model;

public class Album extends Article {

	public static enum GENRE {
		ROCK, TRAP, POP, HIPHOP, OTHERS
	};

	public static enum FORMAT {
		CD, VINYL, CASSETTE, DVD, DIGITAL, BLURAY;
	};

	protected String title, author;
	protected GENRE genre;
	protected int cdLength, songs;
	protected FORMAT format;

	public Album(int codArt, int stock, double price, String title, String author, GENRE genre, int cdLength, int songs,
			FORMAT format) {
		super(codArt, stock, price);
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.cdLength = cdLength;
		this.songs = songs;
		this.format = format;
	}

	public Album() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public GENRE getGenre() {
		return genre;
	}

	public void setGenre(GENRE genre) {
		this.genre = genre;
	}

	public int getCdLength() {
		return cdLength;
	}

	public void setCdLength(int cdLength) {
		this.cdLength = cdLength;
	}

	public int getSongs() {
		return songs;
	}

	public void setSongs(int songs) {
		this.songs = songs;
	}

	public FORMAT getFormat() {
		return format;
	}

	public void setFormat(FORMAT format) {
		this.format = format;
	}

	@Override
	public String toString() {
		return super.toString() + "\nAlbum: " + title + ", by " + author + ".\nGenre: " + genre + ".\nLength: "
				+ cdLength + " min" + ".\nSongs: " + songs + ".\nFormat: " + format + ".";
	}

	@Override
	public String toStringShort() {
		return "Album: " + title + ", by " + author + ".\nPrice: " + super.price + "$\n" + format;
	}

	// checks the genre of the db and returns it to set it into the object

	public static GENRE checkGenre(String genre) {
		genre = genre.toLowerCase();
		switch (genre) {
		case "rock":
			return GENRE.ROCK;
		case "trap":
			return GENRE.TRAP;
		case "pop":
			return GENRE.POP;
		case "hiphop":
			return GENRE.HIPHOP;
		default:
			return GENRE.OTHERS;
		}
	}

	// checks the genre of the method add or modify and returns it to set it into
	// the object

	public static GENRE changeGenre(String genre) {
		genre = genre.toLowerCase();
		switch (genre) {
		case "1":
			return GENRE.ROCK;
		case "2":
			return GENRE.TRAP;
		case "3":
			return GENRE.POP;
		case "4":
			return GENRE.HIPHOP;
		default:
			return GENRE.OTHERS;
		}
	}

	// prints the genre into a string

	public static String genreToString(String genre) {
		genre = genre.toLowerCase();
		switch (genre) {
		case "1":
			return "ROCK";
		case "2":
			return "TRAP";
		case "3":
			return "POP";
		case "4":
			return "HIPHOP";
		default:
			return "OTHERS";
		}
	}

	// checks the format of the db and returns it to set it into the object

	public static FORMAT checkFormat(String format) {
		format = format.toLowerCase();
		switch (format) {
		case "vinyl":
			return FORMAT.VINYL;
		case "cassette":
			return FORMAT.CASSETTE;
		case "dvd":
			return FORMAT.DVD;
		case "digital":
			return FORMAT.DIGITAL;
		case "bluray":
			return FORMAT.BLURAY;
		default:
			return FORMAT.CD;
		}
	}

	// checks the format of the method add or modify and returns it to set it into
	// the object

	public static FORMAT changeFormat(String format) {
		format = format.toLowerCase();
		switch (format) {
		case "1":
			return FORMAT.VINYL;
		case "2":
			return FORMAT.CASSETTE;
		case "3":
			return FORMAT.DVD;
		case "4":
			return FORMAT.DIGITAL;
		case "5":
			return FORMAT.BLURAY;
		default:
			return FORMAT.CD;
		}
	}

	// prints the format into a string

	public static String formatToString(String format) {
		format = format.toLowerCase();
		switch (format) {
		case "1":
			return "VINYL";
		case "2":
			return "CASSETTE";
		case "3":
			return "DVD";
		case "4":
			return "DIGITAL";
		case "5":
			return "BLURAY";
		default:
			return "CD";
		}
	}

}
