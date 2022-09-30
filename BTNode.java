package algorithm;

import java.util.ArrayList;

public class BTNode< Element extends Comparable<Element>> {

    private  int fullnumber; // để kiểm tra node hay key nào đó đã đúng yêu cầu so với bậc của Btree hay chưa
    /*
     * nếu n là bậc của cây
     * Nút (node) : mỗi nút bên trong có tối đa (n-1) khóa và tối thiểu [n/2] -1 khóa
     * Trừ nút gốc có tối đa n nút con và có tối thiểu [n/2] nút con
     * nút gốc có tối thiểu 1 khóa và có ít nhất 2 nút con */

    /* giả sử bặc của cây là n = 3
     * mỗi nút bên trong có tối đa 3-1 khóa , và có tối thiểu là [3/2] - 1 = [2]-1 = 1 khóa
     * mỗi nút con - trừ nút gốc có tối đa 3 nút con, và có tối thiểu 2 nút con
     * */

    /*
     * giả sử bậc của cây là n = 6
     * mỗi nút bên trong có tối đa 6-1=5 khóa , và có tối thiểu là [6/2]-1 = 2 khóa
     * mỗi nút con - trừ nút gốc có tối đa 6 nút con và tối thiểu [6/2] = 3 nút con
     */

    private BTNode<Element> father ; // node cha
    private ArrayList<BTNode<Element>> children = new ArrayList<BTNode<Element>>();
    // biến children tạo theo kiểu mảng để lưu thông tin của node con
    private ArrayList<Element> keys = new ArrayList<>();
    // mảng để lưu thông tin của khóa

    public BTNode() {
    }
    // oder là bậc của cây
    public BTNode(int order) {
        fullnumber = order - 1;
    }

    // getter - setter của father
    public BTNode<Element>  getFather() {
        return father;
    }
    public void setFather(BTNode<Element> father) {
        this.father = father;
    }

    // getter - setter của children
    public ArrayList<BTNode<Element>> getChildren() {
        return children;
    }

    //getter - setter của key
    public ArrayList<Element> getKeys(int i) {
        return keys;
    }
    // kiểm tra xem 1 nốt đã đủ keys hay chưa
    public boolean isFull() {
        return fullnumber == keys.size();
    }
    // kiểm tra keys rỗng
    public boolean isNull() {
        return keys.isEmpty();
    }

    public int getSize() {
        return keys.size();
    }
}
