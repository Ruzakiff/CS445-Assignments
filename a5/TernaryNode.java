package cs445.a5;

class TernaryNode<T> {

	/**
	 * A class that represents nodes in a Ternary tree.
	 */
	    private T data;
	 //   private TernaryNode<T> leftChild;  // Reference to left child
	   // private TernaryNode<T> middleChild; // Reference to right child
	    //private TernaryNode<T> rightChild; // Reference to right child
	    @SuppressWarnings("unchecked")
	    private TernaryNode<T>[] children = (TernaryNode<T>[])new TernaryNode<?>[3];

	    public TernaryNode() {
	        this(null); // Call next constructor
	    }

	    public TernaryNode(T dataPortion) {
	        this(dataPortion, null, null,null); // Call next constructor
	    }

	    public TernaryNode(T dataPortion, TernaryNode<T> newLeftChild, TernaryNode<T> newMiddleChild,
	                      TernaryNode<T> newRightChild) {
	        data = dataPortion;
	        children[0]=newLeftChild;
	        children[1]=newMiddleChild;
	       	children[2]=newRightChild;
	    }

	    /** Retrieves the data portion of this node.
	     *  @return  The object in the data portion of the node. */
	    public T getData() {
	        return data;
	    }

	    /** Sets the data portion of this node.
	     *  @param newData  The data object. */
	    public void setData(T newData) {
	        data = newData;
	    }

	    /** Retrieves the left child of this node.
	     *  @return  The node’s left child. */
	    public TernaryNode<T> getLeftChild() {
	        return children[0];
	    }

	    /** Sets this node’s left child to a given node.
	     *  @param newLeftChild  A node that will be the left child. */
	    public void setLeftChild(TernaryNode<T> newLeftChild) {
	        children[0] = newLeftChild;
	    }

	    /** Detects whether this node has a left child.
	     *  @return  True if the node has a left child. */
	    public boolean hasLeftChild() {
	        return children[0] != null;
	    }

	    /** Retrieves the right child of this node.
	     *  @return  The node’s right child. */
	    public TernaryNode<T> getRightChild() {
	        return children[2];
	    }

	    /** Sets this node’s right child to a given node.
	     *  @param newRightChild  A node that will be the right child. */
	    public void setRightChild(TernaryNode<T> newRightChild) {
	        children[2] = newRightChild;
	    }

	    /** Detects whether this node has a right child.
	     *  @return  True if the node has a right child. */
	    public boolean hasRightChild() {
	        return children[2] != null;
	    }
	    public TernaryNode<T> getMiddleChild() {
	        return children[1];
	    }

	    /** Sets this node’s right child to a given node.
	     *  @param newRightChild  A node that will be the right child. */
	    public void setMiddleChild(TernaryNode<T> newMiddleChild) {
	        children[1] = newMiddleChild;
	    }

	    /** Detects whether this node has a right child.
	     *  @return  True if the node has a right child. */
	    public boolean hasMiddleChild() {
	        return children[1] != null;
	    }
	    
	    /** Detects whether this node is a leaf.
	     *  @return  True if the node is a leaf. */
	    public boolean isLeaf() {
	        return (children[0] == null) && (children[1] == null) && (children[2] == null);
	    }

	    /** Counts the nodes in the subtree rooted at this node.
	     *  @return  The number of nodes in the subtree rooted at this node. */
	    public int getNumberOfNodes() {
	        int leftNumber = 0;
	        int rightNumber = 0;
	        int middleNumber= 0;

	        if (children[0] != null) {
	            leftNumber = children[0].getNumberOfNodes();
	        }

	        if (children[2] != null) {
	            rightNumber = children[2].getNumberOfNodes();
	        }
	        
	        if(children[1]!=null){
	        	middleNumber=children[1].getNumberOfNodes();
	        }

	        return 1 + leftNumber + rightNumber+middleNumber;
	    }

	    /** Computes the height of the subtree rooted at this node.
	     *  @return  The height of the subtree rooted at this node. */
	    public int getHeight() {
	        return getHeight(this); // Call private getHeight
	    }

	    private int getHeight(TernaryNode<T> node) {
	        int height = 0;

	        if (node != null)
	            height = 1 + Math.max(Math.max(getHeight(node.getLeftChild()),
                        getHeight(node.getRightChild())), getHeight(node.getMiddleChild()));
	        
	        return height;
	    }

	    /** Copies the subtree rooted at this node.
	     *  @return  The root of a copy of the subtree rooted at this node. */
	    public TernaryNode<T> copy() {
	        TernaryNode<T> newRoot = new TernaryNode<>(data);

	        if (children[0] != null) {
	            newRoot.setLeftChild(children[0].copy());
	        }

	        if (children[2] != null) {
	            newRoot.setRightChild(children[2].copy());
	        }
	        
	        if (children[1] != null) {
	            newRoot.setMiddleChild(children[1].copy());
	        }
	        
	        return newRoot;
	    }
}
