package board;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ScoreboardRun extends BukkitRunnable {
	private Main plugin;
	private Boards boards;
	private AnimationToList list;
	private int oldSize;

	public ScoreboardRun(Main plugin, Boards boards) {
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
			if (board == null) {
				continue;
			}

			for (Player p : Bukkit.getOnlinePlayers()) {
				String pref = trim(this.plugin.chat.getPlayerPrefix(p));
				String suff = trim(this.plugin.chat.getPlayerSuffix(p));
				Team team = board.getScoreboard().getTeam(p.getName());
				if (team == null) {
					System.out.println(team + " -");
					team = board.getScoreboard().registerNewTeam(p.getName());
				}
				team.setPrefix(pref);
				team.setSuffix(suff);
				team.addEntry(p.getName());
			}

			for (Team t : board.getScoreboard().getTeams()) {
				String name = t.getName();
				Player p = Bukkit.getPlayerExact(name);
				if (p == null && name.length() > 2) {
					t.unregister();
				}
			}

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

	private String trim(String name) {
		String color = translateAlternateColorCodes('&', name);
		if (color.length() > 64)
			return color.substring(0, 64);
		return color;
	}

	private String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
		final char[] b = textToTranslate.toCharArray();
		for (int i = 0; i < b.length - 1; ++i) {
			if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
				b[i] = '§';
				b[i + 1] = Character.toLowerCase(b[i + 1]);
			}
		}
		return new String(b);
	}
}