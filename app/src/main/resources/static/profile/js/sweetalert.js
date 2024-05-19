(function ($) {
    "use strict";

    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-primary btn-lg',
            cancelButton: 'btn btn-outline-primary btn-lg ml-2'
        },
        buttonsStyling: false,
        showClass: {
            popup: 'animate__animated animate__zoomIn'
        },
        hideClass: {
            popup: 'animate__animated animate__zoomOut'
        }
    })

    $(document).on('click', '[data-toggle="alert"]', function() {
        swalWithBootstrapButtons.fire({
            title: $(this).attr('data-message')
        })
    });

    $('.copy').on('click', function() {
        swalWithBootstrapButtons.fire({
            icon: 'success',
            title: 'Your form has been successfully copied!',
            showConfirmButton: false,
        })
    });

    $('.copys').on('click', function() {
        swalWithBootstrapButtons.fire({
            icon: 'success',
            title: 'Your Templates has been successfully copied!',
            showConfirmButton: false,
        })
    });
    $('#basic-title').on('click', function() {
        swalWithBootstrapButtons.fire(
            'The Internet?',
            'That thing is still around?',
            'question'
        )
    });
    $('.success').on('click', function() {
        swalWithBootstrapButtons.fire({
            icon: 'success',
            title: 'Intigrated successfully',
            showConfirmButton: false,
        })
    });
    $('#info').on('click', function() {
        swalWithBootstrapButtons.fire({
            icon: 'info',
            title: 'Good Job!',
            showConfirmButton: false,
        })
    });
    $('#warning').on('click', function() {
        swalWithBootstrapButtons.fire({
            icon: 'warning',
            title: 'Changes are not saved',
            showConfirmButton: false,

        })
    });
    $('#danger').on('click', function() {
        swalWithBootstrapButtons.fire({
            icon: 'error',
            title: 'OOps!!',
            text: 'Something went Wrong',
            showConfirmButton: false,
        })
    });
    $('#confirmation').on('click', function() {
        swalWithBootstrapButtons.fire({
                title: "Are you sure?",
                text: "Once deleted, you will not be able to recover this imaginary file!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            })
            .then((willDelete) => {
                if (willDelete) {
                    swalWithBootstrapButtons.fire("Poof! Your imaginary file has been deleted!", {
                        icon: "success",
                    });
                } else {
                    swalWithBootstrapButtons.fire("Your imaginary file is safe!");
                }
            });
    });
    $('#custom-buttons').on('click', function() {
        swalWithBootstrapButtons.fire("A wild Pikachu appeared! What do you want to do?", {
                buttons: {
                    cancel: "Run away!",
                    catch: {
                        text: "Throw Pokéball!",
                        value: "catch",
                    },
                    defeat: true,
                },
            })
            .then((value) => {
                switch (value) {

                    case "defeat":
                        swalWithBootstrapButtons.fire("Pikachu fainted! You gained 500 XP!");
                        break;

                    case "catch":
                        swalWithBootstrapButtons.fire("Gotcha!", "Pikachu was caught!", "success");
                        break;

                    default:
                        swalWithBootstrapButtons.fire("Got away safely!");
                }
            });
    });
    $('#ajax-request').on('click', function() {
        swalWithBootstrapButtons.fire({
                text: 'Search for a movie. e.g. "La La Land".',
                content: "input",
                button: {
                    text: "Search!",
                    closeModal: false,
                },
            })
            .then(name => {
                if (!name) throw null;

                return fetch(`https://itunes.apple.com/search?term=${name}&entity=movie`);
            })
            .then(results => {
                return results.json();
            })
            .then(json => {
                const movie = json.results[0];

                if (!movie) {
                    return swalWithBootstrapButtons.fire("No movie was found!");
                }

                const name = movie.trackName;
                const imageURL = movie.artworkUrl100;

                swalWithBootstrapButtons.fire({
                    title: "Top result:",
                    text: name,
                    icon: imageURL,
                });
            })
            .catch(err => {
                if (err) {
                    swalWithBootstrapButtons.fire("Oh noes!", "The AJAX request failed!", "error");
                } else {
                    swalWithBootstrapButtons.fire.stopLoading();
                    swalWithBootstrapButtons.fire.close();
                }
            });
    });
    $('#form-input').on('click', function() {
        swalWithBootstrapButtons.fire("Write something here:", {
                content: "input",
            })
            .then((value) => {
                swalWithBootstrapButtons.fire(`You typed: ${value}`);
            });
    });
}) (jQuery);