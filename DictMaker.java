import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.util.*;

public class DictMaker{

	public static void main(String args[]){
		DictMaker dm = new DictMaker();
		dm.readFrom("david.txt");
		dm.readFrom("chad.txt");
		dm.writeDictionary();
	}

	HashSet<String> hash;

	DictMaker(){
		hash = new HashSet<String>();
	}

	public void readFrom(String filename){
		String info;
		String words[];
		File file = new File(filename);

		try{
			FileReader fr = new FileReader(file);
			BufferedReader buff = new BufferedReader(fr);
				
			while(true){
				info = buff.readLine();
					
				if(info == null){
					break;
				}

				words = info.toLowerCase().split("\\W+"); 
				
				for(int i = 0 ; i < words.length ; i++){
					info = words[i];

					if(info.length() < 2){
						continue;
					}
					else if(!hash.contains(info)){
						hash.add(info);
					}	
				}					
			}
			
			buff.close();
		}catch(IOException e){
			System.out.println("Something bad happened :(\n");
			System.exit(1);
		}
	}

	
	public void writeDictionary(){
		File file = new File("vocabulary");
		String list[] = new String[hash.size()];
		list = hash.toArray(list);
		// ArrayList list = new ArrayList(hash.toArray());

		try{
			
			FileWriter fw = new FileWriter (file);
			BufferedWriter buff = new BufferedWriter(fw);
			PrintWriter print = new PrintWriter(buff);
			
			for(int i = 0 ; i < list.length ; i++){
				print.println(list[i]);
			}

			print.close();
			
		}catch(IOException e){}
	}
}