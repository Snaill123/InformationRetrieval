package tf_idf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.*;

public class TEST {
	
	static Map<String,Set<String>> wordsSet = new HashMap<String,Set<String>>();
	
	static Map<String,Vector<String>> wordsAll = new HashMap<String,Vector<String>>();
	
	//ͨ����¼���ļ�·����Ϣ��ȡ�ļ�����
	public static  void  getWords (String docIndex) throws IOException {
		
		//ͨ��docIndex�ļ��е������ҵ�ÿ���ļ������ļ��е����ݽ��зִ�
		BufferedReader bufferR = new BufferedReader(new FileReader(docIndex));
		
		BufferedReader bufrDoc = null;
		
		String docPathReader = null;
		
		while((docPathReader = bufferR.readLine())!= null) {
			
			String[] docInfo = docPathReader.split("\t");
			
			String docName = docInfo[0];
			
			String docPath = docInfo[1];
			
			bufrDoc = new BufferedReader(new FileReader(docPath));
			
			String wordLine = null;
			
			Vector<String> wordsOfDoc = new Vector<String>() ;
			
			Set<String> wordsDiff = new HashSet<String>();
			
			while((wordLine = bufrDoc.readLine()) != null) {
				//����ƥ�������
				String[] words = wordLine.split("\\W");
				
				for(String word : words) {
					
					wordsOfDoc.addElement(word);

					wordsDiff.add(word);
				}
				
			}
			
			wordsSet.put(docName, wordsDiff);
			
			wordsAll.put(docName, wordsOfDoc);
			
		}
		
	}
	

	//ͨ�������filePath�ҵ��ļ����ڣ��������ļ��������ļ���Ϣд��docIndex.txt��	
	public static void getFileIndex(String filePath, String docIndex) throws IOException{
		
		File file = new File(filePath);
		
		File[] fileList = file.listFiles();
		
		BufferedWriter buffer = null;
		
		//��filePath�������ļ�·��д��docIndex�ļ���
		
		buffer = new BufferedWriter(new FileWriter(docIndex));
		
		for(int i = 0; i < fileList.length; i++) {
			
			String docPath = fileList[i].getAbsolutePath();
			
			buffer.write("DocID_"+i+"\t"+docPath);
			
			buffer.newLine();
			
			buffer.flush();
		}    
	}
	
	public static void main(String[] args) throws IOException {
		
		String docIndex = "C:\\Users\\15850\\eclipse-workspace\\tf_idf\\src\\tf_idf\\docIndex.txt";
		
		String filePath = "C:\\Users\\15850\\eclipse-workspace\\tf_idf\\src\\tf_idf\\documents";
		
		getFileIndex(filePath,docIndex);
	
		getWords(docIndex);
		
		TF_IDF tfidf = new TF_IDF();
		
		//Ҫ��ѯ���ĵ���
		String docQuery = "DocID_0";
				
		Map<String,Float> tf = tfidf.tfCal(docQuery,wordsAll);
		
		Map<String,Float> result = tfidf.tfidfCAL(wordsSet, tf, wordsAll);
		
		 // ����Ƚ���
	    Comparator<Map.Entry<String, Float>> valueComparator = new Comparator<Map.Entry<String,Float>>() {
	        @Override
	        public int compare(Entry<String, Float> o1,  Entry<String, Float> o2) {
	     
	            return (o2.getValue().compareTo(o1.getValue()) );
	            
	        }
	        
	    };
	    //MapתΪlist����
	    List<Map.Entry<String, Float>> list = new ArrayList<Map.Entry<String,Float>>(result.entrySet());
	    
	    Collections.sort(list,valueComparator);
	    
	    System.out.println("------------���ʰ���tfidfֵ��������--------------------");
	    
	    for (Map.Entry<String, Float> entry : list) {
	    	
	        System.out.println(entry.getKey() + ":" + entry.getValue());
	        
	    }
	}
	
}