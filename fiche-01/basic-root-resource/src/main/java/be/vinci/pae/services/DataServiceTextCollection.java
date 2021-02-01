package be.vinci.pae.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.vinci.pae.domain.Text;

public class DataServiceTextCollection {
	private static final String DB_FILE_PATH = "db.json";
	private static final String COLLECTION_NAME = "texts";
	private final static ObjectMapper jsonMapper = new ObjectMapper();

	private static List<Text> texts;

	static {
		texts = loadDataFromFile();
	}

	private static List<Text> loadDataFromFile() {
		try {
			JsonNode node = jsonMapper.readTree(Paths.get(DB_FILE_PATH).toFile());
			JsonNode collection = node.get(COLLECTION_NAME);
			if (collection == null)
				return new ArrayList<Text>();
			return jsonMapper.readerForListOf(Text.class).readValue(node.get(COLLECTION_NAME));

		} catch (FileNotFoundException e) {
			return new ArrayList<Text>();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<Text>();
		}
	}

	public static List<Text> getTexts() {
		return texts;
	}

	public static List<Text> getTexts(String level) {
		return null;
		//return texts.stream().filter(Text::getLevels).forEach().;
				//.collect(Collectors.toList());
		//l.getLevel().equals(level)).collect(Collectors.toList());
	}

}
