<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Customer List</title>

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

<style>
body {
	font-family: Arial, sans-serif;
}

.status-active {
	color: green;
}

.status-inactive {
	color: red;
}

table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	padding: 10px;
	text-align: left;
}

td img {
	margin-right: 10px;
	max-width: 50px;
}
</style>
</head>
<body>

	<div class="container mt-5">
		<h2>Customer List</h2>

		<button class="btn btn-primary mb-3" data-bs-toggle="modal"
			data-bs-target="#customerModal" onclick="clearForm()">Add Customer</button>

		<table class="table table-bordered" id="customerTable">
			<thead>
				<tr>
					<th>No.</th>
					<th>Customer Code</th>
					<th>Name</th>
					<th>Email</th>
					<th>Phone</th>
					<th>Address</th>
					<th>Created At</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	</div>

	<!-- Modal thêm/sửa Customer -->
<div class="modal fade" id="customerModal" tabindex="-1" aria-labelledby="customerModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="customerModalLabel">Add Customer</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <form id="customerForm">
          <input type="hidden" id="customerId">
          <div class="mb-3">
            <label for="customerCode" class="form-label">Customer Code</label>
            <input type="text" class="form-control" id="customerCode" name="customerCode" required>
          </div>
          <div class="mb-3">
            <label for="customerName" class="form-label">Customer Name</label>
            <input type="text" class="form-control" id="customerName" name="customerName" required>
          </div>
          <div class="mb-3">
            <label for="customerEmail" class="form-label">Email</label>
            <input type="email" class="form-control" id="customerEmail" name="email" required>
          </div>
          <div class="mb-3">
            <label for="customerPhone" class="form-label">Phone</label>
            <input type="text" class="form-control" id="customerPhone" name="phone" required>
          </div>
          <div class="mb-3">
            <label for="customerAddress" class="form-label">Address</label>
            <input type="text" class="form-control" id="customerAddress" name="address" required>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" onclick="saveCustomer()">Save</button>
      </div>
    </div>
  </div>
</div>


	<script>
	function loadCustomers() {
	    $.ajax({
	        url: '/v2/api/getListCustomer',
	        method: 'GET',
	        dataType: 'json',
	        success: function(response) {
	            var customers = response.result; // Đảm bảo API trả về đúng key này
	            var tableBody = $('#customerTable tbody');
	            tableBody.empty();

	            $.each(customers, function(index, customer) {
	                var row = $('<tr>');
	                row.append('<td>' + (index + 1) + '</td>');
	                row.append('<td>' + customer.customerCode + '</td>');
	                row.append('<td>' + customer.customerName + '</td>');
	                row.append('<td>' + customer.email + '</td>');
	                row.append('<td>' + customer.phone + '</td>');
	                row.append('<td>' + customer.address + '</td>');
	                var createdAt = new Date(customer.createdAt);
	                row.append('<td>' + createdAt.toLocaleDateString('vi-VN') + '</td>');
	                row.append('<td>' +
	                    '<button class="btn btn-warning btn-sm" onclick="editCustomer(' + customer.id + ')">Edit</button> ' +
	                    '<button class="btn btn-danger btn-sm" onclick="deleteCustomer(' + customer.id + ')">Delete</button>' +
	                    '</td>');
	                tableBody.append(row);
	            });
	        },
	        error: function(xhr, status, error) {
	            console.error('Error loading customers: ' + error);
	        }
	    });
	}

	function saveCustomer() {
	    var customerData = {
	        customerCode: $('#customerCode').val(),
	        customerName: $('#customerName').val(),
	        email: $('#customerEmail').val(),
	        phone: $('#customerPhone').val(),
	        address: $('#customerAddress').val()
	    };

	    var customerId = $('#customerId').val();
	    var url = customerId ? '/v2/api/updateCustomer/' + customerId : '/v2/api/createCustomer';
	    var method = customerId ? 'PUT' : 'POST';

	    $.ajax({
	        url: url,
	        method: method,
	        contentType: 'application/json',
	        data: JSON.stringify(customerData),
	        success: function(response) {
	            loadCustomers();
	            $('#customerModal').modal('hide');
	        },
	        error: function(xhr) {
	            alert('Error saving customer');
	        }
	    });
	}


	function editCustomer(customerId) {
		 console.log('Edit Customer ID:', customerId);
	    $.ajax({
	        url: '/v2/api/detailCustomer/' + customerId,
	        method: 'GET',
	        success: function(response) {
	            var customer = response.result;
	            $('#customerId').val(customer.id);
	            $('#customerCode').val(customer.customerCode);
	            $('#customerName').val(customer.customerName);
	            $('#customerEmail').val(customer.email);
	            $('#customerPhone').val(customer.phone);
	            $('#customerAddress').val(customer.address);
	            $('#customerModalLabel').text('Edit Customer');
	            $('#customerModal').modal('show');
	        },
	        error: function(xhr, status, error) {
	            console.error('Error loading customer details: ' + error);
	        }
	    });
	}
	

	function deleteCustomer(customerId) {
		 console.log('Delete Customer ID:', customerId);
	    if (confirm("Bạn chắc chắn muốn xóa customer?")) {
	        $.ajax({
	            url: '/v2/api/deleteCustomer/' + customerId,
	            method: 'DELETE',
	            success: function(response) {
	                loadCustomers();
	            },
	            error: function(xhr, status, error) {
	                console.error('Error deleting customer: ' + error);
	            }
	        });
	    }
	}
		

	function clearForm() {
	    $('#customerForm')[0].reset();
	    $('#customerId').val('');
	    $('#customerModalLabel').text('Add Customer');
	}


    $(document).ready(function() {
        loadCustomers();
    });
	</script>

</body>
</html>
