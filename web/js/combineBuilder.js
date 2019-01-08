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
                type: 'string',
                plugin: 'select2',
                plugin_config: {
                    dropdownParent: $('#modal-product-form'),
                    data: [{id:0,text:'enhancement'},{id:1,text:'bug'},{id:2,text:'duplicate'},{id:3,text:'invalid'},{id:4,text:'wontfix'}]
                },
                valueSetter: function (rule, value) {}
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
        var result = $('#builder-basic').queryBuilder('getRules');
        if (!$.isEmptyObject(result)) {
            alert(JSON.stringify(result, null, 2));
        }
    });
});