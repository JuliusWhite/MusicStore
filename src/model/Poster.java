package model;

public class Poster extends Article {

	public static enum MATERIAL {
		PLASTIC, FABRIC, METALIC, OTHERS
	};

	protected String band;
	protected int height, width;
	protected MATERIAL material;

	public Poster(int codArt, int stock, double price, String band, int height, int width, MATERIAL material) {
		super(codArt, stock, price);
		this.band = band;
		this.height = height;
		this.width = width;
		this.material = material;
	}

	public Poster() {
		super();
	}

	public String getBand() {
		return band;
	}

	public void setBand(String band) {
		this.band = band;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public MATERIAL getMaterial() {
		return material;
	}

	public void setMaterial(MATERIAL material) {
		this.material = material;
	}

	@Override
	public String toString() {
		return super.toString() + "\n" + band + " poster.\nHeight: " + height + "cm.\nWidth: " + width
				+ "cm.\nMaterial: " + material + ".";
	}

	@Override
	public String toStringShort() {
		return band + " poster, " + height + "x" + width + " cm.\nPrice: " + super.price + "$.\nMaterial: " + material
				+ ".";
	}

	// checks the material of the db and returns it to set it into the object

	public static MATERIAL checkMaterial(String material) {
		material = material.toLowerCase();
		switch (material) {
		case "plastic":
			return MATERIAL.PLASTIC;
		case "fabric":
			return MATERIAL.FABRIC;
		case "metalic":
			return MATERIAL.METALIC;
		default:
			return MATERIAL.OTHERS;
		}
	}

	// checks the material of the method add or modify and returns it to set it into
	// the object

	public static MATERIAL changeMaterial(String material) {
		material = material.toLowerCase();
		switch (material) {
		case "1":
			return MATERIAL.PLASTIC;
		case "2":
			return MATERIAL.FABRIC;
		case "3":
			return MATERIAL.METALIC;
		default:
			return MATERIAL.OTHERS;
		}
	}

	// prints the material into a string

	public static String materialToString(String material) {
		material = material.toLowerCase();
		switch (material) {
		case "1":
			return "PLASTIC";
		case "2":
			return "FABRIC";
		case "3":
			return "METALIC";
		default:
			return "OTHERS";
		}
	}

}
