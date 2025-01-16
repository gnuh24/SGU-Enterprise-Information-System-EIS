<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
    <link rel="stylesheet" href="LoginUI.css" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <title>Login</title>
</head>

<body>
    <div class="container" id="container">
        <div class="form-container sign-up">
            <form>
                <h1>Create Account</h1>
                <input type="email" placeholder="Email" id="email" name="Email" />
                <input type="password" placeholder="Mật khẩu" id="matKhau" name="MatKhau" />
                <input type="password" placeholder="Xác thực mật khẩu" id="xacNhanMatKhau" name="XacThucMatKhau" />
                <button type="button" class="btn btn-danger" id="signUpButton">Đăng kí</button>
            </form>
        </div>

        <div class="form-container sign-in">
            <form>
                <h1>Sign In</h1>
                <input type="email" placeholder="Tên đăng nhập" id="tenDangNhapLogin" />
                <input type="password" placeholder="Password" id="passwordLogin" />
                <button type="button" class="btn btn-danger" id="signInButton">Đăng nhập</button>

            </form>
        </div>

        <div class="toggle-container">
            <div class="toggle">
                <div class="toggle-panel toggle-left">
                    <h1>Bạn đã có tài khoản ?</h1>
                    <p>Hãy đăng nhập tại đây để bắt đầu trải nghiệm.</p>
                    <button type="button" class="btn btn-light" id="login">Đăng nhập</button>
                </div>
                <div class="toggle-panel toggle-right">
                    <h1>Xin chào, bạn là người mới ?</h1>
                    <p>Hãy đăng ký tài khoản tại đây để có thể trải nghiệm các tính năng mới.</p>
                    <button type="button" class="btn btn-light" id="register">Đăng kí</button>
                </div>
            </div>
        </div>
    </div>


</body>




<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>



<script>
    //Script xử lý Registation
    const container = document.getElementById("container");
    const registerBtn = document.getElementById("register");
    const loginBtn = document.getElementById("login");

    registerBtn.addEventListener("click", () => {
        container.classList.add("active");
    });

    loginBtn.addEventListener("click", () => {
        container.classList.remove("active");
    });



    const signUpButton = document.getElementById("signUpButton");

    signUpButton.addEventListener('click', async function check(event) {
        event.preventDefault(); // Ngăn chặn hành động mặc định của form

        let matKhau = document.getElementById("matKhau");
        let xacNhanMatKhau = document.getElementById("xacNhanMatKhau");
        let email = document.getElementById("email");


        if (!matKhau.value.trim()) {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Mật khẩu không được để trống',
                icon: 'error',
                confirmButtonText: 'OK'
            });
            matKhau.focus();
            event.preventDefault();
            return;
        }

        if (!xacNhanMatKhau.value.trim()) {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Mật khẩu xác nhận không được để trống',
                icon: 'error',
                confirmButtonText: 'OK'
            });
            xacNhanMatKhau.focus();
            event.preventDefault();
            return;
        }

        if (matKhau.value !== xacNhanMatKhau.value) {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Mật khẩu xác nhận và mật khẩu phải giống nhau',
                icon: 'error',
                confirmButtonText: 'OK'
            });
            xacNhanMatKhau.focus();
            event.preventDefault();
            return;
        }

        if (!email.value.trim()) {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Email không được để trống',
                icon: 'error',
                confirmButtonText: 'OK'
            });
            email.focus();
            event.preventDefault();
            return;
        }

        // Kiểm tra định dạng Email
        if (!isValidEmail(email.value.trim())) {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Email không hợp lệ',
                icon: 'error',
                confirmButtonText: 'OK'
            });
            email.focus();
            event.preventDefault();
            return;
        }


        try {
            const emailExists = await checkEmail(email.value); // đợi kết quả

            if (emailExists === true) {
                Swal.fire({
                    title: 'Lỗi!',
                    text: 'Email tồn tại',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
                email.focus();
                return;
            }
        } catch (error) {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Đã xảy ra lỗi khi kiểm tra tài khoản!',
                icon: 'error',
                confirmButtonText: 'OK'
            });
        }


        $.ajax({
            url: 'http://localhost:8080/Auth/Registration',
            type: 'POST',
            dataType: 'json',
            headers: {
                "Content-Type": "application/x-www-form-urlencoded", // Gửi dữ liệu dưới dạng form
            },
            data: {
                email: email.value,
                password: matKhau.value,
            },
            success: function(response) {
                // Kiểm tra xem phản hồi có thành công hay không
                Swal.fire({
                    title: response.message,
                    text: "Đăng ký thành công !",
                    icon: 'success',
                    confirmButtonText: 'OK'
                });
            },
            error: function(xhr, status, error) {
                Swal.fire({
                    title: 'Lỗi!',
                    text: 'Đã xảy ra lỗi khi đăng kí tài khoản!',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            }
        });
    });

    function isValidEmail(email) {
        // Thực hiện kiểm tra định dạng Email và trả về true hoặc false
        // Bạn có thể sử dụng biểu thức chính quy hoặc các phương thức khác để kiểm tra định dạng Email
        return /[^\s@]+@[^\s@]+\.[^\s@]+/.test(email);
    }

    async function checkEmail(email) {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: 'http://localhost:8080/Account/isThisEmailExists',
                type: 'GET',
                dataType: 'json',
                data: {
                    email: email,
                },
                success: function(response) {
                    resolve(response); // Resolve the promise with the response

                },
                error: function(error) {
                    console.log("Thất bại : " + error)

                    reject(error); // Reject the promise if there's an error
                }
            });
        });
    }
</script>


<script>
    const loginButton = document.getElementById("signInButton");
    const tenDangNhap = document.getElementById("tenDangNhapLogin");
    const matKhau = document.getElementById("passwordLogin");



    loginButton.addEventListener("click", (event) => {
        event.preventDefault();
        if (tenDangNhap.value.trim() === "") {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Bạn không được để trống tên đăng nhập !!',
                icon: 'error',
                confirmButtonText: 'OK'
            });
            tenDangNhap.focus();
            return
        }
        if (matKhau.value.trim() === "") {
            Swal.fire({
                title: 'Lỗi!',
                text: 'Bạn không được để trống mật khẩu !!',
                icon: 'error',
                confirmButtonText: 'OK'
            });
            matKhau.focus();
            return
        }

        login(tenDangNhap.value, matKhau.value)
    });



    // Hàm xử lý kiểm tra tài khoản
    function login(email, password) {

        $.ajax({
            url: 'http://localhost:8080/Auth/SignIn',
            type: 'POST',
            dataType: 'json',
            headers: {
                "Content-Type": "application/x-www-form-urlencoded", // Gửi dữ liệu dưới dạng form
            },
            data: {
                email: email,
                password: password,
            },
            success: function(response) {
                // Kiểm tra xem phản hồi có thành công hay không

                if (response.statusCode === 200) {
                    Swal.fire({
                        title: 'Thành công!',
                        text: response.message,
                        icon: 'success',
                        confirmButtonText: 'OK'
                    }).then((result) => {
                        if (result) {
                            const quyen = response.role;

                            sessionStorage.setItem('id', response.id);
                            sessionStorage.setItem('token', response.token);

                            window.location.href = `../HomePage.php`;
                        }
                    });
                } else {
                    console.error(response);
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
                Swal.fire({
                    title: 'Lỗi!',
                    text: xhr.responseJSON.detailMessage,
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            }
        });
    }
</script>


</html>