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

		int i;


		handler.writeModel(classes, "model");
		
		//=======================================================
		// System.exit(0);

		ArrayList<String> result = new ArrayList<String>();
		ArrayList<String> test = handler.readTestingFile("csdataset.txt");


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
		
/*		int j = training;
		String x, y;
		int[][] confMatrix = new int[classes.size()][classes.size()];
		int[] rowTotal = new int[classes.size()];
		
		for(i = 0 ; i < result.size() ; i++){
			x = result.get(i);
			y = index.get(j);
			confMatrix[catNames.indexOf(x)][catNames.indexOf(y)] += 1;
			rowTotal[catNames.indexOf(x)] += 1;
			j++;
		}

		handler.writeConfusionMatrix(confMatrix, rowTotal, "confusion_matrix" + lambda + ".csv");*/
	}
}