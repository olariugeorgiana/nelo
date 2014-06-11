(function ($) {

    var singleSelection = false;
    var checboxes = null;
    var selectionChangedListener = null;

    $.fn.checkbox = function (options) {

        if (options != undefined && options.singleSelection != undefined && options.singleSelection === true)
            singleSelection = true;
        if (options != undefined && options.selectionChanged != undefined)
            selectionChangedListener = options.selectionChanged;

        var contextPath = $("#contextPath")[0].innerHTML;
        if (this.selector) {

            checboxes = this;

            $.each(this, function (index, checkbox) {
                addClickListener($(checkbox));
            });

        } else {
            this.innerHTML = "daaa";
        }
    }

    function addClickListener(checkbox) {
        var parent = $(checkbox).parent().parent()

        parent.click(function () {
            if (parent.hasClass("selected")) {
                parent.removeClass("selected");
            }
            else if (!parent.hasClass("selected")) {
                parent.addClass("selected");

                if (singleSelection) {
                    $.each(checboxes, function (index, checkbox) {
                        var check = $(checkbox).parent().parent();

                        if (parent[0] != check[0])
                            check.removeClass("selected");
                    });
                }

                selectionChangedListener($(checkbox));

            }
        });
    }

})(jQuery)


