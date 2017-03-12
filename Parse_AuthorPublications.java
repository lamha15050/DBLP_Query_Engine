/**
 * @author Lamha Goel 2015050
*/

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;
/**
 * Helper class
 * Initializes number of Publications for each author according to entityResolutionMap
 */
public class Parse_AuthorPublications extends DefaultHandler{
	private Map<String,Author> authorMap;
	private String content;
	private boolean publicationRecord;
	/** set of all possible publication types*/
	private Set<String> typesOfPublications;
	/**
	 * Constructor
	 * @param map entityResolution map
	 * Initializes: 
	 * authorMap to map
	 * content to empty string
	 * publicationRecord to false
	 * 
	 * typesOfPublications as a set of all possible Publication Types
	 */
	public Parse_AuthorPublications(Map<String,Author> map)
	{
		authorMap = map;
		content = "";
		publicationRecord = false;
		
		this.typesOfPublications = new HashSet<String>();
		this.typesOfPublications.add("article");
		this.typesOfPublications.add("proceedings");
		this.typesOfPublications.add("inproceedings");
		this.typesOfPublications.add("incollection");
		this.typesOfPublications.add("phdthesis");
		this.typesOfPublications.add("masterthesis");
		this.typesOfPublications.add("book");
	}

	/**
	 * SAX Parser function - called at start of each element
	 */
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException
	{
		//System.out.println("Start: " + qName);
		if (typesOfPublications.contains(qName))	//check if person record
		{
			//author = new Author();
			publicationRecord = true;
		}
		else if(publicationRecord && qName.equalsIgnoreCase("author"))
		{
			content = "";
		}
	}

	/**
	 * SAX Parser function - called at end of each element
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		//System.out.println("End: " + qName);
		if (typesOfPublications.contains(qName))
		{
			publicationRecord = false;
		}
		else if(publicationRecord && qName.equalsIgnoreCase("author"))
		{
			authorMap.get(content.trim()).increaseNumberOfPublications();
		}
	}


	/**
	 * SAX Parser function - called when character data is found
	 */
	@Override
	public void characters(char ch[], int start, int length) throws SAXException
	{
		if(publicationRecord)
		{
			content = content + String.copyValueOf(ch, start, length);
		}
		//System.out.println("Content: " + content);
	}
}
