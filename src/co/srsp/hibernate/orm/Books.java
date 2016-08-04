package co.srsp.hibernate.orm;

public class Books implements java.io.Serializable {

	private static final long serialVersionUID = 4744798857627076674L;
	private Integer idbooks;
	private String title;
	private String author;
	private String publisher;
	
	public Books() {
	}

	public Books(String title, String author) {
		this.title = title;
		this.author = author;
	}

	public Integer getIdbooks() {
		return this.idbooks;
	}

	public void setIdbooks(Integer idbooks) {
		this.idbooks = idbooks;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return this.author;
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
	
	

}
