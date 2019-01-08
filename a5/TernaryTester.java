/**
 * 
 */
package cs445.a5;

import java.util.Iterator;

/**
 * @author ryan
 *
 */
public class TernaryTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TernaryTree<String> a=new TernaryTree<>("a");
		TernaryTree<String> b=new TernaryTree<>("b");
		TernaryTree<String> c=new TernaryTree<>("c");
		TernaryTree<String> d=new TernaryTree<>("d");
		System.out.println(d.getHeight());
		TernaryTree<String> e=new TernaryTree<>("e");
		TernaryTree<String> actual=new TernaryTree<>("a",b,c,d);
		//TernaryTree<String> l=new TernaryTree<String>("f",actual,null,null);
		System.out.println(actual.isBalanced());
		//bleh.setRootData("e");
		System.out.println(actual.getRootData());
		System.out.println(b.getHeight());
		Iterator<String> i = actual.getPreorderIterator();
		Iterator<String> j = actual.getPostorderIterator();
		Iterator<String> k = actual.getLevelOrderIterator();
		System.out.println(actual.contains(""));
		System.out.println(i.next());
		System.out.println(i.next());
		System.out.println(i.next());
		System.out.println(k.next());
		System.out.println(k.next());
		System.out.println(k.next());
		
		System.out.println(j.next());
		System.out.println(j.next());
		System.out.println(j.next());
		//System.out.println(j.next());
		
		System.out.println(actual.getPreorderIterator().next());
		
		
	}
}