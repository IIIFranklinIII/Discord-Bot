import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SlashCommands extends ListenerAdapter {
    
    // Bot command content
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equals("welcome")) {
            String userTag = event.getUser().getAsTag();
            event.reply("welcome to the server, **" + userTag + "**").queue(); // <- set ephemeral true
        } else if (command.equals("roles")) {                                         // for private message
            event.deferReply().queue();                                               // .setEphemeral(true).
            String response = "";
            for(Role role: event.getGuild().getRoles()) {
                response += role.getAsMention() + "\n";
            }
            event.getHook().sendMessage(response).queue();
        } else if (command.equals("scgv")) {
            event.reply("UWU!!!").queue();
        } else if (command.equals("say")) {
            OptionMapping messageOption = event.getOption("message");
            String message = messageOption.getAsString();

            MessageChannel channel;
            OptionMapping channelOption = event.getOption("channel");
            if(channelOption != null) {
                channel = (MessageChannel) channelOption.getAsChannel();
            } else {
                channel = event.getChannel();
            }

            channel.sendMessage(message).queue();
            event.reply("Your message was sent!").setEphemeral(true).queue();
        } else if (command.equals("emote")) {
            OptionMapping option = event.getOption("type");
            String type = option.getAsString();

            String replyMessage = "";
            switch (type.toLowerCase()) {
                case "hug" -> {
                    replyMessage = "You hug the closest person to you";
                }
                case "laugh" -> {
                    replyMessage = "You laugh hysterically at everyone around you";
                }
                case "cry" -> {
                    replyMessage = "You can't stop crying";
                }
            }
            event.reply(replyMessage).queue();
        } else if (command.equals("giverole")) {
            Member member = event.getOption("user").getAsMember();
            Role role = event.getOption("role").getAsRole();

            event.getGuild().addRoleToMember(member, role).queue();
            event.reply(member.getAsMention() + " has been given the " + role.getAsMention() + "role!").queue();
        } else if (command.equals("getacat")) {
            EmbedBuilder result = new EmbedBuilder();
            String url ="http://thecatapi.com/api/images/get?format=src&type=png";
            result.setImage(randomize(url));
            event.replyEmbeds(result.build()).queue();
        }
    }
    // Also bot's command inner
    public String randomize(String url) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return url + "&" + random.nextInt() + "=" + random.nextInt();
    }

    // User interface
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        // Command: /welcome
        commandData.add(Commands.slash("welcome", "Get welcomed by the bot"));
        // Command: /roles
        commandData.add(Commands.slash("roles", "Display all roles on the server"));
        // Command: /scgv
        commandData.add(Commands.slash("scgv", "Secret cat-girl voice"));

        // Command: /say <message> [channel]
        OptionData option1 = new OptionData(OptionType.STRING, "message", "The message you want the bot say", true);
        OptionData option2 = new OptionData(OptionType.CHANNEL, "channel", "The channel you want to send in", false)
                .setChannelTypes(ChannelType.TEXT, ChannelType.NEWS, ChannelType.GUILD_PUBLIC_THREAD);
        commandData.add(Commands.slash("say", "Make the bot say a message").addOptions(option1, option2));

        // Command: /emote [type]
        OptionData option3 = new OptionData(OptionType.STRING, "type", "The type of emotion to express", true)
                .addChoice("Hug", "hug")
                .addChoice("Laugh", "laugh")
                .addChoice("Cry", "cry");
        commandData.add(Commands.slash("emote", "Express your emotions through text").addOptions(option3));

        // Command: /giverole <user> <role>
        OptionData option4 = new OptionData(OptionType.USER, "user", "The user to give the role to", true);
        OptionData option5 = new OptionData(OptionType.ROLE, "role", "The role to be given", true);
        commandData.add(Commands.slash("giverole", "Give a user a role").addOptions(option4, option5));
        
        // Command: /getacat
        commandData.add(Commands.slash("getacat", "Bot sends you a cat picture"));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
