/**
 * @author Lamha Goel 2015050 
*/
//import java.awt.*; - importing this creats ambiguity for list
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import java.awt.event.*;

import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 * GUI Class
 * Handles the complete GUI
 */
public class DBLP_GUI implements ActionListener{
	private DBLPQueryEngine engine;
	private JFrame frame;
	private JLabel heading;
	/** queryPanel + resultPanel*/
	private JPanel combinedPanel;
	private JPanel queryPanel;
	private JPanel resultPanel;
	/** drop down list for query types*/
	private JComboBox<String> queryBox;
	private final Dimension panelSize = new Dimension(250,30);
	private final Dimension comboBoxDimension = new Dimension(100,20);
	/** Table model for query1 table*/
	private DefaultTableModel query1model;
	/** Table model for query2 table*/
	private DefaultTableModel query2model;
	/** number of results to display at a time*/
	public static final int count = 20;
	/** used as a n index to see which results to display*/
	private int index;
	private String curQuery;
	/**
	 * Constructor
	 * Initializes the GUI to the main screen
	 * @param engine the driving engine(main class instance)
	 */
	public DBLP_GUI(DBLPQueryEngine engine)
	{
		this.engine = engine;
		frame = new JFrame();
		frame.setTitle("DBLP Query Engine");
		//frame.setSize(800,600);
		maximiseFrame();
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//System.out.println(screenSize.width + " " + screenSize.height);
		//frame.setSize(1366, 725);
		//frame.setState(Frame.NORMAL);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		heading = new JLabel("DBLP Query Engine");
		combinedPanel = new JPanel();
		queryPanel = new JPanel();
		resultPanel = new JPanel();
		frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
		heading = new JLabel("DBLP Query Engine");
		heading.setFont(new Font("Calibri",Font.BOLD,60));
		heading.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.add(heading);
		initialiseMainScreen();
	}
	/**
	 * Method from ActionListener interface
	 * Handles changes/clicks of queryBox,searchButton,resetButton,previousButton,nextButton
	 */
	public void actionPerformed(ActionEvent e)
	{
		String action = ((Component)e.getSource()).getName();
		if("Query Box".equals(action))
		{
			@SuppressWarnings("unchecked")
			String curQuery = (String)((JComboBox<String>)e.getSource()).getSelectedItem();
			if(curQuery.equals(this.curQuery))
			{
				return;
			}
			this.curQuery = curQuery;
			switch(curQuery)
			{
				case "Queries" : frame.setVisible(false);
								 initialiseMainScreen();
								 frame.setVisible(true);
								 break;
				case "Query 1" : Query1Screen();
								 break;
				case "Query 2" : Query2Screen();
								 break;
				case "Query 3" : Query3Screen();
								 break;
			}
		}
		else if("Search".equals(action))
		{
			String curQuery = (String)queryBox.getSelectedItem();
			switch(curQuery)
			{
				case "Query 1" : Query1Search();
				 				break;
				case "Query 2" : Query2Search();
								break;
				case "Query 3" : Query3Search();
								break;
			}
		}
		else if("Reset".equalsIgnoreCase(action))
		{
			frame.setVisible(false);
			curQuery = "Queries";
			initialiseMainScreen();
			frame.setVisible(true); 
		}
		else if("Previous".equals(action))
		{
			if(index<=20)	//First page
			{
				return;
			}
			if(index%20==0)
			{
				index=index-40;
			}
			else
			{
				index = index-20-index%20;
			}
			updateTable();
		}
		else if("Next".equals(action))
		{
			int max=0;
			if(curQuery.equals("Query 1"))
			{
				max = engine.getQuery1Results().size();
			}
			else if(curQuery.equals("Query 2"))
			{
				max = engine.getQuery2Results().size();
			}
			if(index>=max)
			{
				return;
			}
			//Else continue with current index
			updateTable();
		}
	}
	/**
	 * Updates the GUI table
	 */
	private void updateTable()
	{
		frame.setVisible(false);
		if(curQuery.equals("Query 1"))
		{
			List<Publication> results = engine.getQuery1Results();
			int tempCount;
			Object [][] data = new Object[20][9];
			Object[] colNames = new Object[9];
			colNames[0]="S.no.";
			colNames[1]="Authors";
			colNames[2]="Title";
			colNames[3]="Pages";
			colNames[4]="Year";
			colNames[5]="Volume";
			colNames[6]="Journal/BookTitle";
			colNames[7]="URL";
			colNames[8]="EE";
			int max = results.size();
			for(tempCount=0;tempCount<count;index++,tempCount++)
			{
				if(index<max)
				{
					data[tempCount][0] = index+1;
					data[tempCount][1] = results.get(index).getAuthors();
					data[tempCount][2] = results.get(index).getTitle();
					data[tempCount][3] = results.get(index).getPages();
					data[tempCount][4] = results.get(index).getYear();
					data[tempCount][5] = results.get(index).getVolume();
					data[tempCount][6] = results.get(index).getJournal();
					data[tempCount][7] = results.get(index).getUrl();
					data[tempCount][8] = results.get(index).getEe();
				}
				else
				{
					for(int i=0;i<9;i++)
					{
						data[tempCount][i]="";
					}
				}
				
			}
			this.query1model.setDataVector(data,colNames);
		}
		if(curQuery.equals("Query 2"))
		{
			List<Author> results = engine.getQuery2Results();
			int tempCount;
			Object [][] data = new Object[20][3];
			Object[] colNames = new Object[3];
			colNames[0]="S.no.";
			colNames[1]="Author";
			colNames[2]="No. of Publications";
			int max = results.size();
			for(tempCount=0;tempCount<count;index++,tempCount++)
			{
				if(index<max)
				{
					data[tempCount][0] = index+1;
					data[tempCount][1] = results.get(index).getName();
					data[tempCount][2] = results.get(index).getNumberOfPublications();
				}
				else
				{
					data[tempCount][0] = "";
					data[tempCount][1] = "";
					data[tempCount][2] = "";
				}
				
			}
			this.query2model.setDataVector(data,colNames);
		}
		frame.setVisible(true);
	}
	/**
	 * initializes query1 search after validating the input
	 */
	private void Query1Search()
	{
		JLabel error = new JLabel();
		error.setBackground(Color.WHITE);
		error.setForeground(Color.BLACK);
		resultPanel.setLayout(new BoxLayout(resultPanel,BoxLayout.Y_AXIS));
		resultPanel.removeAll();
		@SuppressWarnings("unchecked")
		String searchBy = (String)((JComboBox<String>)queryPanel.getComponent(3)).getSelectedItem();
		if("Search by".equals(searchBy))
		{
			printError(error,"Invalid data: Choose search mode");
			return;
		}
		String nameOrTitle = ((JTextField)((JPanel)queryPanel.getComponent(5)).getComponent(2)).getText();
		if("".equals(nameOrTitle))
		{
			printError(error,"Name/Title field cannot be empty");
			return;
		}
		@SuppressWarnings("unchecked")
		String filter = (String)((JComboBox<String>)queryPanel.getComponent(7)).getSelectedItem();
		@SuppressWarnings("unchecked")
		String sort= (String)((JComboBox<String>)queryPanel.getComponent(13)).getSelectedItem();
		Integer sType = new Integer(1);
		if("Default sort".equals(sort))
		{
			sType = 1;
		}
		else if("Year(Reverse)".equals(sort))
		{
			sType = 2;
		}
		else if("Relevance".equals(sort))
		{
			sType = 3;
		}
		if("No filter".equals(filter))
		{
			if("Author".equals(searchBy))
			{
				engine.query1AuthorTemplate(nameOrTitle, sType, 1);
			}
			else	//Title
			{
				engine.query1TitleTemplate(nameOrTitle, sType, 1);
			}
		}
		else if("Since year".equals(filter))
		{
			String year = ((JTextField)((JPanel)queryPanel.getComponent(9)).getComponent(2)).getText();
			Integer yearInt;
			try
			{
				yearInt = Integer.parseInt(year);
			}
			catch(NumberFormatException e)
			{
				printError(error,"Invalid data: The year can only be a number(4 digits)");
				return;
			}
			if(yearInt < 1000 || yearInt>9999)
			{
				printError(error,"Invalid data: The year has to be exactly 4 digits");
				return;
			}
			if("Author".equals(searchBy))
			{
				engine.query1AuthorTemplate(nameOrTitle, sType, 2, yearInt);
			}
			else	//Title
			{
				engine.query1TitleTemplate(nameOrTitle, sType, 2, yearInt);
			}
		}
		else if("Custom Range".equals(filter))
		{
			String year1 = ((JTextField)((JPanel)queryPanel.getComponent(11)).getComponent(2)).getText();
			String year2 = ((JTextField)((JPanel)queryPanel.getComponent(11)).getComponent(6)).getText();
			
			Integer year1Int,year2Int;
			try
			{
				year1Int = Integer.parseInt(year1);
				year2Int = Integer.parseInt(year2);
			}
			catch(NumberFormatException e)
			{
				printError(error,"Invalid data: The years can only be numbers(4 digits each)");
				return;
			}
			if(year1Int<1000 || year1Int>9999 || year2Int<1000 || year2Int>9999)
			{
				printError(error,"Invalid data: The years have to be exactly 4 digits each");
				return;
			}
			if(year1Int > year2Int)
			{
				printError(error,"Invalid data: First year cannot be later than the second year");
				return;
			}
			if("Author".equals(searchBy))
			{
				engine.query1AuthorTemplate(nameOrTitle, sType, 3, year1Int, year2Int);
			}
			else	//Title
			{
				engine.query1TitleTemplate(nameOrTitle, sType, 3, year1Int, year2Int);
			}
		}
		printQuery1Results();
	}
	/**
	 * prints results of query 1 to the result Panel
	 */
	private void printQuery1Results()
	{
		List<Publication> results = engine.getQuery1Results();
		JLabel result = new JLabel();
		result.setBackground(Color.WHITE);
		result.setForeground(Color.BLACK);
		
		if(results.size()<=0)
		{
			printError(result,"No results found");
			return;
		}
		result.setText(("Number of results:" + results.size()));
		result.setFont(new Font("Times New Roman",Font.PLAIN,20));
		result.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.setVisible(false);
		resultPanel.add(result);
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		JTable resultTable = new JTable(20,8);	//20 rows 8 columns
		resultTable.setEnabled(false);
		JScrollPane resultPane = new JScrollPane(resultTable);
		//resultPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		resultTable.setFillsViewportHeight(false);
		resultPanel.add(resultPane);
		DefaultTableModel model = (DefaultTableModel)resultTable.getModel();
		this.query1model = model;
		int tempCount;
		Object [][] data = new Object[20][9];
		Object[] colNames = new Object[9];
		colNames[0]="S.no.";
		colNames[1]="Authors";
		colNames[2]="Title";
		colNames[3]="Pages";
		colNames[4]="Year";
		colNames[5]="Volume";
		colNames[6]="Journal/BookTitle";
		colNames[7]="URL";
		colNames[8]="EE";
		int max = results.size();
		for(index=0,tempCount=0;tempCount<count;index++,tempCount++)
		{
			if(index<max)
			{
				data[tempCount][0] = index+1;
				data[tempCount][1] = results.get(index).getAuthors();
				data[tempCount][2] = results.get(index).getTitle();
				data[tempCount][3] = results.get(index).getPages();
				data[tempCount][4] = results.get(index).getYear();
				data[tempCount][5] = results.get(index).getVolume();
				data[tempCount][6] = results.get(index).getJournal();
				data[tempCount][7] = results.get(index).getUrl();
				data[tempCount][8] = results.get(index).getEe();
			}
			else
			{
				for(int i=0;i<9;i++)
				{
					data[tempCount][i]="";
				}
			}
			
		}
		model.setDataVector(data,colNames);
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		createPrevNextButton();
		frame.setVisible(true);
	}
	/**
	 * prints message on resultPanel if any issue with input
	 * @param error Label to put the error message on
	 * @param s error message
	 */
	private void printError(JLabel error,String s)
	{
		frame.setVisible(false);
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		error.setText(s);
		error.setFont(new Font("Arial",Font.ITALIC,20));
		error.setAlignmentX(Component.CENTER_ALIGNMENT);
		resultPanel.add(error);
		//addToPanel(resultPanel,error,panelSize,panelSize,panelSize);
		frame.setVisible(true);
	}
	/**
	 * initializes search for query2 after validating input
	 */
	private void Query2Search()
	{
		JLabel error = new JLabel();
		error.setBackground(Color.WHITE);
		error.setForeground(Color.BLACK);
		resultPanel.setLayout(new BoxLayout(resultPanel,BoxLayout.Y_AXIS));
		resultPanel.removeAll();
		String k = ((JTextField)((JPanel)queryPanel.getComponent(3)).getComponent(2)).getText();
		if("".equals(k))
		{
			printError(error,"Invalid data: Number of publications cannot be empty");
			return;
		}
		Integer kInt;
		try
		{
			kInt = Integer.parseInt(k);
		}
		catch(NumberFormatException e)
		{
			printError(error,"Invalid data: Number of publications has to be a number");
			return;
		}
		if(kInt<0)
		{
			printError(error,"Invalid data: Number of publications cannot be negative");
			return;
		}
		engine.query2(kInt);
		printQuery2Results();
	}
	/**
	 * prints results of query 2 to the result Panel
	 */
	private void printQuery2Results()
	{
		List<Author> results = engine.getQuery2Results();
		JLabel result = new JLabel();
		result.setBackground(Color.WHITE);
		result.setForeground(Color.BLACK);
		
		if(results.size()<=0)
		{
			printError(result,"No results found");
			return;
		}
		result.setText(("Number of results:" + results.size()));
		result.setFont(new Font("Times New Roman",Font.PLAIN,20));
		result.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.setVisible(false);
		resultPanel.add(result);
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		JTable resultTable = new JTable(20,3);	//20 rows 3 columns
		resultTable.setEnabled(false);
		JScrollPane resultPane = new JScrollPane(resultTable);
		resultTable.setFillsViewportHeight(false);
		resultPanel.add(resultPane);
		DefaultTableModel model = (DefaultTableModel)resultTable.getModel();
		this.query2model = model;
		int tempCount;
		Object [][] data = new Object[20][3];
		Object[] colNames = new Object[3];
		colNames[0]="S.no.";
		colNames[1]="Author";
		colNames[2]="No. of Publications";
		int max = results.size();
		for(index=0,tempCount=0;tempCount<count;index++,tempCount++)
		{
			if(index<max)
			{
				data[tempCount][0] = index+1;
				data[tempCount][1] = results.get(index).getName();
				data[tempCount][2] = results.get(index).getNumberOfPublications();
			}
			else
			{
				data[tempCount][0] = "";
				data[tempCount][1] = "";
				data[tempCount][2] = "";
			}
			
		}
		model.setDataVector(data,colNames);
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		createPrevNextButton();
		frame.setVisible(true);
	}
	/*
	 * creates the previous and next buttons and puts them on resultPanel
	 */
	private void createPrevNextButton()
	{
		//Create a panel
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		
		Dimension buttonSize = new Dimension(100,40);
		//previous button
		JButton prev=new JButton("Previous");
		prev.setName("Previous");
		prev.addActionListener(this);
		prev.setBackground(Color.BLACK);
		prev.setForeground(Color.WHITE);
		addToPanel(buttonPanel,prev,buttonSize,buttonSize,buttonSize);
						
		//blank space
		buttonPanel.add(Box.createRigidArea(new Dimension(800,0)));
						
		//next button
		JButton next = new JButton("Next");
		next.setName("Next");
		next.addActionListener(this);
		next.setForeground(Color.WHITE);
		next.setBackground(Color.BLACK);
		addToPanel(buttonPanel,next,buttonSize,buttonSize,buttonSize);
						
		//add Panel to frame
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		Dimension bPanelSize = new Dimension(1000,40);
		buttonPanel.setBackground(Color.WHITE);
		//resultPanel.add(buttonPanel);
		addToPanel(resultPanel,buttonPanel,bPanelSize,bPanelSize,bPanelSize);
	}
	private void Query3Search()
	{
		//TODO
		JLabel error = new JLabel();
		error.setBackground(Color.WHITE);
		error.setForeground(Color.BLACK);
		resultPanel.setLayout(new BoxLayout(resultPanel,BoxLayout.Y_AXIS));
		resultPanel.removeAll();
		String authorName = ((JTextField)((JPanel)queryPanel.getComponent(3)).getComponent(2)).getText();
		if("".equals(authorName))
		{
			printError(error,"Author name cannot be empty");
			return;
		}
		Set<String> authors = engine.getEntityResolutionMap().keySet();
		boolean authorPresent = false;
        for(String s :authors)
        {
        	if(s.equalsIgnoreCase(authorName))
        	{
        		authorPresent = true;
        		break;
        	}
        }
		if(!authorPresent)
		{
			printError(error,"No such author in the database");
			return;
		}
		String year = ((JTextField)((JPanel)queryPanel.getComponent(5)).getComponent(2)).getText();
		Integer yearInt;
		try
		{
			yearInt = Integer.parseInt(year);
		}
		catch(NumberFormatException e)
		{
			printError(error,"Invalid data: The year can only be a number(4 digits)");
			return;
		}
		if(yearInt < 1000 || yearInt>9999)
		{
			printError(error,"Invalid data: The year has to be exactly 4 digits");
			return;
		}
		engine.query3(yearInt,authorName);
		printQuery3Results();
	}
	private void printQuery3Results()
	{
		//TODO
		Integer[] results = engine.getQuery3Results();
		JLabel prediction = new JLabel();
		JLabel actual = new JLabel();
		JLabel error = new JLabel();
		
		prediction.setBackground(Color.WHITE);
		prediction.setForeground(Color.BLACK);
		actual.setBackground(Color.WHITE);
		actual.setForeground(Color.BLACK);
		error.setBackground(Color.WHITE);
		error.setForeground(Color.BLACK);
		
		frame.setVisible(false);
		
		if(results[2]==-1)
		{
			resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
			prediction.setText(("Less than 2 entries in databe for this author upto the given year"));
			prediction.setFont(new Font("Times New Roman",Font.PLAIN,20));
			prediction.setAlignmentX(Component.CENTER_ALIGNMENT);
			resultPanel.add(prediction);
			frame.setVisible(true);
			return;
		}
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		prediction.setText(("Predicted value:" + results[0]));
		prediction.setFont(new Font("Times New Roman",Font.PLAIN,20));
		prediction.setAlignmentX(Component.CENTER_ALIGNMENT);
		resultPanel.add(prediction);
		
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		actual.setText(("Actual value:" + results[1]));
		actual.setFont(new Font("Times New Roman",Font.PLAIN,20));
		actual.setAlignmentX(Component.CENTER_ALIGNMENT);
		resultPanel.add(actual);
		
		resultPanel.add(Box.createRigidArea(new Dimension(0,20)));
		Double errorPercent = (Math.abs(results[1]-results[0])/(double)results[1])*100;
		error.setText(("Error percent:" + errorPercent));
		error.setFont(new Font("Times New Roman",Font.PLAIN,20));
		error.setAlignmentX(Component.CENTER_ALIGNMENT);
		resultPanel.add(error);
		frame.setVisible(true);
	}
	/**
	 * Helper method for adding to a panel
	 * @param p the Panel to add to
	 * @param c the JComponent to add
	 * @param max maxSize
	 * @param min minSize
	 * @param pref preferredSize
	 */
	private void addToPanel(JPanel p,JComponent c, Dimension max, Dimension min, Dimension pref )
	{
		c.setMinimumSize(min);
		c.setMaximumSize(max);
		c.setAlignmentX(Component.CENTER_ALIGNMENT);
		c.setPreferredSize(pref);
		p.add(c);
		
	}
	/**
	 * calls addToPanel(JPanel p,JComponent c, Dimension max, Dimension min, Dimension pref) with default values for sizes(Dimensions)
	 * @param p Panel to add to
	 * @param c JComponent to add
	 */
	private void addToPanel(JPanel p,JComponent c)
	{
		/*Default values : 
		//c.setBorder(BorderFactory.createLineBorder(Color.BLACK,2,true));
		//c.setBackground(new Color(40,200,200));
		//c.setForeground(Color.WHITE);
		c.setMinimumSize(new Dimension(100,30));
		c.setMaximumSize(new Dimension(100,30));
		c.setPreferredSize(new Dimension(100,30);
		*/
		
		addToPanel(p,c,comboBoxDimension,comboBoxDimension,comboBoxDimension);
	}
	/**
	 * Initializes the query1 screen
	 * blanks out the result Panel
	 */
	private void Query1Screen()
	{
		frame.setVisible(false);
		initialiseQueryPanel(1);
		initialiseResultPanel();
		String[] searchOptions = {"Search by","Author","Title"};
		JComboBox<String> searchBox = new JComboBox<String>(searchOptions);
		searchBox.setSelectedIndex(0);
		//((JPanel)queryPanel.getComponent(1)).add(searchBox);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,searchBox);
		
		JPanel name = new JPanel();
		JLabel nameTitle = new JLabel("Name/Title tags");
		JTextField nameField = new JTextField(20);
		name.setLayout(new BoxLayout(name,BoxLayout.X_AXIS));
		name.add(nameTitle);
		name.add(Box.createRigidArea(new Dimension(20,0)));
		name.add(nameField);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,name,panelSize,panelSize,panelSize);
		
		String[] filter = {"No filter","Since year","Custom Range"};
		JComboBox<String> filterBox = new JComboBox<String>(filter);
		filterBox.setSelectedIndex(0);
		//((JPanel)queryPanel.getComponent(1)).add(searchBox);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,filterBox);
		
		
		JPanel year = new JPanel();
		JLabel sinceYear = new JLabel("Since Year");
		JTextField yearField = new JTextField("YYYY");
		year.setLayout(new BoxLayout(year,BoxLayout.X_AXIS));
		year.add(sinceYear);
		year.add(Box.createRigidArea(new Dimension(50,0)));
		year.add(yearField);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,year,panelSize,panelSize,panelSize);
		
		JPanel customYear = new JPanel();
		JLabel customYearTag = new JLabel("Custom Range");
		JLabel hyphen = new JLabel("-");
		JTextField customYearField1 = new JTextField("YYYY");
		JTextField customYearField2 = new JTextField("YYYY");
		customYear.setLayout(new BoxLayout(customYear,BoxLayout.X_AXIS));
		customYear.add(customYearTag);
		customYear.add(Box.createRigidArea(new Dimension(20,0)));
		customYear.add(customYearField1);
		customYear.add(Box.createRigidArea(new Dimension(5,0)));
		customYear.add(hyphen);
		customYear.add(Box.createRigidArea(new Dimension(5,0)));
		customYear.add(customYearField2);
		
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,customYear,panelSize,panelSize,panelSize);
		
		String[] sort = {"Default sort","Year(Reverse)","Relevance"};
		JComboBox<String> sortBox = new JComboBox<String>(sort);
		sortBox.setSelectedIndex(0);
		//((JPanel)queryPanel.getComponent(1)).add(searchBox);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,sortBox);
		
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		searchAndResetButtons();
		
		frame.setVisible(true);
	}
	/**
	 * creates search and reset buttons and puts them on queryPanel
	 */
	private void searchAndResetButtons()
	{
		//Create a panel
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		
		//blank space
		buttonPanel.add(Box.createRigidArea(new Dimension(30,0)));
		
		Dimension buttonSize = new Dimension(80,40);
		//search button
		JButton search=new JButton("Search");
		search.setName("Search");
		search.addActionListener(this);
		search.setBackground(Color.BLACK);
		search.setForeground(Color.WHITE);
		addToPanel(buttonPanel,search,buttonSize,buttonSize,buttonSize);
		//System.out.println(startGame.getText());
		//startGame.setBounds(95, 80, 100, 30);
				
		//blank space
		buttonPanel.add(Box.createRigidArea(new Dimension(10,0)));
				
		//reset button
		JButton reset = new JButton("Reset");
		reset.setName("Reset");
		reset.addActionListener(this);
		reset.setForeground(Color.WHITE);
		reset.setBackground(Color.BLUE);
		addToPanel(buttonPanel,reset,buttonSize,buttonSize,buttonSize);
				
		//add Panel to frame
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		addToPanel(queryPanel,buttonPanel,panelSize,panelSize,panelSize);
	}
	/**
	 * Initializes the query2 screen
	 * Blanks out the resultPanel
	 */
	private void Query2Screen()
	{
		frame.setVisible(false);
		
		initialiseQueryPanel(2);
		initialiseResultPanel();
		JPanel noOfPublications = new JPanel();
		JLabel tag = new JLabel("No. Of Publications");
		JTextField kField = new JTextField(20);
		noOfPublications.setLayout(new BoxLayout(noOfPublications,BoxLayout.X_AXIS));
		noOfPublications.add(tag);
		noOfPublications.add(Box.createRigidArea(new Dimension(20,0)));
		noOfPublications.add(kField);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,noOfPublications,panelSize,panelSize,panelSize);
		
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		searchAndResetButtons();
		
		frame.setVisible(true);
	}
	/**
	 * Initializes the query3 screen
	 * Blanks out result panel
	 */
	private void Query3Screen()
	{
		frame.setVisible(false);
		initialiseQueryPanel(3);
		initialiseResultPanel();
		
		JPanel name = new JPanel();
		JLabel nameAuthor = new JLabel("Author name");
		JTextField nameField = new JTextField(20);
		name.setLayout(new BoxLayout(name,BoxLayout.X_AXIS));
		name.add(nameAuthor);
		name.add(Box.createRigidArea(new Dimension(20,0)));
		name.add(nameField);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,name,panelSize,panelSize,panelSize);
		
		JPanel year = new JPanel();
		JLabel uptoYear = new JLabel("Use data upto year");
		JTextField yearField = new JTextField("YYYY");
		year.setLayout(new BoxLayout(year,BoxLayout.X_AXIS));
		year.add(uptoYear);
		year.add(Box.createRigidArea(new Dimension(50,0)));
		year.add(yearField);
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		addToPanel(queryPanel,year,panelSize,panelSize,panelSize);
		
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		searchAndResetButtons();
		
		frame.setVisible(true);

	}
	/**
	 * Maximizes the frame size
	 */
	private void maximiseFrame()
	{
		final GraphicsConfiguration configuration = frame.getGraphicsConfiguration();
        final int left = Toolkit.getDefaultToolkit().getScreenInsets(configuration).left;
        final int right = Toolkit.getDefaultToolkit().getScreenInsets(configuration).right;
        final int top = Toolkit.getDefaultToolkit().getScreenInsets(configuration).top;
        final int bottom = Toolkit.getDefaultToolkit().getScreenInsets(configuration).bottom;

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = screenSize.width - left - right;
        final int height = screenSize.height - top - bottom;
        frame.setResizable(false);
        frame.setSize(width, height);
	}
	/**
	 * Initializes the main(start up) screen
	 */
	private void initialiseMainScreen()
	{
		//TODO add a windowListener to see if frame is resized, if yes, edit sizes of panels???
		//frame.removeAll();
		combinedPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		combinedPanel.setLayout(new BoxLayout(combinedPanel,BoxLayout.X_AXIS));
		queryPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		queryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		resultPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		resultPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		combinedPanel.add(queryPanel);
		combinedPanel.add(resultPanel);
		queryPanel.setSize(new Dimension(frame.getSize().width/4,frame.getSize().height));
		resultPanel.setSize(new Dimension(3*frame.getSize().width/4,frame.getSize().height));
		//queryPanel.setMinimumSize(new Dimension(frame.getSize().width/3,frame.getSize().height));
		//resultPanel.setMinimumSize(new Dimension(2*frame.getSize().width/3,frame.getSize().height));
		queryPanel.setMaximumSize(new Dimension(frame.getSize().width/4,frame.getSize().height));
		resultPanel.setMaximumSize(new Dimension(3*frame.getSize().width/4,frame.getSize().height));
		//queryPanel.setPreferredSize(new Dimension(frame.getSize().width/3,frame.getSize().height));
		//resultPanel.setPreferredSize(new Dimension(2*frame.getSize().width/3,frame.getSize().height));
		initialiseCombinedPanel();
		frame.add(combinedPanel);
		frame.setVisible(true);
	}
	/**
	 * Used for initializing main screen
	 */
	public void initialiseCombinedPanel()
	{
		initialiseQueryPanel(0);
		initialiseResultPanel();
	}
	/**
	 * Initializes the queryPanel (just the query box) with query box's value according to currentQuery
	 * @param n current query
	 */
	public void initialiseQueryPanel(int n)
	{
		queryPanel.removeAll();
		queryPanel.add(Box.createRigidArea(new Dimension(0,20)));
		queryPanel.setLayout(new BoxLayout(queryPanel,BoxLayout.Y_AXIS));
		String[] queries = {"Queries", "Query 1","Query 2","Query 3"};
		queryBox = new JComboBox<String>(queries);
		queryBox.setName("Query Box");
		queryBox.setSelectedIndex(n);
		queryBox.addActionListener(this);
		addToPanel(queryPanel,queryBox);//queryPanel.add(queryBox);
	}
	/**
	 * blanks out the resultPanel
	 */
	public void initialiseResultPanel()
	{
		resultPanel.removeAll();
		resultPanel.setBackground(Color.WHITE);
	}
}
