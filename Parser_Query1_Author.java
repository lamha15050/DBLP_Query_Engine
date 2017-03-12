/**
 * @author Lamha Goel 2015050
*/

import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * Parser for Query1 - Search By Author
 */
public class Parser_Query1_Author extends DefaultHandler{
	private Set<String> authorNamesToSearchFor;
	private String content;
	private Publication p;
	private Set<String> typesOfPublications;
	private Set<String> htmlMarkups;
	private Set<Publication> results;
	private Map<String,Author> authorMap;
	/**
	 * Constructor
	 * @param authorName input by user
	 * @param authorMap entity Resolution map
	 */
	public Parser_Query1_Author(String authorName,Map<String,Author> authorMap)
	{
		this.authorMap = authorMap;
		// initialise all author's relevance to 0
		Set<Author> authors = new HashSet<Author>(authorMap.values());
		for(Author author : authors)
		{
			author.setRelevance(0);
		}
		//initialise authorNamesToSearchFor, also their relevances
		initialiseAuthorNamesToSearchFor(authorName,authors);
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
	/**
	 * finds all author names to be searched for according to entity resolution
	 * @param authorName user input
	 * @param authors set of all authors in database
	 */
	public void initialiseAuthorNamesToSearchFor(String authorName,Set<Author> authors)
	{
		authorNamesToSearchFor = new HashSet<String>();
		authorNamesToSearchFor.add(authorName);
		Author a = authorMap.get(authorName);
		//Add main author - complete name
		if(a!=null)
		{
			a.setRelevance(authorName.split("\\W").length);
			authorNamesToSearchFor.add(a.getName());
			authorNamesToSearchFor.addAll(a.getOtherNames());
		}
		
		//Find all authors related and their relevances
		String[] enteredNameParts = authorName.split("\\W");
		int n = enteredNameParts.length;
		Set<String> allNames = authorMap.keySet();
		for(String s : allNames)
		{
			int tempRelevance = 0;
			a = authorMap.get(s);	//a cannot be null now
			String[] nameParts = s.split("\\W");
			int m = nameParts.length;
			for(int i = 0; i<m; i++)
			{
				for(int j= 0;j<n;j++)
				{
					if(enteredNameParts[j]!=null && enteredNameParts[j].equalsIgnoreCase(nameParts[i]))
					{
						tempRelevance++;
					}
				}
			}
			if(tempRelevance>a.getRelevance())
			{
				a.setRelevance(tempRelevance);
			}
		}
		
		//Add all required name strings to authorNamesToSearchFor
		
		for(Author auth: authors)
		{
			if(auth.getRelevance()>0)
			{
				authorNamesToSearchFor.add(auth.getName());
				authorNamesToSearchFor.addAll(auth.getOtherNames());
			}
		}
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
	 * adds Publication to results if it satisfies the criteria and decides its relevance
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
				//authorMap.get(author).addToYearToNoOfPublications(p.getYear());
				int tempRelevance = authorMap.get(author).getRelevance();
				if(tempRelevance>p.getRelevance())
				{
					p.setRelevance(tempRelevance);
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
		//System.out.println("Content: " + content);
	}
}
