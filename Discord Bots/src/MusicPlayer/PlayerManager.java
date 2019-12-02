package MusicPlayer;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class PlayerManager 
{
	private static PlayerManager INSTANCE;
	private final AudioPlayerManager playerManager;
	private final Map<Long, GuildMusicManager> musicManagers;
	
	private PlayerManager()
	{
		this.musicManagers = new HashMap<Long, GuildMusicManager>();
		this.playerManager = new DefaultAudioPlayerManager();
		
		AudioSourceManagers.registerRemoteSources(playerManager);
		AudioSourceManagers.registerLocalSource(playerManager);
	}
	
	public synchronized GuildMusicManager getGuildMusicManager(Guild guild)
	{
		long guildID = guild.getIdLong();
		GuildMusicManager musicManager = musicManagers.get(guildID);
		
		if(musicManager == null) // Guild doesn't already have a playlist made
		{
			musicManager = new GuildMusicManager(playerManager);
			musicManagers.put(guildID, musicManager);
		}
		
		guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());
		
		return musicManager;
	}
	
	public void loadAndPlay(TextChannel channel, String songURL)
	{
		GuildMusicManager musicManager = getGuildMusicManager(channel.getGuild());
		
		playerManager.loadItemOrdered(musicManager, songURL, new AudioLoadResultHandler() 
		{
			@Override
			public void trackLoaded(AudioTrack track) 
			{
				channel.sendMessage("Adding " + track.getInfo().title + " to the playlist!").queue();
				play(musicManager, track);
			}
			
			@Override
			public void playlistLoaded(AudioPlaylist playlist) 
			{
				AudioTrack first = playlist.getSelectedTrack();
				
				if(first == null)
					first = playlist.getTracks().remove(0);
				
				channel.sendMessage("Adding " + first.getInfo().title + " to the playlist: " + playlist.getName()).queue();
				play(musicManager, first);
				
				playlist.getTracks().forEach(musicManager.scheduler::queue);
			}
			
			@Override
			public void noMatches() 
			{
				channel.sendMessage(songURL + " isn't in the playlist!").queue();
			}
			
			@Override
			public void loadFailed(FriendlyException exception) 
			{
				channel.sendMessage(songURL + " couldn't be played!").queue();
			}
		});
	}
	
	private void play(GuildMusicManager musicManager, AudioTrack track)
	{
		musicManager.scheduler.queue(track);
	}
	
	public static synchronized PlayerManager getInstance()
	{
		if(INSTANCE == null)
			INSTANCE = new PlayerManager();
		
		return INSTANCE;
	}
}
