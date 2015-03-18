package com.oxygen8.net;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable
{
  private String details;
  private String thumbnailUrl;
  private String title;
  private String contact_name;
  private String phone_contact;
  private String location;
  private String category;
  private String price;
  private String email;
  private String postDate;
  
  public Movie() {}
  
  public Movie(String paramString1, String paramString2, String paramString3)
  {
    this.title = paramString1;
    this.thumbnailUrl = paramString2;
    this.details = paramString3;
  }
  public Movie (String title,String thumbnailUrl,String details,String contact_name,String phone_contact,String location,String category,String price,String email){
	  
	  this.title = title;
	  this.thumbnailUrl =thumbnailUrl;
	  this.details = details;
	  this.contact_name = contact_name;
	  this.phone_contact = phone_contact;
	  this.location = location;
	  this.category = category;
	  this.price = price;
	  this.email = email;
  }
  
  public String getPostDate() {
	return postDate;
  }

  public void setPostDate(String postDate) {
	this.postDate = postDate;
  }

  public String getContact_name() {
	return contact_name;
  }

  public void setContact_name(String contact_name) {
	this.contact_name = contact_name;
  }

  public String getPhone_contact() {
	return phone_contact;
  }

  public void setPhone_contact(String phone_contact) {
	this.phone_contact = phone_contact;
  }

  public String getLocation() {
	return location;
  }

  public void setLocation(String location) {
	this.location = location;
  }

  public String getCategory() {
	return category;
  }

  public void setCategory(String category) {
	this.category = category;
  }

  public String getPrice() {
	return price;
  }

  public void setPrice(String price) {
	this.price = price;
  }

  public String getEmail() {
	return email;
  }

  public void setEmail(String email) {
	this.email = email;
  }

  public String getDetails()
  {
    return this.details;
  }
  
  public String getThumbnailUrl()
  {
    return this.thumbnailUrl;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public void setDetails(String paramString)
  {
    this.details = paramString;
  }
  
  public void setThumbnailUrl(String paramString){
    this.thumbnailUrl = paramString;
  }
  
  public void setTitle(String paramString){
	  
    this.title = paramString;
    
  }
  public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {

	@Override
	public Movie createFromParcel(Parcel source) {
		// TODO Auto-generated method stub
		 Movie movie = new Movie();
		 movie.category = source.readString();
		 movie.contact_name = source.readString();
		 movie.details = source.readString();
		 movie.email = source.readString();
		 movie.location = source.readString();
		 movie.phone_contact = source.readString();
		 movie.price = source.readString();
		 movie.thumbnailUrl = source.readString();
		 movie.title = source.readString();
		return movie;
	}

	@Override
	public Movie[] newArray(int size) {
		// TODO Auto-generated method stub
		return new Movie[size];
	}
	  
	  
  };

@Override
public int describeContents() {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public void writeToParcel(Parcel parcel, int flags) {
	// TODO Auto-generated method stub
	
	parcel.writeString(details); 
	parcel.writeString(thumbnailUrl); 
	parcel.writeString(title); 
	parcel.writeString(email);
	parcel.writeString(price);
	parcel.writeString(category);
	parcel.writeString(location);
	parcel.writeString(phone_contact);
	parcel.writeString(contact_name);
	
}
}
