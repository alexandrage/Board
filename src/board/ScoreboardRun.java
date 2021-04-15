package board;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

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

	public void setupTeams(Scoreboard board) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			String pref = trim(this.plugin.chat.getPlayerPrefix(p));
			String suff = trim(this.plugin.chat.getPlayerSuffix(p));
			Team team = board.getTeam(p.getName());
			if (team == null) {
				team = board.registerNewTeam(p.getName());
			}
			team.setColor(ChatColor.getByChar(this.getColor(pref)));
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

	public char getColor(String pref) {
		char color = 'r';
		char[] c = pref.toCharArray();
		for (int i = 0; i < pref.length(); i++) {
			if (c[i] == '§' && i < pref.length()) {
				if (patern.matcher(String.valueOf(new char[] { c[i], c[i + 1] })).matches()) {
					color = c[i + 1];
				}
			}
		}
		return color;
	}

	private String trim(String name) {
		String color = ChatColor.translateAlternateColorCodes('&', name);
		if (color.length() > 64)
			return color.substring(0, 64);
		return color;
	}

	private static Pattern patern;
	static {
		patern = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
	}
}