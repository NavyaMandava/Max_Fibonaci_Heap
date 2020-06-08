public class HeapNode {

    private String name;
    private int value;
    private boolean isChildCut;                     //Defining node attributes
    private int degree;
    private HeapNode parentNode;
    private HeapNode right;
    private HeapNode left;
    private HeapNode child;

    public HeapNode(String s, int val){
        this.name = s;
        this.value = val;
        isChildCut = false;                         //setting defaults to nodes
        degree = 0;
        parentNode = null;
        right = this;
        left = this;
        child = null;
    }


    public String getName() {
        return name;
    }

    public int getValue() {
        return value;                           //getters definiton for all attributes
    }

    public boolean isChildCut() {
        return isChildCut;
    }

    public int getDegree() {
        return degree;
    }

    public HeapNode getParent() {
        return parentNode;
    }

    public HeapNode getRight() {
        return right;
    }

    public HeapNode getLeft() {
        return left;
    }

    public HeapNode getChild() {
        return child;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(int value) {               //Setters definition for all attributes
        this.value = value;
    }

    public void setChildCut(boolean childCut) {
        isChildCut = childCut;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void setParentNode(HeapNode parentNode) {
        this.parentNode = parentNode;
    }

    public void setRight(HeapNode right) {
        this.right = right;
    }

    public void setLeft(HeapNode left) {
        this.left = left;
    }

    public void setChild(HeapNode child) {
        this.child = child;
    }

}
