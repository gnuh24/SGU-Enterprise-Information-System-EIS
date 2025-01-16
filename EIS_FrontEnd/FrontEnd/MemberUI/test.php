<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Three.js 3D Bar Chart</title>
    <style>
        body {
            margin: 0;
            overflow: hidden;
        }

        canvas {
            display: block;
        }
    </style>
</head>

<body>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/three.js/r128/three.min.js"></script>
    <script src="https://threejs.org/examples/js/controls/OrbitControls.js"></script>

    <script>
        // Setup cơ bản
        const scene = new THREE.Scene();
        const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
        const renderer = new THREE.WebGLRenderer();
        renderer.setSize(window.innerWidth, window.innerHeight);
        document.body.appendChild(renderer.domElement);

        // Controls cho phép xoay camera
        const controls = new THREE.OrbitControls(camera, renderer.domElement);

        // Tạo ánh sáng
        const light = new THREE.PointLight(0xffffff, 1, 100);
        light.position.set(10, 10, 10);
        scene.add(light);

        // Thêm hệ trục tọa độ
        const axesHelper = new THREE.AxesHelper(10);
        scene.add(axesHelper);

        // Dữ liệu demo cho biểu đồ
        const data = [{
                x: 1,
                y: 2,
                z: 5
            },
            {
                x: 2,
                y: 3,
                z: 8
            },
            {
                x: 3,
                y: 1,
                z: 4
            },
            {
                x: 4,
                y: 4,
                z: 7
            },
            {
                x: 5,
                y: 2,
                z: 6
            },
        ];

        // Tạo các thanh cột (bar)
        const barWidth = 0.5;
        const barDepth = 0.5;

        data.forEach(item => {
            const barHeight = item.z;
            const geometry = new THREE.BoxGeometry(barWidth, barHeight, barDepth);
            const material = new THREE.MeshStandardMaterial({
                color: 0x44aa88
            });
            const bar = new THREE.Mesh(geometry, material);

            // Đặt vị trí thanh cột
            bar.position.set(item.x, barHeight / 2, item.y);
            scene.add(bar);
        });

        // Đặt vị trí camera
        camera.position.set(5, 5, 10);
        camera.lookAt(0, 0, 0);

        // Hàm render
        function animate() {
            requestAnimationFrame(animate);
            controls.update();
            renderer.render(scene, camera);
        }

        animate();
    </script>
</body>

</html>