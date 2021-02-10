package be.vinci.pae.api;

import org.glassfish.jersey.server.ContainerRequest;

import be.vinci.pae.api.filters.Authorize;
import be.vinci.pae.domain.Film;
import be.vinci.pae.domain.Page;
import be.vinci.pae.domain.User;
import be.vinci.pae.services.DataServiceFilmCollection;
import be.vinci.pae.services.DataServicePageCollection;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Singleton
@Path("/pages")
public class PageResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Authorize
	public Page create(Page page) {
		if (page == null || page.getTitre() == null || page.getUri() == null || page.getTitre().isEmpty()
				|| page.getUri().isEmpty() || page.getAuteur() == null )
			throw new WebApplicationException(
					Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
		if (page.getStatusDePublication() == null) {
			throw new IllegalAccessError();
		}
		DataServicePageCollection.addPage(page);

		return page;
	}

	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Authorize
	public Page updatePage(Page page, @PathParam("id") int id, @Context ContainerRequest request) {

		if (page == null || page.getTitre() == null || page.getUri() == null || page.getTitre().isEmpty()
				|| page.getUri().isEmpty() || page.getAuteur() == null )
			throw new WebApplicationException(
					Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
		User currentUser = (User) request.getProperty("user");

		Page updatedPage = null;
		if (page.getAuteur().getID() == currentUser.getID()) {
			page.setId(id);
			updatedPage = DataServicePageCollection.updatePage(page);
		}
		if (updatedPage == null)
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity("Ressource with id = " + id + " could not be found").type("text/plain").build());

		return updatedPage;
	}
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Authorize
	public Page deletePage(@PathParam("id") int id) {
		if (id == 0)
			throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory id info")
					.type("text/plain").build());

		Page deletedPage = DataServicePageCollection.deletePage(id);
		
		if (deletedPage == null)
			throw new WebApplicationException(Response.status(Status.NOT_FOUND)
					.entity("Ressource with id = " + id + " could not be found").type("text/plain").build());

		return deletedPage;
	}
}
