package com.example.climbtogether.mountain_fragment;

import android.content.Context;

import com.example.climbtogether.mountain_fragment.db_modle.DataDTO;

import java.util.ArrayList;

public interface MountainFragmentVu {
    void showAllInformation();

    void showTextViewBackgroundChange(int textType);

    Context getVuContext();

    void setRecyclerView(ArrayList<DataDTO> allInformation);

    void showSearchNoDataInformation(boolean isShow);

    void setDataChange(ArrayList<DataDTO> dataDTO,String isShow);

    void showDatePick(int sid);

    void setFirestore(int sid, String topTime,DataDTO data);

    void intentToLoginActivity();

    void deleteFavorite(int sid, DataDTO dataDTO);
}
