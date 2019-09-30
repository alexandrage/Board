package board;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class PAPI {
	public static String setPlaceholders(Player player, String string) {
		return PlaceholderAPI.setPlaceholders(player, string);
	}
}