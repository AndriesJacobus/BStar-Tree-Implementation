/*
Name and Surname: Andries Jacobus du Plooy
Student/staff Number: u15226183
*/

public class Node
{
	private int element;
	private BPlusNode left = null;
	private BPlusNode right = null;

	public Node(int element_)
	{
		element = element_;
	}

	public void setElement(int element_)
	{
		element = element_;
	}

	public void setLeft(BPlusNode left_)
	{
		left = left_;
	}

	public void setRight(BPlusNode right_)
	{
		right = right_;
	}

	public int getElement()
	{
		return element;
	}

	public BPlusNode getLeft()
	{
		return left;
	}

	public BPlusNode getRight()
	{
		return right;
	}
}