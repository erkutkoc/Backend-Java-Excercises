package be.vinci.pae.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.text.StringEscapeUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
	public static Text getText(int id) {
		return texts.stream().filter(item -> item.getId() == id).findAny().orElse(null);
	}
	public static List<Text> getTexts() {
		return texts;
	}

	public static List<Text> getTexts(String level) {
		return texts.stream().filter(item -> item.getLevel().equals(level)).collect(Collectors.toList());
	}

	public static Text deleteText(int id) {
		if(texts.size() == 0 |id == 0 ) return null;
		Text deletedText = getText(id);
		if(deletedText == null) return null;
		int index = texts.indexOf(deletedText);
		texts.remove(index);
		saveDataToFile();
		return deletedText;
	}
	private static void saveDataToFile() {
		try {

			Path pathToDb = Paths.get(DB_FILE_PATH);
			if (!Files.exists(pathToDb)) {
				ObjectNode newCollection = jsonMapper.createObjectNode().putPOJO(COLLECTION_NAME, texts);
				jsonMapper.writeValue(pathToDb.toFile(), newCollection);
				return;
			}
			JsonNode allCollections = jsonMapper.readTree(pathToDb.toFile());

			if (allCollections.has(COLLECTION_NAME)) {// remove current collection
				((ObjectNode) allCollections).remove(COLLECTION_NAME);
			}
			String currentCollectionAsString = jsonMapper.writeValueAsString(texts);
			JsonNode updatedCollection = jsonMapper.readTree(currentCollectionAsString);
			((ObjectNode) allCollections).putPOJO(COLLECTION_NAME, updatedCollection);

			jsonMapper.writeValue(pathToDb.toFile(), allCollections);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static int nextTextId() {
		return texts.size() == 0 ? 1 : texts.get(texts.size() -1).getId() +1;
	}
	public static Text addText(Text text) {
		text.setId(nextTextId());
		text.setContent(text.getContent());
		text.setLevel(text.getContent());
		texts.add(text);
		saveDataToFile();
		return text;
	}
	public static Text uptadeText(Text text) {
		if(texts.size() == 0 |text == null ) return null;
		Text updatedText = getText(text.getId());
		if(updatedText == null) return null;
		text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
		text.setLevel(StringEscapeUtils.escapeHtml4(text.getLevel()));
		int index = texts.indexOf(updatedText);
		texts.set(index, text);
		saveDataToFile();
		return updatedText;
	}

}
