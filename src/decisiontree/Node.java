package decisiontree;

import java.util.ArrayList;
import java.util.List;

public class Node
{
	private String value;
	private List<Node> children;
	private Node parent;
	private boolean isAttributeName;

	public Node()
	{
		children = new ArrayList<>();
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public List<Node> getChildren()
	{
		return children;
	}

	public void setChildren(List<Node> children)
	{
		this.children = children;
	}

	public Node getParent()
	{
		return parent;
	}

	public void setParent(Node parent)
	{
		this.parent = parent;
	}

	public boolean isAttributeName()
	{
		return isAttributeName;
	}

	public void setIsAttributeName(boolean isAttributeName)
	{
		this.isAttributeName = isAttributeName;
	}

}
