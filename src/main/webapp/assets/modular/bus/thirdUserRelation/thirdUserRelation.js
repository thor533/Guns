/**
 * 第三方用户关系表管理初始化
 */
var ThirdUserRelation = {
    id: "ThirdUserRelationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ThirdUserRelation.initColumn = function () {
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
ThirdUserRelation.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ThirdUserRelation.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加第三方用户关系表
 */
ThirdUserRelation.openAddThirdUserRelation = function () {
    var index = layer.open({
        type: 2,
        title: '添加第三方用户关系表',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/thirdUserRelation/thirdUserRelation_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看第三方用户关系表详情
 */
ThirdUserRelation.openThirdUserRelationDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '第三方用户关系表详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/thirdUserRelation/thirdUserRelation_update/' + ThirdUserRelation.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除第三方用户关系表
 */
ThirdUserRelation.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/thirdUserRelation/delete", function (data) {
            Feng.success("删除成功!");
            ThirdUserRelation.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("thirdUserRelationId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询第三方用户关系表列表
 */
ThirdUserRelation.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ThirdUserRelation.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ThirdUserRelation.initColumn();
    var table = new BSTable(ThirdUserRelation.id, "/thirdUserRelation/list", defaultColunms);
    table.setPaginationType("client");
    ThirdUserRelation.table = table.init();
});
