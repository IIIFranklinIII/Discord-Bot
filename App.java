import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

public class App extends ListenerAdapter {

    public static final GatewayIntent[] INTENTS = {
            GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES,
            GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS,
            GatewayIntent.GUILD_EMOJIS_AND_STICKERS
    };

    public static void main(String[] args) throws LoginException, IllegalArgumentException, InterruptedException {

        JDA api = JDABuilder.create("token", Arrays.asList(INTENTS)) //discord bot token
                .setActivity(Activity.playing("Tetris"))
                .setStatus(OnlineStatus.ONLINE)
                .addEventListeners(new GuildListener())
                .addEventListeners(new SlashCommands())
                .build().awaitReady();
    }
}
