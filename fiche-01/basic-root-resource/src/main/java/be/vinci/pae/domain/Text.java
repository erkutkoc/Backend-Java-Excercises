package be.vinci.pae.domain;

public class Text {
	private int id;
	private String content;
	private String[] levels = {"easy", "medium", "hard"};
	public int getId() {
		return id;
	}

	public String[] getLevels() {
		return levels;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

