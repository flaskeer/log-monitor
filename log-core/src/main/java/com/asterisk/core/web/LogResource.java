package com.asterisk.core.web;

import org.springframework.http.MediaType;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.tail.FileTailingMessageProducerSupport;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.io.File;

/**
 * @author donghao
 * @Date 17/5/14
 * @Time 上午11:40
 */
@RestController
public class LogResource {

    @GetMapping(value = "/logs", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> logs(String file) {
        File f = new File(file);
        return Flux.create(sink -> {
            SubscribableChannel ch = MessageChannels.publishSubscribe(f.getName()).get();
            FileTailingMessageProducerSupport producerSupport = Files.tailAdapter(f).outputChannel(ch).get();
            MessageHandler handler = msg -> sink.next(String.valueOf(msg.getPayload()).trim());
            sink.onCancel(() -> {
                ch.unsubscribe(handler);
                producerSupport.stop();
            }).onRequest(c -> {
                producerSupport.start();
                ch.subscribe(handler);
            });
        });
    }

}
