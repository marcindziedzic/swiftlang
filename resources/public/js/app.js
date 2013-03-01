
Mousetrap.bind('ctrl+o', function(e) {

    $.getJSON('/api/sheets', function(result) {
        reloadAvailableSheetsList(result);
        showDialogBox('#openSheetDialog', function() {
                loadSheet($('#selectedSheet').prop('value'));
            });
    });

    return false;
});

function reloadAvailableSheetsList(list) {
    $('#availableSheets').empty();

    $.each(list, function(k, v) {
        $('#availableSheets').append('<option>' + v + '</option>')
    });
}

function loadSheet(name) {
    $.getJSON('/api/sheet/' + name, function(result) {
        $('#title').text(name);
        $('#words').empty();

        $.each(result['words'], function(k, v) {
            appendWord(k, v);
        })

    });
}

Mousetrap.bind('ctrl+n', function(e) {
    showDialogBox('#submitQueryDialog', function() {
        var expr = 'expr=' + $('#expression').prop('value');
        $.post('/api/word/add', expr, function(result){
            appendWord(result[0], result[1]);
        });
    });

    return false
});

function appendWord(foreign, native) {
    $('#words').append('<p>' + foreign + ' - ' + native + '</p>')
}

function showDialogBox(selector, onSuccess) {
    $(selector).dialog({
        buttons: { 'GO': onSuccess }
    }) .keyup(function (e) {
            if (e.keyCode == $.ui.keyCode.ENTER) {
                $(this).parent().find('button').trigger('click');
            }
        });
}
