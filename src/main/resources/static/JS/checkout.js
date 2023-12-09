var form = document.querySelector('form');
var resultDiv = document.getElementById('result');

form.addEventListener('submit', function (event) {
    event.preventDefault();

    var phoneNumber = document.getElementById('phoneNumber').value;
    var url = '/check-phone';

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'phoneNumber=' + encodeURIComponent(phoneNumber)
    })
        .then(response => response.json())
        .then(result => {
            if (result.found) {
                // Display customer information if customer is found
                resultDiv.innerHTML = `
                    <p>Tên khách hàng: ${result.fullName}</p>
                    <p>Địa chỉ: ${result.address}</p>
                    
                    
                    
                `;
                // Get a reference to the form element
			var paymentForm = document.getElementById('di');

			// Show the form
				paymentForm.style.display = 'block';
                console.log("succes");
            } else {
                // Display a form if customer is not found
                resultDiv.innerHTML = '<form action="/get-customer-infor" method="POST"> <label for="name">Name:</label>   <input type="text" id="name" name="name" required> <label for="phoneNumber">Phone Number:</label>    <input type="text" id="phoneNumber" name="phoneNumber" required>  <label for="address">Address:</label> <input type="text" id="address" name="address" required>   <button type="submit">Checkout</button></form>  ';
                                console.log("succes")

            }
        })
        .catch(error => console.log(error));
});