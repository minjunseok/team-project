if ($('.new-music').length > 0) {
    const options = {
        centeredSlides: false,
        loop: false,
        slidesPerView: 5,
        autoplay: {
            delay: 3000
        },
        spaceBetween: 15,
        breakpoints: {
            320: {
                slidesPerView: 1
            },
            550: {
                slidesPerView: 2
            },
            768: {
                slidesPerView: 3
            },
            1199: {
                slidesPerView: 4
            },
            1400: {
                slidesPerView: 5
            }
        },
        pagination: {
            el: '.swiper-pagination'
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
        },  

        // And if we need scrollbar
        scrollbar: {
            el: '.swiper-scrollbar'  
        }
    } 
    let swiper = new Swiper('.new-music',options);

    document.addEventListener('ChangeRtl', (e) => {
        swiper.destroy(true, true)
        setTimeout(() => {
            swiper = new Swiper('.new-music',options);
        }, 500);
    })   
}
if ($('.recent-music').length > 0) {
    const options = {
        centeredSlides: false,
        loop: false,
        slidesPerView: 5,
        autoplay: {
            delay: 3000
        },
        spaceBetween: 15,
        breakpoints: {
            320: {
                slidesPerView: 1
            },
            550: {
                slidesPerView: 2
            },
            768: {
                slidesPerView: 3
            },
            1199: {
                slidesPerView: 4
            },
            1400: {
                slidesPerView: 5
            }
        },
        pagination: {
            el: '.swiper-pagination'
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
        },  

        // And if we need scrollbar
        scrollbar: {
            el: '.swiper-scrollbar'  
        }
    } 
    let swiper = new Swiper('.recent-music',options);

    document.addEventListener('ChangeRtl', (e) => {
        swiper.destroy(true, true)
        setTimeout(() => {
            swiper = new Swiper('.recent-music',options);
        }, 500);
    })   
}
if ($('.top-music').length > 0) {
    const options = {
        centeredSlides: false,
        loop: false,
        slidesPerView: 5,
        autoplay: {
            delay: 3000
        },
        spaceBetween: 15,
        breakpoints: {
            320: {
                slidesPerView: 1
            },
            550: {
                slidesPerView: 2
            },
            768: {
                slidesPerView: 3
            },
            1199: {
                slidesPerView: 4
            },
            1400: {
                slidesPerView: 5
            }
        },
        pagination: {
            el: '.swiper-pagination'
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
        },  

        // And if we need scrollbar
        scrollbar: {
            el: '.swiper-scrollbar'  
        }
    } 
    let swiper = new Swiper('.top-music',options);

    document.addEventListener('ChangeRtl', (e) => {
        swiper.destroy(true, true)
        setTimeout(() => {
            swiper = new Swiper('.top-music',options);
        }, 500);
    })   
}
if ($('.sign-slider').length > 0) {
    const options = {
        centeredSlides: false,
        loop: false,
        slidesPerView: 1,
        autoplay: {
            delay: 3000
        },
        spaceBetween: 15,
        pagination: {
            el: '.swiper-pagination'
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev'
        },  

        // And if we need scrollbar
        scrollbar: {
            el: '.swiper-scrollbar'  
        }
    } 
    let swiper = new Swiper('.sign-slider',options);

    document.addEventListener('ChangeRtl', (e) => {
        swiper.destroy(true, true)
        setTimeout(() => {
            swiper = new Swiper('.sign-slider',options);
        }, 500);
    })   
}
