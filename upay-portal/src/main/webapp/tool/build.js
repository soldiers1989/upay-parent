({
    //appDir: "./",
    baseUrl: "../",
    dir: "../portal-built",
    fileExclusionRegExp: /^(r|build|angular)\.js$/,
    optimizeCss: 'standard',
    removeCombined: true,
    paths: {
        'style': 'resources/lib/css',
        'text': 'resources/lib/text',
        'understore': 'resources/lib/understore',
        'class': 'resources/lib/class',
        'jquery': 'resources/lib/jquery-1.9.1.min',
        'bootstrap': 'resources/lib/bootstrap/js/bootstrap.min',
        'highcharts': 'resources/lib/Highcharts-4.1.3/js/highcharts.src',
        'highcharts_exporting': 'resources/lib/Highcharts-4.1.3/js/modules/exporting.src',
        'validation': 'resources/lib/validation/jquery.validate.min',
        'validation-addMethod': 'resources/lib/validation/additional-methods',
        'card': 'resources/lib/validation/card',
        'common': 'resources/common/utils/common',
        'dataTable': 'resources/lib/bootstrap-table-master/dist/locale/bootstrap-table-zh-CN.min',
        'datetimepicker': 'resources/lib/bootstrap-datetimepicker-master/js/locales/bootstrap-datetimepicker.zh-CN',
        'message': 'resources/component/message',
        'uploadify': 'resources/lib/uploadify-v3.2.1/jquery.uploadify.min',
        'placeholder': 'resources/lib/jquery.placeholder',
        'dateUtils': 'resources/component/dateUtils',
        'treeview': 'resources/lib/bootstrap-treeview-master/dist/bootstrap-treeview.min',
        'dropdownList': 'resources/component/dropdownList',
        'jquery.qrcode': 'resources/lib/jquery-qrcode-master/jquery.qrcode',
        'qrcode': 'resources/lib/jquery-qrcode-master/qrcode',
        'respond': 'resources/lib/bootstrap/js/respond',
        'weibo': 'empty:',
        'requireconfig': 'resources/common/config/requireconfig',
        'angular': 'empty:',
        'jquery.event.drag': 'resources/lib/jquery.event.drag-1.5.min',
        'jquery.touchSlider': 'resources/lib/jquery.touchSlider',
        'pwdctrl':'resources/component/pwdCtrl',
        'validateHints':'resources/common/utils/validateHints',
        'jquery-ui': 'resources/lib/jquery-ui/jquery-ui.min',
        'PassGuardCtrl':'resources/lib/PassGuardCtrl'
    },
    shim: {
        'underscore': {
            exports: '_'
        },
        'angular': {
            exports: 'angular'
        },
        'bootstrap': {
            deps: ['jquery', 'respond']
        },
        'highcharts_exporting': {
            deps: ['jquery', 'highcharts']
        },
        'highcharts': {
            deps: ['jquery']
        },
        'validation': {
            deps: ['jquery']
        },
        'validation-addMethod': {
            deps: ['validation', 'card', 'jquery']
        },
        'common': {
            deps: ['jquery']
        },
        'dataTable': {
            deps: ['jquery', 'style!resources/lib/bootstrap-table-master/dist/bootstrap-table.min.css', 'resources/lib/bootstrap-table-master/dist/bootstrap-table.min']
        },
        'datetimepicker': {
            deps: ['jquery', 'style!resources/lib/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css', 'resources/lib/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.min']
        },
        'message': {
            deps: ['jquery']
        },
        'uploadify': {
            deps: [
                'style!resources/lib/uploadify-v3.2.1/uploadify.css',
                'jquery',
                'resources/lib/uploadify-v3.2.1/swfobject'
            ]
        },
        'treeview': {
            deps: ['jquery', 'style!resources/lib/bootstrap-treeview-master/dist/bootstrap-treeview.min.css']
        },
        'cityDropdown': {
            deps: ['jquery']
        },
        'placeholder': {
            deps: ['jquery']
        },
        'qrcode': {
            deps: ['jquery', 'jquery.qrcode']
        },
        'jquery.event.drag':{
	    	deps:['jquery']
	    },
	    'jquery.touchSlider':{
	    	deps:['jquery']
	    },
	    'jquery.qrcode':{
	    	deps:['jquery']
	    },
	    'PassGuardCtrl':{
	    	deps:['jquery']
	    },
	    'pwdctrl':{
	    	deps:['jquery','PassGuardCtrl']
	    },
	     "jquery-ui":{
           deps: ['jquery', 'style!resources/lib/jquery-ui/jquery-ui.css']
        }
    }
//    ,
//    modules: [
//            // {
//            //     name: "common/config/requireconfig"
//            // },
//            // {
//            //     name: "resources/app/login/js/login"
//            // }
//        ]
})