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
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.OnOverflow;

@Path("/rabbit")
public class RabbitResource {

    Logger logger = Logger.getLogger(this.getClass().getName());

    String s = "hellow";

    @Channel("myqueue-out")
    MutinyEmitter<String> emitter;

    @GET()
    @Path("async")
    public Uni<Void> produce() {
        logger.info("producing message " + s);
        return emitter.send(s);
    }

    @GET
    @Path("sync")
    public void produceWait() {
        logger.info("producing message " + s);
        emitter.sendMessageAndAwait(Message.of(s));
        //  return emitter.send(s);
    }
}
