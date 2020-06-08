import java.util.Arrays;

public class FibHeap {

    private HeapNode root;                  //Declaration of root element which points to max always
    private int numNodes;
    int nodesAtTop = 0;
    int n = 0;


    public FibHeap(){                       //Fibheap Constructor 
        numNodes = 0;
    }   

    public HeapNode insertNewNode(String s, int count){     //Node defaults assigned here 
        HeapNode node = new HeapNode(s,count);
        return insertNode(node, false);

    }

    public HeapNode insertNode(HeapNode node, boolean existing) {
                                                    
        if (!existing) numNodes++;                          //if new node, increment num of nodes

                                                            //when heap is empty
        if (root == null){
            root = node;
            return node;
        }

        node.setChildCut(false);                            //All other cases insertion
        node.setLeft(root);
        node.setRight(root.getRight());
        root.getRight().setLeft(node);
        root.setRight(node);

        if (node.getValue() > root.getValue())              //max updated
            root = node;

        return node;

    }

    public void increaseCount(HeapNode node, int val){
        node.setValue(node.getValue() + val);               //Increaseing the node value by a factor

        if (node == root) return;

        HeapNode parent = node.getParent();
        if (parent != null){
            if (parent.getValue() >= node.getValue()) return; 
            performCascadeCut(node, true);               //if node data > child data perform cascadecut and remove node and 
        } else{                                             //insert back into heap
            removeNode(node);
            insertNode(node, true);
        }
    }

    public HeapNode removeMaxNode(){
        if (root == null) return null;                  //when root empty case

        HeapNode node = root;
        HeapNode rootRight = null;
        if (node.getRight() != node) rootRight = root.getRight();
        HeapNode child = node.getChild();

        removeNode(node);                           //removing the node from the heap 1 child
        node.setChild(null);
        node.setDegree(0);
        root = rootRight;

        if (child != null) {                            //more than one child exists for parent 

            HeapNode temp = child;
            HeapNode tempRight = temp;

            while (temp != temp.getRight()){         //removing node from child and updating its parent
                tempRight = temp.getRight();
                temp.setParentNode(null);
                removeNode(temp);
                insertNode(temp, true);
                temp = tempRight;
            }
            temp.setParentNode(null);
            removeNode(temp);
            insertNode(temp, true);

        }

        combineHelper();                      //combines elemnts with same degree in root level

        HeapNode foo = root.getRight();
        HeapNode maxNode = root;

        while (root != foo){
            if (foo.getValue() > maxNode.getValue()) maxNode = foo;
            foo = foo.getRight();
        }
        root = maxNode;                         //maxnode updation and number of nodes count change
        numNodes--;

        return node;            
    }

    private void combineHelper(){                       
        if (root == null) return;

        HeapNode[] nodes = new HeapNode[numNodes+1];        //creating array and setting default values
        Arrays.fill(nodes, null);

        nodesAtTop = 0;
        n = 0;

                                                            //count nodes at top
        HeapNode temp  = root;                              //temporary node temp to iterate in loop
        while(true){
            nodesAtTop++;
            if(temp.getRight() == root) break;
            temp = temp.getRight();
        }

        temp = root;
        while(nodesAtTop != n){                             //melding of nodes is done in this loop
            meld(nodes, temp);
            if(temp.getRight() == root) break;
            temp = temp.getRight();
        }


    }

    private void meld(HeapNode[] nodes, HeapNode temp) {
        int degree = temp.getDegree();
        if(!isDegreePresentInHeap(nodes,temp)){                 //checks if same degree node exists or not
            nodes[degree] = temp;                       
            n++;
        } else{
            HeapNode prevNode = nodes[degree];
            HeapNode combinedNode = combine(prevNode, temp);
            nodesAtTop--;
            nodes[degree] = null;
            n--;
            meld(nodes, combinedNode);                      //Recusrive call of meld combining duplicate degrees
        }
    }

    private HeapNode combine(HeapNode node1, HeapNode node2) {
        HeapNode parent = (node1.getValue() > node2.getValue())? node1 : node2;
        HeapNode child = (parent == node1)? node2 : node1;          //making node with lower value child of higher value

        removeNode(child);

        HeapNode prevChild = parent.getChild();
        if (prevChild != null){
            HeapNode prevChildLeft = prevChild.getLeft();    //adding child and setting the necessary pointers 
            child.setLeft(prevChildLeft);                    // when parent already has a child
            prevChild.setLeft(child);
            prevChildLeft.setRight(child);                  
            child.setRight(prevChild);
        }

        parent.setChild(child);
        child.setParentNode(parent);
        parent.setDegree(parent.getDegree()+1);

        if (child == root) root = parent;
        return parent;

    }

    private void removeNode(HeapNode node) {      //cuts the node from the heap
        HeapNode left = node.getLeft();
        HeapNode right = node.getRight();
        HeapNode parent = node.getParent();

        if (node != right){
            left.setRight(right);           //setting node left and right values
            right.setLeft(left);
        }
        node.setLeft(node);
        node.setRight(node);

        if (parent != null){
            if (node == parent.getChild()){
                if (right != node) parent.setChild(right);      //when parent exists for removing node
                else parent.setChild(null);                     //its childs are to be updated accordingly
            }
            node.setParentNode(null);
            parent.setDegree(parent.getDegree()-1);
        }

    }

    private boolean isDegreePresentInHeap(HeapNode[] nodes, HeapNode temp) {
        HeapNode prevNode = nodes[temp.getDegree()];         //checks if degree exists in nodes array
        return (prevNode != null && prevNode != temp);
    }

    private void performCascadeCut(HeapNode node, boolean isFirstCut){
        if (!node.isChildCut() && !isFirstCut ) return;

        HeapNode parent = node.getParent();
        removeNode(node);                               //removing node and inserting now check for
        insertNode(node, true);                         //parents childcut when false call cascadecut recursively until true

        if (parent != null){
            if (parent.isChildCut()) performCascadeCut(parent, false);
            else parent.setChildCut(true);
        }
    }

}
