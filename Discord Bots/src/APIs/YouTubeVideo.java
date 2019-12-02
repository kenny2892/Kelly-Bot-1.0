package APIs;

public class YouTubeVideo 
{
	private String title;
	private String desc;
	private String url;
	private String thumbnailURL;
	
	public YouTubeVideo(String title, String desc, String url, String thumbnailURL)
	{
		this.title = title.replace("&#39;", "'");
		this.desc = desc;
		this.url = url;
		this.thumbnailURL = thumbnailURL;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getDesc() 
	{
		return desc;
	}

	public String getURL() 
	{
		return url;
	}

	public String getThumbnailURL() 
	{
		return thumbnailURL;
	}
}
