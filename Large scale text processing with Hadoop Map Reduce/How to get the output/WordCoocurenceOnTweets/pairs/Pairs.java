
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Pairs {

  public static class TokenizerMapper extends Mapper<Object, Text, WordPair, IntWritable>{

     private WordPair wordPair = new WordPair();
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    Pattern pattern = Pattern.compile(".*[a-zA-Z]+.*");

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
     // StringTokenizer itr = new StringTokenizer(value.toString());
      //String[] lines = value.toString().replaceAll("\\s+", " ").split("\n");
      String[] lines = value.toString().split("\n");
      for (int i=0;i<lines.length ;i++ ) {
        String [] words = lines[i].toString().split(" ");//picking each line
        
        for (int j=0;j<words.length ;j++ ){
          wordPair.setWord(words[j]);
          for(int k=j+1;k<words.length;k++){//making neighbours
            wordPair.setNeighbor(words[k]);
            Matcher matcher1 = pattern.matcher(words[j]);
            Matcher matcher2 = pattern.matcher(words[k]);
            if( matcher1.matches() && matcher2.matches()){ // donot write anything if the word or pair is space
               context.write(wordPair, one);
            }   
          }
        }
      }
    }
  }

  public static class IntSumReducer
       extends Reducer<WordPair,IntWritable,WordPair,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(WordPair key, Iterable<IntWritable> values,Context context ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(Pairs.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setMapOutputKeyClass(WordPair.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setOutputKeyClass(WordPair.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}