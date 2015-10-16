var _data;

$("body").click(function() {
    $("#headers").parent().parent().removeClass("has-error");
});

$("#go")
    .click(
        function() {
            $(this).addClass('disabled');
            /**
             * Check Query input Format
             */
            var re = /^(([ a-zA-Z\-_]*;|[ a-zA-Z\-_]*\[(F|L),[0-9]{1,2}\];){0,4})([ a-zA-Z\-_]*|[ a-zA-Z\-_]*\[(F|L),[0-9]{1,2}\]){1}$/;
            var m;
            if ((m = re.exec($("#headers").val()) == null)) {
                $("#headers").parent().parent().addClass("has-error");
                return;
            }
            /** End checking */
            /**
             * adding waiting icon layer
             */
            var el = $(".tablePanel");
            $(".tablePanel").block({
                overlayCSS: {
                    backgroundColor: '#fff'
                },
                message: '<i class="fa fa-spinner fa-spin"></i>',
                css: {
                    border: 'none',
                    color: '#333',
                    background: 'none'
                }
            });
            /**
             * hide the unnecessery panels
             */
            $("#error-div").addClass("hide");
            $(".alert-info").removeClass("hide");
            /**
             * putting the TableResult in bodys page
             */
            $(".panel-result")
                .html(
                    '<table class="table  table-striped table-bordered table-hover table-full-width" id="sample_1" ><thead></thead><tbody></tbody></table>');
            $("#sample_1").addClass("hide");
            $("#sample_1 > thead ").html("");
            $("#sample_1 > tbody ").html("");
            var _account = $("input[name=email]").val();
            var _passwd = $("input[name=password]").val();
            var _headers = HeaderValidate(($("#headers").val().length == 0) ? $(
                "#defaultRequest").text() : $("#headers").val());

            /**
             * Send the Request
             */
            $
                .ajax({
                    method: "get",
                    url: "/Details",
                    data: {
                        account: _account,
                        passwd: _passwd,
                        Myheaders: _headers
                    },
                    error: function(data) {
                        $(".tablePanel").unblock();
                        $(this).removeClass('disabled');
                        $(".alert-info").addClass("hide");
                        /**
                         * the system is inaccessible
                         */
                        $("body")
                            .block({
                                overlayCSS: {
                                    backgroundColor: '#fff'
                                },
                                message: '<div class="alert alert-block alert-danger fade in" ><h4 class="alert-heading"><i class="fa fa-frown-o fa-3x"></i> Error</h4><p>The System is temporary inaccessible, Contact your IT team</p><p></p></div>',
                                css: {
                                    border: 'none',
                                    color: '#333',
                                    background: 'none'
                                }
                            });
                    },
                    success: function(result) {
                        $(".alert-info").addClass("hide");
                        _data = result;
                        if (result == "error") {
                            $("#error-div").removeClass("hide");
                        } else if (result == "connexion problems") {
                            $("#error-div").removeClass("hide");
                        } else {
                            $("#mailLabel").text(
                                $("input[name=email]").val());
                            fillTable(result);
                        }
                        $(".tablePanel").unblock();
                        $(this).removeClass('disabled');
                    }
                })

            $(this).removeClass('disabled');
        });

function HeaderValidate(headers) {
    var res = "";
    var NbrHeaders = 0;
    headers.split(";").forEach(function(entry) {
        if (5 == NbrHeaders)
            return res;
        res += entry + ";";
        NbrHeaders++;
    });
    return res;
}

function fillTable(data) {
    /***************************************************************************
     * Add new Table
     */
    $(".panel-result")
        .html(
            '<table class="table  table-striped table-bordered table-hover table-full-width" id="sample_1" ><thead></thead><tbody></tbody></table>');
    /**
     * Set the table headers
     */

    $("#sample_1 > thead ").append("<tr><th>Message</th>");
    $.each(JSON.parse(data).tableHeaders, function(index, value) {
        $("#sample_1 > thead > tr ").append(
            "<th  class='hidden-xs' >" + value + "</th>")
    });
    $("#sample_1 > thead ").append("</tr>");

    /**
     * Add the Data
     **/
    _data = JSON.parse(data).tableData;
    $.each(_data, function(index, line) {
        $("#sample_1 > tbody").append("<tr ></tr>");
        $("#sample_1 > tbody > tr:last-child ").append(
            "<td class='hidden-xs' >" + line.message + "</td>")
        $.each(line.items, function(index, _header) {
            $("#sample_1 > tbody > tr:last-child ").append(
                "<td class='hidden-xs' >" + _header + "</td>")
        });
    });
    TableData.init();
}

$("#closewindow").click(function() {
    console.log("jjjjjj");
    window.close();
});