package board;

import java.util.List;

public class Board {
	private String name;
	private List<Score> ls;
	
	public List<Score> getScore() {
		return ls;
	}
	
	public String getName() {
		return name;
	}
}