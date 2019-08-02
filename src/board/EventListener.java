package board;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {
	IBoard plugin;

	public EventListener(IBoard plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void on(PlayerQuitEvent e) {
		this.plugin.getBoards().removeBoard(e.getPlayer());
	}
}