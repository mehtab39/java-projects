package javaProjects;
import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;
/** * This is the class works as a spell-checker. It uses the file words.txt to 
 * * check whether a given word is correctly spelled of it. */
public class SpellChecker {  
	
	public static void main(String[] args) { 
		HashSet<String> hash = new HashSet<String>();
		try {       
			// Reading the words.txt file.     
			Scanner filein = new Scanner(new File ("C:\\\\Users\\\\Dell\\\\Downloads\\\\words.txt"));  
			// Creating the new dictionary data structure                      
			      
	        
			while (filein.hasNext()) {           
				String tk = filein.next();        
				hash.add(tk.toLowerCase());        
			}  
			System.out.println("Size of HashSet : " + hash.size());    
			
			Scanner userFile = new Scanner(getInputFileNameFromUser()); 
			userFile.useDelimiter("[^a-zA-Z]+");         
			while (userFile.hasNext()){          
				String st = userFile.next().toLowerCase();    
				if(!hash.contains(st)){     
					System.out.println(st + ":" + corrections(st, hash));        
				}
				
			}     
		}       
		catch (IOException e) {      
			System.out.println("File not found - words.txt");    
		}   
		
	}
		
    
/**   
 *   * Lets the user select an input file using a standard file selection  
 *      * dialog box. If the user cancels the dialog without selecting a file,   
 *        * the return value is null.     *    
 *         * @return A file selected by the user, if any. Otherwise, null. 
 *           
 *             */  
	static File getInputFileNameFromUser() {    
		JFileChooser fileDialog = new JFileChooser();    
		fileDialog.setDialogTitle("Select File for Input");       
		int option = fileDialog.showOpenDialog(null);      
		if (option != JFileChooser.APPROVE_OPTION) { 
			System.out.println("not valid");
			return null;  
		}

		return fileDialog.getSelectedFile();

	} 
	static TreeSet<String> corrections(String badWord, HashSet<String> dictionary){  
		
		
		TreeSet<String> tree = new TreeSet<String>(); 
		
		//Delete any one of the letters from the misspelled word, then check if exist in the dictionary. 

		for (int i=0; i<badWord.length(); i++){         
			String s = badWord.substring(0,i) + badWord.substring(i+1);  	
		if(dictionary.contains(s)){  
				tree.add(s);           
			}      
		}       
		//Change any letter in the misspelled word to any other letter , then check if exist in the dictionary.     
		for (int i=0; i<badWord.length(); i++){    
			for (char ch = 'a'; ch <= 'z'; ch++) {     
				String s = badWord.substring(0,i) + ch + badWord.substring(i+1);  
				if(dictionary.contains(s)){                  
					tree.add(s);          
				}         
			}      
		}       
		//Insert any letter at any point in the misspelled word ,then check if exist in the dictionary.     
		for (int i=0; i<=badWord.length(); i++){   
			for (char ch = 'a'; ch <= 'z'; ch++) {          
				String s = badWord.substring(0,i) + ch + badWord.substring(i); 
				if(dictionary.contains(s)){                  
					tree.add(s);               
				}        
			}     
		}      
		//Swap any two neighboring characters in the misspelled word, then check if exist in the dictionary.     
		for(int i=0; i< badWord.length()-1; i++){       
			String s = badWord.substring(0,i)+ badWord.substring(i+1, i+2) + badWord.substring(i,i+1)+ badWord.substring(i+2);   
			if(dictionary.contains(s)){          
				tree.add(s);         
			}     
		}     
		//Insert a space at any point in the misspelled word (and check that      
		//both of the words that are produced are in the dictionary)  

		for (int i=1; i<badWord.length(); i++){           
			String stringInput = badWord.substring(0,i) + " " + badWord.substring(i);      
			String tempString = "";
			StringTokenizer tempWords = new StringTokenizer(stringInput);
			
			while(tempWords.hasMoreTokens()){   
				//Store each word in a temp string.     
				String stringWord1 = tempWords.nextToken();  
				String stringWord2 = tempWords.nextToken();    
				//Check if temp words exist in dictionary otherwise break      
				if(dictionary.contains(stringWord1) && dictionary.contains(stringWord2)){
					tempString = stringWord1 + " " + stringWord2;         
				}             
				else              
					break;          
				tree.add(tempString);   
			}       
		}        
		if(tree.isEmpty()){       
			tree.add("no suggestions");  
		}     
		return tree;   
	}

}

