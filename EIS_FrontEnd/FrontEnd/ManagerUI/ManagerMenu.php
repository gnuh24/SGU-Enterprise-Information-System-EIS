<div class="Sidebar_sideBar__CC4MK">
    <a id="QLTaiKhoan" class="MenuItemSidebar_menuItem__56b1m" href="../QLTaiKhoan/QLTaiKhoan.php">
        <span class="MenuItemSidebar_title__LLBtx">Tài khoản</span> </a>

    <a id="QLLoaiSanPham" class=" MenuItemSidebar_menuItem__56b1m" href="../QLLoaiSanPham/QLLoaiSanPham.php">
        <span class="MenuItemSidebar_title__LLBtx">Loại Sản Phẩm</span>
    </a>
    <a id="QLSanPham" class="MenuItemSidebar_menuItem__56b1m" href="../QLSanPham/QLSanPham.php">
        <span class="MenuItemSidebar_title__LLBtx">Sản Phẩm</span>
    </a>
    <a id="QLThuongHieu" class="MenuItemSidebar_menuItem__56b1m" href="../QLNhaCungCap/QLNhaCungCap.php">
        <span class="MenuItemSidebar_title__LLBtx">Thương hiệu</span>
    </a>
    <a id="QLPhieuNhapKho" class="MenuItemSidebar_menuItem__56b1m" href="../QLPhieuNhapKho/QLPhieuNhapKho.php">
        <span class="MenuItemSidebar_title__LLBtx">Phiếu Nhập Kho</span>
    </a>
    <a id="QLDonHang" class="MenuItemSidebar_menuItem__56b1m" href="../QLDonHang/QLDonHang.php">
        <span class="MenuItemSidebar_title__LLBtx">Đơn Hàng</span>
    </a>
    <a id="ThongKeBanChay" class="MenuItemSidebar_menuItem__56b1m" href="../ThongKe/ThongKeDoanhThuChiTieu.php">
        <span class="MenuItemSidebar_title__LLBtx">Thống Kê bán chạy</span>
    </a>
    </a>
    <a id="ThongKeDonHang" class="MenuItemSidebar_menuItem__56b1m" href="../ThongKe/ThongKeDonHang.php">
        <span class="MenuItemSidebar_title__LLBtx">Thống Kê Đơn Hàng</span>
    </a>
</div>

<script>
    const userRole1 = sessionStorage.getItem('role');

    document.addEventListener('DOMContentLoaded', () => {
        const QLTaiKhoan = document.getElementById('QLTaiKhoan');
        const QLLoaiSanPham = document.getElementById('QLLoaiSanPham');
        const QLSanPham = document.getElementById('QLSanPham');
        const QLThuongHieu = document.getElementById('QLThuongHieu');
        const QLDonHang = document.getElementById('QLDonHang');
        const QLPNK = document.getElementById('QLPhieuNhapKho');
        const ThongKeBanChay = document.getElementById('ThongKeBanChay');
        const ThongKeDonHang = document.getElementById('ThongKeDonHang');

        if (userRole1 == 'Employee') {
            QLTaiKhoan.style.display = 'none';
            ThongKeBanChay.style.display = 'none';
            ThongKeDonHang.style.display = 'none';
            QLPNK.style.display = 'none';
        } else if (userRole1 == 'Admin') {
            QLLoaiSanPham.style.display = 'none';
            QLSanPham.style.display = 'none';
            QLThuongHieu.style.display = 'none';
            QLDonHang.style.display = 'none';
            QLPNK.style.display = 'none';
            ThongKeBanChay.style.display = 'none';
            ThongKeDonHang.style.display = 'none';

        } else {
            QLTaiKhoan.style.display = 'none';

        }
    });
    const currentPath = window.location.pathname.replace("/ECommerceProject/Views/ManagerUI/", "");
    const menuItems = document.querySelectorAll('.MenuItemSidebar_menuItem__56b1m');

    menuItems.forEach(item => {
        // Lấy giá trị href của thẻ <a> và loại bỏ ../
        const hrefPath = item.getAttribute('href').replace("../", "");

        if (hrefPath === currentPath) {
            item.classList.add('active');
        }
    });
</script>