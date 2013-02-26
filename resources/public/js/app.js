
$(document).bind('keydown', 'ctrl+o', function() {

    $.getJSON("/api/sheets", function(result) {
        reloadAvailableSheetsList(result);
        showDialogBox('#openSheetDialog',
            function() {
                loadSheet($("#selectedSheet").prop('value'));
            });
    });

    return false;
});

function reloadAvailableSheetsList(list) {
    $("#availableSheets").empty();

    $.each(list, function(k, v) {
        $("#availableSheets").append('<option>' + v + '</option>')
    });
}

function loadSheet(name) {
    $.getJSON("/api/sheet/" + name, function(result) {
        $("#title").text(name);
        $("#words").empty();

        $.each(result["words"], function(k, v) {
            $("#words").append('<p>' + k + " - " + v + '</p>')
        })
    });
}

function showDialogBox(selector, onSuccess) {
    $(selector).dialog({
            buttons: { 'OK': onSuccess }
        }) .keyup(function (e) {
            if (e.keyCode == $.ui.keyCode.ENTER) {
                $(this).parent().find('button').trigger('click');
            }
        });
}

$(document).bind('keydown', 'ctrl+n', function() {
    var w = prompt("Add word:")
    // do query, add word, display it
    return false
})