package org.example;

import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.exceptions.InvalidDataException;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshakeBuilder;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

public class Server extends WebSocketServer {
  public Server(InetSocketAddress address) {
    super(address);
  }

  @Override
  public ServerHandshakeBuilder onWebsocketHandshakeReceivedAsServer(WebSocket conn, Draft draft, ClientHandshake request) throws InvalidDataException {
    if (!request.getResourceDescriptor().equals("/")) {
      throw new InvalidDataException(0);
    }

    ServerHandshakeBuilder builder = super.onWebsocketHandshakeReceivedAsServer( conn, draft, request );
    //To your checks and throw an InvalidDataException to indicate that you reject this handshake.
    return builder;
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    conn.send("Welcome to the server!"); //This method sends a message to the new client
    broadcast( "new connection: " + handshake.getResourceDescriptor() ); //This method sends a message to all clients connected
    System.out.println("new connection to " + conn.getRemoteSocketAddress());
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out.println("received message from "	+ conn.getRemoteSocketAddress() + ": " + message);
    conn.send(message);
  }

  @Override
  public void onMessage( WebSocket conn, ByteBuffer message ) {
    System.out.println("received ByteBuffer from "	+ conn.getRemoteSocketAddress());
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    System.err.println("an error occurred on connection " + conn.getRemoteSocketAddress()  + ":" + ex);
  }

  @Override
  public void onStart() {
    System.out.println("Server started successfully");
  }
}
