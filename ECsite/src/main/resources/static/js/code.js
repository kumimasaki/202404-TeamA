document.addEventListener('DOMContentLoaded', function () {
    var userMenu = document.querySelector('.user-menu');

    userMenu.addEventListener('mouseenter', function () {
        var dropdown = this.querySelector('.dropdown');
        dropdown.style.display = 'block';
    });

    userMenu.addEventListener('mouseleave', function () {
        var dropdown = this.querySelector('.dropdown');
        dropdown.style.display = 'none';
    });
});
