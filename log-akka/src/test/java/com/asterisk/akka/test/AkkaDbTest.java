package com.asterisk.akka.test;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import com.asterisk.log.akka.actor.AkkaDb;
import com.asterisk.log.akka.domain.SetRequest;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
public class AkkaDbTest {


    private ActorSystem system = ActorSystem.create();

    @Test
    public void test_db() {
        TestActorRef<AkkaDb> actorRef = TestActorRef.create(system, Props.create(AkkaDb.class));
        actorRef.tell(new SetRequest("key", "value"), ActorRef.noSender());
        AkkaDb akkaDb = actorRef.underlyingActor();
        Assert.assertEquals(akkaDb.getMap().get("key"), "value");
    }


}