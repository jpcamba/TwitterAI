import java.util.*;
import java.math.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class TwitterAI{

	public static void main(String args[]){
		
		if (args.length > 0){
			lambda = Double.parseDouble(args[0]);
		}

		TwitterAI t = new TwitterAI();
	}

	private JFrame frame = new JFrame("Sinong nag-tweet?");	
	private JTextArea tweetbox = new JTextArea();
	private JScrollPane scroll, scroll2;	
	private JTextArea typebox = new JTextArea();
	private JButton check = new JButton();		
	private JPanel tweetcon = new JPanel();
	private JPanel typecon = new JPanel();
	private JPanel checkcon = new JPanel();
	private Font font;
	
	private static double lambda;
	private ArrayList<Category> classes;
	private ArrayList<String> vocab;

	TwitterAI(){
		lambda = 1.0;
		initializeModel();
		initializeGUI();
	}

	public void initializeModel(){
		IOHandler handler = new IOHandler();

		vocab = handler.readDictionary("vocabulary");

		//set the dictionary to be used
		Document.setDictionary(vocab);
		Category.setDictionary(vocab);

		//get the classes from the saved model
		classes = handler.readModel("tempmodel");
	}

	public void initializeGUI(){
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 550);
		frame.setLayout(new FlowLayout());      
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.WHITE);
		
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("nfont.otf"));
			font = font.deriveFont(18F);
		}
		
		catch (Exception e) {
		}

		Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
		
		tweetbox.setOpaque(true);
		tweetbox.setEditable(false);
		tweetbox.setFont(font);
		scroll = new JScrollPane(tweetbox);
		scroll.setPreferredSize(new Dimension(730, 350));
		scroll.setBorder(border);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		typebox.setOpaque(true);
		typebox.setFont(font);
		scroll2 = new JScrollPane(typebox);
		scroll2.setBorder(border);
		scroll2.setPreferredSize(new Dimension(730, 70));
		
		check.setText("Check");
		check.setFont(font);
		check.setSize(50, 55);
		check.addActionListener(new ButtonListener());
		
		tweetcon.add(scroll);
		typecon.add(scroll2);		
		checkcon.add(check);		
		tweetcon.setOpaque(false);		
		typecon.setOpaque(false);		
		checkcon.setOpaque(false);		
		
		tweetcon.setPreferredSize(new Dimension(750, 370));
		typecon.setPreferredSize(new Dimension(750, 80));
		checkcon.setPreferredSize(new Dimension(750, 60));
		
		frame.add(tweetcon);
		frame.add(typecon);
		frame.add(checkcon);
	}

	public void checkTweet(String tweet){

		Document doc = new Document(tweet);

		double prob, product, best = Double.NEGATIVE_INFINITY;
		int bestIndex = -1;
		Category temp;

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

		// JTextArea content = new JTextArea(2, 33);
		// content.setLineWrap(true);
		tweetbox.append(tweet);
		tweetbox.append("\n");
		tweetbox.append("result: " + classes.get(bestIndex).name);
		tweetbox.append("\n\n");
		// tweetbox.add(content);

		typebox.setText("");
		// System.out.println(classes.get(bestIndex).name);
			// result.add(classes.get(bestIndex).name);
	}


	protected class ButtonListener implements ActionListener{
		
		public void actionPerformed(ActionEvent event){
			try{
				checkTweet(typebox.getText());
			}catch(Exception e){
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}

	}
}