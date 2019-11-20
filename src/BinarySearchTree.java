public class BinarySearchTree {
    private Node headParent;
    public int size;
    private int height; //never refer to this alone, always use height method

    public BinarySearchTree(){
        height=0;
        headParent=null;
        size=0;
    }
    private class Node {
        private int key;
        private Node left;
        private Node right;
        private int data;

        public Node(int key, int data, Node left, Node right){
            this.key=key;
            this.data=data;
            this.left=left;
            this.right=right;
        }
    }

    // Does this symbol table contain the given key?
    public boolean contains(int key){
        Node currentNode=headParent;
        if(currentNode.key==key){
            return true;
        }
        while (true) {
            if (key < currentNode.key && currentNode.left != null) {
                currentNode = currentNode.left;
            }
            else if (key < currentNode.key && currentNode.left == null) {
                return false;
            }
            if (key > currentNode.key && currentNode.right != null) {
                currentNode = currentNode.right;
            }
            else if (key > currentNode.key && currentNode.right == null) {
                return false;
            }
            if (currentNode.key == key) {
                return true;
            }
        }
    }
    public int height(){// Returns the height of the BST (for debugging)
        heighthelper(0, headParent);
        return height;
    }
    public void heighthelper(int currentheight, Node n){
        if(n==null){
            return;
        }
        currentheight++;
        if(n.left==null && n.right==null){
            if(currentheight>height){
                height=currentheight;
            }
        }
        heighthelper(currentheight, n.left);
        heighthelper(currentheight, n.right);
    }

    // Returns true if this symbol table is empty.
    public boolean isEmpty(){
        return(headParent==null);
    }

    // Returns the largest key in the symbol table.
    //change return value to Key
    public int max(){
        Node temp=headParent;
        while(temp.right!=null){
            temp=temp.right;
        }
        return temp.key;
    }

    // Returns the smallest key in the symbol table.
    //change return value to Key
    public int min(){
        Node temp=headParent;
        while(temp.left!=null){
            temp=temp.left;
        }
        return temp.key;
    }

    // traverse (prints out) the keys as levels, left to right.
    public void levelorder(){
        int height=height();
        for (int x=1; x<=height; x++) {
            levelorderHelper(headParent, x);
        }
    }
    public void levelorderHelper(Node n, int level){
        if(n==null) {
            return;
        }
        //level 1 is headParent
        if(level==1) {
            System.out.println(n.key);
        } else if (level > 1) {
            levelorderHelper(n.left, level-1);
            levelorderHelper(n.right, level-1);
        }
    }

    // traverse (prints out) the keys in inorder order.
    public void inorder(){
        inorderHelper(headParent);
    }
    public void inorderHelper(Node n){
        if(n==null)
            return;
        else if(n.left==null){
            System.out.println(n.key);
            inorderHelper(n.right);
        }
        else{
            inorderHelper(n.left);
            System.out.println(n.key);
            inorderHelper(n.right);
        }
    }

    // traverses (prints out) the keys in postorder order.
    public void postorder(){
        postorderHelper(headParent);
    }
    public void postorderHelper(Node n){
        if(n==null){
            return;
        }
        postorderHelper(n.left);
        postorderHelper(n.right);
        System.out.println(n.key);
    }

    // traverses (prints out) the keys in preorder order
    public void preorder(){
        preorderHelper(headParent);
    }
    public void preorderHelper(Node n){
        if(n==null) {
            return;
        }
        System.out.println(n.key);
        preorderHelper(n.left);
        preorderHelper(n.right);
    }

    // Inserts the specified key-value pair into the symbol table, overwriting the old value with the new value if the symbol table already contains the specified key.
    public void put(int key, int data){
        if(headParent==null){
            Node n=new Node(key, data, null, null);
            size++;
            headParent=n;
        }
        else{
            puthelper(headParent, data, key, null, 0);
        }
    }
    public void puthelper(Node node, int data, int key, Node parent, int direction){
        //direction =0 for left direction=1 for right
        if(node==null){
            Node n=new Node(key, data, null, null);
            if(direction==0){
                parent.left=n;
            }
            else {
                parent.right=n;
            }
            size++;
        }
        else if(key<node.key){
            puthelper(node.left, data, key, node, 0);
        }
        else if(key>node.key){
            puthelper(node.right, data, key, node, 1);
        }
        else if(key==node.key){
            node.data=data;
        }
    }

    // Returns the number of key-value pairs in this symbol table.
    public int size(){
        return size;
    }

    public void delete(int key){
        deletehelper(headParent,key);
    }
    public Node deletehelper(Node headParent, int key){
       //if empty
        if(headParent==null){
            return headParent;
        }
        //go down tree
        if(key>headParent.key){
            headParent.right=deletehelper(headParent.right, key);
        }
        else if(key<headParent.key){
            headParent.left =deletehelper(headParent.left, key);
        }
        //equals headParent, at the Node
        else{
            size--;
            //no children or one child
            if(headParent.left==null){
                return headParent.right;
            }
            else if(headParent.right==null){
                return headParent.left;
            }
            //get smallest in right
            int start=headParent.key;
            while(headParent.left!=null){
                start=headParent.left.key;
                headParent=headParent.left;
            }
            headParent.key=start;
            //delete before
            headParent.right=deletehelper(headParent.right, headParent.key);
        }
        return headParent;
    }

    // Unit tests the BST data type
    public static void main(String[] args){
        BinarySearchTree bst=new BinarySearchTree();
        bst.put(12,12);
        bst.put(8,8);
        bst.put(20,20);
        bst.put(6,6);
        bst.put(15,15);
        bst.put(30,30);
        bst.put(3,3);
        bst.put(7,7);
        bst.put(20,20);
        System.out.println("Level Order:");
        bst.levelorder();
        System.out.println("Level order after removing 20 and adding 1");
        bst.delete(20);
        bst.put(1,1);
        bst.levelorder();
        System.out.println("In Order:");
        bst.inorder();
        System.out.println("Pre Order:");
        bst.preorder();
        System.out.println("Post Order:");
        bst.postorder();
    }
}