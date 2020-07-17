package com.knight.f_interesting.mvp.cart;

import android.content.Context;

import com.knight.f_interesting.buses.CartBus;
import com.knight.f_interesting.models.Cart;
import com.knight.f_interesting.models.Invoice;
import com.knight.f_interesting.models.Product;
import com.knight.f_interesting.utils.AppUtils;

import java.util.List;

public class CartPresenter implements CartContract.Presenter, CartContract.Model.OnFinishedListener {

    private CartContract.View view;
    private CartContract.Model model;
    private Context context;

    public CartPresenter(CartContract.View view, Context context) {
        this.context = context;
        this.view = view;
        model = new CartModel(context);
    }

    @Override
    public void onFinishedGetData(List<Product> products, List<Cart> carts) {
        if (view != null) {
            view.hideProgress();
            view.setDataToView(products, carts);
            view.refresh(products, carts);
        }
    }

    @Override
    public void onFinishedCreateOrder(Invoice invoice) {
        if (view != null) {
            view.hideProgress();
            view.onOrderSuccess(invoice);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        if (view != null) {
            view.hideProgress();
            view.onResponseFailure(throwable);
        }
    }

    @Override
    public void refresh(List<Product> products, List<Cart> carts) {
        CartBus.refresh();
        view.refresh(products, carts);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void createOrder(Invoice invoice, String code) {
        if (view != null) {
            view.showProgress();
            model.createOrder(this, invoice, code);
        }
    }

    @Override
    public void requestData() {
        if (view != null) {
            view.showProgress();
            List<Cart> carts = AppUtils.db.getCart();
            model.getData(this, carts);
        }
    }
}
