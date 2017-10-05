/*
Returns hashmap of keys as text and values as arraylist of text
*/
import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.Text;

public class csvreader {

	public HashMap<Text,ArrayList<Text>> getLemmaMap () {
		HashMap<Text,ArrayList<Text>> map = new HashMap<Text,ArrayList<Text>>();
		String line = "";
		String cvsSplitBy = ",";
		BufferedReader br = null;
		try{
			File file = new File("la.lexicon.csv");
			System.out.println(file.exists());
			br = new BufferedReader(new FileReader(file));
			line = br.readLine();

			while ((line = br.readLine()) != null) {
				String[] lemmaline = line.split(cvsSplitBy);
				if(lemmaline.length>2){
					lemmaline[0]=lemmaline[0].replaceAll("j", "i");
          			lemmaline[0]=lemmaline[0].replaceAll("v", "u");

					Text key = new Text(lemmaline[0].toLowerCase().trim());
					Text value = new Text(lemmaline[2].toLowerCase().trim());
					// use comma as separator

					if(map.containsKey(key)){
						int flag=0;
						// for(Text val: map.get(key)){
						// 	if(val.toString().equals(value.toString()))flag=1; 
						// }
						if(map.get(key).contains(value)==true) flag=1;   //put only unique values	
						if(flag==0) {
							 
							//if(key.equals(new Text("abas"))) System.out.print(" 1" +key+" " + value+ " "+ map.get(key).contains(value)+" ");
							map.get(key).add(value);
					    } 

					}else{
						ArrayList<Text> newvalue=new ArrayList<Text>();

						newvalue.add(key);
						newvalue.add(value);
						map.put(key, newvalue);
						//if(key.equals(new Text("abas"))) System.out.print("2" + key+" " + value+ " "+ map.get(key).contains(value)+" ");
					}

				}        	
			}

			System.out.println("MAP Size "+ map.size()+" reader");
			System.out.println("return 1");
		} catch (FileNotFoundException e) {
			System.out.println(" FileNotFoundException "+ e.toString());
		} catch (IOException e) {
			System.out.println(" IOException "+ e.toString());
		} finally {
			if (br != null) {
				try {
					br.close();
					return map;
				} catch (IOException e) {
					System.out.println(" IOException2 "+ e.toString());
				}
			}
		}
		System.out.println("return 2");
		return map;

	}
	//
	//    public static void main(String[] args) {
	//    	HashMap<Text,ArrayList<Text>> map = getLemmaMap();
	//    }
}