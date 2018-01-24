package board;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlaceHolder {
	public static String set(String input, Player p) {
		input = ChatColor.translateAlternateColorCodes('&', input);
		input = input.replace("{player}", p.getName());
		input = input.replace("{world}", p.getWorld().getName());
		if (Main.economy != null) {
			input = input.replace("{money}", String.valueOf(Main.economy.getBalance(p)));
		}
		if (Main.chat != null) {
			input = input.replace("{prefix}", Main.chat.getPlayerPrefix(p));
			input = input.replace("{group}", Main.chat.getPrimaryGroup(p));
		}
		return input;
	}
}