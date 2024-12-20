<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventory Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        .status-active {
            color: green;
        }
        .status-inactive {
            color: red;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <h2>Inventory Management</h2>

    <!-- Button to trigger Add Inventory Modal -->
    <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#inventoryModal" onclick="clearForm()">Add Inventory</button>

    <!-- Table to display inventory -->
    <table class="table table-bordered" id="inventoryTable">
        <thead>
            <tr>
                <th>No.</th>
                <th>Product Name</th>
                <th>Quantity</th>
                <th>Last Updated</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
           
        </tbody>
    </table>
</div>

<!-- Modal inventory -->
<div class="modal fade" id="inventoryModal" tabindex="-1" aria-labelledby="inventoryModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="inventoryModalLabel">Add Inventory</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="inventoryForm">
                    <input type="hidden" id="inventoryId">
                    <div class="mb-3">
                        <label for="productSelect" class="form-label">Select Product</label>
                        <select class="form-control" id="productSelect" required>
                            <option value="" disabled selected>Select a product</option>
                            
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="quantity" class="form-label">Quantity</label>
                        <input type="number" class="form-control" id="quantity" required>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="saveInventory()">Save</button>
            </div>
        </div>
    </div>
</div>


<script>
//
function loadProducts() {
    $.ajax({
        url: '/v1/api/getListProduct',  
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            if(response && response.result) {
                var products = response.result;  
                var productSelect = $('#productSelect');
                productSelect.empty();  
                productSelect.append('<option value="" disabled selected>Chọn sản phẩm</option>');  

               
                $.each(products, function(index, product) {
                    productSelect.append('<option value="' + product.id + '">' + product.productName + '</option>');
                });
            } else {
                console.error('Không có sản phẩm trong response.result');
            }
        },
        error: function(xhr, status, error) {
            console.error('Lỗi loadProducts: ' + error);
        }
    });
}


function loadInventories() {
    $.ajax({
        url: '/v3/api/getListInventory',
        method: 'GET',
        dataType: 'json',
        success: function(response) {
            var inventories = response.result;
            var tableBody = $('#inventoryTable tbody');
            tableBody.empty();

            $.each(inventories, function(index, inventory) {
                var row = $('<tr>');
                row.append('<td>' + (index + 1) + '</td>');
                row.append('<td>' + inventory.productName + '</td>');
                row.append('<td>' + inventory.quantity + '</td>');
                row.append('<td>' + new Date(inventory.lastUpdated).toLocaleDateString('vi-VN') + '</td>');
                row.append('<td>' +
                    '<button class="btn btn-warning btn-sm" onclick="editInventory(' + inventory.id + ')">Sửa</button> ' +
                    '<button class="btn btn-danger btn-sm" onclick="deleteInventory(' + inventory.id + ')">Xóa</button>' +
                    '</td>');
                tableBody.append(row);
            });
        },
        error: function(xhr, status, error) {
            console.error('Lỗi loadInventories: ' + error);
        }
    });
}


function saveInventory() {
    var inventoryData = {
        productId: $('#productSelect').val(),  
        quantity: $('#quantity').val()  
    };

    var inventoryId = $('#inventoryId').val();
    var url = inventoryId ? '/v3/api/updateInventory/' + inventoryId : '/v3/api/createInventory';
    var method = inventoryId ? 'PUT' : 'POST';

    $.ajax({
        url: url,
        method: method,
        contentType: 'application/json',
        data: JSON.stringify(inventoryData),  
        success: function(response) {
            loadInventories();  
            $('#inventoryModal').modal('hide');  
        },
        error: function(xhr) {
            alert('Lỗi saveInventory');
        }
    });
}


function editInventory(inventoryId) {
    $.ajax({
        url: '/v3/api/getInventory/' + inventoryId, 
        method: 'GET',
        success: function(response) {
            var inventory = response.result;
            $('#inventoryId').val(inventory.id);
            $('#productSelect').val(inventory.productId);  
            $('#quantity').val(inventory.quantity);  
            $('#inventoryModalLabel').text('Sửa Kho');
            $('#inventoryModal').modal('show');  
        },
        error: function(xhr, status, error) {
            console.error('Lỗi chi tiết: ' + error);
        }
    });
}


function deleteInventory(inventoryId) {
    if (confirm("Bạn chắc chắn muốn xóa?")) {
        $.ajax({
            url: '/v3/api/deleteInventory/' + inventoryId, 
            method: 'DELETE',
            success: function(response) {
                loadInventories();
            },
            error: function(xhr, status, error) {
                console.error('Lỗi deleteInventory: ' + error);
            }
        });
    }
}


$(document).ready(function() {
    loadInventories();  
    loadProducts();  
});

</script>

</body>
</html>
