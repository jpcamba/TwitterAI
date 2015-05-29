import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

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

	public ArrayList<String> getCatNames(){
		return this.catNames;
	}

	public ArrayList<Category> getCategories(){
		return this.categories;
	}

	public ArrayList<String> readMap(String filename){
		String info;
		File file = new File(filename);
		hash = new Hashtable<String, Category>();		

		try{
			FileReader fr = new FileReader(file);
			BufferedReader buff = new BufferedReader(fr);
				
			while(true){
				info = buff.readLine();
					
				if(info == null){
					break;
				}

				info = info.split(" ", 2)[1]; 
				if(!catNames.contains(info)){
					catNames.add(info);
					categories.add(new Category(info));
					hash.put(info, new Category(info));
				}

				map.add(info);
			}
			
			buff.close();
		}catch(IOException e){
			System.out.println("Something bad happened :(\n");
			System.exit(1);
		}


		return map;
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

	public Document readFile(String filename){

		Document doc = new Document();
		File file = new File(filename);
		String info;
				
		try{
			FileReader fr = new FileReader(file);
			BufferedReader buff = new BufferedReader(fr);
				
			while(true){
				info = buff.readLine();
					
				if(info == null){
					break;
				}
				doc.add(info);
			}
			
			buff.close();

		}catch(IOException e){
			System.out.println("Something bad happened :(\n");
			System.exit(1);
		}

		return doc;
	}

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



	// for changedic
	public void writeDict(ArrayList<String> list, String filename){
		File file = new File(filename);
		
		try{
			
			FileWriter fw = new FileWriter (file);
			BufferedWriter buff = new BufferedWriter(fw);
			PrintWriter print = new PrintWriter(buff);
			
			for(int i = 0 ; i < list.size() ; i++){
				print.println(list.get(i));
			}

			print.close();
			
		}catch(IOException e){}
	}

	//for changedic
	public ArrayList<String> buildDictionary(int n){

		// Document doc = new Document();
		ArrayList<String> dict = new ArrayList<String>();

		for(int i = 0 ; i < n ; i++){
			File file = new File("data/" + i);
			String info;
					
			try{
				FileReader fr = new FileReader(file);
				BufferedReader buff = new BufferedReader(fr);
					
				while(true){
					info = buff.readLine();
						
					if(info == null){
						break;
					}
					String[] temp = info.toLowerCase().split("[^a-zA-Z]");

					for(int j = 0 ; j < temp.length ; j++){
						if(!temp[j].equals("")){
							int index = dict.indexOf(temp[j]);

							if(index < 0){
								dict.add(temp[j]);
							}
						}
					}
								// doc.add(info);
				}
				
				buff.close();

			}catch(IOException e){
				System.out.println("Something bad happened :(\n");
				System.exit(1);
			}
		}
		

		return dict;
	}
}