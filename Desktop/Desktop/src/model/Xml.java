package model;

import java.util.ArrayList;
import java.util.Date;

public class Xml {

	private String username;
	private static ArrayList<Image> images;
	
	//private Date date;
	
	public Xml(String username, ArrayList<Image> images) {
		super();
		this.username = username;
		Xml.images = images;
		//this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static ArrayList<Image> getImages() {
		return images;
	}

	public void setImages(ArrayList<Image> images) {
		Xml.images = images;
	}

//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}
}
