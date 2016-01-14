package com.example.dto;

public class BusUser {

	private String id;

	private String domainId;
	
	private String username;
	
	private String dept;

	private String contact;

	private String password;
	
	private Integer isActive;
	
	private String type;
	
//	private List<RidingRecord> ridingRecords = new ArrayList<RidingRecord>();
	
//	private List<Ticket> tickets = new ArrayList<Ticket>();


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getDomainId() {
		return domainId;
	}

	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
//
//	public List<RidingRecord> getRidingRecords() {
//		return ridingRecords;
//	}
//
//	public void setRidingRecords(List<RidingRecord> ridingRecords) {
//		this.ridingRecords = ridingRecords;
//	}
//
//	public List<Ticket> getTickets() {
//		return tickets;
//	}

//	public void setTickets(List<Ticket> tickets) {
//		this.tickets = tickets;
//	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}