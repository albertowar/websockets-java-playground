package org.example;

import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws URISyntaxException {
        WebSocketClient client = new Client(new URI("ws://localhost:8887"));
        client.connect();
    }
}
