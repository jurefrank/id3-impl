package entity;

import java.util.ArrayList;
import java.util.List;

//Contains entity
public class Set
{
	private String[] attributeNames;
	private List<Entity> allParameterEntries;

	public Set()
	{
		this(null, new ArrayList<>());
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
