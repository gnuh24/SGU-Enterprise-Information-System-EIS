<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="../adminDemo.css" />
    <link rel="stylesheet" href="taoPhieuNhapKho.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>Chi tiết phiếu nhập kho</title>
</head>

<body>
    <div id="root">
        <div class="App">
            <div class="StaffLayout_wrapper__CegPk">
                <?php require_once "../ManagerHeader.php" ?>

                <div class="Manager_wrapper__vOYy">
                    <?php require_once "../ManagerMenu.php" ?>


                    <div style="padding-left: 16%; width: 100%; padding-right: 2rem">
                        <div class="wrapper">
                            <div style="display: flex; padding-top: 1rem; padding-bottom: 1rem;">
                                <h2>Phiếu Nhập Kho</h2>
                                <div style="margin-left: auto;">
                                    <button style="font-family: Arial; font-size: 1.5rem; font-weight: 700; color: white; color: rgb(65, 64, 64); border: 1px solid rgb(65, 64, 64); background-color: white; padding: 1rem; border-radius: 0.6rem; cursor: pointer;" onclick="clearSelectedProducts()">
                                        <a href="QLPhieuNhapKho.php">
                                            <?php
                                            if (!isset($_GET['MaPhieu'])) echo 'Hủy';
                                            else echo 'Quay lại';
                                            ?>
                                        </a>
                                    </button>

                                    <button id="addsp" style="margin-left: 1rem; font-family: Arial; font-size: 1.5rem; font-weight: 700; color: white; background-color: rgb(65, 64, 64); padding: 1rem; border-radius: 0.6rem; cursor: pointer;" onclick="setShowModal(true)">
                                        Thêm Sản Phẩm
                                    </button>
                                </div>
                            </div>
                            <div class="boxFeature d-flex flex-wrap" style="gap: 2rem;">
                                <input
                                    type="text"
                                    class="form-control"
                                    id="manhacungcap"
                                    placeholder="Nhập tên nhà cung cấp"
                                    style="width: 35%;padding: 10px 0px" />
                                <input
                                    type="text"
                                    class="form-control"
                                    id="sodienthoainhacungcap"
                                    placeholder="Nhập số điện thoại nhà cung cấp"
                                    style="width: 35%;padding: 10px 0;" />
                                <?php if (!isset($_GET['MaPhieu'])) echo '
                                
                                <button style="margin-left: 1rem; font-family: Arial; font-size: 1.5rem; font-weight: 700; color: white; background-color: rgb(65, 64, 64); padding: 1rem; border-radius: 0.6rem; cursor: pointer;" onclick="handleSubmit()">
                                    Tạo phiếu nhập</button>';
                                ?>
                            </div>



                            <div class="boxTable">
                                <div style="background-color: rgb(236, 233, 233); width: 75%;">
                                    <table style="border-collapse: collapse; width: 100%; margin-top: 1rem; border-radius: 1rem;">
                                        <thead>
                                            <tr style="background-color: rgb(40, 40, 40); color: white;">
                                                <th style="padding: 0.5rem;">Mã Sản Phẩm</th>
                                                <th style="padding: 0.5rem;">Tên Sản Phẩm</th>
                                                <th style="padding: 0.5rem;">Đơn giá</th>
                                                <th style="padding: 0.5rem;">Số lượng</th>
                                                <th style="padding: 0.5rem;">Lợi nhuận</th>
                                                <th style="padding: 0.5rem;">Thao tác</th>

                                            </tr>
                                        </thead>
                                        <tbody id="tableBody">
                                        </tbody>
                                    </table>
                                </div>
                                <div style="width: 25%; background-color: rgb(236, 233, 233); padding: 1rem;" id="here">
                                    <label>
                                        <p style="font-size: 1.3rem; font-weight: 700;">Mã Phiếu</p>
                                        <input id="maPNK" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;" value="<?php if (isset($_GET['MaPhieu'])) echo $_GET['MaPhieu']; ?>" disabled="true" />
                                    </label>
                                    <label>
                                        <p style="font-size: 1.3rem; font-weight: 700; margin-top: 1rem;">Tổng Giá Trị</p>
                                        <input id="totalvalue" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;" value="" disabled="true" />
                                    </label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal_overlay" style="<?php
                                        if (isset($_GET['MaPhieu'])) {
                                            echo 'display:none';
                                        };
                                        ?>">
        <div class="modal_content">
            <!-- Đầu modal_content -->
            <span class="close_btn">
                <h3>Chọn Sản Phẩm</h3>
                <i onclick="setShowModal(false)">X</i>
            </span>
            <div style="margin-top: 1rem;">
                <div style="position: relative;">
                    <i class="fa fa-search"></i>
                    <input class="input" placeholder="Tìm kiếm sản phẩm" id="timkiemsp" onkeyup="handleSearchChange(event)" />
                </div>
                <div class="table_wrapper">
                    <table class="product_table">
                        <thead>
                            <tr style="background-color: rgb(40, 40, 40); color: white;">
                                <th style="padding: 0.5rem;">Mã Sản Phẩm</th>
                                <th style="padding: 0.5rem;width: 477px;">Tên Sản Phẩm</th>
                                <th style="padding: 0.5rem;">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody id="tableBody1">
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="pagination" id="pagination"></div>
        </div>
    </div>
    <div class="modal_overlay1" style="<?php
                                        if (isset($_GET['MaPhieu'])) {
                                            echo 'display:none';
                                        };
                                        ?>">

    </div>
</body>



<script>
    function isNewProduct(productId) {
        return productId && productId.startsWith('new-');
    }


    $(document).on('change', '.product_checkbox', function() {
        saveSelectedProducts(); // Lưu trạng thái của các sản phẩm đã chọn

        // Xóa nội dung hiện tại của bảng
        $('#tableBody').empty();

        let selectedProducts = JSON.parse(localStorage.getItem('selectedProducts')) || [];

        selectedProducts.forEach(function(product, index) {
            var selectedProductHTML = `
        <tr style="text-align: center;" data-product-id="${product.id}">
            <td style="padding: 0.5rem;" name="MaSanPham[]">${!isNewProduct(product.id) ? product.id : ''}</td>
            <td style="padding: 0.5rem;">${product.name}</td>
            <td style="padding: 0.5rem;">
                <input type="text" name="donGia[]" id="donGia${product.id}" onblur="formatCurrency(this)" onfocus="clearFormat(this)" value="${product.donGia}" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
            </td>
            <td style="padding: 0.5rem;">
                <input type="text" name="soLuong[]" id="soLuong${product.id}" value="${product.soLuong}" onblur="validateSoLuong(this)" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
            </td>
            <td style="padding: 0.5rem;">
                <input type="text" name="loiNhuan[]" id="loiNhuan${product.id}" value="${product.loiNhuan}" onblur="validateSoLuong(this)" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
            </td>
            <td style="padding: 0.5rem;">
                <button class="delete-btn" data-index="${index}" style="color: red; font-weight: 700; background: none; border: none; cursor: pointer;">X</button>
            </td>
        </tr>`;
            $('#tableBody').append(selectedProductHTML);
        });

        // Gọi ngay tính tổng sau khi thêm sản phẩm vào bảng
        calculateTotalPrice(); // Tính toán lại tổng giá trị ngay sau khi thêm sản phẩm
    });


    document.addEventListener('DOMContentLoaded', function() {
        var modal = document.querySelector('.modal_overlay1');
        if (modal) {
            modal.style.display = 'none'; // Ẩn modal khi trang được tải
        }
    });

    function handleSubmit() {
        var maNhaCungCap = document.getElementById('manhacungcap').value;
        var sodienthoainhacungcap = document.getElementById('sodienthoainhacungcap').value;
        var totalValue = clearCurrencyFormat(document.getElementById('totalvalue').value);
        var productData = [];

        if (maNhaCungCap === '') {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Vui lòng điền tên nhà cung cấp',
            });
            return;
        }

        // Kiểm tra độ dài và ký tự đặc biệt
        var supplierNameRegex = /^[a-zA-Z0-9\s]{3,100}$/;
        if (!supplierNameRegex.test(maNhaCungCap)) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Tên nhà cung cấp phải từ 3 đến 100 ký tự và không chứa ký tự đặc biệt.',
            });
            return;
        }


        if (sodienthoainhacungcap === '' || !validatePhoneNumber(sodienthoainhacungcap)) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Số điện thoại nhà cung cấp không hợp lệ',
            });
            return;
        }

        // Lấy dữ liệu sản phẩm từ bảng
        $('#tableBody tr').each(function() {
            var maSanPham = $(this).find('td:nth-child(1)').text().trim();
            var tenSanPham = $(this).find('td:nth-child(2)').text().trim();
            var donGia = $(this).find('td:nth-child(3) input').val().trim();
            donGia = clearCurrencyFormat(donGia);
            var soLuong = $(this).find('td:nth-child(4) input').val().trim();
            var loiNhuan = $(this).find('td:nth-child(5) input').val().trim();

            var productItem = {
                idProductId: maSanPham || null, // Nếu không có mã sản phẩm, để null
                productName: tenSanPham,
                unitPrice: parseFloat(donGia), // Chuyển về số thực
                quantity: parseInt(soLuong), // Chuyển về số nguyên
                profit: parseFloat(loiNhuan), // Chuyển về số thực
                total: parseFloat(donGia) * parseInt(soLuong) // Tính tổng giá trị
            };

            productData.push(productItem);
        });

        // Kiểm tra xem có sản phẩm nào không
        if (productData.length === 0) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Vui lòng thêm ít nhất một sản phẩm.',
            });
            return false;
        }
        const token = sessionStorage.getItem("token");

        var formData = new FormData();

        // Thêm các trường thông tin chung vào FormData
        formData.append("totalPrice", totalValue);
        formData.append("supplier", maNhaCungCap);
        formData.append("supplierPhone", sodienthoainhacungcap);

        // Duyệt qua productData và thêm từng sản phẩm vào FormData
        productData.forEach((product, index) => {
            formData.append(`inventoryReportDetailCreateFormList[${index}].idProductId`, product.idProductId || "");
            formData.append(`inventoryReportDetailCreateFormList[${index}].productName`, product.productName || "");
            formData.append(`inventoryReportDetailCreateFormList[${index}].quantity`, parseInt(product.quantity) || 0);
            formData.append(`inventoryReportDetailCreateFormList[${index}].unitPrice`, parseFloat(product.unitPrice) || 0);
            formData.append(`inventoryReportDetailCreateFormList[${index}].total`, parseFloat(product.total) || 0);
            formData.append(`inventoryReportDetailCreateFormList[${index}].profit`, parseFloat(product.profit) || 0);
        });
        // Tạo đối tượng dữ liệu để gửi


        // Kiểm tra giá trị totalPrice
        var totalPrice = parseInt(totalValue);
        if (isNaN(totalPrice)) {
            console.error("Invalid totalPrice");
            return;
        }

        $.ajax({
            type: 'POST', // Sử dụng PATCH thay vì POST
            url: 'http://localhost:8080/InventoryReport', // Cập nhật URL nếu cần
            processData: false, // Không xử lý dữ liệu, để FormData tự xử lý
            contentType: false, // Tắt tự động gán content type
            data: formData, // Gửi FormData
            headers: {
                'Authorization': 'Bearer ' + token,
            },
            success: function(response) {
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Cập nhật phiếu nhập kho thành công',
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = 'QLPhieuNhapKho.php';
                    }
                });
            },
            error: function(xhr, status, error) {
                console.error('Đã xảy ra lỗi khi gửi yêu cầu.', error);
            }
        });
    }

    function saveSelectedProducts() {
        // Lấy các sản phẩm đã được lưu từ localStorage
        let selectedProducts = JSON.parse(localStorage.getItem('selectedProducts')) || [];

        // Tạo Map từ danh sách các sản phẩm đã lưu trong localStorage
        let selectedMap = new Map(selectedProducts.map(product => [product.id, product]));

        // Duyệt qua tất cả các checkbox sản phẩm trên giao diện
        $('input[type="checkbox"]').each(function() {
            let productId = $(this).attr('id'); // ID của sản phẩm trong bảng
            let productName = $(this).closest('tr').find('td:eq(1)').text().trim(); // Tên sản phẩm

            // Lấy giá trị đơn giá, số lượng, lợi nhuận từ các ô input tương ứng
            let donGiaElement = document.getElementById(`donGia${productId}`);
            let soLuongElement = document.getElementById(`soLuong${productId}`);
            let loiNhuanElement = document.getElementById(`loiNhuan${productId}`);
            let donGia = donGiaElement ? clearCurrencyFormat(donGiaElement.value) : "1";
            let soLuong = soLuongElement ? soLuongElement.value : "1";
            let loiNhuan = loiNhuanElement ? loiNhuanElement.value : '1';

            if ($(this).prop('checked')) {
                // Nếu sản phẩm đã được chọn, cập nhật hoặc thêm vào Map
                if (selectedMap.has(productId)) {
                    let product = selectedMap.get(productId);
                    product.donGia = donGia;
                    product.soLuong = soLuong;
                    product.loiNhuan = loiNhuan;
                } else {
                    // Thêm sản phẩm mới với ID đã tồn tại trong checkbox
                    selectedMap.set(productId, {
                        id: productId,
                        name: productName,
                        donGia: donGia,
                        soLuong: soLuong,
                        loiNhuan: loiNhuan
                    });
                }
            } else {
                // Nếu sản phẩm không được chọn, vẫn giữ lại trong Map, không xóa
                if (selectedMap.has(productId)) {
                    let product = selectedMap.get(productId);
                    product.donGia = donGia;
                    product.soLuong = soLuong;
                    product.loiNhuan = loiNhuan;
                }
            }
        });

        // Xử lý các sản phẩm không có checkbox nhưng đã tồn tại trong localStorage (cập nhật từ bảng)
        selectedMap.forEach((product, productId) => {
            if (!$(`#${productId}`).length) {
                // Nếu sản phẩm không có checkbox (không có trong giao diện), vẫn cập nhật thông tin
                let donGiaElement = document.getElementById(`donGia${productId}`);
                let soLuongElement = document.getElementById(`soLuong${productId}`);
                let loiNhuanElement = document.getElementById(`loiNhuan${productId}`);
                let donGia = donGiaElement ? clearCurrencyFormat(donGiaElement.value) : product.donGia;
                let soLuong = soLuongElement ? soLuongElement.value : product.soLuong;
                let loiNhuan = loiNhuanElement ? loiNhuanElement.value : product.loiNhuan;

                product.donGia = donGia;
                product.soLuong = soLuong;
                product.loiNhuan = loiNhuan;
            }
        });

        // Lưu lại danh sách sản phẩm đã cập nhật vào localStorage
        localStorage.setItem('selectedProducts', JSON.stringify([...selectedMap.values()]));

        // Tính toán lại tổng giá trị
        calculateTotalPrice();
    }



    $(document).on('input', 'input[name="donGia[]"], input[name="soLuong[]"], input[name="loiNhuan[]"]', function() {
        calculateTotalPrice();
        saveSelectedProducts();
    });


    function loadSelectedProducts() {
        let selectedProducts = JSON.parse(localStorage.getItem('selectedProducts')) || [];
        let selectedIds = new Set(selectedProducts.map(product => product.id));

        $('.product_checkbox').each(function() {
            $(this).prop('checked', selectedIds.has($(this).attr('id')));
        });
    }




    $(document).on('click', '.delete-btn', function() {
        let index = $(this).data('index');

        let selectedProducts = JSON.parse(localStorage.getItem('selectedProducts')) || [];

        // Xóa sản phẩm theo chỉ số (index) khỏi danh sách
        selectedProducts.splice(index, 1);

        // Cập nhật lại localStorage với danh sách đã được cập nhật
        localStorage.setItem('selectedProducts', JSON.stringify(selectedProducts));

        // Làm mới bảng hiển thị
        $('#tableBody').empty();
        loadSelectedProducts();
        // Hiển thị lại sản phẩm còn lại
        selectedProducts.forEach(function(product, index) {
            var selectedProductHTML = `
        <tr style="text-align: center;" data-product-id="${product.id}">
            <td style="padding: 0.5rem;" name="MaSanPham[]">${product.id}</td>
            <td style="padding: 0.5rem;">${product.name}</td>
            <td style="padding: 0.5rem;">
                <input type="text" name="donGia[]" id="donGia${product.id}" onblur="formatCurrency(this)" onfocus="clearFormat(this)" value="${product.donGia}" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
            </td>
            <td style="padding: 0.5rem;">
                <input type="text" name="soLuong[]" id="soLuong${product.id}" value="${product.soLuong}" onblur="validateSoLuong(this)" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
            </td>
            <td style="padding: 0.5rem;">
                <input type="text" name="loiNhuan[]" id="loiNhuan${product.id}" value="${product.loiNhuan}" onblur="validateSoLuong(this)" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
            </td>
            <td style="padding: 0.5rem;">
                <button class="delete-btn" data-index="${index}" style="color: red; font-weight: 700; background: none; border: none; cursor: pointer;">X</button>
            </td>
        </tr>`;
            $('#tableBody').append(selectedProductHTML);
        });

        // Tính toán lại tổng giá trị sau khi xóa sản phẩm
        calculateTotalPrice();
    });

    function validateDonGia(input) {
        var donGia = parseFloat(input.value);
        if (donGia < 1 || isNaN(donGia)) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Đơn giá không được nhỏ hơn 1',
            });

            input.value = "1";
        }
    }

    function validateSoLuong(input) {
        var soLuong = parseInt(input.value);
        if (soLuong < 1 || isNaN(soLuong)) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: "Số lượng phải là một số nguyên lớn hơn hoặc bằng 1",
            });
            input.value = "1";
        }
    }
    let currentSearch = ''; // Biến toàn cục để lưu trữ giá trị tìm kiếm hiện tại

    function handleSearchChange(event) {
        // Lưu giá trị tìm kiếm và gọi hàm load dữ liệu cho trang đầu tiên
        currentSearch = event.target.value.trim();
        loaddatasp(1, currentSearch);
    }

    function loaddatasp(page, search) {
        const token = sessionStorage.getItem("token");

        $('#tableBody1').empty(); // Xóa nội dung cũ của bảng
        let ajaxData = {
            pageNumber: page
        };

        if (search) {
            ajaxData.search = search;
        }

        $.ajax({
            url: 'http://localhost:8080/Product/Admin',
            type: 'GET',
            dataType: "json",
            data: ajaxData,
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(response) {
                var data = response.content;
                var tableBody = document.getElementById("tableBody1");
                var tableContent = "";
                data.forEach(function(record) {
                    var checked = localStorage.getItem('selectedProducts') ?
                        JSON.parse(localStorage.getItem('selectedProducts')).includes(record['id']) :
                        false;
                    var trContent = `
                <tr style="text-align: center;">
                    <td style="padding: 0.5rem;">${record['id']}</td>
                    <td style="padding: 0.5rem;">${record['productName']}</td>
                    <td style="padding: 0.5rem;">
                        <input type="checkbox" class="product_checkbox" id="${record['id']}" ${checked ? 'checked' : ''}/>
                    </td>
                </tr>`;
                    tableContent += trContent;
                });

                tableBody.innerHTML = tableContent;
                loadSelectedProducts(); // Khôi phục trạng thái các sản phẩm đã chọn
                createPagination(page, response.totalPages);
            },
            error: function(xhr, status, error) {
                console.error('Lỗi khi gọi API: ', error);
            }
        });
    }


    function createPagination(currentPage, totalPages) {
        var paginationContainer = document.getElementById("pagination");

        // Xóa nút phân trang cũ (nếu có)
        paginationContainer.innerHTML = '';

        if (totalPages > 1) {
            // Tạo nút cho từng trang và thêm vào chuỗi HTML
            var paginationHTML = '';
            for (var i = 1; i <= totalPages; i++) {
                paginationHTML += '<button class="pageButton">' + i + '</button>';
            }

            // Thiết lập nút phân trang vào paginationContainer
            paginationContainer.innerHTML = paginationHTML;

            // Thêm sự kiện click cho từng nút phân trang
            paginationContainer.querySelectorAll('.pageButton').forEach(function(button, index) {
                button.addEventListener('click', function() {
                    // Gọi hàm loaddatasp khi người dùng click vào nút phân trang
                    loaddatasp(index + 1, currentSearch); // Sử dụng currentSearch thay vì lấy từ input
                });
            });

            // Đánh dấu trang hiện tại
            paginationContainer.querySelector('.pageButton:nth-child(' + currentPage + ')').classList.add('active');
        }
    }

    $(document).ready(function() {
        localStorage.removeItem('selectedProducts');
        if (!checkMaPhieuInUrl()) {
            loadSelectedProducts();
            loaddatasp(1, ''); // Gọi hàm với trang đầu tiên và không có tìm kiếm mặc định
        }
    });


    function loadDataAllWhenUrlHaveId(maPhieu) {
        const token = sessionStorage.getItem("token");

        $.ajax({
            url: 'http://localhost:8080/InventoryReport/' + maPhieu,
            type: 'GET',
            dataType: "json",
            headers: {
                'Authorization': 'Bearer ' + token
            },
            success: function(response) {
                // Cập nhật thông tin nhà cung cấp
                var maNhaCungCap = document.getElementById('manhacungcap');
                maNhaCungCap.value = response.supplier;

                var sodienthoainhacungcap = document.getElementById('sodienthoainhacungcap');
                sodienthoainhacungcap.value = response.supplierPhone;

                // Cập nhật mã phiếu
                var maPNK = document.getElementById("maPNK");
                maPNK.value = response.id; // Đúng cách gán giá trị cho thuộc tính value

                // Cập nhật tổng giá trị
                var totalValue = document.getElementById('totalvalue');
                totalValue.value = formatCurrency1(response.totalPrice);

                var productData = response.inventoryReportDetails;

                var selectedProducts = [];
                var selectedProducts = productData.map(function(product) {
                    return {
                        id: product.productId.toString(),
                        name: product.productName,
                        donGia: product.quantity,
                        soLuong: product.unitPrice,
                        loiNhuan: product.profit

                    };
                });
                localStorage.setItem('selectedProducts', JSON.stringify(selectedProducts));
                // Cập nhật bảng sản phẩm
                var tableBody = document.getElementById("tableBody");
                tableBody.innerHTML = ""; // Xóa nội dung cũ của bảng
                productData.forEach(function(product) {
                    var selectedProductHTML = `
                <tr style="text-align: center;">
                    <td style="padding: 0.5rem;" name="MaSanPham[]">${product.productId}</td>
                    <td style="padding: 0.5rem;">${product.productName}</td>
                    <td style="padding: 0.5rem;">
                        <input type="text" name="donGia[]" id="donGia${product.productId}" onblur="formatCurrency(this)" onfocus="clearFormat(this)" value="${formatCurrency1(product.unitPrice)}" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
                    </td>
                    <td style="padding: 0.5rem;">
                        <input type="text" name="soLuong[]" id="soLuong${product.productId}" value="${product.quantity}" onblur="validateSoLuong(this)" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
                    </td>
                      <td style="padding: 0.5rem;">
                        <input type="text" name="loiNhuan[]" id="loiNhuan${product.productId}" value="${product.profit}" onblur="validateSoLuong(this)" style="height: 3rem; padding: 0.5rem; width: 100%; background-color: white; font-weight: 700; margin-top: 0.5rem;text-align: right;">
                    </td>
                </tr>`;
                    tableBody.insertAdjacentHTML('beforeend', selectedProductHTML);
                });

                // Tải thêm dữ liệu và tạo phân trang nếu cần
                loaddatasp(1, '');
                loadSelectedProducts(); // Khôi phục trạng thái các sản phẩm đã chọn

                document.querySelectorAll('input[name="donGia[]"], input[name="soLuong[]"],input[name="loiNhuan[]"]').forEach(function(input) {
                    input.disabled = true;
                });
                document.getElementById('manhacungcap').disabled = true;
                document.getElementById('sodienthoainhacungcap').disabled = true;
                document.getElementById('addsp').style.display = 'none';

            },
            error: function(xhr, status, error) {
                console.error('Lỗi khi gọi API: ', error);
            }
        });
    }


    // Hàm kiểm tra URL và gọi hàm nếu MaPhieu tồn tại
    function checkMaPhieuInUrl() {
        // Lấy các tham số từ URL
        const urlParams = new URLSearchParams(window.location.search);
        const maPhieu = urlParams.get('MaPhieu');

        // Nếu MaPhieu tồn tại trong URL
        if (maPhieu) {
            loadDataAllWhenUrlHaveId(maPhieu);
        }
    }

    function clearCurrencyFormat(currencyString) {
        return currencyString.replace(/[^\d]/g, '');
    }



    function formatCurrency(input) {
        let value = input.value;
        value = value.replace(/[^\d]/g, ''); // Loại bỏ ký tự không phải số
        input.value = Number(value).toLocaleString('en-US'); // Định dạng tiền tệ
    }

    function formatCurrency1(input) {
        let value = parseFloat(input); // Chuyển đổi đầu vào thành số
        if (!isNaN(value)) {
            // Định dạng tiền tệ Việt Nam
            return value.toLocaleString('vi-VN', {
                style: 'currency',
                currency: 'VND'
            });
        } else {
            console.error('Input must be a number.');
            return '';
        }
    }

    function clearFormat(input) {
        let value = input.value;
        value = value.replace(/[,]/g, ''); // Loại bỏ dấu phân cách hàng nghìn
        input.value = value;
    }

    function validatePhoneNumber(sodienthoainhacungcap) {
        // Loại bỏ các ký tự không phải số
        let phoneNumber = sodienthoainhacungcap.replace(/\D/g, '');

        // Kiểm tra độ dài và bắt đầu bằng số 0
        if (!/^0\d{9,10}$/.test(phoneNumber)) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Số điện thoại phải bắt đầu bằng số 0 và có từ 10 đến 11 chữ số.',
            });
            return false;
        }

        return true; // Nếu số điện thoại hợp lệ
    }


    function setShowModal(show) {
        var modalOverlay = document.querySelector('.modal_overlay');
        modalOverlay.style.display = show ? '' : 'none';
    }


    function clearSelectedProducts() {
        localStorage.removeItem('selectedProducts');
    }

    function calculateTotalPrice() {
        var totalPrice = 0; // Biến lưu tổng giá trị
        var tableRows = document.querySelectorAll('#tableBody tr'); // Lấy tất cả các dòng trong bảng

        // Duyệt qua từng dòng để tính tổng
        tableRows.forEach(function(row) {
            var quantityCell = row.querySelector('input[name="soLuong[]"]'); // Ô số lượng
            var priceCell = row.querySelector('input[name="donGia[]"]'); // Ô đơn giá

            // Kiểm tra nếu các ô tồn tại và có giá trị hợp lệ
            if (quantityCell && priceCell) {
                var quantity = parseInt(quantityCell.value) || 0; // Chuyển đổi số lượng thành số, nếu không hợp lệ thì mặc định là 0
                var price = parseFloat(priceCell.value.replace(/,/g, '')) || 0; // Chuyển đổi giá thành số, loại bỏ dấu phẩy nếu có, nếu không hợp lệ thì mặc định là 0

                // Tính giá trị cho dòng này và cộng vào tổng
                totalPrice += quantity * price;
            }
        });

        // Định dạng lại tổng giá trị thành chuỗi tiền tệ
        var formattedTongGiaTri = totalPrice.toLocaleString('vi-VN', {
            style: 'currency',
            currency: 'VND'
        });

        // Cập nhật vào ô hiển thị tổng giá trị
        var totalPriceElement = document.getElementById('totalvalue');
        if (totalPriceElement) {
            totalPriceElement.value = formattedTongGiaTri; // Gán giá trị đã định dạng vào ô tổng giá trị
        }
    }

    // Gọi hàm khi trang được tải
    window.onload = checkMaPhieuInUrl;
</script>