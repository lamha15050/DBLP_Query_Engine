/**
 * @author Lamha Goel 2015050
*/
/**
 * Incollection class
 * Sub class of Publication
 * Data members: editor,month
 */
public class Incollection extends Publication {

	private String editor;
	private String month;
	/**
	 * Constructor
	 * Initializes all members as empty strings
	 */
	public Incollection()
	{
		super();
		editor = "";
		month = "";
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Incollection";
	}
	public String getEditor() {
		return editor;
	}
	public String getMonth() {
		return month;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
