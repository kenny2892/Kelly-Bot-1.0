package Commands.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import MusicPlayer.TrackScheduler;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Skip extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		TrackScheduler scheduler = musicManager.scheduler;
		AudioPlayer musicPlayer = musicManager.player;
		
		if(musicPlayer.getPlayingTrack() == null)
		{
			event.getChannel().sendMessage("What am I supposed to skip, if theres no music playing?!").queue();
			return;
		}
		
		scheduler.nextTrack();
		event.getChannel().sendMessage("Ok. Skipping to the next song!").queue();
	}

	@Override
	public String getCmdName() 
	{
		return "Skip a Song";
	}

	@Override
	public String getCmd() 
	{
		return "skip";
	}

	@Override
	public String getDesc() 
	{
		return "Skip a song in the playlist.";
	}
}
