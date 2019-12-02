package Commands.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetVolume extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(args.length < 2)
		{
			event.getChannel().sendMessage("How am I supposed to change the volume if no song is even playing?!").queue();
			return;
		}
		
		else if(args.length > 2)
		{
			event.getChannel().sendMessage("Wait ... what are you trying to tell me to do? Try again with just 1 number.").queue();
			return;
		}
		
		else if(!numCheck(args[1]))
		{
			event.getChannel().sendMessage("Wait ... what are you trying to tell me to do? Try again with an actual number.").queue();
			return;
		}
		
		int volume = Integer.parseInt(args[1]);
		
		if(volume > 100)
		{
			event.getChannel().sendMessage("I hate to break it to you, but I can't set the volume that high. Try again with a number from 0 - 100").queue();
			return;
		}
		
		else if(volume < 0)
		{
			event.getChannel().sendMessage("I hate to break it to you, but I can't set the volume that low. Try again with a number from 0 - 100").queue();
			return;
		}
		
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer musicPlayer = musicManager.player;
		
		if(musicPlayer.getPlayingTrack() == null)
		{
			event.getChannel().sendMessage("What am I supposed to change the volume of, if theres no music playing ?!").queue();
			return;
		}
		
		musicPlayer.setVolume(volume);
		Play.setVol(volume);
		event.getChannel().sendMessage("Ok, changeing the volume to: " + volume).queue();
	}

	@Override
	public String getCmdName() 
	{
		return "Set Volume";
	}

	@Override
	public String getCmd() 
	{
		return "vol";
	}

	@Override
	public String getDesc() 
	{
		return "Change the volume of the playlist. Type in a number from 0 - 100";
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
