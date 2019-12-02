package Commands.Music;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class ShowPlaylist extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
		
		if(queue.isEmpty())
		{
			event.getChannel().sendMessage("The playlist is Empty!").queue();
			return;
		}
		
		List<AudioTrack> tracks = new ArrayList<AudioTrack>(queue);
		int countShown = Math.min(tracks.size(), 20);		
		
		EmbedBuilder shownTracks = new EmbedBuilder();
		shownTracks.setTitle("Playlist:");
		shownTracks.setDescription("Total Songs: " + tracks.size());
		
		if(tracks.size() > 20)
			shownTracks.setDescription("Total Songs: " + tracks.size() + " (Only showing the next 20)");			
		
		shownTracks.setColor(Color.blue);
		
		for(int i = 0; i < countShown; i++)
		{
			AudioTrack track = tracks.get(i);
			AudioTrackInfo info = track.getInfo();
			
			shownTracks.appendDescription("\n" + (i + 1) + ". " + info.title + " - " + info.author);
		}
		
		event.getChannel().sendMessage(shownTracks.build()).queue();
		shownTracks.clear();
	}

	@Override
	public String getCmdName() 
	{
		return "Show the Playlist";
	}

	@Override
	public String getCmd() 
	{
		return "showPlaylist";
	}

	@Override
	public String getDesc() 
	{
		return "Show what songs are queued up in the playlist.";
	}
}
