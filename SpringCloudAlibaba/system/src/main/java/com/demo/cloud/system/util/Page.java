package com.demo.cloud.system.util;

/**
 * 分页
 *
 * @author sheng
 * @date 2020/07/01
 */
public class Page {
    private int showCount = 20;
    private int totalPage;
    private int totalResult;
    private int currentPage;
    private int currentResult;
    private String order;
    private PageData pageData;

    public Page() {
    }

    public int getTotalPage() {
        if (this.totalResult % this.showCount == 0) {
            this.totalPage = this.totalResult / this.showCount;
        } else {
            this.totalPage = this.totalResult / this.showCount + 1;
        }

        return this.totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResult() {
        return this.totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public int getCurrentPage() {
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }

        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getShowCount() {
        return this.showCount;
    }

    public void setShowCount(int showCount) {
        this.showCount = showCount;
    }

    public int getCurrentResult() {
        this.currentResult = (this.getCurrentPage() - 1) * this.getShowCount();
        if (this.currentResult < 0) {
            this.currentResult = 0;
        }

        return this.currentResult;
    }

    public void setCurrentResult(int currentResult) {
        this.currentResult = currentResult;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setPd(PageData pd){this.pageData = pd;}

    public PageData getPd(){return this.pageData;}

    public String getOrder() {
        if (this.order == null || this.order.toString().length() == 0) {
            this.order = "id";
        }

        return this.order;
    }
}
