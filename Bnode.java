package thuattoan;
import java.io.Serializable;
import java.util.ArrayList;

public class Bnode<E extends Comparable<E>> implements Serializable {
    private int fnum; // check if the number in 1 node is full or not
    private Bnode<E> father; // father node
    private ArrayList<Bnode<E>> children = new ArrayList<Bnode<E>>(); // list of children node
    private ArrayList<E> keys = new ArrayList<>(); // list of keys

    public Bnode() {
    }

    public Bnode(int order) { // order of the tree {3,4,5}
        fnum = order - 1; // each node can have (order-1) num
    }

    //basic getters and setters
    public Bnode<E> getFather() {
        return father;
    }

    public void setFather(Bnode<E> father) {
        this.father = father;
    }

    public ArrayList<Bnode<E>> getChildren() {
        return children;
    }
    // function return a child node , at index position
    public Bnode<E> getChild(int index) {
        return children.get(index);
    }

    // function use to add , 1 node to the index position
    public void addChild(int index, Bnode<E> node) {
        children.add(index, node);
    }

    // function use to remove 1 node to the index position
    public void removeChild(int index) {
        children.remove(index);
    }
    // function return 1 key at the index position
    public E getKey(int index) {
        return keys.get(index);
    }

    public void addKey(int index, E element) {
        keys.add(index, element);
    }

    public void removeKey(int index) {
        keys.remove(index);
    }

    public ArrayList<E> getKeys() {
        return keys;
    }

    //check if the node is full
    public boolean isFull() {
        return fnum == keys.size();
    }
    //check if node has more elements than order-1
    public boolean isOverflow() {
        return fnum < keys.size();
    }

    public boolean isNull() {
        return keys.isEmpty();
    }

    public int getSize() {
        return keys.size();
    }

    // function use to check if the node is last internal node
    public boolean isLastInternalNode() {
        if (keys.size() == 0)
            return false; //return false if it is a empty
        for (Bnode<E> node : children)
            if (node.keys.size() != 0)
                return false;  //if any children of that node is non zero
        return true; // return true if it is a leaf
    }
}