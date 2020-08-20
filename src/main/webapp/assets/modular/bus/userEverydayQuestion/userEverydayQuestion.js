/**
 * 每日产生的题目编号管理初始化
 */
var UserEverydayQuestion = {
    id: "UserEverydayQuestionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
UserEverydayQuestion.initColumn = function () {
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
UserEverydayQuestion.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        UserEverydayQuestion.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加每日产生的题目编号
 */
UserEverydayQuestion.openAddUserEverydayQuestion = function () {
    var index = layer.open({
        type: 2,
        title: '添加每日产生的题目编号',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/userEverydayQuestion/userEverydayQuestion_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看每日产生的题目编号详情
 */
UserEverydayQuestion.openUserEverydayQuestionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '每日产生的题目编号详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/userEverydayQuestion/userEverydayQuestion_update/' + UserEverydayQuestion.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除每日产生的题目编号
 */
UserEverydayQuestion.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/userEverydayQuestion/delete", function (data) {
            Feng.success("删除成功!");
            UserEverydayQuestion.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userEverydayQuestionId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询每日产生的题目编号列表
 */
UserEverydayQuestion.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    UserEverydayQuestion.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = UserEverydayQuestion.initColumn();
    var table = new BSTable(UserEverydayQuestion.id, "/userEverydayQuestion/list", defaultColunms);
    table.setPaginationType("client");
    UserEverydayQuestion.table = table.init();
});
