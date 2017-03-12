/**
 * @author Lamha Goel 2015050
*/

//import org.apache.commons.math3.stat.regression.SimpleRegression;
import java.io.*;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
/**
 * the main class
 * initializes the entityResolutionMap, authorObjects and the GUI
 */
public class DBLPQueryEngine {
	
	private Map<String,Author> entityResolutionMap;
	/** dblp.xml - xml file*/
	private File in = new File("dblp.xml");
	private SAXParserFactory factory = SAXParserFactory.newInstance();
	private SAXParser sax = null;
	private Set<Publication> results;
	private List<Publication> query1Results;
	private List<Author> query2Results;
	private Integer[] query3Results;
	/**
	 * Constructor
	 */
	public DBLPQueryEngine()
	{
		entityResolutionMap = new HashMap<String,Author>();
		results = new HashSet<Publication>();
		query3Results = new Integer[3];
	}
	public Map<String,Author> getEntityResolutionMap()
	{
		return entityResolutionMap;
	}
	public List<Publication> getQuery1Results()
	{
		return query1Results;
	}
	public List<Author> getQuery2Results()
	{
		return query2Results;
	}
	public Integer[] getQuery3Results()
	{
		return query3Results;
	}
	/**
	 * main function
	 * @param args Console line arguments - not used
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("jdk.xml.entityExpansionLimit", "0");
		DBLPQueryEngine engine = new DBLPQueryEngine();
		engine.initialiseAuthorsMap();
		engine.initialiseAuthorPublications();
		engine.initialiseAuthorPredictions();
		
		/*System.out.println("ready");
		
		
		System.out.println("ready1");
		engine.query3(2014,"Chin-Chen Chang");
		*/
		
		DBLP_GUI dblp = new DBLP_GUI(engine);
		
		/*engine.query1AuthorTemplate("Chin-Chen Chang",1,1);
		SimpleRegression simpleRegression = new SimpleRegression(true);

        // passing data to the model
        // model will be fitted automatically by the class 
        Author auth = engine.getEntityResolutionMap().get("Chin-Chen Chang");
        Map<Integer,Integer> predictMap = auth.getYearToNoOfPublications();
        double[][] values = new double[15][2];
        int count = 0;
        for(int year=2000;year<2015;year++,count++)
		{
        	values[count][0]=(double)year;
			if(predictMap.containsKey(year))
			{
				values[count][1]=predictMap.get(year).doubleValue();
			}
			else
			{
				values[count][1]=0.0;
			}
		}
        simpleRegression.addData(values);
        // querying for model parameters
        System.out.println("slope = " + simpleRegression.getSlope());
        System.out.println("intercept = " + simpleRegression.getIntercept());

        // trying to run model for unknown data
        System.out.println("prediction for 2015 = " + simpleRegression.predict(2015));
        System.out.println("Actual" + auth.getYearToNoOfPublications().get(2015));
*/
		
		
		/*****************************
		 * TODO
		 * 1. Done? Parse to initialise author properly, i.e. their number of publications - would solve query 2
		 * 2. Query 1 - parameters author/title, add the option to calc relevance(some int)
		 * 3. Sorting and filtering
		 * 4. GUI
		 * 5. Libraries for query 3
		 */
	}
	/**
	 * initializes entity resolution map
	 */
	public void initialiseAuthorPredictions()
	{
		Parser_InitialiseForPrediction parser;
		try {
			sax = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		parser = new Parser_InitialiseForPrediction(entityResolutionMap);
		try {
			if(sax!=null)
			{
				sax.parse(in,parser);
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}
	public void initialiseAuthorsMap()
	{
		Parse_InitialiseAuthors parser;
		try {
			sax = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		parser = new Parse_InitialiseAuthors();
		try {
			if(sax!=null)
			{
				sax.parse(in,parser);
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		entityResolutionMap = parser.getEntityResolutionMap();
	}
	/**
	 * sets the number of Publications for all authors
	 */
	public void initialiseAuthorPublications()
	{
		Parse_AuthorPublications parser;
		try {
			sax = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		parser = new Parse_AuthorPublications(entityResolutionMap);
		try {
			if(sax!=null)
			{
				sax.parse(in,parser);
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		//entityResolutionMap = parser.getEntityResolutionMap();
	}
	/**
	 * Template function for query 1 - search by title
	 * @param title user input for title
	 * @param sType sort type
	 * @param fType filter type
	 * @param years Integer array - according to filter type
	 */
	public void query1TitleTemplate(String title,int sType,int fType,Integer ...years)
	{
		parse_query1_title(title);
		SortFilterResultsQuery1 sortFilter = new SortFilterResultsQuery1(results,2,sType,fType,years);
		query1Results = sortFilter.getFinalResults();
	}
	/**
	 * Template function for query 1 - search by author
	 * @param author user input for author
	 * @param sType sort type
	 * @param fType filter type
	 * @param years Integer array - according to filter type
	 */
	
	public void query1AuthorTemplate(String author,int sType,int fType,Integer ...years)
	{
		parse_query1_author(author);
		SortFilterResultsQuery1 sortFilter = new SortFilterResultsQuery1(results,1,sType,fType,years);
		query1Results = sortFilter.getFinalResults();
	}
	/**
	 * parses the file to get results for query1 search by author
	 * @param authorName user input
	 */
	public void parse_query1_author(String authorName)
	{
		Parser_Query1_Author parser;
		try {
			sax = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		parser = new Parser_Query1_Author(authorName,entityResolutionMap);
		try {
			if(sax!=null)
			{
				sax.parse(in,parser);
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		results = parser.getResults();
	}
	/**
	 * parses the file to get results for query1 search by title
	 * @param title user input
	 */
	public void parse_query1_title(String title)	//Add other parameters to allow options
	{
		Parser_Query1_Title parser;
		try {
			sax = factory.newSAXParser();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		parser = new Parser_Query1_Title(title);
		try {
			if(sax!=null)
			{
				sax.parse(in,parser);
			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		results = parser.getResults();
	}
	/**
	 * uses helper class Query2 to find result to query2
	 * @param k user input
	 */
	public void query2(int k)
	{
		Query2 query = new Query2(entityResolutionMap,k);
		query2Results = query.getResults();
	}
	/**
	 * uses Linear Regression for prediction
	 * @param year - year uptil which data can be used
	 * @param authorName - user input: author name
	 */
	public void query3(int year,String authorName)
	{
		//TODO save result in Integer[]
		//SimpleRegression simpleRegression = new SimpleRegression(true);
        Set<String> authors = getEntityResolutionMap().keySet();
        Author author = null;
        for(String s :authors)
        {
        	if(s.equalsIgnoreCase(authorName))
        	{
        		author = getEntityResolutionMap().get(s);
        		break;
        	}
        }
		//Author author = getEntityResolutionMap().get(authorName);
        Map<Integer,Integer> predictMap = author.getYearToNoOfPublications();
        Set<Integer> keys = predictMap.keySet();
        int i=year;
        for(Integer x : keys)
        {
        	if(x<i)
        	{
        		i=x;
        	}
        }
        int count=0;
        i++;
        double diffavg=0.0;
        for(;i<=year;i++,count++)
		{
        	double temp1,temp2;
        	if(predictMap.containsKey(i))
			{
				temp1=predictMap.get(i).doubleValue();
			}
			else
			{
				temp1=0.0;
			}
        	if(predictMap.containsKey(i-1))
			{
				temp2=predictMap.get(i-1).doubleValue();
			}
			else
			{
				temp2=0.0;
			}
        	diffavg+=temp1-temp2;
        	//simpleRegression.addData(i,temp);
		}
        //System.out.println(simpleRegression.getN() + " values added");
        /*if(simpleRegression.getN()<2)
        {
        	query3Results[2] = -1;
        	return;
        }
        else
        {
        	query3Results[2]=0;
        }*/
        //System.out.println("slope = " + simpleRegression.getSlope());
        //System.out.println("intercept = " + simpleRegression.getIntercept());

        if(count<2)
        {
        	query3Results[2]=-1;
        }
        else
        {
        	query3Results[2]=0;
        	diffavg/=count;
        }
        //diffavg/=count;
        //year++;
        //System.out.println("Prediction for " + (year+1) + " = " + simpleRegression.predict(year+1));
        //query3Results[0] = (int) Math.round(simpleRegression.predict(year));
        double last;
        if(predictMap.containsKey(year))
		{
			last=predictMap.get(year).doubleValue();
		}
		else
		{
			last=0.0;
		}
    	
        query3Results[0] = (int)Math.round(last+diffavg);
        if(author.getYearToNoOfPublications().containsKey(year+1))
        {
        	//System.out.println("Actual " + author.getYearToNoOfPublications().get(year+1));
        	query3Results[1]=author.getYearToNoOfPublications().get(year+1);
        }
        else
        {
        	//System.out.println("Actual data not found");
        	query3Results[1]=0;
        }
	}
}
