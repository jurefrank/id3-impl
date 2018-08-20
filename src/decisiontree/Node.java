package decisiontree;

public class Node
{
	private String value;
	private Node[] children;

	public Node()
	{

	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public Node[] getChildren()
	{
		return children;
	}

	public void setChildren(Node[] children)
	{
		this.children = children;
	}

}
