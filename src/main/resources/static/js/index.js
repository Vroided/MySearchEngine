var fa = document.querySelector("#fa");
var temp = document.querySelector("#keyword");
var display = document.querySelector('#display');
var display_list = document.querySelector('#display-list');
var search = document.querySelector('#search');

temp.addEventListener('focus', show);
temp.addEventListener('keyup', getDataAndShow);
temp.addEventListener('keydown', show);
temp.addEventListener('blur', hide);
search.addEventListener('click', function () {
    if (temp.value !== '') {
        window.location.href = "/q?name=" + temp.value;
    }
});


$("#keyword").keyup(function (e) {
    var key = window.event ? e.keyCode : e.which;
    if (key === 13) {
        $("#search").click();
    }
});

function show() {
    if (temp.value !== '') {
        display.style.display = 'block';
    } else {
        display.style.display = 'none';
    }
}

function getData() {
    $.ajax({
        "url": "/book/prefix-title-doc",
        "type": "post",
        "data": {
            "name": temp.value
        },
        "dataType": "json",
        "success": function (data) {

            $('#display-list').off("click","li");
            $('#display-list li').each(function (index) {
                if (index < data.length) {
                    $(this).text(data[index]);
                    $(this).css('display', 'block');
                } else {
                    $(this).text('');
                    $(this).css('display', 'none');
                }
            });
            $('#display-list').on("mousedown", "li", function () {
                console.log($(this).text());
                if ($(this).text !== '') {
                    window.location.href = "/q?name=" + $(this).text();
                }
            });
        },
        "error": function () {
        }
    });
}

function getDataAndShow() {
    getData();
    setPos();
    show();
}

function hide() {
    display.style.display = 'none';
}

window.onload = function () {
    setPos();
}

window.onresize = function () {
    setPos();
}

function setPos() {
    display.style.top = fa.style.height;
    display.style.width = temp.style.width;
    display_list.style.width = temp.style.minWidth;
}

