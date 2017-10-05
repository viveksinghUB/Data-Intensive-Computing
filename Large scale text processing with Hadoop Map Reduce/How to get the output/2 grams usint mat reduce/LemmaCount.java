
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.*;
import java.lang.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.Logger;


public class LemmaCount {

  public static HashMap<Text,ArrayList<Text>> map = new HashMap<Text,ArrayList<Text>>();


  public static class TokenizerMapper extends Mapper<LongWritable,Text,Text,Text>{
  	private Text word = new Text();
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
      String id = "";
      String chapter = "";
      String pgno = "";
      String[] lines = value.toString().split("\n");
      for (int i=0;i<lines.length ;i++ ) {
        //seperating id chapter line and words string
        String[] parts = lines[i].toString().split("\t");//separating into 2 parts
        //uncommenting below lines for including position


        if(parts.length>1){
          // parts[0]=parts[0].replaceAll(">", "");
          parts[0]=parts[0].replaceAll(">", "");
          parts[1]=parts[1].toLowerCase();//convert all to lower case
          parts[1]=parts[1].replaceAll("j", "i");
          parts[1]=parts[1].replaceAll("v", "u");
          parts[1]=parts[1].replaceAll("[^A-Za-z ]+", "");
          
         
         //uncomenting below lines for inclding position
          // String [] idpgchapter=parts[0].split(".");
          // if(idpgchapter.length>1){
          //    id = idpgchapter[0];
          //    chapter = idpgchapter[1];
          //    pgno = idpgchapter[2];
          // }
          // String emit = " < id- "+ id + " , " + " chapter - " + chapter + " , " + " pgno- "+ pgno;
          
          
          String [] words = parts[1].split("\\s+"); //contains the words string
          int position=0;
          for (int j=0;j<words.length ;j++ ){
            position++;
            String emit=parts[0]+" position- "+Integer.valueOf(position)+" >";
            Text valueToemit = new Text(emit); 
            word.set(words[j]);
            context.write(word,valueToemit);
          }//emit word and location as text
        }//parts.length>1
      }//loop over every line
    }//map
  }//TokenizerMapper


  public static class IntSumReducer extends Reducer<Text, Text, Text, Text> {
    
    protected void reduce(Text key,  Iterable<Text> values, Context context) throws IOException, InterruptedException {
        //System.out.println("Entered reducer");
      String valueall=" <------>  ";
      String newkey="word- "+key.toString()+ " "+" lemma- " +key.toString(); //default word is the lemma
      int count= 0;
      for (Text val : values) {
         count=count+1;
         valueall= valueall+" || "+ val.toString();
      }
      valueall=valueall+" count- "+String.valueOf(count);
      Text valuealltext= new Text(valueall);
      if(map.containsKey(key)){
        for(Text lemma: map.get(key)){
          newkey="word- "+key.toString()+ " "+"lemma- " +lemma.toString();
          //newkey=newkey+" lemma- " +lemma.toString();
          Text newKeytext = new Text(newkey);
          context.write(newKeytext,valuealltext);
        }
      }
      else context.write(new Text(newkey),valuealltext);
    }	 
  }  
  public static void main(String[] args) throws Exception {
    csvreader csv = new csvreader();
    map = csv.getLemmaMap();
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(LemmaCount.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setReducerClass(IntSumReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}    