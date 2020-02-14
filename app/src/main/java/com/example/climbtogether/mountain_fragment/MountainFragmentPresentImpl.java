package com.example.climbtogether.mountain_fragment;

import android.util.Log;

import com.example.climbtogether.R;
import com.example.climbtogether.home_fragment.news_view.MountainNewsVu;
import com.example.climbtogether.mountain_fragment.db_modle.DataBaseApi;
import com.example.climbtogether.mountain_fragment.db_modle.DataBaseImpl;
import com.example.climbtogether.mountain_fragment.db_modle.DataDTO;

import java.util.ArrayList;

public class MountainFragmentPresentImpl implements MountainFragmentPresenter {

    private MountainFragmentVu mView;

    public MountainFragmentPresentImpl(MountainFragmentVu mView){
        this.mView = mView;
    }

    @Override
    public void onFilterTextViewBackgroundChangeListener(int textType,String levelType) {
        Log.i("Michael","篩選了 : "+levelType);
        mView.showTextViewBackgroundChange(textType);
        if (levelType.equals(mView.getVuContext().getResources().getString(R.string.all))){
            mView.showSearchNoDataInformation(false);
            ArrayList<DataDTO> dataArrayList = getDataBase().getAllInformation();
            mView.setRecyclerView(dataArrayList);
        }else {
            ArrayList<DataDTO> levelArrayList = getDataBase().getLevelAInformation(levelType);
            if (levelArrayList.size() != 0){
                mView.showSearchNoDataInformation(false);
                mView.setRecyclerView(levelArrayList);
            }else{
                mView.showSearchNoDataInformation(true);
                mView.setRecyclerView(levelArrayList);
            }

        }

    }

    @Override
    public void onWatchMoreClickListener() {
        mView.showAllInformation();
    }

    @Override
    public void onPrepareData() {
        mView.setRecyclerView(getDataBase().getAllInformation());
    }

    @Override
    public void onTopIconChange(int sid,String isShow) {
        DataDTO dataDTO = getDataBase().getDataBySid(sid);
        dataDTO.setCheck(dataDTO.getCheck().equals("false") ? "true" : "false");
        Log.i("Michael","修改過後的資料 check : "+dataDTO.getCheck() + " , 第 "+dataDTO.getSid()+" 筆資料");
        getDataBase().update(dataDTO);
        mView.setDataChange(getDataBase().getAllInformation(),isShow);

    }

    @Override
    public void onShowDatePicker(int sid) {
        DataDTO dataDTO = getDataBase().getDataBySid(sid);

        if (dataDTO.getCheck().equals("false")){
            mView.showDatePick(sid);
        }else {
            mView.deleteFavorite(sid,dataDTO);
        }



    }

    @Override
    public void onCreateDocumentInFirestore(int sid, String topTime) {
        DataDTO dataDTO = getDataBase().getDataBySid(sid);
        mView.setFirestore(sid,topTime,dataDTO);
    }

    @Override
    public void onLoginEvent() {
        mView.intentToLoginActivity();
    }

    private DataBaseApi getDataBase(){
        return new DataBaseImpl(mView.getVuContext());
    }
}
