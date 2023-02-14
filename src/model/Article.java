package model;

public abstract class Article {

	protected int codArt, stock;
	protected double price;

	public Article(int codArt, int stock, double price) {
		super();
		this.codArt = codArt;
		this.stock = stock;
		this.price = price;
	}

	public Article() {
		super();
	}

	public int getCodArt() {
		return codArt;
	}

	public void setCodArt(int codArt) {
		this.codArt = codArt;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Article: " + codArt + ".\nStock: " + stock + ".\nPrice: " + price + "$.";
	}
	
	public String toStringShort() {
		return "Article: " + codArt + ".\nStock: " + stock + ".\nPrice: " + price + "$.";
	}

}
