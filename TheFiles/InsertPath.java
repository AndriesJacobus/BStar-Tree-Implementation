public class InsertPath
{
	String insertPath = "";

	public InsertPath()
	{

	}
	
	public InsertPath(String insertPath_)
	{
		insertPath = insertPath_;
	}

	public String getInsertPath()
	{
		return insertPath;
	}

	public void setInsertPath(String insertPath_)
	{
		insertPath = insertPath_;
	}

	public void addToPath(String insertPathAdd_)
	{
		insertPath += insertPathAdd_;
	}
}