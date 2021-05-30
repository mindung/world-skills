package model;

public class Menu {
	private int no;
	private String name;
	private int cnt;
	
	public Menu(String name, int cnt, int no) {
		this.name = name;
		this.cnt = cnt;
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCnt() {
		return cnt;
	}
	
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	public int getNo() {
		return no;
	}
	
	public void setNo(int no) {
		this.no = no;
	}
	
	
	
}
