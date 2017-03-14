package extension.services;

import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.core.model.product.ProductModel;

public interface ExtendedCustomerReviewService extends CustomerReviewService{

	/**
	 * Gets the number of reviews for a given product in an inclusive ratings range.
	 * @param product
	 * @param minimumRating
	 * @param maximumRating
	 * @return
	 */
	Integer getReviewsForProductInRatingsRange(ProductModel product, Double minimumRating, Double maximumRating) throws Exception;
}
