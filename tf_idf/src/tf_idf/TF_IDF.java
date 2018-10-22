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
	
	//���㵥�ʵ�TFֵ
	public static Map<String,Float> tfCal(String docName,Map<String,Vector<String>> wordsAll){
		//(���ʣ�����������
		HashMap<String,Integer> dict = new HashMap<String, Integer>();
		//(���ʣ����ʴ�Ƶ)
		HashMap<String,Float> tf = new HashMap<String,Float>();
		//��¼����ѯ�ĵ��ĵ�������
		int wordCount = 0;
		//ͳ��ÿ�������������г��ֵĴ���
		for(String word:wordsAll.get(docName)){
			wordCount++;
			//���õ������������ѳ��ֹ�
			if(dict.containsKey(word)) {
				dict.put(word, dict.get(word)+1);
			}else {
				//�õ����������е�һ�γ���
				dict.put(word,1);
			}
		}
		//����ÿ�����ʵ�tfֵ(�õ��ʳ��ִ���/�ܵ�����)
		for(Map.Entry<String, Integer> entry:dict.entrySet()) {
			float wordTF = (float)entry.getValue()/wordCount;
			tf.put(entry.getKey(), wordTF);
		}
		
		return tf;
		
	}
	
	//���㵥�ʵ�TF_IDFֵ
	public static Map<String,Float> tfidfCAL(Map<String,Set<String>>doc_words, Map<String,Float>tf,Map<String,Vector<String>> wordsAll){
		HashMap<String,Float> tfidf = new HashMap<String,Float>();
		int D = wordsAll.size();
		for(String key:tf.keySet()) {
			//Dt��¼�����õ��ʵ�������
			int Dt = 0;
			for(Map.Entry<String, Set<String>> entry: doc_words.entrySet())
			for(String word:entry.getValue()) {
				//ȡ��ÿƪ�ĵ��в��ظ��ĵ��ʼ��Ͻ��бȶ�
					if(word.equals(key)) {
						//������ͬ�ĵ��ʣ�
						Dt++;
				}
			}
			//�����ÿ�����ʵ�IDFֵ
			float idfValue = (float) Math.log(Float.valueOf(D)/Dt + 1);
			//�������tfidfֵ
			tfidf.put(key, idfValue*tf.get(key));
		}
		
		return tfidf;
		
	}
	
}

