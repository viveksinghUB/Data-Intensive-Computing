i=1
for f in latin/*.tess; do
    echo "File name is : $f"
    hdfs dfs -put $f ~/input/2gram/
    cd 2gram/
    START=$(date +%s)
    hadoop jar Act2Pairs.jar Act2Pairs ~/input/2gram ~/output29 &>> output1.txt
    END=$(date +%s)
    DIFF=$(( $END - $START ))
    echo "$i $DIFF " &>> time.txt
    hdfs dfs -rm -r ~/output29
    i=$(( $i + 1 ))
    rm output1.txt
    cd .. 
done