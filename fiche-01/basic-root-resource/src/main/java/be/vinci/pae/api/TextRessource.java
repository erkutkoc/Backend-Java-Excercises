package be.vinci.pae.api;

import java.util.List;

import be.vinci.pae.domain.Text;
import be.vinci.pae.services.DataServiceTextCollection;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/texts")
public class TextRessource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Text> getAllText(@QueryParam("level") String level) {
		if (level != null) {
			return DataServiceTextCollection.getTexts(level);
		}
		return DataServiceTextCollection.getTexts();

	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Text getText(@PathParam("id") int id) {
		if (id == 0)
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory id info")
					.type("text/plain").build());
		Text textFound = DataServiceTextCollection.getText(id);
		if (textFound == null)
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity("Ressource " + id + " not Found!").type("text/plain").build());
		return textFound;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Text create(Text text) {
		if (text == null || text.getContent() == null || text.getContent().isEmpty()|| text.getLevel() == null || text.getLevel().isEmpty())
			throw new WebApplicationException(
					Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
		DataServiceTextCollection.addText(text);
		return text;
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Text deleteText(@PathParam("id") int id) {
		if (id == 0) {
			throw new WebApplicationException(
					Response.status(Status.BAD_REQUEST).entity("Lacs of mandatory id info").type("text/plain").build());
		}
		Text deletedText = DataServiceTextCollection.deleteText(id);
		return deletedText;
	}

	@PUT
	@Path("/{id}")
	public Text updateText(Text text, @PathParam("id") int id) {
		return null;
	}
}
