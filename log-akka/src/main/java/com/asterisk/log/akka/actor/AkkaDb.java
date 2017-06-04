package com.asterisk.log.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.asterisk.log.akka.domain.GetRequest;
import com.asterisk.log.akka.domain.SetRequest;
import com.asterisk.log.akka.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
public class AkkaDb extends AbstractActor {

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);

    protected final Map<String, Object> map = new HashMap<>();


    @Override
    public Receive createReceive() {
        return receiveBuilder().match(SetRequest.class, message -> {
            log.info("receive set request:{}", message);
            map.put(message.getKey(), message.getValue());
            sender().tell(new Status.Success(message.getKey()),self());
        })
                .match(GetRequest.class,message -> {
                    log.info("Received Get request:{}",message);
                    String value = String.valueOf(map.get(message.getKey()));
                    Object response = (value != null) ? value : new Status.Failure(new KeyNotFoundException(message.getKey()));
                    sender().tell(response,self());
                })
                .matchAny(o -> sender().tell(new Status.Failure(new CloneNotSupportedException()),self()))
                .build();

    }

    public Map<String, Object> getMap() {
        return map;
    }
}
