/**
 * @author Lamha Goel 2015050
*/
/**
 * MasterThesis class
 * Sub class of Publication
 * Data members: month,school
 */
public class MasterThesis extends Publication {
	private String month;
	private String school;
	/**
	 * Constructor
	 * Initializes all members as empty strings
	 */
	public MasterThesis()
	{
		super();
		month = "";
		school = "";
	}
	@Override
	public String getType() {
		return "Master Thesis";
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
