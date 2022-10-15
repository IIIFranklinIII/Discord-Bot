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
                                     "1) !sayHi - make the bot say *Hello everyone* \n" +
                                     "2) !help - display command list \n" +
                                     "3) /welcome - get welcomed by the bot" +
                                     "4) /roles - display all roles on the server" +
                                     "5) /scgv - secret cat-girl voice" +
                                     "6) /say - make the bot say a message" +
                                     "7) /emote - express your emotions through text" +
                                     "8) /giverole - give a user a role" +
                                     "9) /getacat - bot sends you a cat picture").queue();
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
        event.getGuild().getTextChannelById("") // general channel ID
                .sendMessage("Welcome to the server " + event.getMember().getAsMention() + "!");
    }
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {
        event.getGuild().getTextChannelById("") // general channel ID
                .sendMessage("Goodbye " + event.getMember().getAsMention() + "!");
    }
}
