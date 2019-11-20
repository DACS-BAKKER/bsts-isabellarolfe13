import java.io.*;
import java.util.*;
import java.util.Scanner;
public class TwentyOneQuestions{

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

    public static void main(String[] args) {
        System.out.println("Welcome to a question game! I will ask you a series of questions and will do my best to try and guess what you are thinking of!");
        BinaryQuestionTree bqt;
        System.out.println("Would you like me to remember my previous memory state?");
        Scanner scan = new Scanner(System.in);
        boolean remember;
        if(scan.nextLine().equals("yes")){
            remember=true;
        }
        else {
            remember = false;
        }
        if(remember) {
            bqt=readFromFile();
        }
        else{
            //rubric for start
            bqt = new BinaryQuestionTree("mammal", "human", "fish");
        }
        boolean playagain=true;
        while(playagain) {
            while (!bqt.atLeaf()) {
                System.out.println(bqt.get() + "?");
                boolean yes = scan.nextLine().equals("yes");
                bqt.respondToQuestion(yes);
            }
            System.out.println(bqt.get() + "?");
            boolean yes = scan.nextLine().equals("yes");
            if (yes) {
                System.out.println("great! game over!");
                bqt.reset();
            } else {
                System.out.println("what were you thinking of?");
                String answer = scan.nextLine();
                System.out.println("what is a question that highlights the difference?");
                String diff = scan.nextLine();
                bqt.addToTree(answer, diff);
            }
            System.out.println("would you like to play again");
            playagain=scan.nextLine().equals("yes");
        }
        savetoFile(bqt);
    }
}
