/**
 * 使用用户的基本信息管理初始化
 */
var BusUser = {
    id: "BusUserTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
BusUser.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '', field: 'id', visible: true, align: 'center', valign: 'middle'},
            {title: '题目内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '题目类型 1判断 2单选  3多选', field: 'type', visible: true, align: 'center', valign: 'middle'},
            {title: '选项a', field: 'optionA', visible: true, align: 'center', valign: 'middle'},
            {title: '选项b', field: 'optionB', visible: true, align: 'center', valign: 'middle'},
            {title: '选项c', field: 'optionC', visible: true, align: 'center', valign: 'middle'},
            {title: '选项d', field: 'optionD', visible: true, align: 'center', valign: 'middle'},
            {title: '答案,用逗号进行  例如A,B', field: 'answer', visible: true, align: 'center', valign: 'middle'},
            {title: '解析', field: 'analysis', visible: true, align: 'center', valign: 'middle'},
            {title: '1 党员干部  2 非中共党员公职人员', field: 'userQuestionType', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
BusUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        BusUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加使用用户的基本信息
 */
BusUser.openAddBusUser = function () {
    var index = layer.open({
        type: 2,
        title: '添加使用用户的基本信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/busUser/busUser_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看使用用户的基本信息详情
 */
BusUser.openBusUserDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '使用用户的基本信息详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/busUser/busUser_update/' + BusUser.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除使用用户的基本信息
 */
BusUser.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/busUser/delete", function (data) {
            Feng.success("删除成功!");
            BusUser.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("busUserId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询使用用户的基本信息列表
 */
BusUser.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    BusUser.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = BusUser.initColumn();
    var table = new BSTable(BusUser.id, "/busUser/list", defaultColunms);
    table.setPaginationType("client");
    BusUser.table = table.init();
});
