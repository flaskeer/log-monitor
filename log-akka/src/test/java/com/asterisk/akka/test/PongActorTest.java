package com.asterisk.akka.test;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.asterisk.log.akka.actor.PongActor;
import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
public class PongActorTest {

    private ActorSystem system = ActorSystem.create();

    private ActorRef actorRef = system.actorOf(Props.create(PongActor.class));

    @Test
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public void test_future() {
        Future sFuture = Patterns.ask(actorRef, "Ping", 1000);
        CompletionStage<String> stage = FutureConverters.toJava(sFuture);
        CompletableFuture<String> jFuture = (CompletableFuture<String>) stage;
//        Assert.assertEquals(jFuture.get(1000, TimeUnit.MILLISECONDS),"Pong");
        jFuture.thenAccept(x -> System.out.println("replied with :" + x)).handle((x,t) -> {
            if (t != null) {
                System.out.println("error " + t);
                return null;
            } else {
                return x;
            }

        });
        Thread.sleep(100);
    }

    @Test(expected = ExecutionException.class)
    @SuppressWarnings("unchecked")
    @SneakyThrows
    public void test_future_fail() {
        Future sfuture = Patterns.ask(actorRef, "unknown", 1000);
        CompletionStage<String> stage = FutureConverters.toJava(sfuture);
        CompletableFuture<String> jFuture = (CompletableFuture<String>) stage;
        jFuture.get(1000,TimeUnit.MILLISECONDS);
    }
}
