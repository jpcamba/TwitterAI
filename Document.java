import java.util.*;

class Document{

	public static ArrayList<String> dictionary;	
	public ArrayList<Integer> wordIndices;
	public boolean[] featureVector;

	Document(){		
		wordIndices = new ArrayList<Integer>();
	}

	public static void setDictionary(ArrayList<String> dictionary){
		Document.dictionary = dictionary;
	}

	public boolean[] getFeatureVector(){
		return this.featureVector;
	}

	public ArrayList<Integer> getWI(){
		return this.wordIndices;
	}

	public void add(String content){
		String[] temp = content.toLowerCase().split("[^a-zA-Z]");
		int index;

		for(int i = 0 ; i < temp.length ; i++){
			if(!temp[i].equals("")){
				index = indexOfStem(temp[i]);
				// index = dictionary.indexOf(temp[i]);

				if(index >= 0 && wordIndices.indexOf(index) == -1){
					wordIndices.add(index);
				}
			}
		}
	}

	public int indexOfStem(String word){

		int index;
		String stem;

		if(dictionary.contains(word)){
			return dictionary.indexOf(word);
		}

		if(word.endsWith("ing")){

			index = word.lastIndexOf("ing");
			stem = word.substring(0, index);
			
			if(dictionary.contains(stem)){
				return dictionary.indexOf(stem);
			}

			stem = stem + "e";
			
			if(dictionary.contains(stem)){
				return dictionary.indexOf(stem);
			}

			return dictionary.indexOf(word);
		}
		
		if(word.endsWith("d")){
			index = word.lastIndexOf("d");
			stem = word.substring(0, index);
			
			if(dictionary.contains(stem)){
				return dictionary.indexOf(stem);
			}
		}

		if(word.endsWith("ed")){
			index = word.lastIndexOf("ed");
			stem = word.substring(0, index);
			
			if(dictionary.contains(stem)){
				return dictionary.indexOf(stem);
			}
		}

		if(word.endsWith("ied")){
				index = word.lastIndexOf("ied");
				stem = word.substring(0, index) + "y";
				
				if(dictionary.contains(stem)){
					return dictionary.indexOf(stem);
				}
				return -1;
			}

		if(word.endsWith("s")){
			index = word.lastIndexOf("s");
			stem = word.substring(0, index);
			
			if(dictionary.contains(stem)){
				return dictionary.indexOf(stem);
			}
		}

		if(word.endsWith("es")){
			index = word.lastIndexOf("es");
			stem = word.substring(0, index);
			
			if(dictionary.contains(stem)){
				return dictionary.indexOf(stem);
			}
		}

		if(word.endsWith("ies")){
			index = word.lastIndexOf("ies");
			stem = word.substring(0, index) + "y";
			
			if(dictionary.contains(stem)){
				return dictionary.indexOf(stem);
			}
		}

		return -1;
	}
}