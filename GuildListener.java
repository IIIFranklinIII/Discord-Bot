import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class GuildListener extends ListenerAdapter {

    /**
     * onMessageReceivedEvent() listens to every user message.
     *
     * if the content of the message is equal to one of event listeners
     * then onMessageReceivedEvent() replies to that message with his commands
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        Message message = event.getMessage();
        String content = message.getContentRaw();
        MessageChannel channel = event.getChannel();

        if(content.equals("!sayHi")) {
            channel.sendMessage("Hello everyone!").queue();
        }
        else if(content.equals("!help")) {
            channel.sendMessage("!!Here you can read all of my text listeners!! \n \n" +
                                     "1) !sayHi - that one command for me to say *Hello everyone* \n" +
                                     "2) !help - that's for display all of commands \n" +
                                     "3) !credits - display all licenses").queue();
        } else if(content.equals("!credits")) {
            channel.sendMessage("@author Wild_Soap \n" +
                                     "@date 09.1.2022 \n" +
                                     "@productName: Bao The Cat \n" +
                                     "@productType: discord-bot \n" +
                                     "@version 2.1.5 \n \n" +
                                     "Copyright (C) 2022 Free Software Foundation, \nInc. <https://fsf.org> \n" +
                                     "Everyone is permitted to copy and distribute verbatim copies \n" +
                                     "of this license document, but changing it is not allowed.").queue();
        }
        System.out.println("Message received from " +
                event.getAuthor().getName() +
                ": " +
                event.getMessage().getContentDisplay());
    }

    /**
     * onGuildMemberJoin() / onGuildMemberRemove() monitor the joining or leaving of a guild member
     * then replies with his commands
     */
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        event.getGuild().getTextChannelById("1010295952394952837") // general channel's ID
                .sendMessage("Welcome to the server " + event.getMember().getAsMention() + "!");
    }
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        event.getGuild().getTextChannelById("1010295952394952837") // general channel's ID
                .sendMessage("Goodbye " + event.getMember().getAsMention() + "!");
    }
}
