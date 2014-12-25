package com.sp.sodhpuch.data;

public class BusinessListData {
	
	private String businessName;
	private String businessAddress;
//	private String imageUrl;
	private String artistUrl;
	private String businessUrl;
	private String phone;
	private String deals;
	
//	public BusinessListData(String name, String artist, String imageUrl, String artistUrl, String trackUrl) {
		public BusinessListData(String name, String address, String id, String phone, String deals) {
		super();
		this.businessName = name;
		this.businessAddress = address;
		this.phone = phone;
		this.deals = deals;
//		this.imageUrl = imageUrl;
		this.artistUrl = "http://www.sodhpuch.com/";
		this.businessUrl = "http://www.sodhpuch.com/";
	}

	public String getName() {
		return businessName;
	}

	public void setName(String name) {
		this.businessName = name;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return businessAddress;
	}

	public void setAddress(String address) {
		this.businessAddress = address;
	}

//	public String getImageUrl() {
//		return imageUrl;
//	}
//
//	public void setImageUrl(String imageUrl) {
//		this.imageUrl = imageUrl;
//	}

	public String getArtistUrl() {
		return artistUrl;
	}

	public void setArtistUrl(String artistUrl) {
		this.artistUrl = artistUrl;
	}

	public String getBusinessUrl() {
		return businessUrl;
	}

	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}
	public String getDeals() {
		return deals;
	}

	public void setDeals(String deals) {
		this.deals = deals;
	}
	
	

}
