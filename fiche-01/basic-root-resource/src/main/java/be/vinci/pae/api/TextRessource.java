package be.vinci.pae.api;

import java.util.List;

import be.vinci.pae.domain.Text;
import be.vinci.pae.domain.Text.Level;
import be.vinci.pae.services.DataServiceTextCollection;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Singleton
@Path("/texts")
public class TextRessource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Text> getAllText(@QueryParam("level") Level level) {
		if (level != null) {
			return DataServiceTextCollection.getTexts(level);
		}
		return DataServiceTextCollection.getTexts();

	}

}
