import java.util.*;
import java.math.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.DefaultCaret;

class TwitterAI{

	public static void main(String args[]){
		
		if (args.length > 0){
			lambda = Double.parseDouble(args[0]);
		}

		TwitterAI t = new TwitterAI();
	}

	private JFrame frame;
	private JTextArea typebox;
	private JTextArea tweetbox;
	private JButton check;

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

	private JScrollPane scroll;

	public void initializeGUI(){
		frame = new JFrame("Sinong nag-tweet?");

		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(622, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		typebox = new JTextArea(4, 34);
		// JTextArea chatbox = new JTextArea(2, 33);
		// chatbox.append("no tweets to show");

		tweetbox = new JTextArea(18, 33);
		tweetbox.setLocation(10, 10);
		tweetbox.setVisible(true);
		tweetbox.setEditable(false);
    DefaultCaret caret = (DefaultCaret)tweetbox.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);


		scroll = new JScrollPane(tweetbox);
		scroll.setSize(390, 250);
    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel panel = new JPanel();
		JPanel right = new JPanel();

		check = new JButton("Check");
		check.setPreferredSize(new Dimension(180, 45));
		check.addActionListener(new ButtonListener());

		panel.setSize(390, 350);
		panel.setLocation(10, 10);
		panel.add(scroll);
		panel.add(typebox);
		panel.setVisible(true);


		right.setSize(230, 350);
		right.setLocation(390, 10);
		right.add(check);
		right.setVisible(true);

		frame.add(panel);
		frame.add(right);
		frame.setVisible(true);
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