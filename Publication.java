/**
 * @author Lamha Goel 2015050
*/

import java.util.*;
//TODO either make sub classes - edit parsing accordingly
//if sub classes make this abstract
//or 
//make variables private
/**
 * Publication class
 * abstract class
 * Data members : authors,title,pages,year,volume,journal,url,ee,relevance
 */
public abstract class Publication {
	protected List<String> authors;
	protected String title;
	protected String pages;
	protected int year;
	protected String volume;
	protected String journal;	//or booktitle
	protected String url;
	protected String ee;
	protected int relevance;

	/**
	 * Constructor
	 * Initializes: 
	 * authors to an empty ArrayList<String>
	 * title,pages,volume,journal,url and ee to empty strings
	 * year and relevance to 0
	 */
	public Publication()
	{
		authors = new ArrayList<String>();
		title = "";
		pages = "";
		year = 0;
		volume = "";
		journal = "";
		url = "";
		ee = "";
		relevance = 0;
	}
	abstract public String getType();
	public List<String> getAuthors() {
		return authors;
	}
	public String getTitle() {
		return title;
	}
	public String getPages() {
		return pages;
	}
	public int getYear() {
		return year;
	}
	public String getVolume() {
		return volume;
	}
	public String getJournal() {
		return journal;
	}
	public String getUrl() {
		return url;
	}
	public String getEe() {
		return ee;
	}
	public int getRelevance() {
		return relevance;
	}
	public void increaseRelevance() {
		relevance++;
	}
	public void addAuthor(String author) {
		this.authors.add(author);
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setEe(String ee) {
		this.ee = ee;
	}
	public void setRelevance(int relevance)
	{
		this.relevance = relevance;
	}
	
	@Override
	public String toString()
	{
		return "Title: " + title + "\nAuthors: " + authors + "\npages: " + pages + "\nyear: " + year + "\nvolume " + volume + "\njournal: " + journal + "\nurl:" + url + "\nee: " + ee + "\n";
	}
}
