/**
 * @author Lamha Goel 2015050
*/
/**
 * Article class
 * Sub class of Publication
 * Data members: number,month
 */
public class Article extends Publication {
	private int number;
	private String month;
	/**
	 * Constructor
	 * Initializes number to 0 and month to an empty string
	 */
	public Article()
	{
		super();
		number = 0;
		month = "";
	}
	@Override
	public String getType()
	{
		return "Article";
	}
	public int getNumber() {
		return number;
	}
	public String getMonth() {
		return month;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
}
