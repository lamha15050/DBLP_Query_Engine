/**
 * @author Lamha Goel 2015050
*/

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Helper class for initializing the yearToNoOfPublications mapping for all authors
 */
public class Parser_InitialiseForPrediction extends DefaultHandler{

	private String content;
	private Publication p;
	private Set<String> typesOfPublications;
	private Set<String> htmlMarkups;
	private Map<String,Author> authorMap;
	/**
	 * Constructor
	 * @param authorMap entity resolution map
	 */
	public Parser_InitialiseForPrediction(Map<String,Author> authorMap)
	{
		this.authorMap = authorMap;
		content="";
		p = null;
		
		this.typesOfPublications = new HashSet<String>();
		this.typesOfPublications.add("article");
		this.typesOfPublications.add("proceedings");
		this.typesOfPublications.add("inproceedings");
		this.typesOfPublications.add("incollection");
		this.typesOfPublications.add("phdthesis");
		this.typesOfPublications.add("masterthesis");
		this.typesOfPublications.add("book");
		
		this.htmlMarkups = new HashSet<String>();
		this.htmlMarkups.add("ref");
		this.htmlMarkups.add("sup");
		this.htmlMarkups.add("sub");
		this.htmlMarkups.add("i");
		this.htmlMarkups.add("tt");
	}
	/**
	 * SAX Parser function - called at start of each element
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException
	{
		if (typesOfPublications.contains(qName)) 
		{
			PublicationFactory factory = new PublicationFactory();
			p = factory.createPublication(qName);
		}
	}


	/**
	 * SAX Parser function - called at end of each element
	 * calls addToYearToNoOfPublications on the appropriate author object(s) with appropriate parameter when the end of a Publication record is encountered 
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		//System.out.println("End: " + qName);
		if (typesOfPublications.contains(qName))
		{
			List<String> authors = p.getAuthors();
			for(String author : authors)
			{
				authorMap.get(author).addToYearToNoOfPublications(p.getYear());
			}
		}
	    else if(qName.equalsIgnoreCase("author"))
		{
			p.addAuthor(content.trim());
	    }
		/*else if(qName.equalsIgnoreCase("title"))
		{
			p.setTitle(content.trim());
		}
		else if(qName.equalsIgnoreCase("pages"))
		{
			p.setPages(content.trim());
		}*/
		else if(qName.equalsIgnoreCase("year"))
		{
			p.setYear(Integer.parseInt(content.trim()));
		}
		/*else if(qName.equalsIgnoreCase("volume"))
		{
			p.setVolume(content.trim());
		}
		else if(qName.equalsIgnoreCase("journal") || qName.equalsIgnoreCase("booktitle"))
		{
			p.setJournal(content.trim());
		}
		else if(qName.equalsIgnoreCase("url"))
		{
			p.setUrl(content.trim());
		}
		else if(qName.equalsIgnoreCase("ee"))
		{
			p.setEe(content.trim());
		}*/
		//If required, could add other fields here
		if(!htmlMarkups.contains(qName))
		{
			content = "";
		}
	}

	/**
	 * SAX Parser function - called when character data is found
	 */
	@Override
	public void characters(char ch[], int start, int length) throws SAXException
	{
		content = content + String.copyValueOf(ch, start, length);
	}
}
