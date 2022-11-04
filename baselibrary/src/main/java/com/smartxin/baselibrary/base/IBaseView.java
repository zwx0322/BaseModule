/**
 * Author: zhengwenxin
 * CreateDate  : 2021/7/2 13:35
 * Description: 页面渲染接口
 */
package com.smartxin.baselibrary.base;

/**
 *     Author: zhengwenxin
 *     CreateDate  : 2021/7/2 13:35
 *     Description: 页面渲染接口
 */
public interface IBaseView {

    /**
     * 初始化界面传递参数
     */
    void initView();
    /**
     * 初始化数据
     */
    void initData();

    /**
     * 初始化点击事件监听
     * */
    void initListener();
}
