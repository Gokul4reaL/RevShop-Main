document.addEventListener("DOMContentLoaded", () => {
    displayCheckoutItems();
});

function displayCheckoutItems() {
    const checkoutItems = JSON.parse(sessionStorage.getItem('checkoutCartItems'));
	console.log("Checkout Items: ", checkoutItems);
    const checkoutItemsContainer = document.getElementById('checkoutItemsContainer');

    checkoutItems.forEach(item => {
        const checkoutItem = document.createElement('div');
        checkoutItem.className = 'checkout-item';
        checkoutItem.innerHTML = `
            <img src="${item.imageUrl}" alt="Product Image">
            <div class="item-info">
                <h3>${item.productName}</h3>
                <p>Price: Rs.${item.priceAtTime.toFixed(2)}</p>
                <p>Quantity: ${item.quantity}</p>
            </div>
        `;
        checkoutItemsContainer.appendChild(checkoutItem);
    });
}

function confirmOrder() {
    const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked').value;
    const userId = localStorage.getItem("userId");
    const totalAmount = getTotalAmount(); // Assuming you have a function to calculate the total amount
    const orderAddress = document.querySelector('textarea[name="orderAddress"]').value;
    
    const checkoutItems = JSON.parse(sessionStorage.getItem('checkoutCartItems')); // Retrieve cart items

    if (paymentMethod === "cod") {
        // Cash on Delivery case
        const checkoutItemsData = checkoutItems.map(item => ({
            productId: item.productId,  // Assuming `productId` is available in checkoutItems
            quantity: item.quantity
        }));

        fetch('/revshop/confirmOrder', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
                'Authorization': 'Bearer ' + localStorage.getItem('userToken')
            },
            body: new URLSearchParams({
                userId,
                paymentMethod,
                totalAmount,
                orderAddress,
                checkoutItems: JSON.stringify(checkoutItemsData)  // Convert to JSON string for sending
            })
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Order confirmed!');
                window.location.href = "/revshop/ordersComponent.jsp"; // Redirect to orders page
            } else {
                alert('Failed to confirm order');
            }
        })
        .catch(error => console.error('Error confirming order:', error));
    } else if (paymentMethod === "online") {
        // Online Payment case (Razorpay)
        const options = {
            "key": "rzp_test_37MbLXJMCUd3yL",  // Replace with your Razorpay key
            "amount": totalAmount * 100,  // Amount in paise
            "currency": "INR",
            "name": "RevShop",
            "description": "Order Payment",
            "handler": function (response) {
				
				const checkoutItemsData = checkoutItems.map(item => ({
				            productId: item.productId,  // Assuming `productId` is available in checkoutItems
				            quantity: item.quantity
				        }));
                // Handle successful payment
                fetch('/revshop/confirmOrder', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
						'Authorization': 'Bearer ' + localStorage.getItem('userToken')
                    },
                    body: new URLSearchParams({
                        userId,
                        paymentMethod: 'online',
                        paymentId: response.razorpay_payment_id,
						totalAmount,
                        orderAddress: orderAddress,
						checkoutItems: JSON.stringify(checkoutItemsData)  // Convert to JSON string for sending
						
                    })
                })
                .then(res => res.json())
                .then(data => {
                    if (data.success) {
                        alert("Payment successful!");
                        window.location.href = "/revshop/ordersComponent.jsp";
                    } else {
                        alert("Order confirmation failed");
                    }
                })
                .catch(err => console.error('Error confirming order:', err));
            },
            "prefill": {
                "name": "Gokul Krishna",
                "email": "gravekrishna@email.com",
                "contact": "8667829061"
            },
            "theme": {
                "color": "#3399cc"
            }
        };
        const rzp = new Razorpay(options);
        rzp.open();
    }
}


        // Dummy function to calculate total amount
        function getTotalAmount() {
            const checkoutItems = JSON.parse(sessionStorage.getItem('checkoutCartItems'));
            let totalAmount = 0;
            checkoutItems.forEach(item => {
                totalAmount += item.priceAtTime * item.quantity;
            });
            return totalAmount;
        }

        function handleLogout() {
            localStorage.removeItem("userId");
            localStorage.removeItem("userToken");
            window.location.href = "/revshop/landingComponent.jsp";
        }