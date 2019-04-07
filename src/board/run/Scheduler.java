package board.run;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.EnumWrappers.ScoreboardAction;
import com.google.common.collect.Lists;

import board.Main;
import board.PlaceHolder;
import board.obj.Score;
import board.packet.WrapperPlayServerScoreboardDisplayObjective;
import board.packet.WrapperPlayServerScoreboardObjective;
import board.packet.WrapperPlayServerScoreboardScore;

public class Scheduler extends BukkitRunnable {

	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			send(p, Main.board.getName(), Main.board.getScore());
		}
	}

	private WrapperPlayServerScoreboardScore newScore(String objName, String name, int value) {
		WrapperPlayServerScoreboardScore score = new WrapperPlayServerScoreboardScore();
		score.setObjectiveName(objName);
		score.setScoreboardAction(ScoreboardAction.CHANGE);
		score.setScoreName(name);
		score.setValue(value);
		return score;
	}

	public void stop() {
		for (Player t : Bukkit.getOnlinePlayers()) {
			remove(t, Main.board.getName());
		}
	}

	public void remove(Player t, String display) {
		WrapperPlayServerScoreboardObjective remove = build(t,display,1);
		remove.sendPacket(t);
	}

	private void send(Player t, String display, List<Score> list) {
		List<WrapperPlayServerScoreboardScore> scores = Lists.newArrayList();
		for (Score score : list) {
			scores.add(newScore("s" + t.getName(), PlaceHolder.set(score.getName(), t), score.getValue()));
		}

		remove(t, display);
		WrapperPlayServerScoreboardObjective objective = build(t, display, 0);
		WrapperPlayServerScoreboardDisplayObjective disp = build(t);
		objective.sendPacket(t);
		disp.sendPacket(t);

		for (WrapperPlayServerScoreboardScore score : scores) {
			score.sendPacket(t);
		}
	}

	private WrapperPlayServerScoreboardObjective build(Player t, String display, int i) {
		WrapperPlayServerScoreboardObjective obj = new WrapperPlayServerScoreboardObjective();
		obj.setName("s" + t.getName());
		obj.setDisplayName(WrappedChatComponent.fromText(display));
		obj.setMode(i);
		return obj;
	}
	
	private WrapperPlayServerScoreboardDisplayObjective build(Player t) {
		WrapperPlayServerScoreboardDisplayObjective disp = new WrapperPlayServerScoreboardDisplayObjective();
		disp.setPosition(1);
		disp.setScoreName("s" + t.getName());
		return disp;
	}
}