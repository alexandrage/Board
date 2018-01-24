package board;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	public static Chat chat = null;
	public static Economy economy = null;
	public static Board board;

	@Override
	public void onEnable() {
		String cfg = "[]";
		File file = new File(this.getDataFolder()+"/board.json");
		if(!file.exists()) {
			this.getDataFolder().mkdirs();
			this.saveResource("board.json", true);
		}
		try {
			cfg = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setupChat();
		setupEconomy();
		Gson gson = new Gson();
		board = gson.fromJson(cfg, Board.class);
		new Scheduler().runTaskTimer(this, 20, 100);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
		if (chatProvider != null) {
			chat = (Chat) chatProvider.getProvider();
		}
		return chat != null;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager()
				.getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}
		return economy != null;
	}
}