import java.util.*;

class Category{

	public static ArrayList<String> dictionary;	

	public String name;
	public ArrayList<Document> docs;
	public double probability;
	public double[] wordProbability; 
	public int[] words;

	Category(String name){
		this.name 	= name;
		this.docs 	= new ArrayList<Document>();
	}

	public static void setDictionary(ArrayList<String> dictionary){
		Category.dictionary = dictionary;
	}

	public void setProbability(int dataset, double lambda){
		this.probability = (double)count()/(double)dataset;
		this.wordProbability = new double[dictionary.size()];

		double count = 0.0; //(double)count();

		int exwords;

		for(int i = 0 ; i < words.length ; i++){
			if(words[i] > 0){
				count += 1.0;
			}
		}

		for(int i = 0 ; i < words.length ; i++){
			wordProbability[i] = ((double)words[i] + lambda)/(count + 2.0*lambda);
		}
	}

	public double getProbability(){
		return this.probability;
	}

	public double getWordProbability(int i, boolean exists){
		
		if(exists){
			return wordProbability[i];
		}
		else{
			return 1.0 - wordProbability[i];
		}
	}

	public void addDocument(Document file){
		
		ArrayList<Integer> wi = file.getWI();

		if(words == null){
			words = new int[dictionary.size()];
		}

		for(int i = 0 ; i < wi.size() ; i++){
			words[wi.get(i)] += 1;
		}

		docs.add(file);
	}

	public int count(){
		return docs.size();
	}
	
}