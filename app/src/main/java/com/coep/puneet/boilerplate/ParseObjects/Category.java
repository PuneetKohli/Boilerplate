package com.coep.puneet.boilerplate.ParseObjects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Category")
public class Category extends ParseObject
{

    public String getCategory_name()
    {
        return getString("category_name");
    }

    public void setCategory_name(String category_name)
    {
        put("category_name", category_name);
    }

    public static ParseQuery<Category> getQuery()
    {
        return ParseQuery.getQuery(Category.class);
    }

    public static boolean isSelected;

}
