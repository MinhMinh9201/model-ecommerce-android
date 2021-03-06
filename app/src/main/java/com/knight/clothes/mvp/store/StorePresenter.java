package com.knight.clothes.mvp.store;

import com.knight.clothes.models.Category;

import java.util.List;

public class StorePresenter implements StoreContract.Presenter, StoreContract.Model.OnFinishedListener {

    private StoreContract.Model model;
    private StoreContract.View view;

    StorePresenter(StoreContract.View view) {
        this.view = view;
        model = new StoreModel();
    }

    @Override
    public void onFinished(List<Category> categories) {
        if (view != null){
            view.hideProgress();
            view.setDataToView(categories);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if (view != null){
            view.hideProgress();
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void requestData() {
        if (view != null){
            view.showProgress();
            model.getData(this);
        }
    }
}
