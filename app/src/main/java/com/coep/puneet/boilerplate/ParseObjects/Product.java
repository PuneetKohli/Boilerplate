package com.coep.puneet.boilerplate.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

@ParseClassName("Product")
public class Product extends ParseObject
{


    public String getProduct_name()
    {
        return getString("product_name");
    }

    public void setProduct_name(String product_name)
    {
        put("product_name", product_name);
    }

    public Category getCategory() {
        return (Category) get("product_category");
    }

    public void setCategory(Category category) {
        put("product_category", category);
    }

    public ParseFile getProductImage() {
        return (ParseFile) get("product_image");
    }

    public void setProductImage(ParseFile productImage) { put("product_image", productImage);}

    public int getProductPrice() {
        return getInt("product_price");
    }

    public void setProductPrice(int productPrice) { put("product_price", productPrice);}

    public int getProductQuantity() {
        return getInt("product_quantity");
    }

    public void setProductQuantity(int productQuantity) {
        put("product_quantity", productQuantity);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<String> getProductTags() {
        return (ArrayList<String>) get("product_tags");
    }

    public void setProductTags(ArrayList<String> productTags) {
        put("product_tags", productTags);
    }

    public static ParseQuery<Product> getQuery()
    {
        return ParseQuery.getQuery(Product.class);
    }
}
