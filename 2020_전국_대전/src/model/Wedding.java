package model;

public class Wedding {
	
	private String name;
	private String address;
	private String w_ty;
	private String m_ty;
	private int rno;
	private int people;
	private int price;
	
	public Wedding(int rno,String name, String address, String w_ty, String m_ty, int people, int price) {
		
		this.rno = rno;
		this.name = name;
		this.address = address;
		this.w_ty = w_ty;
		this.m_ty = m_ty;
		this.people = people;
		this.price = price;
	}

	@Override
	public String toString() {
	
		return super.toString();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getW_ty() {
		return w_ty;
	}

	public void setW_ty(String w_ty) {
		this.w_ty = w_ty;
	}

	public String getM_ty() {
		return m_ty;
	}

	public void setM_ty(String m_ty) {
		this.m_ty = m_ty;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public int getPeople() {
		return people;
	}

	public void setPeople(int people) {
		this.people = people;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
