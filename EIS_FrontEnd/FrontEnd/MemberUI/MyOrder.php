<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" />
    <link rel="stylesheet" href="./HomePage.css" />
    <link rel="stylesheet" href="./login.css" />
    <link rel="stylesheet" href="MyOrder.css" />

    <title>Đơn hàng của tôi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="../HelperUI/formatOutput.js"></script>

</head>

<body>
    <?php require_once "./Header.php" ?>

    <section>
        <div class="center-text" style="margin-top: 20px;">
            <div class="title_section">
                <div class="bar"></div>
                <h2 class="center-text-share">Đơn hàng của bạn</h2>
            </div>
        </div>
    </section>

    <!-- Phần hiển thị đơn hàng -->
    <div class="orderManagement_order_history">
        <p id="emptyCartMessage" class="empty_cart" style="text-align: center;">Bạn chưa có đơn hàng nào!</p>
        <div id="orderHistory"></div>

    </div>
</body>


<?php require_once "./Footer.php" ?>
<script>
    function loadOrders() {
        var customerId = sessionStorage.getItem("id");

        // Hiển thị hiệu ứng loading với SweetAlert2
        Swal.fire({
            title: 'Đang tải đơn hàng...',
            text: 'Vui lòng chờ giây lát.',
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading(); // Hiện biểu tượng loading
            }
        });

        $.ajax({
            url: 'http://localhost:8080/Order/MyOrder', // Đường dẫn API lấy đơn hàng
            type: 'GET',
            headers: {
                'Authorization': 'Bearer ' + sessionStorage.getItem('token') // Thay 'yourTokenKey' bằng khóa lưu token của bạn
            },
            dataType: 'json', // Đảm bảo rằng response được xử lý là JSON
            success: function(response) {
                // Đóng hiệu ứng loading khi nhận được phản hồi
                Swal.close();

                // Kiểm tra kiểu dữ liệu của response
                if (response && Array.isArray(response)) {
                    const orders = response;
                    if (orders.length <= 0) {
                        $('#emptyCartMessage').show(); // Hiện thông báo nếu không có đơn hàng
                    } else {
                        $('#emptyCartMessage').hide(); // Ẩn thông báo nếu có đơn hàng
                        displayOrders(orders); // Gọi hàm để hiển thị danh sách đơn hàng
                    }
                } else {
                    console.error('Dữ liệu trả về không hợp lệ:', response);
                }
            },
            error: function(xhr, status, error) {
                // Đóng hiệu ứng loading và hiển thị thông báo lỗi
                Swal.close();
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Đã xảy ra lỗi khi tải đơn hàng. Vui lòng thử lại sau.',
                });
                console.error('Đã xảy ra lỗi khi tải đơn hàng:', error);
            }
        });
    }


    function displayOrders(orders) {
        const orderHistory = document.getElementById('orderHistory');
        orderHistory.innerHTML = ''; // Xóa dữ liệu cũ

        orders.forEach(hoaDon => {
            var orderHtml = `
                <div class='orderManagement_order_list'>
                    <table class='orderManagement_order_info'>
                        <thead>
                            <tr class='orderManagement_order_title'>
                                <th class='anhMinhHoa'>Ảnh minh họa</th>
                                <th class='tenSanPham'>Tên sản phẩm</th>
                                <th class='donGia'>Đơn giá</th>
                                <th class='soLuong'>Số lượng</th>
                                <th class='thanhTien'>Thành tiền</th>
                            </tr>
                        </thead>
                        <tbody>`;

            // Fetch order details for each order (make sure to use hoaDon.Id, not hoaDon.id)
            $.ajax({
                url: 'http://localhost:8080/Order/MyOrder/' + hoaDon.id, // Đường dẫn API lấy chi tiết đơn hàng
                type: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + sessionStorage.getItem('token') // Thay 'yourTokenKey' bằng khóa lưu token của bạn
                },
                async: false, // Synchronous requests are generally not recommended
                dataType: 'json', // Ensure the response is parsed as JSON
                success: function(chiTietDonHangResponse) {
                    const listCTDH = chiTietDonHangResponse.orderDetails;
                    listCTDH.forEach(chiTiet => {
                        orderHtml += `
                        <tr class='orderManagement_order_detail'>
                            <td class='anhMinhHoa'><img style='width: auto; height: 100px;' src='https://res.cloudinary.com/djhoea2bo/image/upload/v1711511636/${chiTiet.image}'></td>
                            <td class='tenSanPham'>${chiTiet.productName}</td>
                            <td class='donGia'>${formatCurrency(chiTiet.unitPrice)}</td>
                            <td class='soLuong'>${chiTiet.quantity}</td>
                            <td class='thanhTien'>${formatCurrency(chiTiet.total)}</td>
                        </tr>`;
                    });
                },
                error: function(xhr, status, error) {
                    console.error('Đã xảy ra lỗi khi tải chi tiết đơn hàng:', error);
                }
            });

            // Finish building the orderHtml string after the detail request
            orderHtml += `
                        </tbody>
                    </table>
                    <div class='orderManagement_order_thanhTien'>
                        <p style="width: 50%;">Trạng thái: ${fromEnumStatusToText(hoaDon.status)}</p>
                        <p>Tổng giá trị: ${formatCurrency(hoaDon.totalPrice)}</p>
                        <button class='order_detail_button' onclick="toOrderDetail('${hoaDon.id}')"> Chi tiết</button>`;

            if (hoaDon.status !== 'DangGiao' && hoaDon.status !== 'GiaoThanhCong' && hoaDon.status !== 'Huy' && hoaDon.status !== 'DaDuyet') {
                orderHtml += `<button class='cancel_order_button' onclick="cancelOrder('${hoaDon.id}')">Hủy đơn hàng</button>`;
            }

            orderHtml += `</div></div>`;

            orderHistory.innerHTML += orderHtml;
        });
    }

    // Hàm xử lý hủy đơn hàng
    function cancelOrder(maDonHang) {
        // Hiển thị hộp thoại xác thực
        Swal.fire({
            title: 'Xác nhận hủy đơn hàng?',
            text: "Bạn có chắc muốn hủy đơn hàng này?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Hủy đơn hàng'
        }).then((result) => {
            // Nếu người dùng xác nhận hủy đơn hàng
            if (result.isConfirmed) {
                $.ajax({
                    url: '../../Controllers/OrderStatusController.php', // Đường dẫn API lấy chi tiết đơn hàng
                    type: 'POST',
                    data: {
                        orderId: maDonHang,
                        status: "Huy",
                    },
                    success: function(response) {

                        // Hiển thị thông báo và reload trang
                        Swal.fire(
                            'Hủy đơn hàng thành công!',
                            '',
                            'success'
                        ).then((result) => {
                            console.log("Result: " + response);
                            if (result.isConfirmed) {
                                location.reload(); // Hoặc window.location.reload()
                            }
                        });
                    },
                    error: function(xhr, status, error) {
                        console.error('Đã xảy ra lỗi khi hủy đơn hàng.');
                    }
                });


            }
        });

    }

    function toOrderDetail(maDonHang) {
        window.location.href = `MyOrderInDetail.php?maDonHang=${maDonHang}`;
    }

    // Gọi hàm loadOrders khi trang được load
    $(document).ready(function() {
        loadOrders();
    });
</script>


</html>