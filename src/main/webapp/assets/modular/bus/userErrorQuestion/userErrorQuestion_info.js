/**
 * 初始化人员错误的相关题目详情对话框
 */
var UserErrorQuestionInfoDlg = {
    userErrorQuestionInfoData : {}
};

/**
 * 清除数据
 */
UserErrorQuestionInfoDlg.clearData = function() {
    this.userErrorQuestionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserErrorQuestionInfoDlg.set = function(key, val) {
    this.userErrorQuestionInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserErrorQuestionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
UserErrorQuestionInfoDlg.close = function() {
    parent.layer.close(window.parent.UserErrorQuestion.layerIndex);
}

/**
 * 收集数据
 */
UserErrorQuestionInfoDlg.collectData = function() {
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
UserErrorQuestionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userErrorQuestion/add", function(data){
        Feng.success("添加成功!");
        window.parent.UserErrorQuestion.table.refresh();
        UserErrorQuestionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userErrorQuestionInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
UserErrorQuestionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/userErrorQuestion/update", function(data){
        Feng.success("修改成功!");
        window.parent.UserErrorQuestion.table.refresh();
        UserErrorQuestionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userErrorQuestionInfoData);
    ajax.start();
}

$(function() {

});
