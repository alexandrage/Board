package board;

import java.util.List;
import org.bukkit.plugin.java.JavaPlugin;
import net.milkbowl.vault.chat.Chat;

public class Main extends JavaPlugin implements IBoard {
	private Boards boards;
	private ScoreboardRun runs;
	public Chat chat = null;

	@SuppressWarnings("unchecked")
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		this.reloadConfig();
		this.boards = new Boards();
		this.runs = new ScoreboardRun(this, boards);
		this.runs.setList(new Animation((List<String>) this.getConfig().getList("nameList")));
		this.runs.runTaskTimer(this, 1, 1);
		this.getCommand("board").setExecutor(new CommandListener(this, this));
		this.getServer().getPluginManager().registerEvents(new EventListener(this), this);
	}

	@Override
	public Boards getBoards() {
		return this.boards;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setList() {
		this.runs.setList(new Animation((List<String>) this.getConfig().getList("nameList")));
	}
}