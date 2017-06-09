package com.page;
/**
 * 收集分页查询的查询条件
 * @author XD
 *
 */
public class QueryInfo {

	private int pageSize = 8;//每页有多少条数据
	private int currentPage = 1;//当前页是哪一页
	private int startIndex;//分页查询数据的起始索引
	
	
	private String cid;//分类id
	
	private String bname;//书名
	
	private String press;//出版社
	
	private String author;//作者
	
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getStartIndex() {
		/**
		 * 1 0-5
		 * 2 5-10
		 */
		this.startIndex = (this.currentPage-1)*this.pageSize;
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	
	
	
}
