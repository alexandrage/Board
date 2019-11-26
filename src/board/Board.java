package board;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Board {
	private ScoreboardManager manager;
	private Scoreboard board;
	private Objective objective;

	public Board(String displayName) {
		this.manager = Bukkit.getScoreboardManager();
		this.board = manager.getNewScoreboard();
		this.objective = board.registerNewObjective("test", "dummy", displayName);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
	}

	public Scoreboard getScoreboard() {
		return this.board;
	}

	public void setDisplayName(String name) {
		this.objective.setDisplayName(name);
	}

	public void setScore(String name, int index) {
		int length = 64;
		if (name.length() > length + length - 2) {
			name = name.substring(0, length + length - 2);
		}
		String tname = build(index);
		Team team = this.board.getTeam(tname);
		if (team == null) {
			team = this.board.registerNewTeam(tname);
			team.addEntry(tname);
			Score score = objective.getScore(tname);
			score.setScore(index);
		}
		String color = build(name);
		if (name.length() > length) {
			team.setPrefix(name.substring(0, length));
			team.setSuffix("§" + color + name.substring(length));
		} else {
			team.setPrefix(name);
			team.setSuffix("§" + color);
		}
	}

	public void resetScores(int index) {
		String string = build(index);
		this.board.getTeam(string).unregister();
		this.board.resetScores(string);
	}

	private String build(int index) {
		String hex = Integer.toHexString(index);
		StringBuilder sb = new StringBuilder();
		for (char c : hex.toCharArray()) {
			sb.append("§" + c);
		}
		String value = sb.toString();
		sb.setLength(0);
		return value;
	}

	private String build(String name) {
		String color = "f";
		if (name.split("§").length > 1) {
			for (String temp : name.split("§")) {
				if (temp.length() > 0) {
					color = temp.substring(0, 1);
				}
			}
		}
		return color;
	}
}