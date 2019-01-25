$(function () {
    var rules_basic = {
        condition: 'AND',
        rules: [{
                id: 'feature',
                operator: 'equal',
                value: 1
            }, {
                condition: 'OR',
                rules: [{
                        id: 'feature',
                        operator: 'equal',
                        value: 1
                    }, {
                        id: 'feature',
                        operator: 'equal',
                        value: 1
                    }]
            }]
    };
    /*$('#builder-basic').queryBuilder({
     
     filters: [{
     id: 'feature',
     label: 'Feature',
     type: 'integer',
     input: 'select',
     values: {
     1: 'AC',
     2: 'RAIN SENSOR',
     3: 'SPEED SENSITIVE',
     4: 'LED',
     5: 'Auto Light',
     6: 'DRL'
     },
     operators: ['equal']
     }],
     display_empty_filter: 0,
     rules: rules_basic
     });*/
    var rules_widgets = {
        condition: 'OR',
        rules: [{
                id: 'feature',
                operator: 'equal',
                value: '1'
            }]
    };
    /*$.getJSON('http://querybuilder.js.org/assets/demo-data.json', function (data) {
     
     });*/
    $('#builder-basic').queryBuilder({
        filters: [{
                id: 'feature',
                label: 'Feature',
                type: 'integer',
                //input: 'select',
                //multiple: false,
                plugin: 'select2',
                plugin_config: {
                    dropdownParent: $('#modal-product-form'),
                    data: [{id: 0, text: 'enhancement'}, {id: 1, text: 'bug'}, {id: 2, text: 'duplicate'}, {id: 3, text: 'invalid'}, {id: 4, text: 'wontfix'}]
                }
                /*valueSetter: function (rule, value) {
                    rule.$el.find('.rule-input-container input').val(value).trigger("change");
                },
                valueGetter: function (rule) {
                    var input = rule.$el.find('.rule-input-container input');
                    return input.select2('select');
                }*/
            }],
        operators: ['equal'],
        display_empty_filter: 0, /*,
         rules: rules_widgets*/
    })

    /*$("body").on('DOMSubtreeModified', "#builder-basic", function () {
     console.debug("Changed");
     hideOperatorDropdown();
     hideFilterDropDown();
     });*/

    /*function hideOperatorDropdown() {
     $('.rule-operator-container').hide();
     }
     
     function hideFilterDropDown() {
     $('.rule-filter-container').hide();
     }
     
     $('#builder-basic').on('beforeAddGroup.queryBuilder', function () {
     console.log("Group Added");
     hideOperatorDropdown();
     hideFilterDropDown();
     });
     $('#builder-basic').on('beforeAddRule.queryBuilder', function () {
     console.log("After rule.");
     hideOperatorDropdown();
     hideFilterDropDown();
     });*/
    /*$('#builder-basic').on('afterAddRule.queryBuilder', function () {
     $(".rule-filter-container").css("display", "none");
     $(".rule-operator-container").css("display", "none");
     });*/
    $('#btn-reset').on('click', function () {
        $('#builder-basic').queryBuilder('reset');
    });
    $('#btn-set').on('click', function () {
        $('#builder-basic').queryBuilder('setRules', rules_basic);
    });
    $('#btn-get').on('click', function () {
//        alert("btn");
        //var result = $('#builder-basic').queryBuilder('getRules'); 
        if($('#combname').val() != ""){
            var result = $('#builder-basic').queryBuilder('getSQL', false);
            result['qb_name'] = $('#combname').val();
            var ctype = $(this).attr('data-ctype');
            var url_link = "";
            if(ctype == "safety")
                url_link = "createsafety_comb"
            else
                url_link = "createlegislation_comb"
//            alert(url_link);
            if($('#button_status').val() == "edit")
                result['cid'] = $('#combid').val();
            result['qb_status'] = 1;
            alert(JSON.stringify(result));
            if (!$.isEmptyObject(result) && url_link != "") {
    //            console.log(JSON.stringify(result, null, 2));
                    $.ajax({
                        type : "POST",
                        url : url_link,
                        method : "POST",
                        data : result,
                        success : function(response, status, headers, config) {
                            alert(response.maps.status);
                            location.reload();
                        }
                    });
            }
        }
        else{
            alert("Please fill the name");
        }
    });

    var sql_import_export = "feature = 1 AND ( feature = 4 OR feature = 3 ) ";
    $('#btn-set').on('click', function () {
        $('#builder-basic').queryBuilder('setRulesFromSQL', sql_import_export);
    });
    $("body").delegate( "#edit_or_view", "click", function() {  
//    $('#edit_or_view').on('click', function () {
//        alert("edit_or_view");
        var sql_combination = $(this).parents('tr').find('.combination').text();
        $('#builder-basic').queryBuilder('setRulesFromSQL', sql_combination);
        $('#combname').val($.trim($(this).parents('tr').find('#leg_name').text()));
        $('#combid').val($.trim($(this).parents('tr').find('.combination_id').text()));
        $('#button_status').val($(this).attr('name'));
        if($(this).attr('name') == "view")
           $('#btn-get').hide();
        else
           $('#btn-get').show();
    });
});