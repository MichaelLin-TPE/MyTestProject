package com.example.climbtogether.mountain_fragment;

import com.example.climbtogether.mountain_fragment.db_modle.DataDTO;

public interface MountainFragmentPresenter {
    void onFilterTextViewBackgroundChangeListener(int textType,String levelType);

    void onWatchMoreClickListener();

    void onPrepareData();

    void onTopIconChange(int sid,String isShow);

    void onShowDatePicker(int sid);

    void onCreateDocumentInFirestore(int sid, String topTime);

    void onLoginEvent();
}
