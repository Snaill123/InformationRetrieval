package tf_idf;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class TF_IDF {
	
	//计算单词的TF值
	public static Map<String,Float> tfCal(String docName,Map<String,Vector<String>> wordsAll){
		//(单词，单词数量）
		HashMap<String,Integer> dict = new HashMap<String, Integer>();
		//(单词，单词词频)
		HashMap<String,Float> tf = new HashMap<String,Float>();
		//记录待查询文档的单词总数
		int wordCount = 0;
		//统计每个单词在文章中出现的次数
		for(String word:wordsAll.get(docName)){
			wordCount++;
			//若该单词在文章中已出现过
			if(dict.containsKey(word)) {
				dict.put(word, dict.get(word)+1);
			}else {
				//该单词在文章中第一次出现
				dict.put(word,1);
			}
		}
		//计算每个单词的tf值(该单词出现次数/总单词数)
		for(Map.Entry<String, Integer> entry:dict.entrySet()) {
			float wordTF = (float)entry.getValue()/wordCount;
			tf.put(entry.getKey(), wordTF);
		}
		
		return tf;
		
	}
	
	//计算单词的TF_IDF值
	public static Map<String,Float> tfidfCAL(Map<String,Set<String>>doc_words, Map<String,Float>tf,Map<String,Vector<String>> wordsAll){
		HashMap<String,Float> tfidf = new HashMap<String,Float>();
		int D = wordsAll.size();
		for(String key:tf.keySet()) {
			//Dt记录包含该单词的文章数
			int Dt = 0;
			for(Map.Entry<String, Set<String>> entry: doc_words.entrySet())
			for(String word:entry.getValue()) {
				//取出每篇文档中不重复的单词集合进行比对
					if(word.equals(key)) {
						//出现相同的单词，
						Dt++;
				}
			}
			//计算出每个单词的IDF值
			float idfValue = (float) Math.log(Float.valueOf(D)/Dt + 1);
			//最终算出tfidf值
			tfidf.put(key, idfValue*tf.get(key));
		}
		
		return tfidf;
		
	}
	
}

