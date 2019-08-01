package cn.its.basic;

public class BaseQuery {

    private int pageNum = 1;
    private int pageSize = 10;
    private String keyword ;//关键词

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "BaseQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}
