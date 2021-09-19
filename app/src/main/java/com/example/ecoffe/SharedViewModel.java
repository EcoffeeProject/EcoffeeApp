package com.example.ecoffe;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<String> liveData = new MutableLiveData<>();

    public LiveData<String> getLiveData(){
        Log.d("tag","get함수 불러짐: "+ liveData.getValue());
        return liveData;
    }

    public void setLiveData(String str){

        Log.d("tag","set함수 불러짐: "+ str);
        liveData.setValue(str);
    }


}
