// landingComponent.js

document.addEventListener("DOMContentLoaded", function () {
    // Initialize the carousel with a 3-second interval
    const carouselElement = document.querySelector('#landingCarousel');
    const carousel = new bootstrap.Carousel(carouselElement, {
        interval: 3000,  // 3 seconds between slides
        ride: 'carousel'
    });
});
