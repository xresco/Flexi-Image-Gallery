
package com.abed.avg.data.model;


public class Query {

    private Integer count;
    private String created;
    private String lang;
    private Results results;

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }


    /**
     *
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }
    /**
     * 
     * @return
     *     The lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * 
     * @return
     *     The results
     */
    public Results getResults() {
        return results;
    }
}
