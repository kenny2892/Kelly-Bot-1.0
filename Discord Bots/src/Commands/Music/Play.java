package Commands.Music;

import java.net.URL;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import APIs.YouTubeAPI;
import APIs.YouTubeVideo;
import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

// Tutorial from: https://www.youtube.com/watch?v=nRu6AgOFvqo&t=113s

public class Play extends CommandTemplate
{
	private static int volume = 50;
	
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event)
	{
		if(args.length < 2)
		{
			event.getChannel().sendMessage("How am I supposed to play a Requested song if you don't Request a song?!").queue();
			return;
		}
		
		else if(!isURL(args[1]))
		{
			String terms = "";
			for(int i = 1; i < args.length; i++)
				terms += args[i];
			
			YouTubeAPI api = YouTubeAPI.getInstance();
			YouTubeVideo video = api.getSearchResult(terms);			
			
			if(video == null)
			{
				event.getChannel().sendMessage("That's not a valid URL & I couldn't find any results for it on YouTube! Please try again.").queue();
				return;
			}
			
			args[1] = video.getURL();
		}
		
		PlayerManager playerManager = PlayerManager.getInstance();
		
		playerManager.loadAndPlay(event.getChannel(), args[1]);
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer musicPlayer = musicManager.player;
		musicManager.player.setVolume(volume);
		
		if(args[1].matches(".*?t=.*")) // Example: ?t=185
		{
			String num = args[1].substring(args[1].indexOf("?t=") + 3);
			long mills = convSecToMills(num);
			
			AudioTrack playing = musicPlayer.getPlayingTrack();
			
			if(playing == null)
			{
				try 
				{
					Thread.sleep(1000);
					playing = musicPlayer.getPlayingTrack();
				} 
				
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
			}
			
			if(playing != null)
				if(mills <= playing.getDuration())
					musicPlayer.getPlayingTrack().setPosition(mills);			
		}
	}

	@Override
	public String getCmdName() 
	{
		return "Play music!";
	}

	@Override
	public String getCmd()
	{
		return "play";
	}

	@Override
	public String getDesc()
	{
		return "Have me play some sick beats! You can either give me a URL or give me some search terms to find the song on YouTube.";
	}
	
	private boolean isURL(String input)
	{
		try
		{
			new URL(input);
			return true;
		}
		
		catch(Exception e)
		{
			return false;
		}
	}
	
	private long convSecToMills(String input)
	{
		try
		{
			long secs = Integer.parseInt(input);
			long mills = secs * 1000;			
			
			return mills;
		}
		
		catch(Exception e)
		{
			return -1;
		}
	}
	
	public static void setVol(int vol)
	{
		if(vol > -1 && vol < 101)
			volume = vol;
	}
}
