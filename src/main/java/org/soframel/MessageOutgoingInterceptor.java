package org.soframel;

import io.smallrye.reactive.messaging.OutgoingInterceptor;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Message;

import java.util.logging.Logger;

@ApplicationScoped
public class MessageOutgoingInterceptor implements OutgoingInterceptor {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private Boolean lastResult = null;

    @Override
    public void onMessageAck(Message<?> message) {
        logger.info("Message acked: " + message.toString());
        lastResult=Boolean.TRUE;
    }

    @Override
    public void onMessageNack(Message<?> message, Throwable failure) {
        logger.warning("Message nacked! : " + message.toString());
        lastResult=Boolean.FALSE;
    }

    public void reset(){
        lastResult=null;
    }
    public Boolean getLastResult() {
        return lastResult;
    }
}
