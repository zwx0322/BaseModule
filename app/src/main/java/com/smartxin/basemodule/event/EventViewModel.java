package com.smartxin.basemodule.event;

import com.kunminx.architecture.ui.callback.UnPeekLiveData;
import com.smartxin.baselibrary.base.BaseViewModel;

/**
 * Author: zhengwenxin
 * CreateDate: 2022/4/8 9:23
 * Description: Application 层级ViewModel
 */
public class EventViewModel extends BaseViewModel {

    private UnPeekLiveData<Void> testMessage;

    public UnPeekLiveData<Void> getTestMessage() {
        return testMessage = createLiveData(testMessage);
    }
}
