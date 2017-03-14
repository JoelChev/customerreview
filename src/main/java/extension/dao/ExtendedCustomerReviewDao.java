package extension.dao;

import de.hybris.platform.core.model.product.ProductModel;


public interface ExtendedCustomerReviewDao {

	Integer getReviewsForProductInRatingsRange(ProductModel product, Double minimumRating, Double maximumRating);
}