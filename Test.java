import java.util.*;
import java.math.*;

class Test{

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

		//get the classes from the saved model
		ArrayList<Category> classes = handler.readModel("tempmodel");

		Document doc;
		Category cat;

		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> test = handler.readTestingFile("testing_set.txt");

		int i;
		for(i = 0 ; i < test.size() ; i++){
			doc = new Document(test.get(i));
			double prob, product, best = Double.NEGATIVE_INFINITY;
			int bestIndex = -1;
			Category temp;
			System.out.println("solving for: " + i);

			for(int k = 0 ; k < classes.size() ; k++){
				temp = classes.get(k);
				product = 0;

				for(int j = 0 ; j < vocab.size() ; j++){
					prob = temp.getWordProbability(j, doc.words[j]);
					product = Math.log(prob) + product;
				}

				product += Math.log(temp.getProbability());


				if(product > best){
					best = product;
					bestIndex = k;
				}

			}
		//====================================================

			result.add(classes.get(bestIndex).name);
		}

		int training = 0;
		handler.writeResult(training, result, "classification");
		ArrayList<String> index = handler.readClassification("index.txt");

		ArrayList<String> catNames = new ArrayList<String>();
		for(i = 0 ; i < classes.size() ; i++){
			catNames.add(classes.get(i).name);
		}

		String x, y;
		int[][] confMatrix = new int[classes.size()][classes.size()];
		int[] rowTotal = new int[classes.size()];
		
		for(i = 0 ; i < result.size() ; i++){
			x = result.get(i);
			y = index.get(i);
			confMatrix[catNames.indexOf(x)][catNames.indexOf(y)] += 1;
			rowTotal[catNames.indexOf(x)] += 1;
		}

		handler.writeConfusionMatrix(confMatrix, rowTotal, "confusion_matrix" + lambda + ".csv", catNames);
	}
}