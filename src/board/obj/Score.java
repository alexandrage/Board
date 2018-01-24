package board.obj;

public class Score {
	private String name;
	private int value;

	public Score(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public Integer getValue() {
		return this.value;
	}
}