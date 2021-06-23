package board;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import java.awt.Color;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ScoreboardRun extends BukkitRunnable {
	private Main plugin;
	private Boards boards;
	private Animation list;
	private int oldSize;

	public ScoreboardRun(Main plugin, Boards boards) {
		this.plugin = plugin;
		this.boards = boards;
	}

	public void setList(Animation list) {
		this.list = list;
	}

	@Override
	public void run() {
		String next = this.list.next();
		for (Player player : Bukkit.getOnlinePlayers()) {
			Board board = boards.getBoard(player);
			if (board == null) {
				continue;
			}
			this.setupTeams(board.getScoreboard());
			List<String> scoreList = this.plugin.getConfig().getStringList("scoreList");
			Collections.reverse(scoreList);
			for (int i = 0; i < scoreList.size(); i++) {
				String string = scoreList.get(i);
				if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
					string = PAPI.setPlaceholders(player, string);
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

	@SuppressWarnings("deprecation")
	public void setupTeams(Scoreboard board) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			String pref = color(this.plugin.chat.getPlayerPrefix(p));
			String suff = color(this.plugin.chat.getPlayerSuffix(p));
			Team team = board.getTeam(p.getName());
			if (team == null) {
				team = board.registerNewTeam(p.getName());
			}
			BaseComponent[] temp = TextComponent.fromLegacyText(pref);
			ChatColor color = ChatColor.getByChar(temp[temp.length - 1].getColor().toString().substring(1));
			if (color == null) {
				Color name = Color.decode(temp[temp.length - 1].getColor().getName());
				color = ColorUtil.fromRGB(name.getRed(), name.getGreen(), name.getBlue());
			}
			team.setColor(color);
			team.setPrefix(pref);
			team.setSuffix(suff);
			team.addEntry(p.getName());
		}
		for (Team t : board.getTeams()) {
			String name = t.getName();
			Player p = Bukkit.getPlayerExact(name);
			if (p == null && name.length() > 2) {
				t.unregister();
			}
		}
	}

	private String color(String name) {
		String color = ChatColor.translateAlternateColorCodes('&', name);
		return color;
	}
}