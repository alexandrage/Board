package board;

import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import board.api.WG;

public class PlaceHolder {
	public static String set(String input, Player p) {
		input = input.replace("{DEATHS}",  String.valueOf(p.getStatistic(Statistic.DEATHS)));
		input = input.replace("{PLAYER_KILLS}",  String.valueOf(p.getStatistic(Statistic.PLAYER_KILLS)));
		input = input.replace("{MOB_KILLS}",  String.valueOf(p.getStatistic(Statistic.MOB_KILLS)));
		input = input.replace("{player}", p.getName());
		input = input.replace("{world}", p.getWorld().getName());
		if (Main.economy != null) {
			input = input.replace("{money}", String.valueOf(Main.economy.getBalance(p)));
		}
		if (Main.chat != null) {
			input = input.replace("{prefix}", Main.chat.getPlayerPrefix(p));
			input = input.replace("{group}", Main.chat.getPrimaryGroup(p));
		}
		if (Main.wg != null) {
			input = WG.set(p, input);
		}
		input = ChatColor.translateAlternateColorCodes('&', input);
		return input;
	}
}