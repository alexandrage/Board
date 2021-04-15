package board;

import java.util.List;

public class Animation {
	private List<String> list;
	private int index;

	public Animation(List<String> list) {
		this.list = list;
	}

	public String next() {
		if (index == list.size()) {
			this.index = 0;
		}
		return this.list.get(this.index++);
	}
}