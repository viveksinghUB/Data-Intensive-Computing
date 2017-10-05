

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


public class WordPair implements Writable,WritableComparable<WordPair> {

    private Text word;
    private Text neighbor1;
    private Text neighbor2;

    public WordPair(Text word, Text neighbor1,Text neighbor2) {
        this.word = word;
        this.neighbor1 = neighbor1;
        this.neighbor2 = neighbor2;
    }

    public WordPair(String word, String neighbor1,String neighbor2) {
        this(new Text(word),new Text(neighbor1),new Text(neighbor2));
    }

    public WordPair() {
        this.word = new Text();
        this.neighbor1 = new Text();
        this.neighbor2 = new Text();
    }

   //@Override
    public int compareTo(WordPair other) {
        int returnVal = this.word.compareTo(other.getWord());
        if(returnVal != 0){
            return returnVal;
        }
        if(this.neighbor1.toString().equals("*")){
            return -1;
        }else if(other.getNeighbor1().toString().equals("*")){
            return 1;
        }
        return this.neighbor1.compareTo(other.getNeighbor1());
    }

    public static WordPair read(DataInput in) throws IOException {
        WordPair wordPair = new WordPair();
        wordPair.readFields(in);
        return wordPair;
    }

    //@Override
    public void write(DataOutput out) throws IOException {
        word.write(out);
        neighbor1.write(out);
        neighbor2.write(out);
    }

    //@Override
    public void readFields(DataInput in) throws IOException {
        word.readFields(in);
        neighbor1.readFields(in);
        neighbor2.readFields(in);
    }

    @Override
    public String toString() {
        return word+","+neighbor1+","+neighbor2+"*";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordPair wordPair = (WordPair) o;

        if (neighbor1 != null ? !neighbor1.equals(wordPair.neighbor1) : wordPair.neighbor1 != null) return false;
        if (word != null ? !word.equals(wordPair.word) : wordPair.word != null) return false;

        return true;
    }

    // @Override
    // public int hashCode() {
    //     int result = word != null ? word.hashCode() : 0;
    //     result = 163 * result + (neighbor != null ? neighbor.hashCode() : 0);
    //     return result;
    // }

    public void setWord(String word){
        this.word.set(word);
    }
    public void setNeighbor1(String neighbor1){
        this.neighbor1.set(neighbor1);
    }
    public void setNeighbor2(String neighbor2){
        this.neighbor2.set(neighbor2);
    }

    public Text getWord() {
        return word;
    }

    public Text getNeighbor1() {
        return neighbor1;
    }
    public Text getNeighbor2() {
        return neighbor2;
    }
}