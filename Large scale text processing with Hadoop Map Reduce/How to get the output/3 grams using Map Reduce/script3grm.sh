i=1
for f in latin/*.tess; do
    echo "File name is : $f"
    hdfs dfs -put $f ~/input/3gram/
    cd 3gram/
    START=$(date +%s)
    hadoop jar Act2Pairs3gram.jar Act2Pairs3gram ~/input/3gram ~/output39 &>> output1.txt
    END=$(date +%s)
    DIFF=$(( $END - $START ))
    echo "$i $DIFF " &>> time.txt
    hdfs dfs -rm -r ~/output39
    i=$(( $i + 1 ))
    echo "Files processed : $i"
    rm output1.txt
    cd .. 
done