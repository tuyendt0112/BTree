package thuattoan;

import java.util.ArrayList;

public class Bnode<Element extends Comparable <Element> > {

	private int fullnum; 
	// để kiểm tra node hay key nào đó đã đúng yêu cầu so với bậc của Btree hay chưa
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

	private Bnode<Element> father; // node cha 
	// array children de luu cac node con
	private ArrayList<Bnode <Element>> children = new ArrayList<>(); 
	private ArrayList<Element> keys = new ArrayList<>();
	
	
	public Bnode() {
		
	}
	
	// n la bac cua cay 
	public Bnode(int n) {
		fullnum = n - 1;
	}
	
	
	public Bnode<Element> getFather() {
		return father;
	}
	
	public void setFather(Bnode<Element> father) {
		this.father = father;
	}
	
	
	public ArrayList<Bnode<Element>> getChildren(){
		return children;
	}
	
	
	public ArrayList<Element> getKeys(){
		return keys;
	}
	// them node con vao tap children , o vi tri pos
	public void addChild(Bnode<Element> node, int pos )	{
		children.add(pos, node);
	}
	// xoa 1 node con o trong tap con, tai vi tri pos
	public void removeChild(int pos) {
		children.remove(pos);
	}
	// lay node con o vi tri pos trong tap children
	public Bnode getChild(int pos) {
		return children.get(pos);
	}
	//them 1 key vao tap keys tai vi tri pos 
	public void addKey(Element K, int pos) {
		keys.add(pos,K);
	}
	// xoa 1 key trong tap keys tai vi tri pos
	public void removeKey(int pos) {
		keys.remove(pos);
	}
	// lay gia tri 1 key tai vi tri pos 
	public Element getKey(int pos) {
		return keys.get(pos);
	}
	// lay kich thuoc cua mang keys
	public int getSize() {
		return keys.size();
	}
	
	
	// mot so ham kiem tra cay 
	
	// kiem tra cay da day hay chua , kiem tra so luong khoa
	
	public boolean isFull() {
		if (fullnum == keys.size()) {
			return true;
		}else {
			return false;
		}
	}
	
	// kiem tra khoa cay rong 
	public boolean isNull() {
		if ( keys.isEmpty()) {
			return true;
		}else {
			return false;
		}
	}
	
	// kiem tra so luong khoa co vuot qua hay kh 
	public boolean isOverflow() {
		if(fullnum < keys.size()) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
}
