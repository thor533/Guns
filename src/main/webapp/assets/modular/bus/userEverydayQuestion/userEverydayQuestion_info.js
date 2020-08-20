/**
 * 初始化每日产生的题目编号详情对话框
 */
var UserEverydayQuestionInfoDlg = {
    userEverydayQuestionInfoData : {}
};

/**
 * 清除数据
 */
UserEverydayQuestionInfoDlg.clearData = function() {
    this.userEverydayQuestionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserEverydayQuestionInfoDlg.set = function(key, val) {
    this.userEverydayQuestionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserEverydayQuestionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserEverydayQuestionInfoDlg.close = function() {
    parent.layer.close(window.parent.UserEverydayQuestion.layerIndex);
}

/**
 * 收集数据
 */
UserEverydayQuestionInfoDlg.collectData = function() {
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
UserEverydayQuestionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userEverydayQuestion/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserEverydayQuestion.table.refresh();
        UserEverydayQuestionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userEverydayQuestionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserEverydayQuestionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userEverydayQuestion/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserEverydayQuestion.table.refresh();
        UserEverydayQuestionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userEverydayQuestionInfoData);
    ajax.start();
}

$(function() {

});
