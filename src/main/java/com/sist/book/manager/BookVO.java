package com.sist.book.manager;

public class BookVO {
	private int no;
	private String title;
	private String author;
	private String publisher;
	private int oprice; // 원래
	private int sprice; // 할인
	private int rank;
	private String regdate;
	private String review;
	private String poster;
	private String pricaData;
	private String titleData;
	
	
	public String getPricaData() {
		return pricaData;
	}
	public void setPricaData(String pricaData) {
		this.pricaData = pricaData;
	}
	public String getTitleData() {
		return titleData;
	}
	public void setTitleData(String titleData) {
		this.titleData = titleData;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
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
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public int getOprice() {
		return oprice;
	}
	public void setOprice(int oprice) {
		this.oprice = oprice;
	}
	public int getSprice() {
		return sprice;
	}
	public void setSprice(int sprice) {
		this.sprice = sprice;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	
	
}
