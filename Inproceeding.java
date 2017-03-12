/**
 * @author Lamha Goel 2015050
*/
/**
 * Inproceeding class
 * Sub class of Publication
 * Data members: editor,month
 */
public class Inproceeding extends Publication {

	private String editor;
	private String month;
	/**
	 * Constructor
	 * Initializes all members as empty strings
	 */
	public Inproceeding()
	{
		super();
		editor = "";
		month = "";
	}
	@Override
	public String getType() {
		return "Inproceeding";
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
