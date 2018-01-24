package board;

import java.util.List;

public class Board {
	private Integer tick;
	private String name;
	private List<Score> ls;
	
	public List<Score> getScore() {
		return ls;
	}
	
	public String getName() {
		return name;
	}
	
	public Integer getTick() {
		return tick;
	}
}