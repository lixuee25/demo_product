<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Product List</title>

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
		<h2>Product List</h2>

		<button class="btn btn-primary mb-3" data-bs-toggle="modal"
			data-bs-target="#productModal" onclick="clearForm()">Add
			Product</button>
		<!--  <button class="btn btn-primary mb-3" data-bs-toggle="modal" data-bs-target="#productSKUModal" onclick="clearForm()">Add ProductsSKU</button> --> 
		<table class="table table-bordered" id="productTable">
			<thead>
				<tr>
					<th>No.</th>
					
					<th>Bar Code</th>
					<th>Product Name</th>
					<th>Category</th>
					<th>Status</th>
					<th>Price</th>
					<th>Unit</th>
					<th>Created At</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>

			</tbody>
		</table>
	</div>
<!-- Model cua Product -->
	<div class="modal fade" id="productModal" tabindex="-1"
		aria-labelledby="productModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="productModalLabel">Add Product</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="productForm">
						<input type="hidden" id="productId">
						<div class="mb-3">
							<label for="productBarcode" class="form-label">BarCode</label> <input
								type="text" class="form-control" id="productBarcode" required>
						</div>
						<div class="mb-3">
							<label for="productName" class="form-label">Product Name</label>
							<input type="text" class="form-control" id="productName" required>
						</div>
						<div class="mb-3">
							<label for="productCategory" class="form-label">Category</label>
							<input type="text" class="form-control" id="productCategory"
								required>
						</div>
						<div class="mb-3">
							<label for="productPrice" class="form-label">Price</label> <input
								type="number" class="form-control" id="productPrice" required>
						</div>
						<div class="mb-3">
							<label for="productUnit" class="form-label">Unit</label> <input
								type="text" class="form-control" id="productUnit" required>
						</div>
						<div class="mb-3">
							<label for="productStatus" class="form-label">Status</label> <select
								class="form-control" id="productStatus">
								<option value="1">Available</option>
								<option value="0">Out of Stock</option>
							</select>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary"
						onclick="saveProduct()">Save</button>
				</div>
			</div>
		</div>
	</div>




	<!-- Modal để thêm SKU -->
	<div class="modal fade" id="productSKUModal" tabindex="-1"
		aria-labelledby="productSKUModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="productSKUModalLabel">Add SKUs</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<input type="hidden" id="productBarCode">

					<!-- SKU Table -->
					<table class="table table-bordered" id="skuTable">
						<thead>
							<tr>
								<th>SKU Code</th>
								<th>SKU Name</th>
								<th>Price</th>
								<th>Created At</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input type="text" class="sku_code form-control"
									placeholder="SKU Code"></td>
								<td><input type="text" class="sku_name form-control"
									placeholder="SKU Name"></td>
								<td><input type="number" class="price form-control"
									placeholder="Price"></td>
								<td><button class="btn btn-danger"
										onclick="$(this).closest('tr').remove()">Remove</button></td>
							</tr>
						</tbody>
					</table>

					
				</div>
				<div class="modal-footer">
				<button type="button" class="btn btn-secondary"
						onclick="addSKURow()">Add SKU</button>
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>							
					<button type="button" class="btn btn-primary" onclick="saveSKU()">Save SKUs</button>  
				</div>
			</div>
		</div>
	</div>

	<script>
    function loadProducts() {
        $.ajax({
            url: '/v1/api/getListProduct',
            method: 'GET',
            dataType: 'json',
            success: function(response) {
                var products = response.result;
                var tableBody = $('#productTable tbody');
                tableBody.empty();

                $.each(products, function(index, product) {
                    var row = $('<tr>');
                    row.append('<td>' + (index + 1) + '</td>');
                   <!-- row.append('<td><img src="" alt="product image"></td>');-->
                    row.append('<td>' + product.barCode + '</td>');
                    row.append('<td>' + product.productName + '</td>');
                    row.append('<td>' + product.categoryName + '</td>');
                    row.append('<td class="' + (product.status === "0" ? "status-inactive" : "status-active") + '">' + (product.status === "0" ? "Out of Stock" : "Available") + '</td>');
                    row.append('<td>' + product.price.toLocaleString('en-US', { style: 'currency', currency: 'VND' }) + '</td>');
                    row.append('<td>' + product.unit + '</td>');

                    var createdAt = new Date(product.createdAt);
                    var formattedDate = createdAt.toLocaleDateString('vi-VN') + ' ' + createdAt.toLocaleTimeString('vi-VN');
                    row.append('<td>' + formattedDate + '</td>');

                    row.append('<td><button class="btn btn-warning btn-sm" onclick="editProduct(' + product.id + ')">Edit</button> ' +
                    		  '<button class="btn btn-info btn-sm" data-bs-toggle="modal" data-bs-target="#productSKUModal" onclick="createSKU(`' + product.barCode + '`)">Detail</button>' +
                              '<button class="btn btn-danger btn-sm" onclick="deleteProduct(' + product.id + ')">Delete</button>' + 
                               
        					   '</td>');

                    tableBody.append(row);
                });
            },
            error: function(xhr, status, error) {
                console.error('API error: ' + error);
            }
        });
    }
    
    
    
    
   

    
    function saveProduct() {
        var productData = {
        		 	barCode: $('#productBarcode').val(),
        		    productName: $('#productName').val(),
        		    categoryName: $('#productCategory').val(),
        		    price: $('#productPrice').val(),
        		    unit: $('#productUnit').val(),
        		    status: $('#productStatus').val()
        };

        var productId = $('#productId').val();
        var url = productId ? '/v1/api/updateProduct/' + productId : '/v1/api/createProduct';
        var method = productId ? 'PUT' : 'POST';

        $.ajax({
            url: url,
            method: method,
            contentType: 'application/json',
            data: JSON.stringify(productData),
            success: function(response) {
                loadProducts();
                $('#productModal').modal('hide');
            },
            error: function(xhr) {
                var errorMessage = xhr.responseJSON ? xhr.responseJSON.message : 'Lỗi không xác định';
                alert('Lỗi: ' + errorMessage);
            }
        });
    }


    function editProduct(productId) {
        $.ajax({
            url: '/v1/api/detailProduct/' + productId,
            method: 'GET',
            success: function(response) {
                var product = response.result;
                $('#productId').val(product.id);
                $('#productBarcode').val(product.barCode);
                $('#productName').val(product.productName);
                $('#productCategory').val(product.categoryName);
                $('#productPrice').val(product.price);
                $('#productUnit').val(product.unit);
                $('#productStatus').val(product.status);
                $('#productModalLabel').text('Edit Product');
                $('#productModal').modal('show');
            },
            error: function(xhr, status, error) {
                console.error('Lỗi tìm chi tiết sản phẩm: ' + error);
            }
        });
    }

    function deleteProduct(productId) {
        if (confirm("Bạn có chắc muốn xóa sản phẩm này không?")) {
            $.ajax({
                url: '/v1/api/deleteProduct/' + productId,
                method: 'DELETE',
                success: function(response) {
                    loadProducts();
                },
                error: function(xhr, status, error) {
                    console.error('Lỗi xóa sản phẩm : ' + error);
                }
            });
        }
    }

                                         
    function clearForm() {
        $('#productForm')[0].reset();
        $('#productId').val('');
        $('#productModalLabel').text('Add Product');
    }

    $(document).ready(function() {
        loadProducts();
    });
    
    
    
   <!-- sku   -->   
   
 
   function createSKU(barCode) {
	    $('#productBarCode').val(barCode); 

	   
	    $.ajax({
	        url: '/v1/api/' + barCode + '/skus', 
	        method: 'GET',
	        success: function(response) {
	            console.log("Thông tin sản phẩm với danh sách SKU: ", response.result);

	            var productWithSKUs = response.result;
	            var skuTableBody = $('#skuTable tbody');
	            skuTableBody.empty(); 

	           
	            productWithSKUs.skus.forEach(function(sku) {
	                var row = $('<tr>');
	                row.append('<td>' + sku.skuCode + '</td>');
	                row.append('<td>' + sku.skuName + '</td>');
	                row.append('<td>' + sku.price.toLocaleString('en-US', { style: 'currency', currency: 'VND' }) + '</td>');
	                skuTableBody.append(row);
	            });

	       
	            $('#productSKUModal').modal('show');
	        },
	        error: function(xhr, status, error) {
	            console.error('Lỗi tìm chi tiết sản phẩm: ' + error);
	        }
	    });
	}


   function addSKURow() {
	    var newRow = `
	        <tr>
	            <td><input type="text" class="form-control sku_code" placeholder="SKU Code"></td>
	            <td><input type="text" class="form-control sku_name" placeholder="SKU Name"></td>
	            <td><input type="number" class="form-control price" placeholder="Price"></td>
	           <!-- <td><button type="button" class="btn btn-danger btn-sm" onclick="removeSKURow(this)">Remove</button></td> -->
	        </tr>`;
	    $('#skuTable tbody').append(newRow);
	}

	function removeSKURow(button) {
	    $(button).closest('tr').remove(); 
	}

   
  

	
	function saveSKU() {
	    var barCode = $('#productBarCode').val(); 
	    var skus = []; 
	   
	    $('#skuTable tbody tr').each(function() {
	        var skuCode = $(this).find('.sku_code').val();
	        var skuName = $(this).find('.sku_name').val();
	        var price = parseFloat($(this).find('.price').val());

	        if (skuCode && skuName && !isNaN(price)) {
	            skus.push({
	                skuCode: skuCode,
	                skuName: skuName,
	                price: price
	            });
	        }
	    });


	   
	    $.ajax({
	        url: '/v1/api/' + barCode + '/skus', 
	        method: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify(skus),
	        success: function(response) {
	            alert('Thêm SKU thành công!');
	            $('#productSKUModal').modal('hide'); 
	            loadProducts(); 
	        },
	        error: function(xhr) {
	            var errorMessage = xhr.responseJSON ? xhr.responseJSON.message : 'Lỗi không xác định';
	            alert('Lỗi: ' + errorMessage); 
	        }
	    });
	}


	
		
</script>

</body>
</html>



<!--   function createSKU(barCode){
   	
  	 $.ajax({
           url: '/v1/api/' + barCode + '/skus',
           method: 'GET',
           success: function(response) {
          
              console.log(JSON.stringify(response));
           },
           error: function(xhr, status, error) {
               console.error('Lỗi tìm chi tiết sản phẩm: ' + error);
           }
       });
  	
  }  -->
  
  
  
  <!-- 
  $.ajax({
          url: '/v1/api/' + barCode + '/skus',
          method: 'GET',
          success: function(response) {
              console.log("Thông tin sản phẩm với danh sách SKU: ", response.result);

             
              var productWithSKUs = response.result;
              var skuTableBody = $('#skuTable tbody');
              skuTableBody.empty(); 
             
              productWithSKUs.skus.forEach(function(sku) {
                  var row = $('<tr>');
                  row.append('<td>' + sku.skuCode + '</td>');
                  row.append('<td>' + sku.skuName + '</td>');
                  row.append('<td>' + sku.price.toLocaleString('en-US', { style: 'currency', currency: 'VND' }) + '</td>');
                 
                  skuTableBody.append(row);
              });

              
              $('#productSKUModal').modal('show');
          },
          error: function(xhr, status, error) {
              console.error('Lỗi tìm chi tiết sản phẩm: ' + error);
          }
      });
  }
   -->
   
   
   <!-- 
   function saveSKU() {
	    var barCode = $('#productBarCode').val(); 
	    var skus = []; 
 
	    $('#skuTable tbody tr').each(function() {
	        var skuCode = $(this).find('.skuCode').val();
	        var skuName = $(this).find('.skuName').val();
	        var price = parseFloat($(this).find('.skuPrice').val());
       
	        if (skuCode && skuName && !isNaN(price)) {
	            skus.push({
	                skuCode: skuCode,
	                skuName: skuName,
	                price: price
	            });
	        }
	    });
	  
	    var data = {
	        barCode: barCode,
	        skus: skus
	    };

	    
	    $.ajax({
	    	url: '/v1/api/' + barCode + '/skus',  
	        method: 'POST',
	        contentType: 'application/json',
	        data: JSON.stringify(data),
	        success: function(response) {
	            alert('Thêm SKU thành công!');
	            $('#productSKUModal').modal('hide'); 
	            loadProducts(); 
	        },
	        error: function(xhr) {
	            var errorMessage = xhr.responseJSON ? xhr.responseJSON.message : 'Lỗi không xác định';
	            alert('Lỗi: ' + errorMessage); 
	        }
	    });
	}

	
  	
    -->
  <!-- 
   function createSKU(barCode) {
	    $('#productBarCode').val(barCode); 

	    $.ajax({
	        url: '/v1/api/' + barCode + '/skus', 
	        method: 'GET',
	        success: function(response) {
	            console.log("Thông tin sản phẩm với danh sách SKU: ", response.result);

	            var productWithSKUs = response.result;
	            var skuTableBody = $('#skuTable tbody');
	            skuTableBody.empty(); 
	            
	            productWithSKUs.skus.forEach(function(sku) {
	                var row = $('<tr>');
	                row.append('<td>' + sku.skuCode + '</td>');
	                row.append('<td>' + sku.skuName + '</td>');
	                row.append('<td>' + sku.price.toLocaleString('en-US', { style: 'currency', currency: 'VND' }) + '</td>');
	                skuTableBody.append(row);
	            });

	            $('#productSKUModal').modal('show');
	        },
	        error: function(xhr, status, error) {
	            console.error('Lỗi tìm chi tiết sản phẩm: ' + error);
	        }
	    });
	}
   -->
   
   
   <!-- 
   function addSKURow() {
	    var newRow = `
	        <tr>
	            <td><input type="text" class="form-control skuCode" placeholder="SKU Code"></td>
	            <td><input type="text" class="form-control skuName" placeholder="SKU Name"></td>
	            <td><input type="number" class="form-control skuPrice" placeholder="Price"></td>
	            <td><button type="button" class="btn btn-danger btn-sm" onclick="removeSKURow(this)">Remove</button></td>
	        </tr>`;
	    $('#skuTable tbody').append(newRow);
	}

	function removeSKURow(button) {
	    $(button).closest('tr').remove();
	}
    -->