
Mousetrap.bind('ctrl+o', function() {

    $.getJSON('/api/sheets', function(result) {
        reloadAvailableSheetsList(result);
        showModal('#openSheetModal', function() {
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

Mousetrap.bind('ctrl+n', function() {
    showModal('#submitQueryModal', function() {
        var expr = 'expr=' + $('#expression').prop('value');
        $.post('/api/word/add', expr, function(result){
            appendWord(result[0], result[1]);
        });
    });

    return false
});

function appendWord(foreign, origin) {
    $('#words').append('<li>' + foreign + ' - ' + origin + '</li>')
}

function showModal(selector, onSuccess) {
    $(selector).on('shown', function () {
        $(selector + ' input')
            .val("")
            .focus()
            .keyup(function (e) {
                if (e.keyCode == 13) {
                    $(this).parent().find('button').trigger('click');
                }});

        $(selector + ' button').click(onSuccess);
    });

    $(selector).modal('show');
}