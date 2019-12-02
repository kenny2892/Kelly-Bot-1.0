package Commands.Music;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class NowPlaying extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer musicPlayer = musicManager.player;
		
		if(musicPlayer.getPlayingTrack() == null)
		{
			event.getChannel().sendMessage("Absolutely Nothing is Playing").queue();
			return;
		}
		
		AudioTrackInfo info = musicPlayer.getPlayingTrack().getInfo();
		
		EmbedBuilder currSong = new EmbedBuilder();
		currSong.setTitle(String.format("Current Song: [%s] (%s)", info.title, info.uri));
		
		if(musicPlayer.isPaused())
			currSong.setDescription("⏸");
		
		else
			currSong.setDescription("▶");
		
		currSong.appendDescription(" " + getTime(musicPlayer.getPlayingTrack().getPosition()) + " / " + getTime(musicPlayer.getPlayingTrack().getDuration()));
		currSong.appendDescription("\nLink: " + info.uri);
		
		currSong.setColor(Color.red);
		
		event.getChannel().sendMessage(currSong.build()).queue();
		currSong.clear();
	}

	@Override
	public String getCmdName() 
	{
		return "Now Playing";
	}

	@Override
	public String getCmd() 
	{
		return "nowplaying";
	}

	@Override
	public String getDesc() 
	{
		return "Show what song is currently playing.";
	}
	
	private String getTime(long millis) // Source: https://stackoverflow.com/questions/4142313/convert-timestamp-in-milliseconds-to-string-formatted-time-in-java
	{
		Date date = new Date(millis);
		DateFormat format = new SimpleDateFormat("HH:mm:ss.SSS");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return format.format(date);
	}
}
