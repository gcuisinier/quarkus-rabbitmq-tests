package org.soframel;

import java.util.logging.Logger;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

@Path("/rabbit")
public class RabbitResource {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Channel("myqueue-out")
    MutinyEmitter<String> emitter;

    @GET
    public Uni<Void> produce(@QueryParam(value = "s") String s) {
        logger.info("producing message " + s);
        return emitter.send(s);
    }
}
