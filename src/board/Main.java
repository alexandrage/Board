package board;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {
	public static Chat chat = null;
	public static Economy economy = null;
	public static Board board;
	public static WorldGuardPlugin wg = null;
	private static Main instance;
	static Scheduler scheduler;
	@Override
	public void onEnable() {
		instance = this;
		getCommand("board").setExecutor(new CommandListener());
		load();
		setupChat();
		setupEconomy();
		setupWG();
	}

	public static void load() {
		File file = new File(instance.getDataFolder() + "/board.json");
		if (!file.exists()) {
			instance.getDataFolder().mkdirs();
			instance.saveResource("board.json", true);
		}
		try {
			String cfg = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
			Gson gson = new Gson();
			if(scheduler!=null) {
				scheduler.stop();
				scheduler.cancel();
			}
			board = gson.fromJson(cfg, Board.class);
			scheduler = new Scheduler();
			scheduler.runTaskTimerAsynchronously(instance, 0, board.getTick());
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private boolean setupWG() {
		if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
			wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		}
		return wg != null;
	}
}