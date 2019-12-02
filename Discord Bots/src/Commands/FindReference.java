package Commands;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import Main.KellyBot;
import Main.Reference;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class FindReference extends CommandTemplate
{
	private ArrayList<Reference> references = new ArrayList<Reference>();
	
	public FindReference() 
	{
		// SAO Abridged
		// Episode 1
		references.add(new Reference("SAOBurn", "Burn This Fucker to the Ground", "https://youtu.be/V6kJKxvbgZ0?t=23", "SAO Abriged", "SAO", "Burn"));
		references.add(new Reference("SAOPebble", "For You See", "https://youtu.be/V6kJKxvbgZ0?t=77", "SAO Abriged", "SAO", "Pebble"));
		references.add(new Reference("BallsDeep69", "BallsDeep69", "https://youtu.be/V6kJKxvbgZ0?t=123", "SAO Abriged", "SAO", "BallsDeep69"));
		references.add(new Reference("AltF4ThisShit", "Alt F4 This Shit", "https://youtu.be/V6kJKxvbgZ0?t=145", "SAO Abriged", "SAO", "Alt F4", "I can't Alt F4 this shit"));
		references.add(new Reference("SAOHA", "HAHA", "https://youtu.be/V6kJKxvbgZ0?t=161", "SAO Abriged", "SAO", "HA"));
		references.add(new Reference("SAOPerson", "Oh it's a person", "https://youtu.be/V6kJKxvbgZ0?t=221", "SAO Abriged", "SAO", "its a person", "it's a person"));
		references.add(new Reference("SAOTron", "How many of you have seen Tron?", "https://youtu.be/V6kJKxvbgZ0?t=254", "SAO Abriged", "SAO", "How many of you have seen Tron", "seen Tron"));
		references.add(new Reference("SAOScanners", "How many of you have seen Scanners?", "https://youtu.be/V6kJKxvbgZ0?t=283", "SAO Abriged", "SAO", "How many of you have seen Scanners", "Seen Scanners", "Scanners"));
		references.add(new Reference("SAOWhy", "Why would you do such a thing?", "https://youtu.be/V6kJKxvbgZ0?t=300", "SAO Abriged", "SAO", "Why would you do such a thing", "y would you do such a thing", "Steven is that you"));
		references.add(new Reference("SAOBeatMMO", "So you want us to beat an MMO?", "https://youtu.be/V6kJKxvbgZ0?t=329", "SAO Abriged", "SAO", "So you want us to beat an MMO", "So u want us to beat an MMO"));
		references.add(new Reference("SAOFuckU", "Fuck You!", "https://youtu.be/V6kJKxvbgZ0?t=332", "SAO Abriged", "SAO", "Fuck You", "Fuck u", "hostility here", "everquest"));
		references.add(new Reference("SAONoGender", "LOVE KNOWS NO GENDER!", "https://youtu.be/V6kJKxvbgZ0?t=358", "SAO Abriged", "SAO", "love knows no gender", "your not a girl", "im ok with this", "i'm ok with this"));
		references.add(new Reference("SAO20Somethings", "I have peeled away your petty facades", "https://youtu.be/V6kJKxvbgZ0?t=371", "SAO Abriged", "SAO", "peeled away your facade", "I've peeled away your", "ive peeled away your", "fairly attractive 20 somethings"));
		references.add(new Reference("SAOStereotypes", "Way to break down stereotypes", "https://youtu.be/V6kJKxvbgZ0?t=384", "SAO Abriged", "SAO", "stereotypes", "cept you fatty", "way to bring down the curve"));
		references.add(new Reference("SAODie", "If you die in the game, you die for real", "https://youtu.be/V6kJKxvbgZ0?t=388", "SAO Abriged", "SAO", "die for real", "they live, and then they stop", "they live and then they stop", "keep that tabbed"));
		references.add(new Reference("SAOProfanityFilter", "I've disabled the profanity filter", "https://youtu.be/V6kJKxvbgZ0?t=422", "SAO Abriged", "SAO", "ive disabled the profanity filter", "have fun with that", "profanity filter"));
		references.add(new Reference("SAOFucked", "WE'RE FUCKED!", "https://youtu.be/V6kJKxvbgZ0?t=429", "SAO Abriged", "SAO", "were fucked", "we're fucked"));
		references.add(new Reference("SAOTalkWithKlein", "Kirito talks to Ballsy", "https://youtu.be/V6kJKxvbgZ0?t=439", "SAO Abriged", "SAO", "really, you need my help", "really, u need my help"
									 , "really u need my help", "i need some cannon fodder", "as tempting as that sounds", "well, monkeys and typewriters", "well monkeys and typewriters", "in any case", "most unbearable asshole"));
		references.add(new Reference("SAOBoar", "Well screw you too!", "https://youtu.be/V6kJKxvbgZ0?t=474", "SAO Abriged", "SAO", "screw you too", "screw u too", "kill a boar", "he called me an asshole"));
		
		// Episode 2
		references.add(new Reference("SAOStandsInFire", "Why would you just stand in fire?", "https://youtu.be/Ye2u1za7Pac?t=26", "SAO Abriged", "SAO", "y would u just stand in fire", "stand in fire"));
		references.add(new Reference("SAOLife&Death", "You either live, or you die", "https://youtu.be/Ye2u1za7Pac?t=36", "SAO Abriged", "u either live or u die", "silent gods", "bullshit metaphors with", "difference between a simile and a metaphor", "tininess of his"));		
		references.add(new Reference("SAO2000People", "2000 PEOPLE ARE DEAD!", "https://youtu.be/Ye2u1za7Pac?t=100", "SAO Abriged", "SAO", "2000 people", "there are a hundred floors", "there r a hundred floors"));
		references.add(new Reference("SAOBetaTesters", "BETA TESTERS?!", "https://youtu.be/Ye2u1za7Pac?t=127", "SAO Abriged", "SAO", "beta testers"));
		references.add(new Reference("SAOEvidence", "Pfft! Evidence.", "https://youtu.be/V6kJKxvbgZ0?t=161", "SAO Abriged", "SAO", "any evidence to back that up", "don't need no evidence", "dont need no evidence", "don't need evidence", "dont need evidence", "isnt that right jesus", "isn't that right jesus"));
		references.add(new Reference("SAOHA", "HAHA", "https://youtu.be/V6kJKxvbgZ0?t=161", "SAO Abriged", "SAO", "HA"));
	}
	
	@Override
	public void hook(String[] args, GuildMessageReceivedEvent event) 
	{
		if(args.length == 2) // ~ref keyPhrase
		{
			int index = findIndex(args[1]);
			
			if(index == -1)
			{
				EmbedBuilder noRef = new EmbedBuilder();
				noRef.setTitle("Error: I know that one!");
				noRef.setDescription("I don't recognize that reference! Check my database to see the key phrases I know with: " + KellyBot.getPrefix() + "showRefs");
				noRef.setColor(Color.red);

				event.getChannel().sendMessage(noRef.build()).queue();
				noRef.clear();
			}
			
			else
			{
				Reference ref = references.get(index);
				
				event.getChannel().sendTyping();
				event.getChannel().sendMessage("__" + ref.getTitle() + ":__\n" + ref.getLink()).queue();
			}
		}
		
		else if(args.length < 2) // No 2nd Arg
		{
			EmbedBuilder notEnoughArgs = new EmbedBuilder();
			notEnoughArgs.setTitle("Error: Improper Input");
			notEnoughArgs.setDescription("😕 You didn't tell me what references to show! Try typing: " + KellyBot.getPrefix() + "ref [reference name]");
			notEnoughArgs.setColor(Color.red);

			event.getChannel().sendMessage(notEnoughArgs.build()).queue();
			notEnoughArgs.clear();
		}
		
		else if(args.length > 2) // More than 2 Args
		{
			EmbedBuilder toMany = new EmbedBuilder();
			toMany.setTitle("Error: To Much Stuff, Not Enough Space!");
			toMany.setDescription("😕 To many references! Try typing: " + KellyBot.getPrefix() + "ref [reference name]");
			toMany.setImage("https://media1.tenor.com/images/bc7a8e060496a6b889c9100c20dce14e/tenor.gif?itemid=7919962");
			toMany.setColor(Color.red);

			event.getChannel().sendMessage(toMany.build()).queue();
			toMany.clear();
		}
	}

	@Override
	public String getCmdName() 
	{
		return "Find References";
	}

	@Override
	public String getCmd() 
	{
		return "ref";
	}

	@Override
	public String getDesc() 
	{
		return "Find a references that I know!";
	}
	
	private int findIndex(String key)
	{
		int index = 0;
		for(Reference ref : references)
		{
			if(ref.getKeyPhrase().equalsIgnoreCase(key))
				break;
			
			index++;
		}
		
		if(index == references.size())
			return findByTag(key);
		
		return index;
	}
	
	private int findByTag(String key)
	{
		if(key == null || key.length() == 0)
			return -1;
		
		ArrayList<Reference> results = new ArrayList<Reference>();

		for(Reference ref : references)
		{
			if(ref.getTagsAsString().contains(key.toLowerCase()))
				results.add(ref);
		}
		
		if(results.size() == 0)
			return -1;
		
		Random r = new Random();
		int index = r.nextInt(results.size());
		
		return references.indexOf(results.get(index));	
	}
	
	public ArrayList<Reference> getReferences()
	{
		return references;
	}
}
