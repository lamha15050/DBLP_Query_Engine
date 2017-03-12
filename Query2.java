/**
 * @author Lamha Goel 2015050
*/

import java.util.*;
/**
 * Helper class for Query2
 */
public class Query2 {
	/** maps author names to author, used for entity resolution*/
	private Map<String,Author> authorMap;
	/** k - input by the user */
	private int k;
	/** output results, sorted in descending order according to numberOfPublications */
	private List<Author> results;
	/**
	 * Constructor
	 * @param authorMap entity Resolution Map
	 * @param k user input for k
	 */
	public Query2(Map<String,Author> authorMap,int k)
	{
		this.authorMap = authorMap;
		this.k = k;
		runQuery();
	}
	public List<Author> getResults()
	{
		return results;
	}
	/**
	 * finds results according to the input and sorts them
	 */
	private void runQuery()
	{
		results = new ArrayList<Author>();
		Set<Author> allAuthors = new HashSet<Author>(authorMap.values());
		for(Author author : allAuthors)
		{
			if(author.getNumberOfPublications()>k)
			{
				results.add(author);
			}
		}
		results.sort(new Comparator<Author>(){
			public int compare(Author a1, Author a2){
				return a2.getNumberOfPublications() - a1.getNumberOfPublications();
			}
		});
	}
}
