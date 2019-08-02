package board;

import org.bukkit.scheduler.BukkitRunnable;
import me.clip.placeholderapi.PlaceholderAPI;

import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ScoreboardRun extends BukkitRunnable {
	private Plugin plugin;
	private Boards boards;
	private AnimationToList list;
	private int oldSize;

	public ScoreboardRun(Plugin plugin, Boards boards) {
		this.plugin = plugin;
		this.boards = boards;
	}

	public void setList(AnimationToList list) {
		this.list = list;
	}

	@Override
	public void run() {
		String next = this.list.next();
		for (Player player : Bukkit.getOnlinePlayers()) {
			Board board = boards.getBoard(player);
			if(board==null) {
				continue;
			}
			List<String> scoreList = this.plugin.getConfig().getStringList("scoreList");
			Collections.reverse(scoreList);
			for (int i = 0; i < scoreList.size(); i++) {
					String string = scoreList.get(i);
					if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
						string = PlaceholderAPI.setPlaceholders(player, string);

					}
					board.setScore(string, i);
			}
			for (int i = scoreList.size(); i < this.oldSize; i++) {
				board.resetScores(i);
			}
			board.setDisplayName(next);
			this.oldSize = scoreList.size();
		}
	}
}