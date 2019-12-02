package Commands.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Pause extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer musicPlayer = musicManager.player;
		
		if(musicPlayer.getPlayingTrack() == null)
		{
			event.getChannel().sendMessage("What am I supposed to pause, if theres no music playing ?!").queue();
			return;
		}
		
		if(!musicPlayer.isPaused())
		{
			musicPlayer.setPaused(true);
			event.getChannel().sendMessage("Ok. Pausing the current song!").queue();
		}
		
		else
		{
			musicPlayer.setPaused(false);
			event.getChannel().sendMessage("Ok. Unpausing the current song!").queue();
		}
	}

	@Override
	public String getCmdName() 
	{
		return "Pause";
	}

	@Override
	public String getCmd() 
	{
		return "pause";
	}

	@Override
	public String getDesc() 
	{
		return "Pause or Unpause the Current Song";
	}
}
