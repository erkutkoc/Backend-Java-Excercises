package be.vinci.pae.domain;

import org.apache.commons.text.StringEscapeUtils;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

public class Text {
	private int id;
	private String content;
	private final String[] levels = {"easy", "medium", "hard"};
	private String level;
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
		return StringEscapeUtils.escapeHtml4(content);
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getLevel() {
		return StringEscapeUtils.escapeHtml4(level);
	}
	public void setLevel(String level) {
		for (String string : levels) {
			if(string.equals(level)){
				this.level = level;
			}
		}
		throw new WebApplicationException(
				Response.status(Status.NOT_ACCEPTABLE).entity("Not conform level! ").type("text/plain").build());

	}
}

