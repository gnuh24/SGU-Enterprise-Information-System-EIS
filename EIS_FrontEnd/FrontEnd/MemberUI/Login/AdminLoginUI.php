<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="LoginUI.css">
    <title>Admin Login</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<body>
    <div class="container" id="container">
        <div class="form-container sign-in" style="width: 100%">
            <form id="adminLoginForm">
                <h1>Login Admin</h1>
                <input type="email" placeholder="Email" style="width: 50%" id="adminEmail" name="email" required />
                <input type="password" placeholder="Password" style="width: 50%" id="adminPassword" name="password" required />
                <button type="button" class="btn btn-danger" id="adminLoginButton">Đăng nhập</button>
            </form>
        </div>
    </div>
</body>

<script>
    $(document).ready(function() {
        // Bắt sự kiện click cho nút đăng nhập
        $('#adminLoginButton').click(function(e) {
            e.preventDefault(); // Ngăn chặn hành động mặc định của form

            var email = $('#adminEmail').val().trim(); // Lấy giá trị email và xóa khoảng trắng
            var password = $('#adminPassword').val().trim(); // Lấy giá trị mật khẩu và xóa khoảng trắng

            // Kiểm tra xem email và mật khẩu có được nhập hay không
            if (!email) {
                Swal.fire({
                    title: 'Lỗi!',
                    text: 'Email không được để trống!',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
                $('#adminEmail').focus();
                return;
            }

            if (!password) {
                Swal.fire({
                    title: 'Lỗi!',
                    text: 'Mật khẩu không được để trống!',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
                $('#adminPassword').focus();
                return;
            }

            // Gọi AJAX để xử lý yêu cầu đăng nhập
            $.ajax({
                url: 'http://localhost:8080/Auth/LoginAdmin',
                type: 'POST',
                dataType: 'json', // Định dạng dữ liệu phản hồi là JSON
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded", // Gửi dữ liệu dưới dạng form
                },
                data: {
                    email: email,
                    password: password,
                },
                success: function(response) {
                    // Kiểm tra phản hồi từ server
                    if (response.statusCode === 200) { // Nếu đăng nhập thành công
                        Swal.fire({
                            title: 'Thành công!',
                            text: response.message,
                            icon: 'success',
                            confirmButtonText: 'OK'
                        }).then((result) => {
                            if (result) {

                                // Lưu các thông tin vào sessionStorage
                                sessionStorage.setItem('id', response.id);
                                sessionStorage.setItem('role', response.role);
                                sessionStorage.setItem('token', response.token);

                                if (response.role === "Admin") {
                                    window.location.href = `../../ManagerUI/QLTaiKhoan/QLTaiKhoan.php`;
                                } else if (response.role === "Manager") {
                                    window.location.href = `../../ManagerUI/QLSanPham/QLSanPham.php`;
                                } else if (response.role === "Employee") {
                                    window.location.href = `../../ManagerUI/QLSanPham/QLSanPham.php`;
                                }
                            }
                        });
                    } else {
                        // Trường hợp đăng nhập thất bại
                        Swal.fire({
                            title: 'Lỗi!',
                            text: response.message,
                            icon: 'error',
                            confirmButtonText: 'OK'
                        });
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Lỗi chi tiết:', xhr.responseText); // In ra nội dung phản hồi
                    Swal.fire({
                        title: 'Lỗi!',
                        text: JSON.parse(xhr.responseText).detailMessage,
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                }
            });
        });
    });
</script>

</html>