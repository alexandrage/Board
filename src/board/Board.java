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
		String string = build(index);
		Team team = this.board.getTeam(string);
		if (team == null) {
			team = this.board.registerNewTeam(string);
			team.addEntry(string);
			Score score = objective.getScore(string);
			score.setScore(index);
		}
		team.setPrefix(name);
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
		return sb.toString();
	}
}