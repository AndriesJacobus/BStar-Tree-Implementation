/*
Name and Surname: Andries Jacobus du Plooy
Student/staff Number: u15226183
*/

public class Main
{
	public static void main(String args[])
	{
		BPlusTree tree = new BPlusTree(5);

		tree.insertElement(new Integer(5));
		tree.insertElement(10);
		tree.insertElement(15);
		tree.insertElement(20);

		tree.insertElement(25);
		tree.insertElement(30);
		tree.insertElement(35);

		tree.insertElement(40);
		tree.insertElement(45);
		tree.insertElement(50);

		tree.insertElement(55);
		tree.insertElement(60);

		System.out.println(tree.rootToString());

		System.out.println("Search for 10: " + tree.search(10));
		

		// BPlusNode root = new BPlusNode(5);
		// root = root.insert(2);
		// System.out.println("Root: " + root.toString());

		// root = root.insert(4);
		// System.out.println("Root: " + root.toString());

		// root = root.insert(6);
		// System.out.println("Root: " + root.toString());

		// root = root.insert(8);
		// System.out.println("Root: " + root.toString());

		// root = root.insert(10);
		// System.out.println("Root: " + root.toString());
		// System.out.println("Root's left: " + root.getLeftAt(0).toString());
		// System.out.println("Root's right: " + root.getRightAt(0).toString());

		// root = root.insert(3);
		// root = root.insert(7);

		// System.out.println("Root: " + root.toString());
		// System.out.println("Root's left: " + root.getLeftAt(0).toString());
		// System.out.println("Root's right: " + root.getRightAt(0).toString());

		// root = root.insert(11);

		// System.out.println("Root: " + root.toString());
		// System.out.println("Root's left: " + root.getLeftAt(0).toString());
		// System.out.println("Root's right: " + root.getRightAt(0).toString());
	}
}
