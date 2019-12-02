package Commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import Commands.Music.ChangeTrack;
import Commands.Music.JoinVC;
import Commands.Music.LeaveVC;
import Commands.Music.NowPlaying;
import Commands.Music.Pause;
import Commands.Music.Play;
import Commands.Music.SetPosition;
import Commands.Music.SetVolume;
import Commands.Music.ShowPlaylist;
import Commands.Music.Skip;
import Commands.Music.Stop;
import Main.KellyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter
{
	public enum Types
	{
		Clear, FindReference, Greeting, Help, SetPrefix, SetStatus,
		JoinVC, LeaveVC, Play, Stop, ShowPlaylist, Skip, ShowPlaying, Pause, SetVolume, SkipTo, SetPos, YTSearch,
		UserInfo,
		Whitelist;
	}
	
	private CommandTemplate clear, reference, greeting, help, prefix, status, whitelist;
	private CommandTemplate joinVC, leaveVC, play, stop, showPlaylist, skip, playing, pause, ytSearch, volume, skipTo, pos;
	private CommandTemplate userInfo;
	private GuildMessageReceivedEvent defaultEvent;
	
	public Commands()
	{
		clear = new Clear();
		reference = new FindReference();
		greeting = new Greeting();
		help = new Help();
		prefix = new SetPrefix();
		status = new SetStatus();
		whitelist = new Whitelist();
		joinVC = new JoinVC();
		leaveVC = new LeaveVC();
		play = new Play();
		stop = new Stop();
		showPlaylist = new ShowPlaylist();
		skip = new Skip();
		playing = new NowPlaying();
		pause = new Pause();
		ytSearch = new YTSearch();
		volume = new SetVolume();
		skipTo = new ChangeTrack();
		pos = new SetPosition();
		userInfo = new UserInfo();
		
		defaultEvent = null;
	}
	
	public final void useCmd(String cmdName, String ... inputs)
	{
		if(defaultEvent == null && KellyBot.isOn())
			defaultEvent = KellyBot.createDefaultEvent();
		
		CommandTemplate cmd = findCmd(getCmdType(cmdName));
		
		String[] args = new String[inputs.length + 1];
		args[0] = cmdName;
		
		for(int i = 0; i < inputs.length; i++)
			args[i + 1] = inputs[i];
		
		if(cmd != null || whitelistCheck(null, cmd))
			cmd.hook(args, defaultEvent);
	}
	
	public final void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].startsWith(KellyBot.getPrefix()))
		{
			CommandTemplate cmd = findCmd(getCmdType(args[0].substring(KellyBot.getPrefix().length())));
			
			if(cmd != null || whitelistCheck(event, cmd))
				cmd.hook(args, event);
			
			else
			{
				EmbedBuilder noSuchCommand = new EmbedBuilder();
				noSuchCommand.setTitle("Error: That's not a Command");
				noSuchCommand.setDescription(" I don't know any commands with that name. Please check " + KellyBot.getPrefix() + "help");
				noSuchCommand.setColor(Color.red);

				event.getChannel().sendMessage(noSuchCommand.build()).queue();
				noSuchCommand.clear();
			}
		}
	}
	
	public boolean setWhitelist(Types cmdType, ArrayList<String> whitelist)
	{
		if(whitelist == null)
			return false;
		
		CommandTemplate cmd = findCmd(cmdType);
		
		if(cmd == null)
			return false;
		
		cmd.setWhite(whitelist);
		return true;
	}
	
	public boolean removeFromWhitelist(Types cmdType, GuildMessageReceivedEvent event, String role)
	{
		if(whitelist == null)
			return false;
		
		CommandTemplate cmd = findCmd(cmdType);
		
		if(cmd == null)
			return false;
		
		cmd.removeWhite(role, event);
		return true;
	}
	
	public boolean addToWhitelist(Types cmdType, GuildMessageReceivedEvent event, String role)
	{
		if(whitelist == null)
			return false;
		
		CommandTemplate cmd = findCmd(cmdType);
		
		if(cmd == null)
			return false;
		
		cmd.addWhite(role, event);
		return true;
	}
	
	public final Types getCmdType(String input)
	{
		input = input.toLowerCase();
		
		if(input.equals("clear") || input.equals("clr"))
			return Types.Clear;
		
		else if(input.equals("ref") || input.equals("reference") || input.equals("findreference"))
			return Types.FindReference;
		
		else if(input.equals("hi") || input.equals("hello") || input.equals("greetings") || input.equals("hola"))
			return Types.Greeting;
		
		else if(input.equals("help"))
			return Types.Help;
		
		else if(input.equals("prefix") || input.equals("setprefix"))
			return Types.SetPrefix;
		
		else if(input.equals("status"))
			return Types.SetStatus;
		
		else if(input.equals("joinvc") || input.equals("jvc"))
			return Types.JoinVC;
		
		else if(input.equals("leavevc") || input.equals("lvc"))
			return Types.LeaveVC;
		
		else if(input.equals("play"))
			return Types.Play;
		
		else if(input.equals("stop"))
			return Types.Stop;
		
		else if(input.equals("showplay") || input.equals("showplaylist") || input.equals("playlist"))
			return Types.ShowPlaylist;
		
		else if(input.equals("nowplaying") || input.equals("curr") || input.equals("showplaying"))
			return Types.ShowPlaying;
		
		else if(input.equals("skip") || input.equals("next"))
			return Types.Skip;
		
		else if(input.equals("pause") || input.equals("unpause"))
			return Types.Pause;
		
		else if(input.equals("vol") || input.equals("setvolume"))
			return Types.SetVolume;
		
		else if(input.equals("track") || input.equals("skipto"))
			return Types.SkipTo;
		
		else if(input.equals("ytsearch") || input.equals("yt"))
			return Types.YTSearch;
		
		else if(input.equals("pos") || input.equals("setpos"))
			return Types.SetPos;
		
		else if(input.equals("userinfo"))
			return Types.UserInfo;
		
		else if(input.equals("whitelist"))
			return Types.Whitelist;
		
		return null;
	}
	
	private CommandTemplate findCmd(Types cmd)
	{
		if(cmd == null)
			return null;
		
		switch(cmd)
		{
			case Clear:
				return this.clear;
				
			case FindReference:
				return this.reference;
				
			case Greeting:
				return this.greeting;
				
			case Help:
				return this.help;
				
			case SetPrefix:
				return this.prefix;
				
			case SetStatus:
				return this.status;
				
			case JoinVC:
				return this.joinVC;
				
			case LeaveVC:
				return this.leaveVC;
				
			case Play:
				return this.play;
				
			case Stop:
				return this.stop;
				
			case ShowPlaylist:
				return this.showPlaylist;
				
			case Skip:
				return this.skip;
				
			case ShowPlaying:
				return this.playing;
				
			case Pause:
				return this.pause;
				
			case YTSearch:
				return this.ytSearch;
				
			case SetVolume:
				return this.volume;
				
			case SkipTo:
				return this.skipTo;
				
			case SetPos:
				return this.pos;
				
			case UserInfo:
				return this.userInfo;
				
			case Whitelist:
				return this.whitelist;
		}
		
		return null;
	}
	
	public final boolean whitelistCheck(GuildMessageReceivedEvent event, CommandTemplate cmd)
	{
		if(cmd == null)
			return false;
		
		else if(!cmd.hasWhitelist() || event == null)
			return true;
		
		List<Role> roles = event.getMember().getRoles();
		
		boolean whitelistCheck = false;
		for(Role role : roles)
		{
			if(cmd.inWhiteList(role.getName()))
				whitelistCheck = true;
		}
		
		return whitelistCheck;
	}
	
	public final CommandTemplate getCmdClone(Types cmdType)
	{
		CommandTemplate cmd = findCmd(cmdType);
		
		if(cmd == null)
			return null;
		
		try
		{
			cmd = (CommandTemplate) cmd.clone();
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return cmd;
	}
	
	public final String getCmd(Types cmd)
	{
		return findCmd(cmd).getCmd();
	}
	
	public final String getName(Types cmd)
	{
		return findCmd(cmd).getCmdName();
	}
	
	public final String getDesc(Types cmd)
	{
		return findCmd(cmd).getDesc();
	}
	
	public final String getWhitelistAsString(Types cmd)
	{
		return findCmd(cmd).getWhitelistAsStr();
	}
	
	public final ArrayList<CommandTemplate> cmdClones()
	{
		ArrayList<CommandTemplate> cmds = new ArrayList<CommandTemplate>();
		
		try
		{
			cmds.add((CommandTemplate) help.clone());
			cmds.add((CommandTemplate) clear.clone());
			cmds.add((CommandTemplate) reference.clone());
			cmds.add((CommandTemplate) greeting.clone());
			cmds.add((CommandTemplate) prefix.clone());
			cmds.add((CommandTemplate) status.clone());
			cmds.add((CommandTemplate) joinVC.clone());
			cmds.add((CommandTemplate) leaveVC.clone());
			cmds.add((CommandTemplate) play.clone());
			cmds.add((CommandTemplate) stop.clone());
			cmds.add((CommandTemplate) showPlaylist.clone());
			cmds.add((CommandTemplate) skip.clone());
			cmds.add((CommandTemplate) playing.clone());
			cmds.add((CommandTemplate) pause.clone());
			cmds.add((CommandTemplate) ytSearch.clone());
			cmds.add((CommandTemplate) volume.clone());
			cmds.add((CommandTemplate) skipTo.clone());
			cmds.add((CommandTemplate) pos.clone());
			cmds.add((CommandTemplate) userInfo.clone());
			cmds.add((CommandTemplate) whitelist.clone());
		}

		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return cmds;
	}
	
	public void setDefaultChannel(GuildMessageReceivedEvent event)
	{
		if(event != null)
			defaultEvent = event;
	}
}
