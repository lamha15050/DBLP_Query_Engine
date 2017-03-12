/**
 * @author Lamha Goel 2015050
*/

import java.util.*;
/**
 * Author class
 * Data members: name, otherNames(aliases), numberOfPublications, relevance(no. of matched words during a search)
 */
public class Author {
	private String name;
	private List<String> otherNames;
	private int numberOfPublications;
	private int relevance;
	private Map<Integer,Integer> yearToNoOfPublications;
	/**
	 * Constructor
	 * Initializes:
	 * name to an empty string
	 * otherNames to a blank ArrayList<String>
	 * numberOfPublications and relevance to 0
	 */
	public Author()
	{
		name = "";
		otherNames = new ArrayList<String>();
		numberOfPublications = 0;
		relevance = 0;
		yearToNoOfPublications = new HashMap<Integer,Integer>();
	}
	
	public String getName() {
		return name;
	}

	public List<String> getOtherNames() {
		return otherNames;
	}

	public int getNumberOfPublications() {
		return numberOfPublications;
	}
	
	public int getRelevance()
	{
		return relevance;
	}

	public Map<Integer, Integer> getYearToNoOfPublications() {
		return yearToNoOfPublications;
	}

	public void addToYearToNoOfPublications(Integer key) {
		if(yearToNoOfPublications.containsKey(key))
		{
			Integer temp = yearToNoOfPublications.get(key);
			yearToNoOfPublications.put(key, temp+1);
		}
		else
		{
			yearToNoOfPublications.put(key, 1);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addOtherName(String otherName) {
		this.otherNames.add(otherName);
	}

	public void setNumberOfPublications(int numberOfPublications) {
		this.numberOfPublications = numberOfPublications;
	}
	public void increaseNumberOfPublications() {
		this.numberOfPublications++;
	}
	
	public void setRelevance(int relevance)
	{
		this.relevance = relevance;
	}
	public String toString()
	{
		return name + "," + numberOfPublications;
	}
}
