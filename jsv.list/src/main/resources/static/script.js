$(() => {
    const url = window.location.origin;

    $("#upload").submit((e) => {
        e.preventDefault();
        console.log("LELELE");
        $.ajax({
            url: `${url}/files`, type: "POST",
            data: {file: $(this).serialize()}
        });
    });

    function remove(id) {
        $.ajax({url: `${url}/files/${id}`, type: "DELETE"});
    }
});