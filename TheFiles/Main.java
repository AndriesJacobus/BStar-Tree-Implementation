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
		tree.insertElement(70);

		System.out.println("\n" + tree.rootToString()+ "\n");
		System.out.println("Search for 70: " + tree.search(70));
		System.out.println("Search for 40: " + tree.search(40));
		System.out.println("Search for 30: " + tree.search(30));

		System.out.println("\nHeight: " + tree.height());
	}
}
