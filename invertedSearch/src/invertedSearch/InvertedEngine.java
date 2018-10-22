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
		//通过docIndex文件中的内容找到每个文件，并将文件中的内容做单词统计
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
						//对文档内容作统计处理
						wordDeal(wordOfDoc,docID,tmp,wordsCount);
					}
				}
			}
		}
		//将吹李后的结果写入wordIndex.txt中
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
	        	//单词在当前文章中是首次出现 
	        	Set<Integer> positions = new HashSet<Integer>();
	        	//添加位置记录
	        	positions.add(pos);
	        	//记录该单词出现的文章号和出现在该文章中的位置
	            TreeMap<String,Set<Integer>> tmpST = new TreeMap<String,Set<Integer>>();
	            
	            tmpST.put(docID, positions);
	            
	            //将该项记录放在该单词对应的map项中
	            
	            tmp.put(wordOfDoc,tmpST);
	        }        
	        else
	        {//单词在tmp中已近存在获取该单词在对应docID中出现次数，若是首次出现
	         //count = null，则将（docID ,1)加入到tmpST中；若不是首次出现，则将count++后，再将信息回写到tmpST中。
	        
	         TreeMap<String,Set<Integer>> tmpST = tmp.get(wordOfDoc);
	         
	         if(tmpST.get(docID) == null) {
	        	 Set<Integer> poss = new HashSet<Integer>();
	        	 poss.add(pos);
	        	 tmpST.put(docID, poss);
	         }else {
	        	 //增加位置记录
	        	 tmpST.get(docID).add(pos);
	         }
	        
	         tmp.put(wordOfDoc,tmpST);  //将最新结果回写到tmp中   
	        }
	 }
		

	//通过传入的filePath找到文件所在，并将该文件下所有文件信息写到docIndex.txt中	
	public static void getFileIndex(String filePath, String docIndex) throws IOException{
		File file = new File(filePath);
		
		File[] fileList = file.listFiles();
		
		BufferedWriter buffer = null;
		//将filePath下所有文件路径写入docIndex文件中
		buffer = new BufferedWriter(new FileWriter(docIndex));
		
		for(int i = 0; i < fileList.length; i++) {
			
			String docPath = fileList[i].getAbsolutePath();
			
			buffer.write("DocID_"+i+"\t"+docPath);
			
			buffer.newLine();
			
			buffer.flush();
		}    
	}
	
	
}
