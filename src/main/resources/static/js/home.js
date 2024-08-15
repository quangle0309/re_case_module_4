new WOW().init();

flatpickr(".date-flatpickr", {
    dateFormat: 'd-m-Y',
    minDate: 'today',
    maxDate: new Date().fp_incr(14)
});


// Cuộn trang lên đầu khi nhấn nút
$('#scrollToTop').click(() => {
    window.scrollTo({
        top: 0,
        behavior: 'smooth'
    });
});

$('.failed').click(() => {
    Swal.fire({
        icon: "warning",
        iconColor: "#ffc107",
        title: "Tính năng đang phát triển!",
        confirmButtonText: "Đóng",
        color: "#003366",
    })
})

document.addEventListener('DOMContentLoaded', function () {
    new Swiper('.swiper-container', {
        slidesPerView: 4,
        spaceBetween: 10,
        loop: true,
        autoplay: {
            delay: 3000,
            disableOnInteraction: false,
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        breakpoints: {
            500: {
                slidesPerView: 1
            },
            768: {
                slidesPerView: 2,
                spaceBetween: 30,
            },
            1024: {
                slidesPerView: 4,
                spaceBetween: 40,
            },
        }
    });
});

$(document).ready( function () {
// xử lý select dropdown
    document.querySelectorAll('.departure-item').forEach(el => {
        el.addEventListener('click', event => {
            const airportCity = el.querySelector(".city-departure").textContent;
            const inputDisplay = document.getElementById('departure');
            const airportId = el.getAttribute('data-value');
            const inputId = document.getElementById('departureAirportId');
            inputDisplay.value = airportCity;
            inputId.value = airportId;
        });
    });

    document.querySelectorAll('.arrival-item').forEach(el => {
        el.addEventListener('click', event => {
            const airportCity = el.querySelector(".city-arrival").textContent;
            const inputDisplay = document.getElementById('arrival');
            const airportId = el.getAttribute('data-value');
            const inputId = document.getElementById('arrivalAirportId');
            inputDisplay.value = airportCity;
            inputId.value = airportId;
        });
    });
});

// ajax
$(document).ready(function() {
    $('.departure-item').on('click', function() {
        let departureAirportId = $(this).data('value');
        let departureText = $(this).find('.city-departure').text();

        $('#departure').val(departureText);
        $('#departureAirportId').val(departureAirportId);

        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            url: '/api/airports/arrival',
            type: 'GET',
            data: { departureAirportId: departureAirportId },
            success: function(data) {
                console.log(data)

                $('.arrival-dropdown-menu').empty();
                $('#arrival').val("");
                $('#arrivalAirportId').val("");


                $.each(data, function(index, airport) {
                    $('.arrival-dropdown-menu').append(
                        '<li class="dropdown-item arrival-item" data-value="' + airport.airportId + '">' +
                        '<span class="city-code">' + airport.airportCode + '</span> ' +
                        '<span class="city-arrival">' + airport.city + '</span>' +
                        '</li>'
                    );
                });


                $('.arrival-item').on('click', function() {
                    let arrivalText = $(this).find('.city-arrival').text();
                    $('#arrival').val(arrivalText);
                    $('#arrivalAirportId').val($(this).data('value'));
                });
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    });
});

$(document).ready(function () {
    const form = $('form[action="/flights/search"]');
    const departureInput = $('#departureAirportId');
    const arrivalInput = $('#arrivalAirportId');

    form.on('submit', function (event) {
        let isValid = true;
        let message = "";

        if (departureInput.val() === "") {
            message += "Vui lòng chọn điểm khởi hành!\n";
            isValid = false;
        } else if (arrivalInput.val() === "") {
            message += "Vui lòng chọn điểm đến!\n";
            isValid = false;
        }

        if (!isValid) {
            toastr.options = {
                "closeButton": false,
                "debug": true,
                "newestOnTop": false,
                "progressBar": true,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "5000",
                "extendedTimeOut": "1000",
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeToggle",
                "hideMethod": "fadeOut"
            }
            toastr["warning"](message)
            event.preventDefault();
        }
    });
});

