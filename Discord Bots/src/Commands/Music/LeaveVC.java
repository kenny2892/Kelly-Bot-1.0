package Commands.Music;

import Commands.CommandTemplate;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class LeaveVC extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		TextChannel channel = event.getChannel();
		AudioManager audioMngr = event.getGuild().getAudioManager();
		
		if(!audioMngr.isConnected()) // Not in a vc
		{
			channel.sendMessage("You do realize I'm not even IN a vc, right?").queue();
			return;
		}
		
		VoiceChannel vc = audioMngr.getConnectedChannel();
		
		if(!vc.getMembers().contains(event.getMember()))
		{
			channel.sendMessage("Sorry, but I'll only leave this VC if you join.").queue();
			return;
		}
		
		audioMngr.closeAudioConnection();
		channel.sendMessage("WELP, I guess I'm no longer wanted here!").queue();
	}

	@Override
	public String getCmdName() 
	{
		return "Leave VC";
	}

	@Override
	public String getCmd() 
	{
		return "leaveVC";
	}

	@Override
	public String getDesc() 
	{
		return "Kick me out of your Voice Channel ... not like I wanted to be there to begin with Baka.";
	}
}
