package Commands.Music;

import Commands.CommandTemplate;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

public class JoinVC extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		TextChannel channel = event.getChannel();
		AudioManager audioMngr = event.getGuild().getAudioManager();
		
		if(audioMngr.isConnected()) // Already in a vc
		{
			channel.sendMessage("Sorry, I'm already in a Voice Channel!").queue();
			return;
		}
		
		GuildVoiceState memberVoiceState = event.getMember().getVoiceState();
		
		if(!memberVoiceState.inVoiceChannel())
		{
			channel.sendMessage("Sorry, but your not in a Voice Channel").queue();
			return;
		}
		
		VoiceChannel vc = memberVoiceState.getChannel();
		Member myself = event.getGuild().getSelfMember();
		
		if(!myself.hasPermission(vc, Permission.VOICE_CONNECT))
		{
			channel.sendMessage("Sorry, I don't have permission to join that Voice Channel").queue();
			return;
		}
		
		audioMngr.openAudioConnection(vc);
		channel.sendMessage("Cool Beans! I'm joining the vc!").queue();
	}

	@Override
	public String getCmdName() 
	{
		return "Join Voice Channel";
	}

	@Override
	public String getCmd() 
	{
		return "joinVC";
	}

	@Override
	public String getDesc() 
	{
		return "Invite me into your voice channel!";
	}
	
}
