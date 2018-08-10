package entity;

import java.util.List;

public class Set
{
	private String[] attributeNames;
	private List<Entity> allParameterEntries;

	public Set()
	{
		this(null, null);
	}

	public Set(String[] attributeNames, List<Entity> allEntries)
	{
		this.attributeNames = attributeNames;
		this.allParameterEntries = allEntries;
	}

	public List<Entity> getAllParameterEntries()
	{
		return allParameterEntries;
	}

	public void setAllParameterEntries(List<Entity> allEntries)
	{
		this.allParameterEntries = allEntries;
	}

	public String[] getAttributeNames()
	{
		return attributeNames;
	}

	public void setAttributeNames(String[] attributeNames)
	{
		this.attributeNames = attributeNames;
	}

	public void addParameters(Entity entity)
	{
		allParameterEntries.add(entity);
	}

}
