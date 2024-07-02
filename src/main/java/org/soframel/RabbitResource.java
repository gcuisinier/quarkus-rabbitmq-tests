package org.soframel;

import java.util.logging.Logger;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/rabbit")
public class RabbitResource {

    Logger logger = Logger.getLogger(this.getClass().getName());

    // @Inject
    // @Channel("myqueue-out")
    Emitter<String> emitter;

    @GET
    public void produce(@QueryParam(value = "s") String s) {
        logger.info("producing message " + s);
        emitter.send(s);
    }
}
