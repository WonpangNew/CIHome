(function () {
    'use strict';

    var canvas = document.getElementById('particle-canvas');
    if (!canvas || !canvas.getContext) {
        return;
    }

    var ctx = canvas.getContext('2d');
    var particles = [];
    var pointer = { x: null, y: null };
    var viewportWidth = window.innerWidth;
    var viewportHeight = Math.max(window.innerHeight, document.documentElement.clientHeight);

    function particleConfig() {
        var base = Math.round(window.innerWidth / 14);
        return {
            count: Math.max(60, Math.min(140, base)),
            maxDistance: window.innerWidth > 1200 ? 160 : 120,
            speed: 0.35
        };
    }

    var config = particleConfig();

    function resizeCanvas() {
        var ratio = window.devicePixelRatio || 1;
        viewportWidth = window.innerWidth;
        viewportHeight = Math.max(window.innerHeight, document.documentElement.clientHeight);
        canvas.width = viewportWidth * ratio;
        canvas.height = viewportHeight * ratio;
        canvas.style.width = '100%';
        canvas.style.height = '100%';
        ctx.setTransform(ratio, 0, 0, ratio, 0, 0);
        config = particleConfig();
        if (particles.length === 0) {
            initParticles();
        }
    }

    function randomVelocity() {
        var direction = Math.random() > 0.5 ? 1 : -1;
        return (Math.random() * config.speed) * direction;
    }

    function createParticle() {
        return {
            x: Math.random() * viewportWidth,
            y: Math.random() * viewportHeight,
            vx: randomVelocity(),
            vy: randomVelocity(),
            radius: Math.random() * 2.2 + 0.6,
            opacity: Math.random() * 0.6 + 0.2
        };
    }

    function initParticles() {
        particles = [];
        for (var i = 0; i < config.count; i += 1) {
            particles.push(createParticle());
        }
    }

    function updateParticles() {
        for (var i = 0; i < particles.length; i += 1) {
            var p = particles[i];
            p.x += p.vx;
            p.y += p.vy;

            if (p.x < 0 || p.x > viewportWidth) {
                p.vx *= -1;
            }
            if (p.y < 0 || p.y > viewportHeight) {
                p.vy *= -1;
            }
        }
    }

    function drawParticles() {
        ctx.clearRect(0, 0, viewportWidth, viewportHeight);
        ctx.fillStyle = '#f2f5ff';
        for (var i = 0; i < particles.length; i += 1) {
            var p = particles[i];
            ctx.globalAlpha = p.opacity;
            ctx.beginPath();
            ctx.arc(p.x, p.y, p.radius, 0, Math.PI * 2);
            ctx.fill();
        }
    }

    function drawConnections() {
        ctx.lineWidth = 0.6;
        for (var i = 0; i < particles.length; i += 1) {
            for (var j = i + 1; j < particles.length; j += 1) {
                var p1 = particles[i];
                var p2 = particles[j];
                var dx = p1.x - p2.x;
                var dy = p1.y - p2.y;
                var distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < config.maxDistance) {
                    ctx.globalAlpha = (1 - distance / config.maxDistance) * 0.4;
                    ctx.strokeStyle = 'rgba(114, 201, 255, 0.8)';
                    ctx.beginPath();
                    ctx.moveTo(p1.x, p1.y);
                    ctx.lineTo(p2.x, p2.y);
                    ctx.stroke();
                }
            }

            if (pointer.x !== null) {
                var dpX = particles[i].x - pointer.x;
                var dpY = particles[i].y - pointer.y;
                var pointerDistance = Math.sqrt(dpX * dpX + dpY * dpY);
                if (pointerDistance < config.maxDistance * 0.9) {
                    ctx.globalAlpha = (1 - pointerDistance / (config.maxDistance * 0.9)) * 0.5;
                    ctx.strokeStyle = 'rgba(89, 255, 229, 0.9)';
                    ctx.beginPath();
                    ctx.moveTo(particles[i].x, particles[i].y);
                    ctx.lineTo(pointer.x, pointer.y);
                    ctx.stroke();
                }
            }
        }
        ctx.globalAlpha = 1;
    }

    function tick() {
        updateParticles();
        drawParticles();
        drawConnections();
        window.requestAnimationFrame(tick);
    }

    window.addEventListener('resize', function () {
        resizeCanvas();
        initParticles();
    });

    window.addEventListener('mousemove', function (event) {
        pointer.x = event.clientX;
        pointer.y = event.clientY;
    });

    window.addEventListener('mouseleave', function () {
        pointer.x = null;
        pointer.y = null;
    });

    resizeCanvas();
    initParticles();
    tick();
})();
