1) to run individually, go to 2gram / 3gram folder,
,open terminal in that directory, start hadoop, 
copy paste the command in sequence document.

2)to run for 400 documents

 open terminal in this folder
 run the below given commands:
a)for 2 gram and 400 docs
 hdfs dfs -mkdir -p ~/input/2gram/
 ./script2gramm.sh
a)for 3 gram and 400 docs
 hdfs dfs -mkdir -p ~/input/2gram/
 ./script3grm.sh