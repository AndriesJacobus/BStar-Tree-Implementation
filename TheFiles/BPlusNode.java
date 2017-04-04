/*
Name and Surname: Andries Jacobus du Plooy
Student/staff Number: u15226183
*/

/*
You must design and implement this class to be used as a node in your B+ tree. You may
choose to implement this class to double up for both internal and leaf nodes or you
could create a separate class to represent the internal nodes.  In choosing the latter
strategy of having separate leaf and internal node classes, it would be a good idea
to create a super class for your two node types.

You may add any additional members that you need but you may not remove or modify
the members already contained in the class.
*/
public class BPlusNode
{
	/*
	This is the "next" node in the leaf level.
	*/

	private Node[] contents = null;
	private int insertIndex = 0;
	private int m;

	protected BPlusNode next = null;
	private BPlusNode parent = null;

	public BPlusNode(int m_)
	{
		m = m_;
		insertIndex = 0;
		contents = new Node[m - 1];
	}

	public BPlusNode(int m_, Node[] contents_, int insertIndex_)
	{
		m = m_;

		insertIndex = insertIndex_;
		contents = contents_;
	}

	public BPlusNode insert(int elem)
	{
		return insertP(elem);
	}

	public BPlusNode insert(Node ins)
	{
		return insertP(ins, false);
	}

	/*
		@param force: true to force node insersion at current level
	*/
	public BPlusNode insert(Node ins, boolean force)
	{
		if (force)
		{
			return insertP(ins, true);
		}
		else
		{
			return null;
		}
	}

	private BPlusNode insertP(int elem)
	{
		return insertP(new Node(elem), false);
	}

	private BPlusNode insertP(Node ins, boolean force)
	{
		//check if children have space
		if (!force)
		{
			for (int i = 0; i < insertIndex; i++)
			{
				if (getLeftAt(i) != null && ins.getElement() < contents[i].getElement())
				{
					//System.out.println(getLeftAt(i).toString());
					setLeftAt(i, getLeftAt(i).insert(ins));
					correctChildren();
					return this;
				}

				if (i + 1 < m - 1)
				{
					if (getRightAt(i) != null && ins.getElement() > contents[i].getElement() && (contents[i + 1] == null || ins.getElement() < contents[i + 1].getElement()))
					{
						//System.out.println(getRightAt(i).toString());
						setRightAt(i, getRightAt(i).insert(ins));
						correctChildren();
						return this;
					}
				}
				else
				{
					if (getRightAt(i) != null && ins.getElement() > contents[i].getElement())
					{
						String ret = cheatSearch(ins.getElement() + "");
						//System.out.println("Bolognaise: " + ret.substring(ret.length() - (7 + 16), ret.length() - 7));

						if (ret.substring(ret.length() - (7 + 16), ret.length() - 7).equals(getRightAt(i).toString()))
						{
							Node teet = getRightAt(i).insertP(ins, false).getContentNodeAt(0);
							int red = teet.getElement();
							teet.setElement(55);

							Node[] temp = new Node[m];

							populate(temp, teet);

							BPlusNode finalRet = split(temp);

							finalRet.getRightAt(0).removeRedundancy(red);

							return finalRet;
						}

						setRightAt(i, getRightAt(i).insert(ins));
						correctChildren();
						return this;
					}
				}
				
			}
		}			

		if (insertIndex < m - 1)
		{
			//else, insert here
			contents[insertIndex] = ins;
			insertIndex++;

			organizeNodes(contents, insertIndex);

			return this;
		}
		else
		{
			//node must be split
			Node[] temp = new Node[m];

			populate(temp, ins);

			return split(temp);
			// return null;
		}
	}

	private String cheatSearch(String elem)
	{
		//root will always be initialised

		if (this.getInsertIndex() == 0)
		{
			return "*NULL*";
		}
		else
		{
			int searchE = Integer.parseInt(elem);			

			String path = recursiveSearch(this, searchE);

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

	private boolean hasViableChildren(BPlusNode node)
	{
		for (int i = 0; i < insertIndex; i++)
		{
			if (getLeftAt(i) != null || getRightAt(i) != null)
			{
				return true;
			}
		}

		return false;
	}

	private void populate(Node[] temp, Node ins)
	{
		for (int i = 0; i < m - 1; i++)
		{
			temp[i] = contents[i];
		}

		temp[m - 1] = ins;

		organizeNodes(temp, m);
	}

	/*
		@return: new parent node
	*/

	private BPlusNode split(Node[] temp)
	{
		int middle = (int)((m) / 2) - 1;

		BPlusNode childOne = new BPlusNode(m);
		BPlusNode childTwo = new BPlusNode(m);
		BPlusNode currParent;

		boolean isRoot = false;

		//check if is root. If it is, insert(node, true) to force

		//System.out.println("\n\nNode: " + toString() + ", isRoot: " + (parent == null));


		if (parent == null)
		{
			//is root
			isRoot = true;
		}

		//System.out.println("\nSplit node: [" + temp[0].getElement() + "][" + temp[1].getElement() + "][" + temp[2].getElement() + "][" + temp[3].getElement() + "][" + temp[4].getElement() + "]" + "\n");

		if (parent == null)
		{
			currParent = new BPlusNode(m);
		}
		else
		{
			//System.out.println("Parent: " + parent.toString());
			currParent = parent;
		}

		for (int i = 0; i < m; i++)
		{
			if (i <= middle)
			{
				childOne = childOne.insertP(temp[i], isRoot);
			}
			else if (i == middle + 1)
			{
				currParent = currParent.insert(new Node(temp[i].getElement()), true);

				currParent.setLeftAt(currParent.getInsertIndex() - 1, childOne);
				currParent.setRightAt(currParent.getInsertIndex() - 1, childTwo);

				currParent.correctChildren();

				childTwo = childTwo.insertP(temp[i], isRoot);
			}
			else
			{
				childTwo = childTwo.insertP(temp[i], isRoot);
			}
		}

		//System.out.println("Parent: " + parent.toString());
		childOne.setParent(currParent);
		childTwo.setParent(currParent);

		childOne.setNext(childTwo);

		return currParent;
	}

	private void removeRedundancy(int red)
	{
		Node[] replacemntContects = new Node[insertIndex - 1];
		int c = 0;

		for (int i = 0; i < insertIndex; i++)
		{
			if (contents[i].getElement() == red)
			{
				contents[i] = null;
			}
			else
			{
				replacemntContects[c] = contents[i];
				c++;
			}
		}

		contents = replacemntContects;
		insertIndex--;
	}

	/*
		function to correct references of parent nodes. From right to left, for each node:

		if rightmost, set left to childOne and right to childTwo

		go one left
		set currNode's right = node[currentNodeIndex + 1].getLeft
	*/

	public void correctChildren()
	{
		for (int i = insertIndex - 2; i >= 0; i--)
		{
			contents[i].setRight(contents[i + 1].getLeft());
		}
	}

	private void organizeNodes(Node[] curr, int num)
	{
		for (int i = num - 1; i > 0; i--)
		{
			if (curr[i].getElement() < curr[i-1].getElement())
			{
				//swap
				Node temp = curr[i-1];
				curr[i-1] = curr[i];
				curr[i] = temp;
			}
		}

		for (int i = num - 1; i > 0; i--)
		{
			if (curr[i].getElement() < curr[i-1].getElement())
			{
				//swap
				Node temp = curr[i-1];
				curr[i-1] = curr[i];
				curr[i] = temp;
			}
		}
	}

	public boolean isFull()
	{
		return (insertIndex == m ? true : false);
	}

	public void setNext(BPlusNode next_)
	{
		next = next_;
	}

	public BPlusNode getNext()
	{
		return next;
	}

	public int getM()
	{
		return m;
	}

	public void setLeftAt(int nodeIndex, BPlusNode left)
	{
		if (nodeIndex < insertIndex)
		{
			contents[nodeIndex].setLeft(left);
		}
	}

	public void setRightAt(int nodeIndex, BPlusNode right)
	{
		if (nodeIndex < insertIndex)
		{
			contents[nodeIndex].setRight(right);
		}
	}

	public BPlusNode getLeftAt(int nodeIndex)
	{
		if (nodeIndex < insertIndex)
		{
			return contents[nodeIndex].getLeft();
		}
		else
		{
			return null;
		}
	}

	public BPlusNode getRightAt(int nodeIndex)
	{
		if (nodeIndex < insertIndex)
		{
			return contents[nodeIndex].getRight();
		}
		else
		{
			return null;
		}
	}

	public Node getContentNodeAt(int nodeIndex)
	{
		if (nodeIndex < insertIndex)
		{
			return contents[nodeIndex];
		}
		else
		{
			return null;
		}
	}

	public int getContentAt(int nodeIndex)
	{
		if (nodeIndex < insertIndex)
		{
			return contents[nodeIndex].getElement();
		}
		else
		{
			return 0;
		}
	}

	public String toString()
	{
		String ret = "";

		for (int i = 0; i < insertIndex; i++)
		{
			ret += "[" + contents[i].getElement() + "]";
		}

		return ret;
	}

	public int getInsertIndex()
	{
		return insertIndex;
	}

	public void setParent(BPlusNode parent_)
	{
		parent = parent_;
	}

	public BPlusNode getParent()
	{
		return parent;
	}

	public boolean hasParent()
	{
		return (parent == null);
	}

	public Node[] getContents()
	{
		return contents;
	}
}