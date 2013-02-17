
// bind function is called twice, repair

$(document).bind('keydown', 'ctrl+o', function() {

    $.getJSON("/api/sheets", function(data) {
        reloadAvailableSheets(data);
        showSingleInputDialogBox('#openSheetDialog',
            function() {
                // post to website in order to download data


                alert($("#selectedSheet").prop('value'));
            });
    });

    return false;
});

function reloadAvailableSheets(data) {
    $("#availableSheets").empty();

    $.each(data, function(k, v) {
        $("#availableSheets").append('<option>' + v + '</option>')
    });
}

function showSingleInputDialogBox(selector, onSuccess) {
    $(selector)
        .dialog({
            buttons: {
                'OK': onSuccess
            }

        })
        .keyup(function (e) {
            if (e.keyCode == $.ui.keyCode.ENTER) {
                $(this).parent().find('button').trigger('click');
            }
        });
}

// it should be possible to add word to non-existing sheet, the default one (nil) should be use then
$(document).bind('keydown', 'ctrl+n', function() {
    var w = prompt("Add word:")
    // do query, add word, display it
    return false
})