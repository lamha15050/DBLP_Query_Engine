/**
 * @author Lamha Goel 2015050
*/
/**
 * PhDThesis class
 * Sub class of Publication
 * Data members: month,school
 */
public class PhDThesis extends Publication {
	private String month;
	private String school;
	/**
	 * Constructor
	 * Initializes all members as empty strings
	 */
	public PhDThesis()
	{
		super();
		month = "";
		school = "";
	}
	
	@Override
	public String getType() {
		return "PhD Thesis";
	}

	public String getMonth() {
		return month;
	}

	public String getSchool() {
		return school;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public void setSchool(String school) {
		this.school = school;
	}

}
