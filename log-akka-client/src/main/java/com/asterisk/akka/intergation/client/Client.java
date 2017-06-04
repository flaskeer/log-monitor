package com.asterisk.akka.intergation.client;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import com.asterisk.log.akka.domain.GetRequest;
import com.asterisk.log.akka.domain.SetRequest;
import scala.compat.java8.FutureConverters;

import java.util.concurrent.CompletionStage;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
public class Client {

    private final ActorSystem system = ActorSystem.create("LocalSystem");

    private final ActorSelection remoteDb;

    public Client(String remoteAddress) {
        this.remoteDb = system.actorSelection("akka.tcp://akkadb@" + remoteAddress + "/user/akkadb");
    }

    public CompletionStage set(String key,Object value) {
        return FutureConverters.toJava(Patterns.ask(remoteDb,new SetRequest(key,value),2000));
    }

    public CompletionStage<Object> get(String key) {
        return FutureConverters.toJava(Patterns.ask(remoteDb,new GetRequest(key),2000));
    }
}
