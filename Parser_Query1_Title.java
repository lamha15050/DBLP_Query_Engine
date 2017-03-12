/**
 * @author Lamha Goel 2015050
*/

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * XML Parser - parses the file for query1 - search By Title 
 */
public class Parser_Query1_Title extends DefaultHandler{
	
	//** all words in input title */
	private String[] tags;
	private String content;		
	private Publication p;
	/** Set of all possible types of publications*/
	private Set<String> typesOfPublications;
	/** Set of all possible html markups - required for parsing the file correctly*/
	private Set<String> htmlMarkups;
	/** Output - results - set of Publications*/
	private Set<Publication> results;
	/**
	 * Constructor
	 * @param title user input string
	 */
	public Parser_Query1_Title(String title)
	{
		//tags=title.split("[ .()-,]");
		//tags = title.split(" ");
		tags = title.split("\\W");	//split around all non-word characters
		p = null;
		content = "";
		results = new HashSet<Publication>();	
		
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
	
	public Set<Publication> getResults()
	{
		return results;
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
	 * adds publication to results if it satisfies the criteria, and decides their relevance
	 */
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		//System.out.println("End: " + qName);
		if (typesOfPublications.contains(qName))
		{
			int n = tags.length;
			String[] publicationTags = p.getTitle().split("\\W");
			int m = publicationTags.length;
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<m;j++)
				{
					//TODO break if found a match or not? Like "xyz" occurring twice increases relevance twice or just once?
					if(tags[i]!=null && tags[i].equalsIgnoreCase(publicationTags[j]))
					{
						p.increaseRelevance();
					}
				}
			}
			if(p.getRelevance()>0)
			{
				results.add(p);
			}
		}
	      /*authors;
	String title;
	String pages;
	int year;
	String volume;
	String journal;
	String url;*/
		else if(qName.equalsIgnoreCase("author"))
		{
			p.addAuthor(content.trim());
	    }
		else if(qName.equalsIgnoreCase("title"))
		{
			p.setTitle(content.trim());
		}
		else if(qName.equalsIgnoreCase("pages"))
		{
			p.setPages(content.trim());
		}
		else if(qName.equalsIgnoreCase("year"))
		{
			p.setYear(Integer.parseInt(content.trim()));
		}
		else if(qName.equalsIgnoreCase("volume"))
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
		}
		//can add checks(and appropriate casts) for other fields if required
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
		//System.out.println("Content: " + content);
	}
}
