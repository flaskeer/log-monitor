package com.asterisk.akka.intergation.client;

import lombok.SneakyThrows;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
public class ClientTest {

    @Test
    @SneakyThrows
    public void test_remote() {
        Client client = new Client("127.0.0.1:2552");
        client.set("123",123);
        Integer result = (Integer) ((CompletableFuture) client.get("123")).get();
        Assert.assertEquals(result,Integer.valueOf(123));
    }
}
