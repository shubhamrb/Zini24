package com.mamits.zini24user.data.model.home;

import com.mamits.zini24user.data.model.product.ProductDataModel;

import java.io.Serializable;
import java.util.List;

public class TransportModel implements Serializable {

    List<CategoryListItem> categoryList;
    List<ProductDataModel> productList;

    public List<ProductDataModel> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductDataModel> productList) {
        this.productList = productList;
    }

    public List<CategoryListItem> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryListItem> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "TransportModel{" +
                "categoryList=" + categoryList +
                ", productList=" + productList +
                '}';
    }
}
