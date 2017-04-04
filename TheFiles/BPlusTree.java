/*
Name and Surname: Andries Jacobus du Plooy
Student/staff Number: u15226183
*/

/*You must complete this class to create a fully functional B+-Tree containing
Integer objects.  Additional instructions are provided in comments throughout the class*/
public class BPlusTree
{
	/*
	1. You may not change the signatures of any of the given methods.  You may 
	however add any additional methods and/or field which you may require to aid 
	you in the completion of this assignment.
	
	2. You will have to design and implement a your own Node class.  The BPlusNode 
	should house Integer objects. You are given a partial BPlusNode class.
	
	3. You will notice that there are some overloaded methods, some of which work 
	for Integer objects and some with primitive type int.  You have to find a way 
	to implement the methods to work with both types.
	*/

	private BPlusNode root = null;
	
	public BPlusTree(int m)
	{
		/*
		The constructor.  You must create a BPlusTree object of order m,
		where m is passed as a parameter to the constructor.
		You may assume that m will always be valid.
		*/

		root = new BPlusNode(m);
	}
	
	public BPlusTree(BPlusNode root_)
	{
		/*
		The constructor.  You must create a BPlusTree object of order m,
		where m is passed as a parameter to the constructor.
		You may assume that m will always be valid.
		*/

		root = root_;
	}
	
	public String insertElement(int element)
	{
		/*
		The int element passed as a parameter should be inserted in
		your B+-Tree.  The method should return a string representation
		of the path followed through the tree to find a node
		to insert into.  If the tree was empty, return the string 
		representatio of the new root.
		*/

		String ret = search(element);

		root = root.insert(element);

		if (ret == "*NULL*")
		{
			ret = search(element);
		}
		else
		{
			ret = ret.substring(0, ret.length() - 7);
		}
	
		return ret;
	}
	
	public String insertElement(Integer element)
	{
		/*
		The int element passed as a parameter should be inserted in
		your B+-Tree.  The method should return a string representation
		of the path followed through the tree to find a node
		to insert into.  If the tree was empty, return the string 
		representation of the new root.
		*/

		String ret = search((int)element);

		root = root.insert((int)element);

		if (ret == "*NULL*")
		{
			ret = search((int)element);
		}
		else
		{
			ret = ret.substring(0, ret.length() - 7);
		}
	
		return ret;
	}
	
	public String deleteElement(int element)
	{
		/*
		The int element passed as a parameter should be deleted from
		your B+-Tree.  The method should return a string representation
		of the path followed through the tree to find the node to delete
		from. If the tree was empty, return the string representation
		of the NULL node.
		*/
				
		return "";
	}
	
	public String deleteElement(Integer element)
	{
		/*
		The int element passed as a parameter should be deleted from
		your B+-Tree.  The method should return a string representation
		of the path followed through the tree to find the node to delete
		from. If the tree was empty, return the string representation
		of the NULL node.
		*/
				
		return "";
	}
	
	public String search(int element)
	{
		/*
		A String should be returned representing the search path
		for the element sent in as a parameter.  Refer to the
		specification for more details.
		*/

		return cheatSearch(element + "");
	}
	
	public String search(Integer element)
	{
		/*
		A String should be returned representing the search path
		for the element sent in as a parameter.  Refer to the
		specification for more details.
		*/

		return cheatSearch(element + "");
	}

	private String cheatSearch(String elem)
	{
		//root will always be initialised

		if (root.getInsertIndex() == 0)
		{
			return "*NULL*";
		}
		else
		{
			int searchE = Integer.parseInt(elem);			

			String path = recursiveSearch(root, searchE);

			if (path.substring(path.length() - 1, path.length()).equals(","))
			{
				path = path.substring(0, path.length() - 1);
			}
			else		//Doesn't have to be here
			{
				path = path.substring(0, path.length());
			}

			return path;
		}

	}

	private String recursiveSearch(BPlusNode temp, int searchE)
	{
		String path = "";
		boolean added = false;
		int searchR;

		for (int i = 0; i < temp.getInsertIndex(); i++)
		{
			if (!added)
			{
				path += toOut(temp);
				added = true;
			}

			//System.out.println("Where: " + temp.elem[0] + ", NumIns: " + temp.getInsertIndex() + "\n");

			searchR = temp.getContents()[i].getElement();

			if (searchE == searchR)
			{
				return path;		//done
			}
			else if (searchE < searchR)
			{
				if (temp.getLeftAt(i) == null)
				{
					path += "*NULL*";
					break;
				}
				else
				{
					path += recursiveSearch(temp.getLeftAt(i), searchE);
					break;
				}
			}
			else if (i == temp.getInsertIndex() - 1)
			{
				//last iteration, also look at left
				if (searchE > searchR)
				{
					if (temp.getRightAt(i) == null)
					{
						path += "*NULL*";
						break;
					}
					else
					{
						path += recursiveSearch(temp.getRightAt(i), searchE);
						break;
					}
				}
			}
		}

		return path;
	}

	private String toOut(BPlusNode printE)
	{
		String out = "";

		Node[] cont = printE.getContents();

		for (int i = 0; i < printE.getInsertIndex(); i++)
		{
			if (cont[i] == null)
			{
				out += "[]";
			}
			else
			{
				out += "[" + cont[i].getElement() + "]";
			}

			if (i == printE.getInsertIndex() - 1)
			{
				out += ",";
			}
		}

		return out;
	}
	
	public int height()
	{
		/*
			This method should return the height of the tree.
		*/

		return heightOfTree(root);
	}

	private int heightOfTree(BPlusNode node)
	{
	    if (node == null)
	    {
	        return 0;
	    }
	    else
	    {
	    	int current = 1;

	    	Node[] nodes = node.getContents();
	    	int max = 0;

	    	for (int i = 0; i < node.getInsertIndex(); i++)
	    	{
	    		if (i == node.getInsertIndex() - 1)
	    		{
	    			max = Math.max(max, heightOfTree(node.getRightAt(i)));
	    		}
	    		else
	    		{
	    			max = Math.max(max, heightOfTree(node.getLeftAt(i)));
	    		}
	    	}

	    	current += max;

	        return current;
	    }
	}
	
	public int countInternalNodes()
	{
		/*
			This method should count and return the number of internal nodes.

			=> Internal node = node between [root, leaves)

			     I         ROOT (root is also an INTERNAL NODE, unless it is leaf)
			   /   \
			  I     I      INTERNAL NODES
			 /     / \
			o     o   o    EXTERNAL NODES (or leaves)
		*/

		return countIntNodesRec(root);
	}

	private int countIntNodesRec(BPlusNode start)
	{
		if (start.getInsertIndex() == 0)
		{
			return 0;
		}

		int ret = 0;

		for (int i = 0; i < start.getInsertIndex(); i++)
	    {
    		if (i == start.getInsertIndex() - 1)
    		{
    			//Check right
    			if (start.getRightAt(i) != null)
    			{
    				ret++;
    				countIntNodesRec(start.getRightAt(i));
    			}
    		}
    		else
    		{
    			//Check left
    			if (start.getLeftAt(i) != null)
    			{
    				ret++;
    				countIntNodesRec(start.getLeftAt(i));
    			}
    		}
	    }

		return ret;
	}
	
	public int countLeafNodes()
	{
		/*
			This method should count and return the number of leaf nodes.
		*/

		return 0;
	}
	
	public int fullness()
	{
		/*
			This method should return as a percentage the fullness of the tree.
			The percentage is out of 100 and if, for example, 70 is returned,
			it means that the tree is 70% full. A tree containing no keys is 0% 
			full.  Round your answer up to the nearest integer.
		*/
		
		return 0;
	}
	
	public String breadthFirst()
	{
		/*
			This method returns a String containing the nodes in breath-first
			order.  You should not include null nodes in the string.  The string
			for an empty tree is simply an empty string.
		*/

		return "";
	}
	
	public BPlusNode getFirstLeaf()
	{
		/*
			This method should return the left-most leaf node in the tree.
			If the tree is empty, return null.
		*/
		
		return null;
	}

	public BPlusNode getRoot()
	{
		return root;
	}

	public String rootToString()
	{
		String ret = "";

		ret += "Root: " + root.toString() + "\n{";
		ret += "\n  Root[0]: " + root.getContentAt(0) + "\n   , root[0].left: " + root.getLeftAt(0) + "\n   , root[0].right: " + root.getRightAt(0) + ".";
		ret += "\n  Root[1]: " + root.getContentAt(1) + "\n   , root[1].left: " + root.getLeftAt(1) + "\n   , root[1].right: " + root.getRightAt(1) + ".";
		ret += "\n  Root[2]: " + root.getContentAt(2) + "\n   , root[2].left: " + root.getLeftAt(2) + "\n   , root[2].right: " + root.getRightAt(2) + ".";
		ret += "\n  Root[3]: " + root.getContentAt(3) + "\n   , root[3].left: " + root.getLeftAt(3) + "\n   , root[3].right: " + root.getRightAt(3) + ".";
		ret += "\n}";

		return ret;
	}
}
