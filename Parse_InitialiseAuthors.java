/**
 * @author Lamha Goel 2015050
*/

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Helper class
 * Initializes the entity Resolution map
 */
public class Parse_InitialiseAuthors extends DefaultHandler{

	private Map<String,Author> entityResolutionMap;
	private Author author;
	/** based on whether www record with appropriate key*/
	private boolean authorRecord;		
	private String content;
	/**
	 * Constructor
	 * initializes:
	 * entityResolutionMap to empty HashMap<String,Author>
	 * author reference to null
	 * authorRecord to false
	 * content to empty string
	 */
	public Parse_InitialiseAuthors()
	{
		entityResolutionMap = new HashMap<String,Author>();
		author = null;
		authorRecord = false;
		content= "";
	}
	
	public Map<String, Author> getEntityResolutionMap() {
		return entityResolutionMap;
	}

	/**
	 * SAX Parser function - called at start of each element
	 */
	@Override
	   public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException
	   {
		   //System.out.println("Start: " + qName);
	      if (qName.equalsIgnoreCase("www") && attributes.getValue("key").startsWith("homepages/"))	//check if person record
	      {
	    	  author = new Author();
	         authorRecord = true;
	      }
	      else if(authorRecord && qName.equalsIgnoreCase("author"))
	      {
	    	  content = "";
	      }
	   }


		/**
		 * SAX Parser function - called at end of each element
		 */
	   @Override
	   public void endElement(String uri, String localName, String qName) throws SAXException {
		   //System.out.println("End: " + qName);
	      if (qName.equalsIgnoreCase("www")) {
	    	  authorRecord = false;
	      }
	      else if(authorRecord && qName.equalsIgnoreCase("author"))
	      {
	    	  entityResolutionMap.put(content.trim(), author);
	    	  if(author.getName().isEmpty())
	    	  {
	    		  author.setName(content.trim());
	    	  }
	    	  else
	    	  {
	    		  author.addOtherName(content.trim());
	    	  }
	      }
	   }

		/**
		 * SAX Parser function - called when character data is found
		 */
	   @Override
	   public void characters(char ch[], int start, int length) throws SAXException
	   {
		   if(authorRecord)
		   {
			   	content = content + String.copyValueOf(ch, start, length);
		   }
	      //System.out.println("Content: " + content);
	   }
}
