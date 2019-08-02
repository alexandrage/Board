package board;

import org.bukkit.configuration.file.FileConfiguration;

public interface IBoard {
	public FileConfiguration getConfig();

	public Boards getBoards();

	public void setList();
}