# Data-Intensive-Computing
Data Intensive Computing projects using MapReduce, Spark, Hadoop, Tableau<br>
It comprises of DIC projects implemented in Jupyter notebook or R Studio in R language.<br>
H1B analytics Tableau Dashboard<br>
GOALS:<br>
Major goals of this project are to:<br>
1. Identify a problem with multi-variate data and different forms analysis resulting in charts.<br>
2. Discover patterns by analyzing the data.<br>
3. Design a dashboard with multiple charts that offer insights into the data.<br>
4. Build a data product by adding interaction to the charts to allow a user to understand the<br>
behavior of the data by changing values of parameters.<br>
5. Write and publish a report that describes the dashboard .<br>

<br><br>
Large scale text processing with Hadoop Map Reduce
<br><br>
Major goals of this project is to:<br><br>
Identify problems solvable using MR approach.<br>
Design MR Algorithms for solving big data problems involving Write Once Read Many (WORM) data such as historical text, health care data.
Understand and learn to apply MapReduce (MR) algorithm for processing large data sets.<br>
Store and retrieve text data in Hadoop Distributed File System (HDFS) as <key,value>.<br>
Implement the MR solutions designed in steps 2 and 3 on a stand-alone virtual machine or on the cloud (Amazon AWS, Google or cloud service providers).<br>
Interpret the results to enable decision making.<br><br>

What's Implemented ?<br>
1.Wordcount on tweets Collected tweets from twitter. Ran “wordcount” on the tweets . Visualized the output using “word cloud’.<br><br>
2.Word co-occurrence on tweets Performed word co-occurrence as described in, with pairs and stripes methods for the tweets collected for the step above.Used MapReduce approach with the data stored on the HDFS.<br>
3.Wordcount on Classical Latin text This problem was provided by researchers in the Classics department at UB. They have provided two classical texts and a lemmatization file to convert words from one form to a standard or normal form. In this case you will use several passes through the documents. The documents needed for this process are available in in the UBbox [7]. Pass 1: Lemmetization using the lemmas.csv file Pass 2: Identify the words in the texts by <word <docid, [chapter#, line#]> for two documents. Pass 3: Repeat this for multiple documents<br>
4.Word co-occurrence among multiple documents “Scaled up” the word co-occurrence by increasing the number of documents processed from 2 to n. Recorded the performance of the MR infrastructure.<br>

Data Clients and Information Servers <br> 
GOALS:<br> <br> 
 1. Install a work environment for carrying out various activities of the data science process.
<br>  2. Understand and implement Application Programming Interface (API) based programmatic data collection from popular (/public) data sources (Data clients).
<br>  3. Process the data collected for extracting basic information.
<br>  4. Serve the information extracted through simple applications and visualization (Information servers).

<br> Data Cleaning and Munging<br> 
GOALS:<br> <br> 

<br> Convert data in various format into a form that is convenient for in-memory operations. Transform from external storage formats such as xml, sqllite into a common external format, comma separated value (.csv) convenient for exploratory data analysis using R. This allows for easy reading of data into the memory as data frames.
<br> Extract (data munging) useful data from raw data collected by real survey instruments. You will use the actual survey document in interpreting the survey results.
<br> Repurpose data from a popular domain (such as sports) for consumption by different genre of applications.
<br> Transform data using operations such as grouping, categorization and binning to stage them for in-memory analysis. (R is optimized to work well with in-memory data.)
<br> Document data cleaning steps using Markdown, a text-based HTML authoring format. This is an essential step in “reproducible research” and is offered within Jupyter platform.
<br> Learn and understand the scientific data collection process, surveys, and nature of raw data and the need and motivation for cleaning and munging the data.

<br> Algorithms and Models for Data Analysis, Learning and Prediction.
<br> GOALS:<br> <br> 

<br> <br> Decide on the algorithms that will be used in solving the problem based on the data characteristics and the attributes of the problems.
<br> Learn to apply algorithms: linear regression, K-NN and K-means, which algorithm to use and why and when.
<br> Make valid and reasonable assumptions about the variables (features) of a problem, goodness of a model fit, metrics for evaluation of errors, outliers, scaling, and choosing appropriate data ranges for the computation.
<br> Choose the parameters for prediction based on experimentation (repeated trials) and acceptable values of error rates. Document the experimentation process and the rationale.
<br> Plot the outcomes for easy visualization of data analysis.
<br> Interpret the results to enable decision making.

