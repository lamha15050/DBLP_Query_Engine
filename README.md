# DBLP_Query_Engine
## Advanced Programming Project - November, 2016

Project requirements described in file "Project Description"

### Description of each class

- DBLPQueryEngine: contains the main function, has the driver code
- DBLP_GUI: Handles the complete GUI aspect of the project
- Parse_AuthorPublications, Parse_InitialiseAuthors, Parse_InitialiseForPrediction: parse the DBLP file for initialisation of the system
- Parser_Query1_Author, Parser_Query1_Title: parse the file to answer a query of type query 1(described in project requirements) depending on whether author/title has been chosen
- SortFilterResultsQuery1: Helper class for query 1 - Sorts and filters the results obtained
- Query2: Helper class for query 2
- Publication: Parent class for all types of Publications
- PublicationFactory: Decided which sub-class of Publication to instantiate
- Proceeding, PHDThesis, MasterThesis, Inproceeding, Incollection, Book, Article - Sub classes of Publication: help differentiate different kinds of Publications
- Author: Holds author info, including all other names used and no. of publications (also yearwise)
