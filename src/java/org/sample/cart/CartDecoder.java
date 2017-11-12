/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sample.cart;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author lisading
 */
public class CartDecoder implements Decoder.Text<Cart>{    
    
    private static Cart cart;
    
    @Override
    public Cart decode(String jsonString) throws DecodeException {
        
        System.out.println("decoding: " + jsonString);
        
        JsonObject jsonObject = Json.createReader(new StringReader(jsonString)).readObject();
        String action = jsonObject.getString("action");
        String item = jsonObject.getString("item"); 
        
        // Initialize a cart if there is not one
        if (cart == null) {
            cart = new Cart();
        }

        // Add items to the Cart or remove items from the Cart
        if ((action != null)&&(item != null)) {

          if ("add".equals(action)) {
            cart.addItem(jsonObject);

          } else if ("remove".equals(action)) {
            cart.removeItems(jsonObject);

          }
        }    

        return cart;
    }

    @Override
    public boolean willDecode(String string) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
    
    
}
