##LARGE SCALE DATA (TEXT) PROCESSING WITH HADOOP MAPREDUCE

##Major goals of this project is to:

1. Identify problems solvable using MR approach.
2. Design MR Algorithms for solving big data problems involving Write Once Read Many (WORM)
data such as historical text, health care data.
3. Understand and learn to apply MapReduce (MR) algorithm for processing large data sets.
4. Store and retrieve text data in Hadoop Distributed File System (HDFS) as <key,value>.
5. Implement the MR solutions designed in steps 2 and 3 on a stand-alone virtual machine or on
the cloud (Amazon AWS, Google or cloud service providers).
6. Interpret the results to enable decision making.

## What's Implemented ?

1.Wordcount on tweets
Collected tweets from twitter. Ran “wordcount” on the tweets . Visualized the output using “word cloud’.

2.Word co-occurrence on tweets
Performed word co-occurrence as described in, with
pairs and stripes methods for the tweets collected for the step above.Used MapReduce
approach with the data stored on the HDFS.

3.Wordcount on Classical Latin text
This problem was provided by researchers in the Classics department at UB. They have provided
two classical texts and a lemmatization file to convert words from one form to a standard or
normal form. In this case you will use several passes through the documents. The documents
needed for this process are available in in the UBbox [7].
Pass 1: Lemmetization using the lemmas.csv file
Pass 2: Identify the words in the texts by <word <docid, [chapter#, line#]> for two documents.
Pass 3: Repeat this for multiple documents

4.Word co-occurrence among multiple documents 
“Scaled up” the word co-occurrence by increasing the number of documents processed from 2 to n. 
Recorded the performance of the MR infrastructure.
