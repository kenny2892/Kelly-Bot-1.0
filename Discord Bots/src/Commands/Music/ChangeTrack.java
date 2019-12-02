package Commands.Music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import MusicPlayer.TrackScheduler;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class ChangeTrack extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event)
	{
		if(args.length != 2)
		{
			event.getChannel().sendMessage("You need to tell me what track number to switch to.").queue();
			return;
		}
		
		else if(!numCheck(args[1]))
		{
			event.getChannel().sendMessage("That isn't even a number?!").queue();
			return;
		}
		
		int trackNum = Integer.parseInt(args[1]);
		if(trackNum < 0)
		{
			event.getChannel().sendMessage("Ha Ha Ha, very funny. Give me a positive number.").queue();
			return;
		}
		
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		TrackScheduler scheduler = musicManager.scheduler;
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
		
		if(queue.isEmpty())
		{
			event.getChannel().sendMessage("The playlist is Empty!").queue();
			return;
		}
		
		List<AudioTrack> tracks = new ArrayList<AudioTrack>(queue);
		
		if(trackNum > tracks.size())
		{
			event.getChannel().sendMessage("That isn't a valid index number. Try again with a number from 0 - " + tracks.size()).queue();
			return;
		}
		
		for(int i = 0; i < trackNum; i++)
			scheduler.nextTrack();
		
		event.getChannel().sendMessage("Ok. Skipping to that song!").queue();
	}

	@Override
	public String getCmdName() 
	{
		return "Change Track";
	}

	@Override
	public String getCmd() 
	{
		return "skipto";
	}

	@Override
	public String getDesc() 
	{
		return "Change which track is currently playing. To get the index number, use the \"playlist\" command.";
	}
	
	private boolean numCheck(String input)
	{
		try
		{
			Integer.parseInt(input);
			return true;
		}
		
		catch(Exception e)
		{
			return false;
		}
	}
}
