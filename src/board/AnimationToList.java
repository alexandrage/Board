package board;

import java.util.List;

public class AnimationToList {
	private List<String> list;
	private int index;

	public AnimationToList(List<String> list) {
		this.list = list;
	}

	public String next() {
		if (index == list.size()) {
			this.index = 0;
		}
		return this.list.get(this.index++);
	}
}