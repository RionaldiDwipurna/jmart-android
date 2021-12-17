package RionaldiJmartFH.jmart_android.model;

import static RionaldiJmartFH.jmart_android.LoginActivity.getLoggedAccount;
/**
 *  Base mode of the product
 *
 * @author Rionaldi Dwipurna Wongsoputra
 */
public class Product extends Serializable{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    public String toString(){
        getLoggedAccount().name = name;
        return getLoggedAccount().name;
    }
}
