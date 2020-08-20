/**
 * 初始化第三方用户关系表详情对话框
 */
var ThirdUserRelationInfoDlg = {
    thirdUserRelationInfoData : {}
};

/**
 * 清除数据
 */
ThirdUserRelationInfoDlg.clearData = function() {
    this.thirdUserRelationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ThirdUserRelationInfoDlg.set = function(key, val) {
    this.thirdUserRelationInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ThirdUserRelationInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ThirdUserRelationInfoDlg.close = function() {
    parent.layer.close(window.parent.ThirdUserRelation.layerIndex);
}

/**
 * 收集数据
 */
ThirdUserRelationInfoDlg.collectData = function() {
    this
    .set('id')
    .set('content')
    .set('type')
    .set('optionA')
    .set('optionB')
    .set('optionC')
    .set('optionD')
    .set('answer')
    .set('analysis')
    .set('userQuestionType')
    .set('createTime');
}

/**
 * 提交添加
 */
ThirdUserRelationInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/thirdUserRelation/add", function(data){
        Feng.success("添加成功!");
        window.parent.ThirdUserRelation.table.refresh();
        ThirdUserRelationInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.thirdUserRelationInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ThirdUserRelationInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/thirdUserRelation/update", function(data){
        Feng.success("修改成功!");
        window.parent.ThirdUserRelation.table.refresh();
        ThirdUserRelationInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.thirdUserRelationInfoData);
    ajax.start();
}

$(function() {

});
