//Written by Patrick Alexis
//Written on August 5, 2016

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GettysburgParser {
	
	public static class Pair implements Comparable<Pair>
	{
		String str;
		int count;
		
		//when pair is being initialized, it has a count of 1
		public Pair(String str){
			this.str = str;
			this.count = 1;
		}
        
		//will be used to sort all pairs in descending order
		public int compareTo(Pair o) {
			return o.count - this.count;
		}
		
		public String toString(){
			return String.format("%-15s ==> %5d", str, count);
		}
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Document doc;
		try {

			// fetching url
			doc = Jsoup.connect("http://avalon.law.yale.edu/19th_century/gettyb.asp").get();
	
			// get paragraph
			Elements str = doc.select("p");
			String[] parse = str.text().replaceAll("^\"|\"$", "").replaceAll("\\--|\\-", " ").split("[,.\\s]+");
			
			HashMap<String, Pair> map = new HashMap<String, Pair>(); //hold all word and count pairings
			ArrayList<Pair> sortedList = new ArrayList<Pair>(); //will hold all pairs sorted
			
			//iterate through my string array
			for(String s : parse){
				if(map.containsKey(s.toLowerCase())){
					Pair pair = map.get(s.toLowerCase());
					pair.count += 1;
				}else{
					Pair pair = new Pair(s.toLowerCase());
					map.put(s.toLowerCase(), pair);
				}
			}
			
		  
           System.out.println("---------WORDS COUNT----------");
           
           for(Map.Entry<String, Pair> temp: map.entrySet()){
        	   System.out.println(temp.getValue());
        	   sortedList.add(temp.getValue()); //adding all pairs to arraylist
           }
           
           System.out.println();
           System.out.println("------TOP 10 FREQUENT WORDS--------");  
           
           Collections.sort(sortedList); //sort the list
           
           int counter = 0;
           for(Pair temp : sortedList){
        	   if(counter == 10){
        		   break;
        	   }
        	   System.out.println(temp);
        	   counter++;
           }
           
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
