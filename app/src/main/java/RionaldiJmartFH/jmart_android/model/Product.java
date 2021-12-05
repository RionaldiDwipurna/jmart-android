package RionaldiJmartFH.jmart_android.model;

import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;

public class Product extends Serializable{
    public String toString(String name){
        getLoggedAccount().name = name;
        return getLoggedAccount().name;
    }
}
