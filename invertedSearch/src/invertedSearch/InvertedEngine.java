package invertedSearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class InvertedEngine {
		
	public static void main(String[] args) throws IOException {
		String filePath = "C:\\Users\\15850\\eclipse-workspace\\invertedSearch\\src\\invertedSearch\\documents";
		String docIndex = "C:\\Users\\15850\\eclipse-workspace\\invertedSearch\\src\\invertedSearch\\docIndex.txt";
		String wordIndex = "C:\\Users\\15850\\eclipse-workspace\\invertedSearch\\src\\invertedSearch\\wordIndex.txt";
		getFileIndex(filePath,docIndex);
		getWordsFrequency(docIndex,wordIndex);
		System.out.println("finished");
	}

	public static void getWordsFrequency(String docIndex, String wordIndex) throws IOException {
		//ͨ��docIndex�ļ��е������ҵ�ÿ���ļ��������ļ��е�����������ͳ��
		TreeMap<String,TreeMap<String,Set<Integer>>> tmp = new TreeMap<String,TreeMap<String,Set<Integer>>>();
		BufferedReader buffer = new BufferedReader(new FileReader(docIndex));
		BufferedWriter bufferW = new BufferedWriter(new FileWriter(wordIndex));
		BufferedReader bufrDoc = null;
		String docIDandPath = null;
		while((docIDandPath = buffer.readLine())!= null) {
			String[] docInfo = docIDandPath.split("\t");
			String docID = docInfo[0];
			String docPath = docInfo[1];
			int wordsCount = 0;
			bufrDoc = new BufferedReader(new FileReader(docPath));
			String wordLine = null;
			while((wordLine = bufrDoc.readLine()) != null) {
				String[] words = wordLine.split("\\W");
				for(String wordOfDoc : words) {
					if(!wordOfDoc.equals("")) {
						wordsCount++;
						//���ĵ�������ͳ�ƴ���
						wordDeal(wordOfDoc,docID,tmp,wordsCount);
					}
				}
			}
		}
		//�������Ľ��д��wordIndex.txt��
		String wordFreInfo = null;
		Set<Map.Entry<String,TreeMap<String,Set<Integer>>>> entrySet = tmp.entrySet();
		Iterator<Map.Entry<String,TreeMap<String,Set<Integer>>>> it = entrySet.iterator();
		while(it.hasNext()) {
			Map.Entry<String,TreeMap<String,Set<Integer>>> em = it.next();
            wordFreInfo =em.getKey()+"\t" + em.getValue()+"\t";
            bufferW.write(wordFreInfo);
            bufferW.newLine();
            bufferW.flush();
		}
		 bufferW.close();
	     buffer.close();
	     bufrDoc.close();
	}

	public static void wordDeal(String wordOfDoc, String docID, TreeMap<String, TreeMap<String, Set<Integer>>> tmp,Integer pos) {
		 wordOfDoc = wordOfDoc.toLowerCase();
	        if(!tmp.containsKey(wordOfDoc))
	        {   
	        	//�����ڵ�ǰ���������״γ��� 
	        	Set<Integer> positions = new HashSet<Integer>();
	        	//���λ�ü�¼
	        	positions.add(pos);
	        	//��¼�õ��ʳ��ֵ����ºźͳ����ڸ������е�λ��
	            TreeMap<String,Set<Integer>> tmpST = new TreeMap<String,Set<Integer>>();
	            
	            tmpST.put(docID, positions);
	            
	            //�������¼���ڸõ��ʶ�Ӧ��map����
	            
	            tmp.put(wordOfDoc,tmpST);
	        }        
	        else
	        {//������tmp���ѽ����ڻ�ȡ�õ����ڶ�ӦdocID�г��ִ����������״γ���
	         //count = null���򽫣�docID ,1)���뵽tmpST�У��������״γ��֣���count++���ٽ���Ϣ��д��tmpST�С�
	        
	         TreeMap<String,Set<Integer>> tmpST = tmp.get(wordOfDoc);
	         
	         if(tmpST.get(docID) == null) {
	        	 Set<Integer> poss = new HashSet<Integer>();
	        	 poss.add(pos);
	        	 tmpST.put(docID, poss);
	         }else {
	        	 //����λ�ü�¼
	        	 tmpST.get(docID).add(pos);
	         }
	        
	         tmp.put(wordOfDoc,tmpST);  //�����½����д��tmp��   
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
	
	
}
