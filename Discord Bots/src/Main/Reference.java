package Main;

import java.util.ArrayList;

public class Reference 
{
	private String link;
	private String title;
	private String keyPhrase;
	private ArrayList<String> tags = new ArrayList<String>();

	public Reference(String keyPhrase, String title, String link, String ... tags)
	{
		this.link = link;
		this.title = title;
		this.keyPhrase = keyPhrase;
		
		for(String tag : tags)
			if(tag != null && tag.length() != 0)
				this.tags.add(tag);
	}

	public String getLink() 
	{
		return link;
	}

	public void setLink(String link) 
	{
		if(link != null && link.length() != 0)
			this.link = link;
	}

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		if(title != null && title.length() != 0)
			this.title = title;
	}

	public String getKeyPhrase() 
	{
		return keyPhrase;
	}

	public void setKeyPhrase(String keyPhrase) 
	{
		if(keyPhrase != null && keyPhrase.length() != 0)
			this.keyPhrase = keyPhrase;
	}
	
	public String getTagsAsString()
	{
		String results = "";
		
		for(String tag : tags)
			results += tag;
		
		return results.toLowerCase();			
	}
	
	public ArrayList<String> getTags()
	{
		return tags;
	}
	
	public void addTag(String tag)
	{
		if(tag != null && tag.length() != 0)
			tags.add(tag);
	}
}
