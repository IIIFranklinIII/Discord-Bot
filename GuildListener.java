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
