package board;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class CommandListener implements CommandExecutor {

	private Plugin plugin;
	private IBoard board;

	public CommandListener(Plugin plugin, IBoard board) {
		this.plugin = plugin;
		this.board = board;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("board.reload")) {
			this.plugin.reloadConfig();
			sender.sendMessage("board reloaded");
			this.board.setList();
			return true;
		}
		return false;
	}
}