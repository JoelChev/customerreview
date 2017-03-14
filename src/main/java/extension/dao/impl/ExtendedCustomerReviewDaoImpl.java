package extension.dao.impl;

import java.util.Collections;

import extension.dao.CustomerReviewDaoExt;
import de.hybris.platform.customerreview.constants.GeneratedCustomerReviewConstants;
import de.hybris.platform.customerreview.dao.impl.CustomerReviewModel;
import de.hybris.platform.customerreview.dao.impl.FlexibleSearchQuery;
import de.hybris.platform.customerreview.dao.impl.SearchResult;
import de.hybris.platform.core.model.product.ProductModel;

import customerreview.dao.CustomerReviewDao;
import customerreview.dao.impl.DefaultCustomerReviewDao;
import dao.ExtendedCustomerReviewDao;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.constants.GeneratedCustomerReviewConstants;
import de.hybris.platform.customerreview.dao.impl.CustomerReviewModel;
import de.hybris.platform.customerreview.dao.impl.FlexibleSearchQuery;
import de.hybris.platform.customerreview.dao.impl.SearchResult;


public class ExtendedCustomerReviewDaoImpl extends DefaultCustomerReviewDao implements ExtendedCustomerReviewDao{

	private static final String productInRatingsRangeQuery =
			"SELECT count(*) FROM {" + "CustomerReview" + "} WHERE {" + "product" + "}=?product AND {" + "rating" + "}" +
					">= ?minRating AND {" + "rating" + "} <= ?maxRating";

	public Integer getReviewsForProductInRatingsRange(ProductModel product, Double minimumRating, Double maximumRating)
	{
		FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery(productInRatingsRangeQuery);
		flexibleSearchQuery.addParameter("product", product);
		flexibleSearchQuery.addParameter("minRating", minimumRating);
		flexibleSearchQuery.addParameter("maxRating", maximumRating);
		flexibleSearchQuery.setResultClassList(Collections.singletonList(Integer.class));
		SearchResult<Integer> searchResult = getFlexibleSearchService().search(fsQuery);

		return searchResult.getResult();
	}
}
