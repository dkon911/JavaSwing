import java.io.FileOutputStream;

public class Book {
	String name, author;
	int id, yearPublish;

	Book() {
	};

	Book(int iz, String n, String a, int y) {
		name = n;
		id = iz;
		author = a;
		yearPublish = y;

	}

	void GhiFile(FileOutputStream fout) {
		try {
			String s = id + ", " + name + ", " + author + ", " + yearPublish +"\n";
			byte b[] = s.getBytes(); // Chuyen thanh byte de ghi du lieu
			
			fout.write(b);
		} catch (Exception e) {
			System.out.print("\nError" + e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYearPublish() {
		return yearPublish;
	}

	public void setYearPublish(int yearPublish) {
		this.yearPublish = yearPublish;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
