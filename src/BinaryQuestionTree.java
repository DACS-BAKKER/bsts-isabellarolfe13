import java.io.Serializable;

public class BinaryQuestionTree implements Serializable {
    private static final long serialVersionUID = -8061645729607315972L;
    private Node root;
    private Node current;

    public class Node implements Serializable{
        private static final long serialVersionUID = -8360645723607315972L;
        public String data;
        public Node left;
        public Node right;
        public Node(String data, Node left, Node right){
            this.data=data;
            this.left=left;
            this.right=right;
        }
    }
    public BinaryQuestionTree(String question, String yesanswer, String noanswer){
        Node yesNode=new Node(yesanswer,null, null);
        Node noNode=new Node(noanswer, null, null);
        root=new Node(question, yesNode, noNode);
        current=root;
    }
    public boolean atLeaf() {
        return (current.left == null);
    }

    public String get(){
        return current.data;
    }

    public void respondToQuestion(boolean yes){
        if(yes){
            current=current.left;
        }
        else{
            current=current.right;
        }
    }

    public void addToTree(String correctanswer, String question){
        if(!atLeaf()){
            throw new IllegalStateException();
        }
        Node yesNode=new Node(correctanswer, null, null);
        Node noNode=new Node(current.data,null, null);
        current.data=question;
        current.left=yesNode;
        current.right=noNode;
        reset();
    }

    //reset current back to root
    public void reset(){
        current=root;
    }
}
