
var cartId = "cart";

var localAdapter = {

    saveCart: function (object) {

        var stringified = JSON.stringify(object);
        localStorage.setItem(cartId, stringified);
        return true;

    },
    getCart: function () {

        return JSON.parse(localStorage.getItem(cartId));

    },
    clearCart: function () {

        localStorage.removeItem(cartId);

    }

};

var ajaxAdapter = {
  saveCart: function (object) {
    var stringified = JSON.stringify(object);
    // thực hiện yêu cầu ajax ở đây
  },
   getCart: function () {
   
  },
  clearCart: function () {
    // thực hiện yêu cầu ajax ở đây
  }
};

var storage = localAdapter;

var helpers = {

    getHtml: function (id) {

        return document.getElementById(id).innerHTML;

    },
    setHtml: function (id, html) {

        document.getElementById(id).innerHTML = html;
        return true;

    },
    itemData: function (object) {

        var count = object.querySelector(".count"),
            patt = new RegExp("^[1-9]([0-9]+)?$");
        count.value = (patt.test(count.value) === true) ? parseInt(count.value) : 1;

        var item = {

            name: object.getAttribute('data-name'),
            price: object.getAttribute('data-price'),
            id: object.getAttribute('data-id'),
            count: count.value,
            total: parseInt(object.getAttribute('data-price')) * parseInt(count.value)

        };
        return item;

    },
    updateView: function () {

        var items = cart.getItems(),
            template = this.getHtml('cartT'),
            compiled = _.template(template, {
                items: items
            });
        this.setHtml('cartItems', compiled);
        this.updateTotal();

    },
    emptyView: function () {

        this.setHtml('cartItems', '<p>Nothing to see here</p>');
        this.updateTotal();

    },
    updateTotal: function () {

        this.setHtml('totalPrice', cart.total + '$');

    }

};

var cart = {

    count: 0,
    total: 0,
    items: [],
    getItems: function () {

        return this.items;

    },
    setItems: function (items) {

        this.items = items;
        for (var i = 0; i < this.items.length; i++) {
            var _item = this.items[i];
            this.total += _item.total;
        }

    },
    clearItems: function () {

        this.items = [];
        this.total = 0;
        storage.clearCart();
        helpers.emptyView();

    },
    addItem: function (item) {

        if (this.containsItem(item.id) === false) {

            this.items.push({
                id: item.id,
                name: item.name,
                price: item.price,
                count: item.count,
                total: item.price * item.count
            });

            storage.saveCart(this.items);


        } else {

            this.updateItem(item);

        }
        this.total += item.price * item.count;
        this.count += item.count;
        helpers.updateView();

    },
    containsItem: function (id) {

        if (this.items === undefined) {
            return false;
        }

        for (var i = 0; i < this.items.length; i++) {

            var _item = this.items[i];

            if (id == _item.id) {
                return true;
            }

        }
        return false;

    },
    updateItem: function (object) {

        for (var i = 0; i < this.items.length; i++) {

            var _item = this.items[i];

            if (object.id === _item.id) {

                _item.count = parseInt(object.count) + parseInt(_item.count);
                _item.total = parseInt(object.total) + parseInt(_item.total);
                this.items[i] = _item;
                storage.saveCart(this.items);

            }

        }

    }

};

function displayCart() {
	var cartData = cart.getItems();
	

$.ajax({
    url: "http://localhost:8080/submit-cart",
    type: "POST",
    contentType: "application/json",
    data: JSON.stringify(cartData),
    success: function(response) {
        console.log("Dữ liệu đã được gửi thành công.");
		window.location.href = '/checkout'},
    error: function(xhr, status, error) {
        console.log("Đã xảy ra lỗi trong quá trình gửi dữ liệu giỏ hàng.");
        console.log("Lỗi:", error);
        console.log("Trạng thái:", status);
        console.log("Phản hồi từ máy chủ:", xhr.responseText);
        // Xử lý lỗi nếu cần
    }
});
            
           
  
}


var checkoutButton = document.getElementById('checkoutButton');
checkoutButton.addEventListener('click', displayCart);

document.addEventListener('DOMContentLoaded', function () {

    if (storage.getCart()) {

        cart.setItems(storage.getCart());
        helpers.updateView();

    } else {

        helpers.emptyView();

    }
    var products = document.querySelectorAll('.product button');
    [].forEach.call(products, function (product) {

        product.addEventListener('click', function (e) {

            var item = helpers.itemData(this.parentNode);
            cart.addItem(item);


        });

    });

    document.querySelector('#clear').addEventListener('click', function (e) {

        cart.clearItems();

    });


});