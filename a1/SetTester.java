/**
 * 
 */
package cs445.a1;

/**
 * @author ryan
 *
 */
public class SetTester {
	public static void main(String[] args) throws NullPointerException, SetFullException {
		String der[]={"asdf","2432"};
		Set<String> testing=new Set<String>(der);
		System.out.println(testing);
		testing.add("potat");
		testing.add("potat");
		testing.add("rob");
		testing.add("false");
		testing.add("bad");
		testing.add("asdfsdf");
		testing.toArray();
		testing.remove("rob");
		testing.remove();
		System.out.println("");
		testing.toArray();
		//testing.add(null);


	}
}