
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


public class Stripes {

  public static class TokenizerMapper extends Mapper<LongWritable,Text,Text,MapWritable>{

	private MapWritable stripeMap = new MapWritable();  	
    private final static IntWritable one = new IntWritable(1);

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
     // StringTokenizer itr = new StringTokenizer(value.toString());
      String[] lines = value.toString().split("\n");
    //  if (lines.length > 1) { // atleast 1 line received
        for (int i=0;i<lines.length ;i++ ) {
          String [] words = lines[i].toString().split(" ");//picking each line
          if(words.length>1){
	          stripeMap.clear();
	          for (int j=0;j<words.length ;j++ ){
	            Text word = new Text(words[j]);
		            for(int k=j+1;k<words.length;k++){//making neighbours
		            	Text pair = new Text(words[k]);
		            	if(stripeMap.containsKey(pair)){
		                   IntWritable count = (IntWritable)stripeMap.get(pair);
		                   count.set(count.get()+1);
		                }else{
		                   stripeMap.put(pair,new IntWritable(1));
		                }
		          	}
 
	          	context.write(word,stripeMap);
	          }
	        }  
        }
    }
  }

  public static class IntSumReducer extends Reducer<Text, MapWritable, Text, Text> {
    private IntWritable result = new IntWritable();
    private MapWritable incrementingMap = new MapWritable();

    @Override
    protected void reduce(Text key, Iterable<MapWritable> values, Context context) throws IOException, InterruptedException {
        //DSystem.out.println("Entered reducer");
        incrementingMap.clear();
        for (MapWritable value : values) {
            addAll(value);
        }
        Text valueString = MaptoString(incrementingMap,key);
        context.write(key, valueString);
    }

    private Text MaptoString(MapWritable mapWritable,Text key ) {
    	StringBuilder sb=new StringBuilder();
    	  for (Map.Entry<Writable, Writable> entry : mapWritable.entrySet()) {
		    sb.append("-> ( "+entry.getKey()+" , ");
		    sb.append(entry.getValue()+" )");
		  }
		  sb.append("\n");
		  Text t = new Text(sb.toString());
		  return t;
    }	

    private void addAll(MapWritable mapWritable) {
        Set<Writable> keys = mapWritable.keySet();
        for (Writable key : keys) {
            IntWritable fromCount = (IntWritable) mapWritable.get(key);
            if (incrementingMap.containsKey(key)) {
                IntWritable count = (IntWritable) incrementingMap.get(key);
                count.set(count.get() + fromCount.get());
            } else {
                incrementingMap.put(key, fromCount);
            }
        }
    }
}

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "word count");
    job.setJarByClass(Stripes.class);
    job.setMapperClass(TokenizerMapper.class);
  //  job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(MapWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}