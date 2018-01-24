package board.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import board.Main;

public class CommandListener implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("board.reload")) {
			Main.load();
			sender.sendMessage("board reloaded");
			return true;
		}
		return false;
	}
}