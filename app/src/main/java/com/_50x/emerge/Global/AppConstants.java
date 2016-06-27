package com._50x.emerge.Global;

public final class AppConstants
{

    /*** Example App Constants to understand nomenclature ***/

    public static final int REQUEST_CODE = 0;

    public static final String PREFS_NAME = "XYZ_App_Preferences";
    public static final String PREFS_IS_LOGGED_IN = "isLoggedIn";

    public static final int GALLERY_NUM_IMGS_TO_SELECT = 8;
    public static final int REQUEST_SELECT_CATEGORY = 300;
    public static final int REQUEST_SELECT_COLOR = 301;
    public static final int TYPE_NEW_CATEGORY_LIST = 1;
    public static final int TYPE_OLD_CATEGORY_LIST = 0;

    public static final String RESULT_CATEGORY_NAME = "resultCategoryName";
    public static final String RESULT_SUBCATEGORY_NAME = "resultSubCategoryName";
    public static final String RESULT_SUBSUBCATEGORY_NAME = "resultSubsubCategoryName";
    public static final String RESULT_COLOR_LIST = "resultColorList";
    public static final int RESULT_IMAGE_FULLSCREEN = 1;
    public static final int REQUEST_CAMERA = 2;
    public static final int REQUEST_GALLERY = 3;

    //API Constants
    public static final String ACTION_VALIDATE_USER = "validateUser";
    public static final String ACTION_ADD_PRODUCT = "AddNewProduct";
    public static final String ACTION_EDIT_PRODUCT = "EditProduct";
    public static final String ACTION_SHOW_PRODUCTS = "ShowMyProducts";
    public static final String ACTION_EDIT_PROFILE = "editProfile";

    //Intent Extras - Names
    public static final String INTENT_IS_NEW_PRODUCT = "Intent_isNewProduct";
    public static final int INTENT_EXTRA_LIMIT = 0;

    //Intent Extras - Values
    public static final int NEW_PRODUCT = 1;
    public static final int EDIT_PRODUCT = 0;

    public static final int IMAGE_RESIZE_DIMEN = 800;
}
