<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="Profile.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <title>Thông tin cá nhân</title>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="../HelperUI/formatOutput.js"></script>

</head>

<body>
    <?php require_once "./Header.php" ?>
    <div class="col-12">
        <div class="my-2 d-flex justify-content-center">
            <h3 style="z-index: 1; font-size: 32px; color: #7b181a; position: relative; background-color: white; padding: 0 20px; margin: 30px 0; font-family: Roboto; font-weight: bold !important;">My Profile</h3>
            <hr>
        </div>
        <div class="row mb-5 gx-5 d-flex justify-content-center" id="contentprofile" style="height: fit-content; margin: 0px;"></div>
    </div>
    <?php require_once "./Footer.php" ?>
</body>

<script>
    var email = '';


    function formatDateForInput(dateString) {
        // Tách ngày, tháng, năm từ chuỗi dd/MM/yyyy
        const [day, month, year] = dateString.split('/');
        // Trả về chuỗi theo định dạng yyyy-MM-dd
        return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`;
    }

    function loadUserInfoFromsessionStorage() {
        // Lấy dữ liệu từ sessionStorage
        var userData = sessionStorage.getItem("id");

        // Kiểm tra xem userData có tồn tại không
        if (!userData) {
            console.error("No user data found in sessionStorage");
            return;
        }

        $.ajax({
            url: 'http://localhost:8080/Account/' + userData,
            method: "GET",
            dataType: "json",
            headers: {
                'Authorization': 'Bearer ' + sessionStorage.getItem('token') // Thay 'yourTokenKey' bằng khóa lưu token của bạn
            },
            success: function(response) {
                // Kiểm tra nếu phản hồi chứa dữ liệu
                var userInfo = response; // Lấy thông tin người dùng đầu tiên từ mảng dữ liệu
                // Format thông tin hiển thị
                var infoPage = document.getElementById("contentprofile");
                infoPage.innerHTML = `
                    <div class='col-xxl-8 mb-5 mb-xxl-0'>
                        <form name="profileForm" action="Profile.php?maTaiKhoan=${userInfo.accountId}" method="POST" onsubmit="return validateForm()">
                            <div class='bg-secondary-soft px-4 py-5 rounded'>
                                <div class='row g-3' style='text-align:left;'>
                                    <div class='col-md-6'>
                                        <label class='form-label'>Họ tên *</label>
                                        <input type='text' class='form-control' name='hoten' value='${userInfo.fullname ? userInfo.fullname : ''}'>
                                    </div>
                                    <div class='col-md-6'>
                                        <label class='form-label'>Số điện thoại *</label>
                                        <input type='text' class='form-control' name='sodienthoai' value='${userInfo.phoneNumber ? userInfo.phoneNumber : ''}'>
                                    </div>
                                    <div class='col-md-6'>
                                        <label for='gioitinh'>Giới tính</label>
                                        <div class='form-check form-check-inline'>
                                            <input class='form-check-input' type='radio' name='gioitinh' id='inlineRadio1' value='Male' ${userInfo.gender === 'Male' ? 'checked' : ''}>
                                            <label class='form-check-label' for='inlineRadio1'>Nam</label>
                                        </div>
                                        <div class='form-check form-check-inline'>
                                            <input class='form-check-input' type='radio' name='gioitinh' id='inlineRadio2' value='Female' ${userInfo.gender === 'Female' ? 'checked' : ''}>
                                            <label class='form-check-label' for='inlineRadio2'>Nữ</label>
                                        </div>
                                    </div>
                                    <div class='col-md-6'>
                                        <label for='birthday'>Ngày sinh</label>
<input type='date' class='form-control' id='birthday' name='ngaysinh' 
       onchange='validateAge()' 
       value='${formatDateForInput(userInfo.birthday)}'>
                                           </div>
                                    <div class='col-md-6'>
                                        <label for='inputEmail4' class='form-label'>Email *</label>
                                        <input type='email' class='form-control' id='inputEmail4' name='email' value='${userInfo.email}' readonly >
                                    </div>
                                    <div class='col-md-6'>
                                        <label class='form-label'>Địa chỉ *</label>
                                        <input type='text' class='form-control' name='diachi' value='${userInfo.address ? userInfo.address : ''}'>
                                    </div>
                                    <button class='btn btn-primary' type='submit' style='background-color: rgb(146, 26, 26);'>Thay đổi thông tin</button>
                                </div>
                            </div>
                        </form>
                    </div>
                `;

            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    }

    function validateAge() {
        const birthdayInput = document.getElementById('birthday').value;

        if (!birthdayInput) {
            return; // Không làm gì nếu trường ngày sinh trống
        }

        const birthday = new Date(birthdayInput);
        const today = new Date();

        // Tính tuổi
        let age = today.getFullYear() - birthday.getFullYear();
        const monthDiff = today.getMonth() - birthday.getMonth();
        const dayDiff = today.getDate() - birthday.getDate();

        // Điều chỉnh nếu tháng và ngày chưa qua
        if (monthDiff < 0 || (monthDiff === 0 && dayDiff < 0)) {
            age--;
        }

        // Kiểm tra nếu tuổi dưới 18
        if (age < 18) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Bạn phải trên 18 tuổi để tiếp tục!'
            });

            // Xóa giá trị trong trường ngày sinh
            document.getElementById('birthday').value = '';
        }
    }



    window.onload = loadUserInfoFromsessionStorage;

    function validateForm() {
        // Lấy các giá trị từ form
        var form = document.forms["profileForm"];
        var fullname = form['hoten'].value.trim();
        var phone = form['sodienthoai'].value.trim();
        var birthday = form['ngaysinh'].value.trim();
        var gender = form['gioitinh'].value;
        var address = form['diachi'].value.trim();

        // Kiểm tra các trường bắt buộc
        if (!fullname || !phone || !birthday || !address) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Vui lòng điền đầy đủ thông tin!'
            });
            return false; // Ngăn chặn form gửi theo cách truyền thống
        }

        // Lấy accountId từ URL
        var accountId = new URLSearchParams(window.location.search).get('maTaiKhoan');
        if (!accountId) {
            Swal.fire({
                icon: 'error',
                title: 'Lỗi',
                text: 'Không tìm thấy mã tài khoản!'
            });
            return false;
        }
        $.ajax({
            url: "http://localhost:8080/Account/UpdateInformation",
            method: "PATCH",
            data: {
                accountId: accountId,
                fullname: fullname,
                phone: phone,
                birthday: birthday,
                gender: gender,
                address: address
            },
            headers: {
                'Authorization': 'Bearer ' + sessionStorage.getItem('token'),
                "Content-Type": "application/x-www-form-urlencoded",

            },
            contentType: "application/json",
            success: function(response) {
                Swal.fire({
                    icon: 'success',
                    title: 'Thành công',
                    text: 'Cập nhật thông tin thành công!'
                }).then(() => {
                    location.reload(); // Reload lại trang sau khi cập nhật thành công
                });

            },
            error: function(error) {
                console.error('Error:', error);
                Swal.fire({
                    icon: 'error',
                    title: 'Lỗi',
                    text: 'Có lỗi xảy ra khi gửi yêu cầu!'
                });
            }
        });

        return false; // Ngăn chặn form gửi theo cách truyền thống
    }
</script>

</html>