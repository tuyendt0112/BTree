package thuattoan;


import java.io.*;
import java.util.LinkedList;

public class BTree<K extends Comparable<K>> implements Serializable {
    private final int hnum;
    private Bnode<K> root = null;
    private int order;
    private int index;
    private int treeSize;

    public final Bnode<K> nullnode = new Bnode<K>();
    private LinkedList<BTree<K>> stepTrees = new LinkedList<BTree<K>>(); //a linked list of keys

    public BTree(int order) {
        if (order < 3) {
            try {
                throw new Exception("B-tree's order can not lower than 3");
            } catch (Exception e) {
                e.printStackTrace();
            }
            order = 3; // putting order as 3 if order inputted is less than 3
        }
        this.order = order;
        hnum = (order - 1) / 2;
    }

    //basic getters and setters
    public Bnode<K> getRoot() {
        return root;
    }
    public void setRoot(Bnode<K> root) {
        this.root = root;
    }


    public int getTreeSize() {
        return treeSize;
    }

    public int gethnum() {
        return hnum;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public LinkedList<BTree<K>> getStepTrees() {
        return stepTrees;
    }

    public void setStepTrees(LinkedList<BTree<K>> stepTrees) {
        this.stepTrees = stepTrees;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int getHeight() {
        if (isEmpty()) {
            return 0;
        } else {
            return getHeight(root); //calling the function
        }
    }

    public static void getHeights(){
        return;
    }
    // function return the height of the node
    public int getHeight(Bnode<K> node) {
        int height = 0;
        Bnode<K> currentNode = node;
        while (!currentNode.equals(nullnode)) { //iterate over nodes till  reach the end of tree
            //set current node as the first children of the current node
            currentNode = currentNode.getChild(0);
            height++;
        }
        return height;
    }

    // function get the node by the key
    public Bnode<K> getNode(K key) {
        if (isEmpty()) {
            return nullnode;
        }
        Bnode<K> currentNode = root;
        while (!currentNode.equals(nullnode)) { //iterate over nodes till  reach the end of the tree
            int i = 0;
            while (i < currentNode.getSize()) { //iterate over keys of the node
                //if the key is equal to the present key, return
                if (currentNode.getKey(i).equals(key)) {

                    index = i;
                    return currentNode;
                    //if the key is less than the current key, go to the children
                } else if (currentNode.getKey(i).compareTo(key) > 0) {
                    currentNode = currentNode.getChild(i);
                    i = 0;
                } else { //if the key is bigger than the current key, go to next key of the current node
                    i++;
                }
            }
            //if current node is not null, get to the last children node of current node
            if (!currentNode.isNull()) {
                currentNode = currentNode.getChild(currentNode.getSize());
            }
        }
        return nullnode;
    }

    // function return n half of the node after insert
    private Bnode<K> getHalfKeys(K key, Bnode<K> fullNode) {
        int fullNodeSize = fullNode.getSize();

        // Add ode to the appropriate location
        for (int i = 0; i < fullNodeSize; i++) {
            if (fullNode.getKey(i).compareTo(key) > 0) {
                fullNode.addKey(i, key);
                break;
            }
        }
        if (fullNodeSize == fullNode.getSize())
            fullNode.addKey(fullNodeSize, key);

        //System.out.println("\n" + "Insert the key");
//		stepMess.add("Insert " + key + " in between");
        stepTrees.add(cloneCreate.clone(this));
        return getHalfKeys(fullNode);
    }
     // function return half of the node
    private Bnode<K> getHalfKeys(Bnode<K> fullNode) {
        Bnode<K> newNode = new Bnode<K>(order);
        for (int i = 0; i < hnum; i++) {
            newNode.addKey(i, fullNode.getKey(0));
            fullNode.removeKey(0);
        }
        return newNode;
    }
     // function return the left keys
    private Bnode<K> getRestOfHalfKeys(Bnode<K> halfNode) {
        Bnode<K> newNode = new Bnode<K>(order);
        int halfNodeSize = halfNode.getSize();
        for (int i = 0; i < halfNodeSize; i++) {
            if (i != 0) {
                newNode.addKey(i - 1, halfNode.getKey(1));
                halfNode.removeKey(1);
            }
            newNode.addChild(i, halfNode.getChild(0));
            halfNode.removeChild(0);
        }
        return newNode;
    }
     // function use to merge childNode with its fatherNode, at index position
    private void mergeWithFatherNode(Bnode<K> childNode, int index) {
        childNode.getFather().addKey(index, childNode.getKey(0));
        childNode.getFather().removeChild(index);
        childNode.getFather().addChild(index, childNode.getChild(0));
        childNode.getFather().addChild(index + 1, childNode.getChild(1));
    }
    // function use to merge childNode with its fatherNode
    private void mergeWithFatherNode(Bnode<K> childNode) {
        int fatherNodeSize = childNode.getFather().getSize();
        for (int i = 0; i < fatherNodeSize; i++) {
            if (childNode.getFather().getKey(i).compareTo(childNode.getKey(0)) > 0) {
                mergeWithFatherNode(childNode, i);
                break;
            }
        }
        if (fatherNodeSize == childNode.getFather().getSize()) {
            mergeWithFatherNode(childNode, fatherNodeSize);
        }
        for (int i = 0; i <= childNode.getFather().getSize(); i++)
            childNode.getFather().getChild(i).setFather(childNode.getFather());
    }
    // function use to set father for split node
    private void setSplitFatherNode(Bnode<K> node) {
        for (int i = 0; i <= node.getSize(); i++)
            node.getChild(i).setFather(node);
    }

     // function use to process node if the keys size is overflow
    private void processOverflow(Bnode<K> currentNode) {
        //make a new node that has the half of elements of currentNode
        Bnode<K> newNode = getHalfKeys(currentNode);
        for (int i = 0; i <= newNode.getSize(); i++) {
            newNode.addChild(i, currentNode.getChild(0));
            currentNode.removeChild(0);
        }
        Bnode<K> originalNode = getRestOfHalfKeys(currentNode);
        currentNode.addChild(0, newNode);
        currentNode.addChild(1, originalNode);
        originalNode.setFather(currentNode);
        newNode.setFather(currentNode);
        setSplitFatherNode(originalNode);
        setSplitFatherNode(newNode);
        //System.out.println("Enter the key in the middle. This is caused due to overflow");
        stepTrees.add(cloneCreate.clone(this));
    }
    // function use to insert 1 key
    public void insert(K key) {
        // If tree is empty
        if (isEmpty()) {
            root = new Bnode<K>(order);
            root.addKey(0, key);
            treeSize++;
            root.setFather(nullnode);
            root.addChild(0, nullnode);
            root.addChild(1, nullnode);
            //System.out.println("Insert root");
            stepTrees.add(cloneCreate.clone(this));
            return;
        }
        Bnode<K> currentNode = root;
        // If tree is not empty
        //Navigate to the location to insert the key
        while (!currentNode.isLastInternalNode()) {
            int i = 0;
            while (i < currentNode.getSize()) {
                // break if currentNode is leaf
                if (currentNode.isLastInternalNode()) {
                    i = currentNode.getSize();
                } else if (currentNode.getKey(i).compareTo(key) > 0) {
                    currentNode = currentNode.getChild(i);
                    i = 0;
                } else {
                    i++;
                }
            }
            if (!currentNode.isLastInternalNode())
                currentNode = currentNode.getChild(currentNode.getSize());
        }
        // If node is not full then insert non-full
        if (!currentNode.isFull()) {
            int i = 0;
            while (i < currentNode.getSize()) {
                //insertion when key> insertKey
                if (currentNode.getKey(i).compareTo(key) > 0) {
                    currentNode.addKey(i, key);
                    currentNode.addChild(currentNode.getSize(), nullnode);
                    treeSize++;
                    //System.out.println("Insert case : non full");
                    //stepMess.add("Insert " + key );
                    stepTrees.add(cloneCreate.clone(this));
                    return;
                } else {
                    i++;
                }
            }
            //insert at the end of the node
            currentNode.addKey(currentNode.getSize(), key);
            currentNode.addChild(currentNode.getSize(), nullnode);
            treeSize++;

            //System.out.println("Insert case3: non-full");
            //stepMess.add("Insert " + key ");
            stepTrees.add(cloneCreate.clone(this));
        } else {
            // If node is full
            //Insert the node in the appropriate position
            //Then take 1/2 key + child in the node (just inserted)
            Bnode<K> newChildNode = getHalfKeys(key, currentNode);
            for (int i = 0; i < hnum; i++) { // insert after bottom up split
                newChildNode.addChild(i, currentNode.getChild(0));
                currentNode.removeChild(0);
            }
            newChildNode.addChild(hnum, nullnode);
            //Get half a half
            // Uploading n to 1 item (to be a father)
            Bnode<K> originalFatherNode = getRestOfHalfKeys(currentNode);
            currentNode.addChild(0, newChildNode);
            currentNode.addChild(1, originalFatherNode);
            originalFatherNode.setFather(currentNode);
            newChildNode.setFather(currentNode);
            treeSize++;
            //System.out.println("Bring up the middle key");
            stepTrees.add(cloneCreate.clone(this));
            // If on current, child node cap is higher
            //and node upload on top ...
            if (!currentNode.getFather().equals(nullnode)) {
                while (!currentNode.getFather().isOverflow() && !currentNode.getFather().equals(nullnode)) {
                    boolean flag = currentNode.getSize() == 1 && !currentNode.getFather().isOverflow();
                    if (currentNode.isOverflow() || flag) {
                        mergeWithFatherNode(currentNode);
                        currentNode = currentNode.getFather();
                        //System.out.println("inserted ");
                        stepTrees.add(cloneCreate.clone(this));
                        // If it's full again, repeat the action earlier
                        if (currentNode.isOverflow()) {
                            processOverflow(currentNode);
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }


    // function return the number of the node's father child index which matches the node

    private int findChild(Bnode<K> node) {
        if (!node.equals(root)) {
            Bnode<K> fatherNode = node.getFather();

            for (int i = 0; i <= fatherNode.getSize(); i++) {
                if (fatherNode.getChild(i).equals(node))
                    return i;
            }
        }
        return -1;
    }

    // function use to balance the tree if the node's father have different height of right and left
    private Bnode<K> balanceDeletedNode(Bnode<K> node) {
        boolean flag;
        int nodeIndex = findChild(node);
        K pair;
        Bnode<K> fatherNode = node.getFather();
        Bnode<K> currentNode;
        if (nodeIndex == 0) {
            currentNode = fatherNode.getChild(1);
            // Is the (deleted) node on the left-most side (index 0)
            flag = true;
        } else {
            currentNode = fatherNode.getChild(nodeIndex - 1);
            flag = false;
        }
        int currentSize = currentNode.getSize();
        if (currentSize > hnum) {
            if (flag) {
                pair = fatherNode.getKey(0);
                node.addKey(node.getSize(), pair);
                fatherNode.removeKey(0);
                pair = currentNode.getKey(0);
                currentNode.removeKey(0);
                node.addChild(node.getSize(), currentNode.getChild(0));
                currentNode.removeChild(0);
                fatherNode.addKey(0, pair);
                if (node.isLastInternalNode()) {
                    node.removeChild(0);
                }
                //System.out.println("Case 2a:");
                //stepMess.add(" ");
                stepTrees.add(cloneCreate.clone(this));
            } else {
                pair = fatherNode.getKey(nodeIndex - 1);
                node.addKey(0, pair);
                fatherNode.removeKey(nodeIndex - 1);
                pair = currentNode.getKey(currentSize - 1);
                currentNode.removeKey(currentSize - 1);
                node.addChild(0, currentNode.getChild(currentSize));
                currentNode.removeChild(currentSize);
                fatherNode.addKey(nodeIndex - 1, pair);
                if (node.isLastInternalNode()) {
                    node.removeChild(0);
                }
                // System.out.println("Case 2a:");
                //stepMess.add(" ");
                stepTrees.add(cloneCreate.clone(this));
            }
            return node;
        } else {
            if (flag) {
                currentNode.addKey(0, fatherNode.getKey(0));
                fatherNode.removeKey(0);
                fatherNode.removeChild(0);
                if (root.getSize() == 0) {
                    root = currentNode;
                    currentNode.setFather(nullnode);
                }
                if (node.getSize() == 0) {
                    currentNode.addChild(0, node.getChild(0));
                    currentNode.getChild(0).setFather(currentNode);
                }
                for (int i = 0; i < node.getSize(); i++) {
                    currentNode.addKey(i, node.getKey(i));
                    currentNode.addChild(i, node.getChild(i));
                    currentNode.getChild(i).setFather(currentNode);
                }
                // Case 2b.1
                //System.out.println("Case 2b: Merging");
                stepTrees.add(cloneCreate.clone(this));
            } else {
                currentNode.addKey(currentNode.getSize(), fatherNode.getKey(nodeIndex - 1));
                fatherNode.removeKey(nodeIndex - 1);
                fatherNode.removeChild(nodeIndex);
                if (root.getSize() == 0) {
                    root = currentNode;
                    currentNode.setFather(nullnode);
                }
                int currentNodeSize = currentNode.getSize();
                if (node.getSize() == 0) {
                    currentNode.addChild(currentNodeSize, node.getChild(0));
                    currentNode.getChild(currentNodeSize).setFather(currentNode);
                }
                for (int i = 0; i < node.getSize(); i++) {
                    currentNode.addKey(currentNodeSize + i, node.getKey(i));
                    currentNode.addChild(currentNodeSize + i, node.getChild(i));
                    currentNode.getChild(currentNodeSize + i).setFather(currentNode);
                }
                //System.out.println("Case 2b: Merging");
                stepTrees.add(cloneCreate.clone(this));
            }
            return fatherNode;
        }
    }
    // function use to replay the last internal node to the node inputted
    private Bnode<K> replaceNode(Bnode<K> node) {
        Bnode<K> currentNode = node.getChild(index + 1);
        while (!currentNode.isLastInternalNode()) {
            currentNode = currentNode.getChild(0);
        }
        if (currentNode.getSize() - 1 < hnum) {
            // Replaced with the nearest (oldest) son
            currentNode = node.getChild(index);
            int currentNodeSize = currentNode.getSize();
            while (!currentNode.isLastInternalNode()) {
                currentNode = currentNode.getChild(currentNodeSize);
            }
            node.addKey(index, currentNode.getKey(currentNodeSize - 1));
            currentNode.removeKey(currentNodeSize - 1);
            currentNode.addKey(currentNodeSize - 1, node.getKey(index + 1));
            node.removeKey(index + 1);
            index = currentNode.getSize() - 1;
            //System.out.println("Case 3a: Replaced with the nearest child (max)");
            stepTrees.add(cloneCreate.clone(this));
        } else {
            // Replace with the nearest right child (minimum)
            node.addKey(index + 1, currentNode.getKey(0));
            currentNode.removeKey(0);
            currentNode.addKey(0, node.getKey(index));
            node.removeKey(index);
            index = 0;
            //System.out.println("Case 3b: Replace with the nearest right child (min)");
            stepTrees.add(cloneCreate.clone(this));
        }
        return currentNode;
    }

    // function use to delete 1 key
    /*
     * Case 1: If k is in the node x which is a leaf and x.size -1 >= hnum
     * Case 2: If k is in the node x which is a leaf and x.size -1 < hnum Case
     * Case 3: If k is in the node x and x is an internal node (not a leaf)
     */
    public void delete(K key) {
        stepTrees.add(cloneCreate.clone(this));
        // Find the node has the key
        Bnode<K> node = getNode(key);
        Bnode<K> deleteNode = null;
        if (node.equals(nullnode))
            return;
        // If it is root -> Delete always
        if (node.equals(root) && node.getSize() == 1 && node.isLastInternalNode()) {
            root = null;
            treeSize--;
            //System.out.println("delete0");
            stepTrees.add(cloneCreate.clone(this));
        } else {
            boolean flag = true;
            boolean isReplaced = false;
            //  case 3
            if (!node.isLastInternalNode()) {
                node = replaceNode(node);
                deleteNode = node;
                isReplaced = true;
            }
            // If delete affects the height of the tree
            if (node.getSize() - 1 < hnum) {
            //System.out.println("Case 2:");
                //  case 2
                node = balanceDeletedNode(node);
                if (isReplaced) {
                    for (int i = 0; i <= node.getSize(); i++) {
                        for (int j = 0; i < node.getChild(i).getSize(); j++) {
                            if (node.getChild(i).getKey(j).equals(key)) {
                                deleteNode = node.getChild(i);
                                break;
                            }
                        }
                    }
                }
            } else if (node.isLastInternalNode()) {
                //  Case 1
                // System.out.println("Case 1: Delete1");
                node.removeChild(0);
            }
            while (!node.getChild(0).equals(root) && node.getSize() < hnum && flag) {
                //System.out.println("Debug3");
                //System.out.println("This is case 3: Recursively delete");
                if (node.equals(root)) {
                    for (int i = 0; i <= root.getSize(); i++) {
                        if (root.getChild(i).getSize() == 0) {
                            flag = true;
                            break;
                        } else {
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    node = balanceDeletedNode(node);
                }
            }
            if (deleteNode == null) {
                // Check whether previously deleted or just rebalance / replace
                node = getNode(key);
            } else {
                node = deleteNode;
            }
            if (!node.equals(nullnode)) {
                //After replace is completed, delete node
                for (int i = 0; i < node.getSize(); i++) {
                    if (node.getKey(i) == key) {
                        node.removeKey(i);
                    }
                }
                treeSize--;
                //System.out.println("delete");
                stepTrees.add(cloneCreate.clone(this));
            }
        }
    }


}

class cloneCreate {
    public static <T extends Serializable> T clone(T object) {
        T cloneObject = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cloneObject = (T) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cloneObject;
    }
}
