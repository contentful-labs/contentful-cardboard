package com.contentful.cardboard.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.contentful.cardboard.R;
import com.contentful.cardboard.vault.Product;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductDisplay extends LinearLayout {

  @Bind(R.id.display_title)
  protected TextView title;

  @Bind(R.id.display_description)
  protected TextView description;

  @Bind(R.id.display_rating)
  protected RatingBar rating;

  @Bind(R.id.display_price)
  protected TextView price;

  public ProductDisplay(Context context) {
    super(context);
  }

  public ProductDisplay(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ProductDisplay(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void update(Product product) {
    title.setText(product.name());
    description.setText(product.description());
    rating.setRating(product.rating().floatValue());
    price.setText(product.price() + " €");
  }
}
