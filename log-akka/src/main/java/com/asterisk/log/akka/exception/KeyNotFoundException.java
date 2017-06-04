package com.asterisk.log.akka.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author donghao
 * @version 1.0
 * @date 2017/6/4
 */
@Getter
@AllArgsConstructor
@ToString
public class KeyNotFoundException extends Exception implements Serializable {

    private String key;

}
