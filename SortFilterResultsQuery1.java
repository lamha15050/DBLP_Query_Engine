/**
 * @author Lamha Goel 2015050 
*/

import java.util.*;
/**
 * Helper class for sorting and filtering query1 results
 */
public class SortFilterResultsQuery1 {
	Set<Publication> results;
	/* queryType values1 : author, 2:title */
	private int queryType;
	/** sortType values 1: date(latest first) 2:date(oldest first) 3:relevance*/
	private int sortType;
	/** filterType values 1: none 2:since year 3:between years*/
	private int filterType;
	/**year1 may or may not hold values depending on filter type*/
	private Integer year1;  
	/**year2 may or may not hold values depending on filter type*/
	private Integer year2;
	/**holds final results*/
	private List<Publication> finalResults;
	/**
	 * 
	 * @param results set of non-filtered and unsorted results
	 * @param qType query type
	 * @param sType sort Type
	 * @param fType filter Type
	 * @param years Array of integers having values for years if required by current filter type
	 */
	public SortFilterResultsQuery1(Set<Publication> results,int qType,int sType,int fType, Integer ...years)
	{
		this.results = results;
		this.queryType = qType;
		this.sortType = sType;
		this.filterType = fType;
		switch(fType)
		{
			case 3: year2 = years[1];	
					//fallthrough
			case 2: year1 = years[0];
					break;
		}
		filter();
		sort();
	}
	public List<Publication> getFinalResults()
	{
		return finalResults;
	}
	/**
	 * modifies the result array according to filter type
	 * removes all entries which aren't present in current year range
	 */
	private void filter()
	{
		//If fType ==3 ,remove all after year2, remove all before year1
		//if ftype == 2, remove all before year1
		Set<Publication> publicationsToRemove = new HashSet<Publication>();
		switch(filterType)
		{
			case 3: for(Publication p : results)
					{
						if(p.getYear()>year2)
						{
							publicationsToRemove.add(p);
						}
					}
					results.removeAll(publicationsToRemove);
					//fallthrough
			case 2: for(Publication p : results)
					{
						if(p.getYear()<year1)
						{
							publicationsToRemove.add(p);
						}
					}
					results.removeAll(publicationsToRemove);
			}
	}
	/**
	 * Makes a list out of set of results
	 * Sorts the list according to current sortType 
	 */
	private void sort()
	{
		finalResults = new ArrayList<Publication>(results);
		switch(sortType)
		{
			case 1: sortDate();
					break;
			case 2: sortDateReverse();
					break;
			case 3: sortRelevance();
					break;
		}
	}
	/**
	 * Sort by date function(Latest first)
	 */
	private void sortDate()
	{
		finalResults.sort(new Comparator<Publication>(){
			public int compare(Publication p1,Publication p2)
			{
				return p2.getYear() - p1.getYear();
			}
		});
	}
	/**
	 * Sort by date(reverse) function (Oldest first)
	 */
	private void sortDateReverse()
	{
		finalResults.sort(new Comparator<Publication>(){
			public int compare(Publication p1,Publication p2)
			{
				return p1.getYear() - p2.getYear();
			}
		});
	}
	/**
	 * Sort by relevance function(Most relevant first)
	 */
	private void sortRelevance()
	{
		finalResults.sort(new Comparator<Publication>(){
			public int compare(Publication p1,Publication p2)
			{
				return p2.getRelevance() - p1.getRelevance();
			}
		});
	}
}
