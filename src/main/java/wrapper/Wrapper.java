package wrapper;

import core.Account;
import core.Product;
import entities.AccountEntity;
import entities.ProductEntity;

public class Wrapper {
    public static Product wrapProduct(ProductEntity productEntity){
        Product product = new Product();
        product.setId(productEntity.getId());
        product.setAmount(productEntity.getAmount());
        product.setManufacturer(productEntity.getManufacturer());
        product.setName(productEntity.getName());
        product.setPrice(productEntity.getPrice());
        product.setUpdated(productEntity.getUpdated());
        return product;
    }
    public static ProductEntity wrapProduct(Product product){
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setAmount(product.getAmount());
        productEntity.setManufacturer(product.getManufacturer());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setUpdated(product.getUpdated());
        return productEntity;
    }
    public static Account wrapAccount(AccountEntity accountEntity){
        Account account = new Account();
        account.setId(accountEntity.getId());
        account.setEmail(accountEntity.getEmail());
        account.setPassword(accountEntity.getPassword());
        account.setRole(accountEntity.getRole());
        return account;
    }
    public static AccountEntity wrapAccount(Account account){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmail(account.getEmail());
        accountEntity.setPassword(account.getPassword());
        accountEntity.setRole(account.getRole());
        return accountEntity;
    }
}
