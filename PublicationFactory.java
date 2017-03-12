/**
 * @author Lamha Goel 2015050
*/
/**
 * PublicationFactory class
 * Factory design pattern
 * Used to decide which sub class of Publication to instantiate
 */
public class PublicationFactory {
	/**
	 * Instantiates a sub class of Publication
	 * @param type defines the type of Publication to be instantiated
	 * @return reference to the instance of some sub class of Publication, according to type
	 */
	public Publication createPublication(String type)
	{
		Publication p = null;
		if(type==null || type.equals("")){
			return p;
		}
		if(type.equalsIgnoreCase("article"))
		{
			p = new Article();
		}
		else if(type.equalsIgnoreCase("proceedings"))
		{
			p = new Proceeding();
		}
		else if(type.equalsIgnoreCase("inproceedings"))
		{
			p = new Inproceeding();
		}
		else if(type.equalsIgnoreCase("incollection"))
		{
			p = new Incollection();
		}
		else if(type.equalsIgnoreCase("phdthesis"))
		{
			p = new PhDThesis();
		}
		else if(type.equalsIgnoreCase("masterthesis"))
		{
			p = new MasterThesis();
		}
		else if(type.equalsIgnoreCase("book"))
		{
			p = new Book();
		}
		return p;
	}
}
