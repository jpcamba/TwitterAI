import java.util.*;
import java.math.*;

class Train{

	// public static String[] filename = {"entertainment", "terrorism"};
	public static String[] filename = {"chad.txt", "david.txt"};
	
	public static void main(String args[]){

		double lambda = 1.0;

		if (args.length > 0){
			lambda = Double.parseDouble(args[0]);
		}

		IOHandler handler = new IOHandler();
		
		ArrayList<String> vocab = handler.readDictionary("vocabulary");

		//set the dictionary to be used
		Document.setDictionary(vocab);
		Category.setDictionary(vocab);

		ArrayList<Category> classes = new ArrayList<Category>();
		ArrayList<String> catNames = new ArrayList<String>();

		Document doc;
		Category cat;

		int i;
		for(i = 0 ; i < filename.length ; i++){
			//new category for each tweet archive
			cat = new Category(filename[i]);
			catNames.add(filename[i]);
			classes.add(cat);

			//read tweet archives for this category
			//one line is one tweet, so one file is one person
			handler.readTrainingFile(filename[i], cat);
		}


		int training = 0;
		for(i = 0 ; i < classes.size() ; i++){
			training += classes.get(i).count();
		}

		for(i = 0 ; i < classes.size() ; i++){
			classes.get(i).setProbability(training, lambda);
		}

		handler.writeModel(classes, "tempmodel");
	}
}