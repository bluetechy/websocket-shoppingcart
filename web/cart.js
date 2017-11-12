// Timestamp of cart that page was last updated with
var lastCartUpdate = 0;

// Define websocket URL
var wsUri = "ws://" + document.location.host + document.location.pathname + "endpoint";
// Initialize the connection to the server from client
var websocket = new WebSocket(wsUri);

// Send messages from the server to the client
websocket.onmessage = updateCart;

function onMessage(evt) {
    console.log("received: " + evt.data);
    if (typeof evt.data == "string") {
        drawImageText(evt.data);
    } else {
        drawImageBinary(evt.data);
    }
}

// When error occurs between the communications
websocket.onerror = function(evt) { onError(evt); };

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}
    
// Tell the server to add item with the itemcode
function addToCart(itemCode) {
    
    console.log("Add to cart " + itemCode);
    var item = '{"action":"add", "item":"' + itemCode + '"}';
    websocket.send(item);
}

// Tell the server to remove item with the itemcode
function removeFromCart(itemCode) {

    console.log("Remove from cart " + itemCode);
    var item = '{"action":"remove", "item":"' + itemCode + '"}';
    websocket.send(item);
}

// Update shopping-cart to reflect contents of cart described in Json document.
function updateCart(cartJson) {
    
    console.log("Update cart");
        
    // Handle the Json Object, parse its properties
    var jsonCart = JSON.parse(cartJson.data);
    var generated = jsonCart.cart.generated;
    
    if (generated > lastCartUpdate) {
        
        lastCartUpdate = generated;
        
        // Clear the HTML list used to display the cart contents
        var contents = document.getElementById("contents");
        contents.innerHTML = "";
        
        // Loop over the items in the cart
        var items = jsonCart.cart.cartItem;
        for (var I = 0 ; I < items.length ; I++) {
            var item = items[I];
            
            // Extract the text nodes from the name and quantity elements
            var name = item.name;
            var quantity = item.quantity;
            
            // Create and add a list item HTML element for this cart item
            var listItem = document.createElement("li");
            listItem.appendChild(document.createTextNode(name+" x "+quantity));
            contents.appendChild(listItem);
        }
    }
    
    // Update the cart's total using the value from the cart document
    document.getElementById("total").innerHTML = jsonCart.cart.total;
}



