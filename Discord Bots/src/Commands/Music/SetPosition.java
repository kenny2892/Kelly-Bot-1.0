package Commands.Music;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import Commands.CommandTemplate;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetPosition extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		long pos = 0;
		
		if(args.length != 2)
		{
			event.getChannel().sendMessage("I can't change the position of the song, if no song is playing!").queue();
			return;
		}
		
		pos = numCheck(args[1]);
		if(pos < 0)
		{
			event.getChannel().sendMessage("That's not a number!").queue();
			return;
		}
		
		PlayerManager playerManager = PlayerManager.getInstance();
		GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
		AudioPlayer musicPlayer = musicManager.player;
		
		if(musicPlayer.getPlayingTrack() == null)
		{
			event.getChannel().sendMessage("Absolutely Nothing is Playing").queue();
			return;
		}
		
		if(pos > musicPlayer.getPlayingTrack().getDuration())
		{
			event.getChannel().sendMessage("That's to long of a number.").queue();
			return;
		}
		
		musicPlayer.getPlayingTrack().setPosition(pos);
		event.getChannel().sendMessage("Ok, moved to that position in the song.").queue();
	}

	@Override
	public String getCmdName() 
	{
		return "Set Position";
	}

	@Override
	public String getCmd() 
	{
		return "pos";
	}

	@Override
	public String getDesc() 
	{
		return "Skip through the current song.";
	}
	
	private long numCheck(String input)
	{
		try
		{
			if(!input.contains(":"))
			{
				if(input.length() < 2)
					input = "0" + input;
				
				// Source: https://stackoverflow.com/questions/8826270/how-to-convert-hhmmss-sss-to-milliseconds
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				format.setTimeZone(TimeZone.getTimeZone("UTC"));
				
				String time = "00:00:" + input + ".000";
				Date date = format.parse("1970-01-01 " + time);

				return date.getTime();
			}
			
			else
			{
				String[] parts = input.split(":");
				
				String hours = "";
				String mins = "";
				String secs = "";
				
				if(parts.length == 2)
				{
					hours = "00:";
					
					mins = parts[0];
					if(mins.length() < 2)
						mins = "0" + mins;
					
					secs = parts[1];
					if(secs.length() < 2)
						secs = "0" + secs;
				}
				
				else if(parts.length == 3)
				{
					hours = parts[0];
					if(hours.length() < 2)
						hours = "0" + hours;
					hours = hours + ":";
					
					mins = parts[1];
					if(mins.length() < 2)
						mins = "0" + mins;
					
					secs = parts[2];
					if(secs.length() < 2)
						secs = "0" + secs;
				}
				
				else
					return -1;
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				format.setTimeZone(TimeZone.getTimeZone("UTC"));
				
				String time = hours + mins + ":" + secs + ".000";
				Date date = format.parse("1970-01-01 " + time);

				return date.getTime();
			}
		}
		
		catch(Exception e)
		{
			return -1;
		}
	}
}
