package com.android.topprevents.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class EventDataModel {
    @SerializedName("websites")
    @Expose
    private List<Website> websites = new ArrayList<Website>();
    @SerializedName("quote_max")
    @Expose
    private String quoteMax;
    @SerializedName("quote_available")
    @Expose
    private String quoteAvailable;

    /**
     *
     * @return
     * The websites
     */
    public List<Website> getWebsites() {
        return websites;
    }

    /**
     *
     * @param websites
     * The websites
     */
    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }

    /**
     *
     * @return
     * The quoteMax
     */
    public String getQuoteMax() {
        return quoteMax;
    }

    /**
     *
     * @param quoteMax
     * The quote_max
     */
    public void setQuoteMax(String quoteMax) {
        this.quoteMax = quoteMax;
    }

    /**
     *
     * @return
     * The quoteAvailable
     */
    public String getQuoteAvailable() {
        return quoteAvailable;
    }

    /**
     *
     * @param quoteAvailable
     * The quote_available
     */
    public void setQuoteAvailable(String quoteAvailable) {
        this.quoteAvailable = quoteAvailable;
    }
}
