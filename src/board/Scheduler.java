package board;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;
import com.google.common.collect.Lists;

public class Scheduler extends BukkitRunnable {

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			send(p, "§e§llocalhost", Main.board.getScore());
		}
	}

	private static WrapperPlayServerScoreboardScore newScore(String objName, String name, int value) {
		WrapperPlayServerScoreboardScore score = new WrapperPlayServerScoreboardScore();
		score.setObjectiveName(objName);
		score.setScoreboardAction(ScoreboardAction.CHANGE);
		score.setScoreName(name);
		score.setValue(value);
		return score;
	}

	private static void send(Player t, String display, List<Score> list) {
		List<WrapperPlayServerScoreboardScore> scores = Lists.newArrayList();
		for (Score score : list) {
			scores.add(newScore("s" + t.getName(), PlaceHolder.set(score.getName(), t), score.getValue()));
		}

		WrapperPlayServerScoreboardObjective remove = new WrapperPlayServerScoreboardObjective();
		remove.setName("s" + t.getName());
		remove.setDisplayName(display);
		remove.setMode(1);
		remove.sendPacket(t);

		WrapperPlayServerScoreboardObjective objective = new WrapperPlayServerScoreboardObjective();
		objective.setName("s" + t.getName());
		objective.setDisplayName(display);
		objective.setMode(0);

		WrapperPlayServerScoreboardDisplayObjective disp = new WrapperPlayServerScoreboardDisplayObjective();
		disp.setPosition(1);
		disp.setScoreName("s" + t.getName());
		objective.sendPacket(t);
		disp.sendPacket(t);

		for (WrapperPlayServerScoreboardScore score : scores) {
			score.sendPacket(t);
		}
	}
}