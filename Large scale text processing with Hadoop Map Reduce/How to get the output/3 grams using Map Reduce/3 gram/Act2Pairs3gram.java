
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Act2Pairs3gram {
  public static HashMap<Text,ArrayList<Text>> map = new HashMap<Text,ArrayList<Text>>();

  public static class TokenizerMapper extends Mapper<Object, Text, WordPair, Text>{

    private WordPair wordPair = new WordPair();
    private Text word = new Text();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
      String[] lines = value.toString().split("\n");
        for (int i=0;i<lines.length ;i++ ) {
          //from lematizer
          String[] parts = lines[i].toString().split("\t");//separating into 2 parts
          if(parts.length>1){
            parts[1]=parts[1].toLowerCase();//convert all to lower case
            parts[1]=parts[1].replaceAll("j", "i");
            parts[1]=parts[1].replaceAll("v", "u");
            parts[1]=parts[1].replaceAll("[^A-Za-z ]+", "");         
            String [] words = parts[1].split("\\s+"); //contains the words string

            for (int j=0;j<words.length ;j++ ){
              wordPair.setWord(words[j]);
              for(int k=j+1;k<words.length;k++){
                wordPair.setNeighbor1(words[k]);
                for(int l=k+1;l<words.length;l++){
                	wordPair.setNeighbor2(words[l]);
                	context.write(wordPair,new Text(parts[0]));
            	}
              }//emit word and location as text
            }//parts.length>1
          }
        } //for (int i=0;i<lines.length ;i++ ) 
    }//map method
  }//TokenizerMapper class

  public static class IntSumReducer extends Reducer<WordPair,Text,Text,Text> {

    public void reduce(WordPair key, Iterable<Text> values,Context context ) throws IOException, InterruptedException {
      Text word1 =key.getWord();
      Text word2 =key.getNeighbor1();
      Text word3 =key.getNeighbor2();
      String newkey=" ";
      String valueall="";
      int count= 0;
      for (Text val : values) {
         count=count+1;
         valueall= valueall+ val.toString()+" , ";
      }
      valueall=valueall+" count- "+String.valueOf(count);
      ArrayList<Text> lemmaList1 =new ArrayList<Text>();
      ArrayList<Text> lemmaList2=new ArrayList<Text>();
      ArrayList<Text> lemmaList3=new ArrayList<Text>();

      if(map.containsKey(word1)){
        lemmaList1=map.get(word1);
      }
      if(map.containsKey(word2)){
        lemmaList2=map.get(word2);
      }
      if(map.containsKey(word3)){
        lemmaList3=map.get(word3);
      }
      if(lemmaList1==null || lemmaList2==null || lemmaList3==null ){ // lemma for both dosent exist
        context.write(new Text("\n"+key.toString()),new Text(valueall));
      }else if(lemmaList1!=null && lemmaList2!=null && lemmaList3!=null){//lemma of neighbour  exist
      	context.write(new Text("\n"+key.toString()),new Text(valueall));//Writing the words directly
      	//iterating over to get the lemmas
        for(Text lemma1: lemmaList1){
          for(Text lemma2: lemmaList2){
          	for(Text lemma3: lemmaList3){
	            newkey=lemma1.toString()+","+lemma2.toString()+","+lemma3.toString()+" ";
	            context.write(new Text(newkey),new Text(valueall));
        	}
          }
        }   
      }        
    }   
  }

  public static void main(String[] args) throws Exception {
    csvreader csv = new csvreader();
    map = csv.getLemmaMap();
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(Act2Pairs3gram.class);
    job.setMapperClass(TokenizerMapper.class);
   // job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setMapOutputKeyClass(WordPair.class);
    job.setMapOutputValueClass(Text.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}