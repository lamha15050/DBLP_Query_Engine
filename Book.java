/**
 * @author Lamha Goel 2015050
*/
/**
 * Book class
 * Sub class of Publication
 * Data members: isbn,month
 */

public class Book extends Publication {
	private String isbn;
	private String month;
	/**
	 * Constructor
	 * Initializes all members as empty strings
	 */
	public Book()
	{
		super();
		isbn="";
		month= "";
	}
	@Override
	public String getType() {
		return "Book";
	}
	public String getIsbn() {
		return isbn;
	}
	public String getMonth() {
		return month;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
