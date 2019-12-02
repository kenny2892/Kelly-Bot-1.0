package Commands;

import APIs.YouTubeAPI;
import APIs.YouTubeVideo;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class YTSearch extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(args.length < 2)
		{
			event.getChannel().sendMessage("You need to tell me what to search for!").queue();
			return;
		}
		
		YouTubeAPI api = YouTubeAPI.getInstance();
		
		String terms = "";
		for(int i = 1; i < args.length; i++)
			terms += args[i] + " ";
		
		YouTubeVideo vidResult = api.getSearchResult(terms);
		
		if(vidResult == null)
		{
			event.getChannel().sendMessage("Sorry, I couldn't find anything matching your search.").queue();
			return;
		}
		
		event.getChannel().sendMessage("Here's your video: " + vidResult.getURL()).queue();
	}

	@Override
	public String getCmdName() 
	{
		return "Search for a YouTube Video";
	}

	@Override
	public String getCmd() 
	{
		return "ytsearch";
	}

	@Override
	public String getDesc() 
	{
		return "Search for a YouTube Video and get the First Result.";
	}
}
