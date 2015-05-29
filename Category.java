import java.util.*;

class Category{

	public static ArrayList<String> dictionary;	

	public String name;
	public double probability;
	public double[] wordProbability; 
	public int[] words;
	public int tweets;

	Category(String name){
		this.name 	= name;
		this.words  = new int[dictionary.size()];
		this.tweets  = 0;
	}

	Category(String[] info){
		this.name = info[0];
		this.probability = Double.parseDouble(info[1]);
		this.wordProbability = new double[dictionary.size()];

		for(int i = 2 ; i < info.length ; i++){
			wordProbability[i-2] = Double.parseDouble(info[i]);
		}
	}

	public static void setDictionary(ArrayList<String> dictionary){
		Category.dictionary = dictionary;
	}

	public void setProbability(int dataset, double lambda){
		this.probability = (double)count()/(double)dataset;
		this.wordProbability = new double[dictionary.size()];

		double count = 0.0; 

		for(int i = 0 ; i < words.length ; i++){
				count += words[i];
		}

		for(int i = 0 ; i < words.length ; i++){
			// System.out.println("(" + words[i] + " + " + lambda + ")/(" + count + " + " + dictionary.size() + "*" + lambda + ")");
			wordProbability[i] = ((double)words[i] + lambda)/(count + (double)dictionary.size()*lambda);
		}
	}

	public double getProbability(){
		return this.probability;
	}

	public double getWordProbability(int i, int occurrence){
			return Math.pow(wordProbability[i], (double)occurrence);
	}

	public void addTweets(String content){
		String[] temp = content.toLowerCase().split("\\W+");
		int index;

		for(int i = 0 ; i < temp.length ; i++){
			if(!temp[i].equals("")){
				index = dictionary.indexOf(temp[i]);

				if(index >= 0){
					words[index] += 1;
				}
			}
		}

		this.tweets += 1;
	}

	public int count(){
		return this.tweets;
	}
	
}