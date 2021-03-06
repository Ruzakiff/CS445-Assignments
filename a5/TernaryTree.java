package cs445.a5;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cs445.StackAndQueuePackage.*; // Needed by tree iterators

public class TernaryTree<E> implements TernaryTreeInterface<E>,TernaryTreeBonus<E> {
    private TernaryNode<E> root;
    
    public TernaryTree() {
        root = null;
    }
    
    public TernaryTree(E rootData) {
        root = new TernaryNode<>(rootData);
    }
    
    public TernaryTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree,
            TernaryTree<E> rightTree) {
    	privateSetTree(rootData, leftTree, middleTree,rightTree);
    }
    
    public void setTree(E rootData) {
        root = new TernaryNode<>(rootData);
    }
    
    public void setTree(E rootData, TernaryTreeInterface<E> leftTree, TernaryTreeInterface<E> middleTree,
            TernaryTreeInterface<E> rightTree) {
    	privateSetTree(rootData, (TernaryTree<E>)leftTree, (TernaryTree<E>)middleTree,
           (TernaryTree<E>)rightTree);
    }
    
    private void privateSetTree(E rootData, TernaryTree<E> leftTree, TernaryTree<E> middleTree,
            TernaryTree<E> rightTree) {
    	TernaryNode<E> root = new TernaryNode<>(rootData);

    	if ((leftTree != null) && !leftTree.isEmpty()) {
    		root.setLeftChild(leftTree.root);
    	}

    	
    	if ((middleTree != null) && !middleTree.isEmpty()) {
    		if (middleTree != leftTree) {
    			root.setRightChild(middleTree.root);
    		} else {
    			root.setRightChild(middleTree.root.copy());
    		}
    	}
    	if ((rightTree != null) && !rightTree.isEmpty()) {
    		if (rightTree != leftTree && rightTree!=middleTree) {
    			root.setRightChild(rightTree.root);
    		} else {
    			root.setRightChild(rightTree.root.copy());
    		}
    	}
    	this.root = root;

    	if ((leftTree != null) && (leftTree != this)) {
    		leftTree.clear();
    	}
    	if ((middleTree != null) && (middleTree != this)) {
    		middleTree.clear();
    	}
    	if ((rightTree != null) && (rightTree != this)) {
    		rightTree.clear();
    	}
    }
    
    public E getRootData() {
        if (isEmpty()||root==null) {
            throw new EmptyTreeException();
        } else {
            return root.getData();
        }
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public void clear() {
        root = null;
    }
    
    protected void setRootData(E rootData) {
        root.setData(rootData);
    }

    protected void setRootNode(TernaryNode<E> rootNode) {
        root = rootNode;
    }
    
    protected TernaryNode<E> getRootNode() {
    	if(isEmpty()){
    		throw new EmptyTreeException();
    	}
        return root;
    }
    
    public int getHeight() {
        int height = 0;
        if (!isEmpty()) {
            height = root.getHeight();
        }
        return height;
    }
    
    public int getNumberOfNodes() {
        int numberOfNodes = 0;
        if (!isEmpty()) {
            numberOfNodes = root.getNumberOfNodes();
        }
        return numberOfNodes;
    }
    
    public Iterator<E> getPreorderIterator() {
        return new PreorderIterator();
    }

    public Iterator<E> getPostorderIterator() {
        return new PostorderIterator();
    }

    public Iterator<E> getLevelOrderIterator() {
        return new LevelOrderIterator();
    }
    
    private class PreorderIterator implements Iterator<E> {
    	private StackInterface<TernaryNode<E>> nodeStack;
    	
    	public PreorderIterator() {
             nodeStack = new LinkedStack<>();
             if (root != null) {
                 nodeStack.push(root);
             }
        }
    	
    	public boolean hasNext() {
             return !nodeStack.isEmpty();
        }
    	
    	public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeStack.pop();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

                // Push into stack in reverse order of recursive calls
                if (rightChild != null) {
                    nodeStack.push(rightChild);
                }
                if (middleChild != null) {
                    nodeStack.push(middleChild);
                }

                if (leftChild != null) {
                    nodeStack.push(leftChild);
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }
    	
    	public void remove() {
             throw new UnsupportedOperationException();
        }
    }
    private class PostorderIterator implements Iterator<E> {
        private StackInterface<TernaryNode<E>> nodeStack;
        private TernaryNode<E> currentNode;

        public PostorderIterator() {
            nodeStack = new LinkedStack<>();
            currentNode = root;
        }

        public boolean hasNext() {
            return !nodeStack.isEmpty() || (currentNode != null);
        }
        public E next() {
            boolean foundNext = false;
            TernaryNode<E> leftChild, rightChild,middleChild, nextNode = null;

            // Find leftmost leaf
            while (currentNode != null) {
                nodeStack.push(currentNode);
                leftChild = currentNode.getLeftChild();
                middleChild=currentNode.getMiddleChild();
                if (leftChild == null) {
                	if(middleChild==null){
                		 currentNode = currentNode.getRightChild();
                	}
                	else{
                	 currentNode = currentNode.getMiddleChild();
                	}
                   
                } else {
                    currentNode = leftChild;
                }
            }

            // Stack is not empty either because we just pushed a node, or
            // it wasn'E empty to begin with since hasNext() is true.
            // But Iterator specifies an exception for next() in case
            // hasNext() is false.

            if (!nodeStack.isEmpty()) {
                nextNode = nodeStack.pop();
                // nextNode != null since stack was not empty before pop

                TernaryNode<E> parent = null;
                if (!nodeStack.isEmpty()) {
                    parent = nodeStack.peek();
                    if (nextNode == parent.getLeftChild()) {
                    	if(parent.getMiddleChild()==null){
                    		currentNode=parent.getRightChild();
                    	}
                    	else{
                    	currentNode=parent.getMiddleChild();
                    	}
                       
                    } 
                    else if(nextNode==parent.getMiddleChild()){
                    	currentNode=parent.getRightChild();
                    }
                    else{
                    	currentNode=null;
                    }
                } 
                else {
                    currentNode = null;
                }
            } else {
                throw new NoSuchElementException();
            }

            return nextNode.getData();
        }
    }
    
    private class LevelOrderIterator implements Iterator<E> {
    	private QueueInterface<TernaryNode<E>> nodeQueue;
    	
    	public LevelOrderIterator() {
            nodeQueue = new LinkedQueue<>();
            if (root != null) {
                nodeQueue.enqueue(root);
            }
        }
    	
    	public boolean hasNext() {
              return !nodeQueue.isEmpty();
        }
    	
    	public E next() {
            TernaryNode<E> nextNode;

            if (hasNext()) {
                nextNode = nodeQueue.dequeue();
                TernaryNode<E> leftChild = nextNode.getLeftChild();
                TernaryNode<E> middleChild = nextNode.getMiddleChild();
                TernaryNode<E> rightChild = nextNode.getRightChild();

                // Add to queue in order of recursive calls
                if (leftChild != null) {
                    nodeQueue.enqueue(leftChild);
                }
                
                if (middleChild != null) {
                    nodeQueue.enqueue(middleChild);
                }
                if (rightChild != null) {
                    nodeQueue.enqueue(rightChild);
                }
            } else {
                throw new NoSuchElementException();
            }
            return nextNode.getData();
        }
    	
    	public void remove() {
             throw new UnsupportedOperationException();
        }
    }

	@Override
	public Iterator<E> getInorderIterator() {
		throw new UnsupportedOperationException();
		//return null;
	}

	@Override
	public boolean contains(E elem) {
		PreorderIterator i=new PreorderIterator();
		while(i.hasNext()){
			E temp=i.next();
			if(temp.equals(elem)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isBalanced(){
		if(root==null){
			return true;
		}
		int lh=0,mh=0,rh=0;
		if (!isEmpty()){
			if(root.hasLeftChild()){
				lh=root.getLeftChild().getHeight();
			}
			if(root.hasMiddleChild()){
				mh=root.getMiddleChild().getHeight();
			}
			if(root.hasRightChild()){
				rh=root.getRightChild().getHeight();
			}
			
		}
		if(Math.abs(lh-mh)<=1&&Math.abs(mh-rh)<=1&&Math.abs(lh-rh)<=1){
			return true;
		}
		return false;
	}
}