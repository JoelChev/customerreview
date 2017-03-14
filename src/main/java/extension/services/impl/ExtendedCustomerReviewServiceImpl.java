package extension.services.impl;

import de.hybris.platform.customerreview.constants.CustomerReviewConstants;
import de.hybris.platform.customerreview.impl.DefaultCustomerReviewService;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import extension.dao.ExtendedCustomerReviewDao;
import extension.services.ExtendedCustomerReviewService;
import extension.exceptions.InvalidCustomerReviewException;

public class ExtendedCustomerReviewServiceImpl extends DefaultCustomerReviewService implements ExtendedCustomerReviewService {

    private String[] badWordsArray;

    private String badWords;

    private ExtendedCustomerReviewDao extendedCustomerReviewDao;
    /**
     * Gets the number of reviews for a given product in an inclusive ratings range.
     * @param product
     * @param minimumRating
     * @param maximumRating
     * @return
     */
    public Integer getReviewsForProductInRatingsRange(ProductModel product, Double minimumRating, Double maximumRating) throws Exception{
        if(minimumRating == null){
            throw new IllegalArgumentException("Minimum rating must be provided");
        }
        if(maximumRating == null){
            throw new IllegalArgumentException("Maximum rating must be provided");
        }
        if(product == null) {
            throw new IllegalArgumentException("Product must be provided");
        }
        if(minimumRating >maximumRating){
            throw new IllegalArgumentException("Minimum rating must be less than or equal to maximum rating.");
        }
        if(minimumRating < CustomerReviewConstants.DEFAULTS.MINIMAL_RATING){
            throw new IllegalArgumentException("Minimum rating must be at least 0.0");
        }
        if(maximumRating > CustomerReviewConstants.DEFAULTS.MAXIMAL_RATING){
            throw new IllegalArgumentException("Maximum rating can be at most 5.0");
        }
        return getExtendedCustomerReviewDao().getReviewsForProductInRatingsRange(product, minimumRating, maximumRating);
    }

    @Override
    public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user, ProductModel product)
  {
      CustomerReviewModel customerReviewModel = null;
      try {
          if (comment != null && comment.length() > 0) {
              if (badWordsArray != null && badWordsArray.length > 0) {
                  for (String word : badWordsArray) {
                      if (comment.toLowerCase().contains(word)) {
                          throw new InvalidCustomerReviewException("The word " + word + " is not allowed in a review.");
                      }
                  }
              }
          }
          if(rating < CustomerReviewConstants.DEFAULTS.MINIMAL_RATING){
              throw new InvalidCustomerReviewException("Rating must be 0.0 or greater");
          }
          customerReviewModel = super.createCustomerReview(rating, headline, comment, user, product);
      }
      catch(InvalidCustomerReviewException e){
          e.printStackTrace();
      }
      return customerReviewModel;

   }


    public ExtendedCustomerReviewDao getExtendedCustomerReviewDao() {
        return extendedCustomerReviewDao;
    }

    public void setExtendedCustomerReviewDao(ExtendedCustomerReviewDao extendedCustomerReviewDao) {
        this.extendedCustomerReviewDao = extendedCustomerReviewDao;
    }

    public String[] getBadWordsArray() {
        return badWordsArray;
    }

    public void setBadWordsArray(String[] badWordsArray) {
        this.badWordsArray = badWordsArray;
    }

    public String getBadWords() {
        return badWords;
    }

    public void setBadWords(String badWords) {
        this.badWords = badWords;
        if (badWords != null && badWords.length() > 0) {
            badWordsArray = badWords.split(",");
        }
    }
}
