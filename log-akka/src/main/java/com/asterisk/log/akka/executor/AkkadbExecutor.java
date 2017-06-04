package com.asterisk.log.akka.executor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.asterisk.log.akka.actor.AkkaDb;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
public class AkkadbExecutor {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("akkadb");
        ActorRef actorRef = system.actorOf(Props.create(AkkaDb.class), "akka-db");
    }
}
