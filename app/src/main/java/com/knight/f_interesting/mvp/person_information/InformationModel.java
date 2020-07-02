package com.knight.f_interesting.mvp.person_information;

import android.content.Context;

import com.knight.f_interesting.api.APIInterface;
import com.knight.f_interesting.api.Client;
import com.knight.f_interesting.buses.UserBus;
import com.knight.f_interesting.models.ResponseObject;
import com.knight.f_interesting.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationModel implements InformationContract.Model {
    @Override
    public void getUser(final OnFinishedListener onFinishedListener, Context context) {
        User user = UserBus.current();
        if( user != null && user.getName() != null){
            onFinishedListener.onFinish(user);
        }
        else requestApi(onFinishedListener, context);
    }

    private void requestApi(final OnFinishedListener onFinishedListener, Context context){
        APIInterface api = Client.client().create(APIInterface.class);
        Call<ResponseObject<User>> call = api.getUser(Client.header(context));
        call.enqueue(new Callback<ResponseObject<User>>() {
            @Override
            public void onResponse(Call<ResponseObject<User>> call, Response<ResponseObject<User>> response) {
                onFinishedListener.onFinish(response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseObject<User>> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });
    }

    @Override
    public void updateUser(User user) {

    }
}