import java.util.*;

class Document{

	public static ArrayList<String> dictionary;	
	public int[] words;

	Document(String content){		
		this.words  = new int[dictionary.size()];
		add(content);
	}

	public static void setDictionary(ArrayList<String> dictionary){
		Document.dictionary = dictionary;
	}

	public void add(String content){
		String[] temp = content.toLowerCase().split("\\W+");
		int index;

		for(int i = 0 ; i < temp.length ; i++){
			if(!temp[i].equals("")){
				// index = indexOfStem(temp[i]);
				index = dictionary.indexOf(temp[i]);

				if(index >= 0){
					words[index] += 1;
				}
			}
		}
	}
}