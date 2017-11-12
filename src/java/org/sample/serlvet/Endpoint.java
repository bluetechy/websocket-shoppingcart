package org.sample.serlvet;

import org.sample.cart.CartEncoder;
import org.sample.cart.CartDecoder;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.sample.cart.Cart;

/**
 *
 * @author lisading
 */
@ServerEndpoint(value="/endpoint", encoders = CartEncoder.class, decoders = CartDecoder.class)


public class Endpoint {
    
    private static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnMessage
    public void onMessage(Cart cart, Session session) throws IOException, EncodeException {
        for (Session peer : peers) {
            peer.getBasicRemote().sendObject(cart);
        }
    }
    
    @OnOpen
    public void onOpen (Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose (Session peer) {
        peers.remove(peer);
    }
    
}


