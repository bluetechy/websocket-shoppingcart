package org.sample.cart;

import java.math.BigDecimal;
import java.util.*;
import javax.json.JsonObject;

/**
 * A very simple shopping Cart
 */
public class Cart {

    private HashMap<Item, Integer> contents;

    // Create a new Cart instance
    public Cart() {
        contents = new HashMap<Item, Integer>();
    }

    // Add a named item to the cart
    public void addItem(JsonObject jsonObject) {
        Catalog catalog = new Catalog();

        String itemCode = jsonObject.getString("item");

        if (catalog.containsItem(itemCode)) {
            Item item = catalog.getItem(itemCode);

            int newQuantity = 1;
            if (contents.containsKey(item)) {
                Integer currentQuantity = contents.get(item);
                newQuantity += currentQuantity.intValue();
            }

            contents.put(item, new Integer(newQuantity));
        }
    }

    // Remove the named item from the cart
    public void removeItems(JsonObject jsonObject) {

        // This logic is similar to addItems method.
        Catalog catalog = new Catalog();
        String itemCode = jsonObject.getString("item");

        if (catalog.containsItem(itemCode)) {
            Item item = catalog.getItem(itemCode);

            if (contents.containsKey(item)) {
                // obtain the number of items in the cart
                int currQuantity = contents.get(item);
                int newQuantity = currQuantity;

                // Reduce the item number by one
                newQuantity -= 1;

                if (newQuantity >= 1) {
                    // Update quantity in the cart
                    contents.put(item, newQuantity);
                } else {
                    // Clear the cart if there is only one item
                    contents.remove(new Catalog().getItem(itemCode));
                }
            }
        }
    }


    /**
     * The json format of this cart should be:
     * {"cart": 
     * {"generated": "xxxx-xx-xx",
     * "total": "xxx",
     * "cartItem":[
     *  {"itemcode": "hat001",
     *  "name": "Hat",
     *  "quantity": 1
     *  }, {
     *  "itemcode": "cha001",
     *  "name": "Chair",
     *  "quantity": 2
     *  }]
     * }}
    **/

    public String toJson() {

        StringBuffer json = new StringBuffer();
        json.append("{\"cart\":{\"generated\": \"" + System.currentTimeMillis() + "\",");
        json.append("\"total\": \"" + getCartTotal() + "\",");

        json.append("\"cartItem\":");
        json.append("[");

        for (Iterator<Item> I = contents.keySet().iterator() ; I.hasNext() ; ) {
            Item item = I.next();
            int itemQuantity = contents.get(item).intValue();
            json.append("{");
            json.append("\"itemcode\": \"" + item.getCode() + "\",");
            json.append("\"name\": \"" + item.getName() + "\",");
            json.append("\"quantity\": \"" + itemQuantity + "\"");
            json.append("},");
        }
        if (!contents.isEmpty()) {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("]");
        json.append("}}");
        System.out.println(json.toString());

        return json.toString();

    }

    // Get total amount in the cart
    private String getCartTotal() {
        int total = 0;

        for (Iterator<Item> I = contents.keySet().iterator() ; I.hasNext() ; ) {
          Item item = I.next();
          int itemQuantity = contents.get(item).intValue();

          total += (item.getPrice() * itemQuantity);
        }

        return "$"+new BigDecimal(total).movePointLeft(2);
    }



}
