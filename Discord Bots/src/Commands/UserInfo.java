package Commands;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.jagrosh.jdautilities.commons.utils.FinderUtil;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class UserInfo extends CommandTemplate
{
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		String userToFind = "";
		if(args.length == 2)
			userToFind = args[1];
		
		else if(args.length > 2)
		{
			event.getChannel().sendMessage("Sorry, I can only display one user at a time.").queue();
			return;
		}
		
		else
			userToFind = event.getAuthor().getName();
		
		List<User> matchingUsers = FinderUtil.findUsers(userToFind, event.getJDA()); // By real name & nick names
		
		if(matchingUsers.isEmpty())
		{
			List<Member> matchingMembers = FinderUtil.findMembers(userToFind, event.getGuild());
			
			if(matchingMembers.isEmpty())
			{
				event.getChannel().sendMessage("Sorry, I couldn't find anyone matching your description.").queue();
				return;
			}
			
			matchingUsers = matchingMembers.stream().map(Member :: getUser).collect(Collectors.toList()); // Convert all found Members into Users
		}
		
		User user = matchingUsers.get(0);
		Member member = event.getGuild().getMember(user);
		
		EmbedBuilder info = new EmbedBuilder();
		info.setTitle("Info for: " + user.getName());
		info.setThumbnail(user.getEffectiveAvatarUrl().replaceFirst("gif", "png"));
		info.setColor(member.getColor());
		info.addField("Username#Descriminator: ", String.format("%#s", user), false);
		info.addField("Display Name: ", member.getEffectiveName(), false);
		info.addField("User Id + Mention: ", String.format("%s (%s)", user.getId(), member.getAsMention()), false);
		info.addField("Account Created: ", user.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
		info.addField("Joined Server: ", member.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME), false);
		info.addField("Online Status: ", member.getOnlineStatus().name().toLowerCase().replaceAll("_", " "), false);
		info.addField("Bot Account: ", user.isBot() ? "✔" : "❌", false);
		
		event.getChannel().sendMessage(info.build()).queue();
		info.clear();		
	}

	@Override
	public String getCmdName() 
	{
		return "Show the User's Info";
	}

	@Override
	public String getCmd() 
	{
		return "userinfo";
	}

	@Override
	public String getDesc() 
	{
		return "Show Info about the Specified User.";
	}
}
