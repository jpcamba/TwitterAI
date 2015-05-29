import java.io.*;
import java.util.*;

public class IOHandler{

	ArrayList<String> map;
	ArrayList<String> dictionary;
	ArrayList<String> catNames;
	ArrayList<Category> categories;

	Hashtable<String, Category> hash;

	IOHandler(){
		map = new ArrayList<String>();
		dictionary = new ArrayList<String>();
		catNames = new ArrayList<String>();
		categories = new ArrayList<Category>();
	}

	public ArrayList<String> readDictionary(String filename){

		String info;
		File file = new File(filename);
				
		try{
			FileReader fr = new FileReader(file);
			BufferedReader buff = new BufferedReader(fr);
				
			while(true){
				info = buff.readLine();
					
				if(info == null){
					break;
				}

				dictionary.add(info);
			}
			
			buff.close();

		}catch(IOException e){
			System.out.println("Something bad happened :(\n");
			System.exit(1);
		}

		return dictionary;
	}

	public void readTrainingFile(String filename, Category category){

		File file = new File(filename);
		Document doc;
		String info;
				
		try{
			FileReader fr = new FileReader(file);
			BufferedReader buff = new BufferedReader(fr);
				
			while(true){
				info = buff.readLine();
					
				if(info == null){
					break;
				}

				// doc = new Document(info, cat);
				category.addTweets(info);
				// category.incrementTweets();
				// category.addDocument(doc);
			}
			
			buff.close();

		}catch(IOException e){
			System.out.println("Something bad happened :(\n");
			System.exit(1);
		}
	}

	public ArrayList<String> readTestingFile(String filename){

		ArrayList<String> list = new ArrayList<String>();
		File file = new File(filename);
		// Document doc;
		String info;
				
		try{
			FileReader fr = new FileReader(file);
			BufferedReader buff = new BufferedReader(fr);
				
			while(true){
				info = buff.readLine();
					
				if(info == null){
					break;
				}

				//one line is one tweet
				// doc = new Document(info);
				list.add(info);
				// category.addDocument(doc);
			}
			
			buff.close();

		}catch(IOException e){
			System.out.println("Something bad happened :(\n");
			System.exit(1);
		}

		return list;
	}

	public ArrayList<Category> readModel(String filename){
		
		ArrayList<Category> retVal = new ArrayList<Category>();
		File file = new File(filename);
		Category cat;
		String info;
				
		try{
			FileReader fr = new FileReader(file);
			BufferedReader buff = new BufferedReader(fr);
				
			while(true){
				info = buff.readLine();
					
				if(info == null){
					break;
				}

				cat = new Category(info.split(","));
				retVal.add(cat);
			}
			
			buff.close();

		}catch(IOException e){
			System.out.println("Something bad happened :(\n");
			System.exit(1);
		}

		return retVal;
	}

	public void writeModel(ArrayList<Category> cat, String filename){
		File file = new File(filename);
		Category temp;

		try{
			
			FileWriter fw = new FileWriter (file);
			BufferedWriter buff = new BufferedWriter(fw);
			PrintWriter print = new PrintWriter(buff);
			
			for(int i = 0 ; i < cat.size() ; i++){
				temp = cat.get(i);
				//comma lang, don't put space
				print.print(temp.name + "," + temp.getProbability());
				for(int j = 0 ; j < dictionary.size() ; j++){
					print.print("," + temp.getWordProbability(j, 1));
				}
				print.println("");
				// print.println((start + i) + " " + list.get(i));
			}

			print.close();
			
		}catch(IOException e){}
	}

	//serializable
/*	public void writeModelBin(ArrayList<Category> cat){
		// File file = new File("tempmodel");
		Category temp;

		try{
			FileOutputStream fout = new FileOutputStream("model");
			ObjectOutputStream oos = new ObjectOutputStream(fout);   
			
			for(int i = 0 ; i < cat.size() ; i++){
				temp = cat.get(i);
				oos.writeObject(temp);
			}

			oos.close();
			
		}catch(IOException e){}
	}

	public ArrayList<Category> readModelBin(){
		
		ArrayList<Category> cat;
		Category temp;

		try{
			FileInputStream fin = new FileInputStream("c:\\address.ser");
	   	ObjectInputStream ois = new ObjectInputStream(fin);
		  

		  while()
		  temp = (Category) ois.readObject();
		  cat.add(temp);

		  ois.close();
			
		}catch(IOException e){}
	}*/

	public void writeResult(int start, ArrayList<String> list, String filename){
		File file = new File(filename);
		
		try{
			
			FileWriter fw = new FileWriter (file);
			BufferedWriter buff = new BufferedWriter(fw);
			PrintWriter print = new PrintWriter(buff);
			
			for(int i = 0 ; i < list.size() ; i++){
				print.println((start + i) + " " + list.get(i));
			}

			print.close();
			
		}catch(IOException e){}
	}

	public void writeConfusionMatrix(int[][] confMatrix, int[] rowTotal, String filename){
		File file = new File(filename);

		try{
			
			FileWriter fw = new FileWriter (file);
			BufferedWriter buff = new BufferedWriter(fw);
			PrintWriter print = new PrintWriter(buff);
			
			print.print("Categories, ");
			for(int i = 0 ; i < catNames.size() ; i++){
				print.print("," + catNames.get(i));
			}

			print.println("");

			for(int i = 0 ; i < hash.size() ; i++){
				print.print(catNames.get(i) + ",");
				for(int j = 0 ; j < hash.size() ; j++){
					print.print("," + confMatrix[i][j]);
				}
				print.println();
			}

			print.print("\n\nCategories, ");
			for(int i = 0 ; i < catNames.size() ; i++){
				print.print("," + catNames.get(i));
			}

			print.println("");


			for(int i = 0 ; i < hash.size() ; i++){
				print.print(catNames.get(i) + ",");
				for(int j = 0 ; j < hash.size() ; j++){
					double x = (double)confMatrix[i][j]/(double)rowTotal[i];
					print.print("," + x);
				}

				print.println();
			}

			print.close();
			
		}catch(IOException e){}
	}

}