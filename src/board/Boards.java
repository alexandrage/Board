package board;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Boards {
	private Map<String, Board> boardMap = new ConcurrentHashMap<String, Board>();

	public Board getBoard(Player player) {
		if (player.hasPermission("board.use")) {
			if (!this.boardMap.containsKey(player.getName())) {
				Board board = new Board("-");
				this.boardMap.put(player.getName(), board);
				player.setScoreboard(board.getScoreboard());
			}
		} else if (this.boardMap.containsKey(player.getName())) {
			removeBoard(player);
		}
		return this.boardMap.get(player.getName());
	}

	public void removeBoard(Player player) {
		player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		this.boardMap.remove(player.getName());
	}
}