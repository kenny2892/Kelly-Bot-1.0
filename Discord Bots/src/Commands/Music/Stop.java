package Commands.Music;

import Commands.CommandTemplate;
import Main.KellyBot;
import MusicPlayer.GuildMusicManager;
import MusicPlayer.PlayerManager;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class Stop extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(args[0].equalsIgnoreCase(KellyBot.getPrefix() + getCmd()))
		{
			PlayerManager playerManager = PlayerManager.getInstance();
			GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
			
			musicManager.scheduler.getQueue().clear();
			musicManager.player.stopTrack();
			musicManager.player.setPaused(false);
			
			event.getChannel().sendMessage("Stopping all Music!").queue();
		}
	}

	@Override
	public String getCmdName() 
	{
		return "Stop Music";
	}

	@Override
	public String getCmd() 
	{
		return "stop";
	}

	@Override
	public String getDesc() 
	{
		return "Stop any music that is currently playing.";
	}
}
