package algorithm;

import java.util.LinkedList;

public class BTree<Element extends Comparable<Element>>  {
    // gốc - root
    private BTNode<Element> root = null;  // khởi tạo nút gốcbangwfg null - chưa có giá trị
    private int order; // bộc của cây
    private  int halfNumber ;
    private  int index;
    private  int treeSize; // kích thước của cây - kiểm tra cân bằng

    public  BTNode<Element> nullBTNode = new BTNode<Element>();

    private LinkedList<BTree<Element>> stepTrees = new LinkedList<BTree<Element>>();
    // tạo 1 link list dùng để lưu vị trí ca nốt

    public  BTree(){

    }

    public BTree(int order){
        this.order = order;
        halfNumber = (order - 1) / 2;
    }

    public BTNode<Element> getRoot() {
        return root;
    }
    public void setRoot(BTNode<Element> root) {
        this.root = root;
    }


    public int getTreeSize() {
        return treeSize;
    }

    public int getHalfNumber() {
        return halfNumber;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public LinkedList<BTree<Element> > getStepTrees() {
        return stepTrees;
    }

    public void setStepTrees(LinkedList<BTree<Element>> stepTrees) {
        this.stepTrees = stepTrees;
    }

    public boolean isEmpty() {
        return root == null;
    }

    // hàm sử dụng chìa khóa để tìm node, và trả về node chứa chìa khóa cần tìm
    public BTNode<Element> getNode(Element keys) {
        // nếu rỗng thì trả về cây rỗng 
        if (isEmpty()) {
            return nullBTNode;
        }
        BTNode<Element> currentNode = root;
        // cây đang xét hiện tại phải khác cây rỗng 
        while (!currentNode.equals(nullBTNode)) {
            int i = 0;
            // getsize - trả về số khóa trên nốt
            while (i < currentNode.getSize()) {

            }

        }

    }


}
