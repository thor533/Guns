/**
 * 初始化人员收藏的相关题目详情对话框
 */
var UserCollectionQuestionInfoDlg = {
    userCollectionQuestionInfoData : {}
};

/**
 * 清除数据
 */
UserCollectionQuestionInfoDlg.clearData = function() {
    this.userCollectionQuestionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserCollectionQuestionInfoDlg.set = function(key, val) {
    this.userCollectionQuestionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserCollectionQuestionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserCollectionQuestionInfoDlg.close = function() {
    parent.layer.close(window.parent.UserCollectionQuestion.layerIndex);
}

/**
 * 收集数据
 */
UserCollectionQuestionInfoDlg.collectData = function() {
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
UserCollectionQuestionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userCollectionQuestion/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserCollectionQuestion.table.refresh();
        UserCollectionQuestionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userCollectionQuestionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserCollectionQuestionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userCollectionQuestion/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserCollectionQuestion.table.refresh();
        UserCollectionQuestionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userCollectionQuestionInfoData);
    ajax.start();
}

$(function() {

});
