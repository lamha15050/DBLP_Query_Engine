/**
 * @author Lamha Goel 2015050
*/
/**
 * Proceeding class
 * Sub class of Publication
 * Data members: editor,month
 */
public class Proceeding extends Publication {

	private String editor;
	private String month;
	/**
	 * Constructor
	 * Initializes all members as empty strings
	 */
	public Proceeding()
	{
		super();
		editor = "";
		month = "";
	}
	@Override
	public String getType() {
		return "Proceeding";
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
