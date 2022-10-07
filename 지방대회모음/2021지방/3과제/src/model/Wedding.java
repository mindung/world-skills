package model;

public class Wedding {
	
	public String name;
	public String address;
	public String w_ty;
	public String m_ty;
	public int wNo;
	public int people;
	public int price;
	
	public Wedding(int wNo, String name, String address, String w_ty, String m_ty, int people, int price) {
		
		this.wNo = wNo;
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
		return wNo;
	}

	public void setRno(int rno) {
		this.wNo = rno;
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
