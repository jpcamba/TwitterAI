import java.util.*;
import java.math.*;

class Main{
	
	public static void main(String args[]){

		double lambda = 1.0;

		if (args.length > 0){
			lambda = Double.parseDouble(args[0]);
		}

		IOHandler handler = new IOHandler();

		ArrayList<String> vocab = handler.readDictionary("stemmedWords");
		// ArrayList<String> vocab = handler.readDictionary("vocab2");
		Document.setDictionary(vocab);
		Category.setDictionary(vocab);

		ArrayList<String> index = handler.readMap("index");
		int training = (int)(Math.ceil(index.size()*0.6));

		ArrayList<Category> classes = handler.getCategories();
		ArrayList<String> catNames = handler.getCatNames();

		Document doc;
		Category cat;

		int i;
		for(i = 0 ; i < training ; i++){
			doc = handler.readFile("data/" + i);
			cat = classes.get(catNames.indexOf(index.get(i)));
			cat.addDocument(doc);
		}

		for(i = 0 ; i < classes.size() ; i++){
			classes.get(i).setProbability(training, lambda);
		}

		ArrayList<String> result = new ArrayList<String>();

		for(i = training ; i < index.size() ; i++){
			doc = handler.readFile("data/" + i);
			ArrayList<Integer> wi = doc.getWI();
			double prob, product, best = Double.NEGATIVE_INFINITY;
			int bestIndex = -1;
			Category temp;
			System.out.println("solving for: " + i);

			for(int k = 0 ; k < classes.size() ; k++){
				temp = classes.get(k);
				product = 0;

				for(int j = 0 ; j < vocab.size() ; j++){
					if(wi.contains(j)){
						prob = temp.getWordProbability(j, true);
					}
					else{
						prob = temp.getWordProbability(j, false);
					}
					product = Math.log(prob) + product;
				}
				product += Math.log(temp.getProbability());


				if(product > best){
					best = product;
					bestIndex = k;
				}				
			}

			result.add(classes.get(bestIndex).name);
		}

		handler.writeResult(training, result, "classification");
		
		int j = training;
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

		handler.writeConfusionMatrix(confMatrix, rowTotal, "confusion_matrix" + lambda + ".csv");
	}
}