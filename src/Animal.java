import java.awt.EventQueue;
import java.awt.event.*;


import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JSpinner;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import java.awt.Canvas;
import javax.swing.JEditorPane;
import javax.swing.DropMode;
import java.awt.SystemColor;


public class Animal {

	private JFrame frame;
	private JTextField usertextField;
	private BinaryQuestionTree bqt;
	private static boolean checked=false;
	private static boolean newgame=false;
	private static String text="";
	private static JLabel lblmain = new JLabel("");
	private boolean waitingForIsCorrect=false;
	private boolean waitingForActualAnswer=false;
	private boolean waitingForDiffQuestion=false;
	private String answer;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Animal window = new Animal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void savetoFile(BinaryQuestionTree bqt){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saves.ser"));
			out.writeObject(bqt);
			out.close();
		}
		catch(IOException e){
			System.out.println(e);
		}
	}
	public static BinaryQuestionTree readFromFile(){
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream("saves.ser"));
			BinaryQuestionTree bqt = (BinaryQuestionTree) in.readObject();
			in.close();
			return bqt;
		}
		catch(IOException | ClassNotFoundException e){
			System.out.println(e);
		}
		return null;
	}


	/**
	 * Create the application.
	 */
	public Animal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void Respond() {
		if(bqt.atLeaf()) {
			if(waitingForIsCorrect) {
				if(usertextField.getText().equals("yes")){
					bqt.reset();
					waitingForIsCorrect=false;
					waitingForActualAnswer=false;
					waitingForDiffQuestion=false;
				}
				else {
					lblmain.setText("What is it?");
					waitingForIsCorrect=false;
					waitingForActualAnswer=true;
				}
			}
			else if(waitingForActualAnswer) {
				answer=usertextField.getText();
				waitingForActualAnswer=false;
				lblmain.setText("What is the difference?");
				waitingForDiffQuestion=true;
			}
			else if(waitingForDiffQuestion){
				bqt.addToTree(answer, usertextField.getText());
				waitingForActualAnswer=false;
				waitingForIsCorrect=false;
				waitingForDiffQuestion=false;
				bqt.reset();
			}
			else {
				lblmain.setText("is this correct?");
				waitingForIsCorrect=true;
			}
		}
		else {
			boolean yes;
			if(usertextField.equals("yes")) {
				yes=true;
			}
			else {
				yes=false;
			}
			bqt.respondToQuestion(yes);
			lblmain.setText(bqt.get());
		}

	}

	private void initialize() {

		frame = new JFrame();
		frame.getContentPane().setIgnoreRepaint(true);
		frame.getContentPane().setBackground(SystemColor.textHighlight);
		frame.getContentPane().setForeground(SystemColor.textHighlight);
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel lblIsabellasQuestions = new JLabel("Isabella's 21 Questions");
		lblIsabellasQuestions.setBounds(96, -15, 394, 82);
		lblIsabellasQuestions.setFont(new Font("Al Bayan", Font.PLAIN, 32));
		lblIsabellasQuestions.setBackground(Color.GREEN);
		lblIsabellasQuestions.setHorizontalAlignment(SwingConstants.CENTER);
		lblIsabellasQuestions.setForeground(SystemColor.menuText);
		frame.getContentPane().setLayout(null);

		JCheckBox chckbxRecallPreviousMemory = new JCheckBox("recall previous memory state?");
		chckbxRecallPreviousMemory.setBounds(156, 26, 236, 71);
		chckbxRecallPreviousMemory.setForeground(new Color(102, 0, 255));
		frame.getContentPane().add(chckbxRecallPreviousMemory);
		frame.getContentPane().add(lblIsabellasQuestions);

		chckbxRecallPreviousMemory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Test");
				checked = !checked;
			}
		});

		usertextField = new JTextField();
		usertextField.setBounds(6, 120, 144, 71);
		frame.getContentPane().add(usertextField);
		usertextField.setColumns(10);
		usertextField.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				text = usertextField.getText();
				System.out.println(text);
				Respond();
			}});

		lblmain.setForeground(new Color(0, 0, 205));
		lblmain.setBounds(363, 93, 110, 71);
		frame.getContentPane().add(lblmain);

		JLabel label = new JLabel("");
		Image img=new ImageIcon(this.getClass().getResource("new-icon.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(250, 155, 322, 217);
		frame.getContentPane().add(label);


		JLabel label_1 = new JLabel("");
		label_1.setBounds(352, 65, 160, 139);
		Image img2=new ImageIcon(this.getClass().getResource("Bubble-icon.png")).getImage();
		label_1.setIcon(new ImageIcon(img2));
		frame.getContentPane().add(label_1);

		JButton btnNewGame_1 = new JButton("New Game");
		btnNewGame_1.setForeground(Color.MAGENTA);
		btnNewGame_1.setBackground(Color.PINK);
		btnNewGame_1.setBounds(6, 261, 144, 46);
		frame.getContentPane().add(btnNewGame_1);

		if(checked) {
			bqt=readFromFile();
		}
		else{
			bqt = new BinaryQuestionTree("mammal", "cat", "goldfish");
		}

		btnNewGame_1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				System.out.println("testing new game");
				newgame=!newgame;
				bqt.reset();
			} 
		} );


		JButton btnStats = new JButton("Stats");
		btnStats.setForeground(Color.MAGENTA);
		btnStats.setBackground(Color.PINK);
		btnStats.setBounds(6, 203, 144, 46);
		frame.getContentPane().add(btnStats);

		JLabel lblTypeBelow = new JLabel("Type below!");
		lblTypeBelow.setBackground(SystemColor.controlHighlight);
		lblTypeBelow.setBounds(28, 81, 88, 27);
		frame.getContentPane().add(lblTypeBelow);
		btnStats.addActionListener(new ActionListener() { 

			public void actionPerformed(ActionEvent e) { 
				System.out.println("testing stats");
				newgame=!newgame;
			} 
		} );

		Image img3=new ImageIcon(this.getClass().getResource("Document-Blank-icon.png")).getImage();
	}
}
