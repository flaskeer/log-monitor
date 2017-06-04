package com.asterisk.log.akka.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
public class PongActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().matchEquals("Ping", s -> sender().tell("Pong", ActorRef.noSender()))
                .matchAny(x -> {
                    sender().tell(new Status.Failure(new Exception("unknown message")), self());
                })
                .build();

    }
}
