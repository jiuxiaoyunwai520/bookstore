package com.page;

import java.util.List;


/**
 * 提供页面所需要的一切信息
 * @author XD
 *
 */
public class PageBean {

	private List beanList;//页面数据
	
	private int totalRecord;//数据总条数
	private int pageSize;//每页几条数据
	private int totalPage;//总页数
	private int currentPage;//当前页
	private int prePage;//上一页
	private int nextPage;//下一页
	private int[] pageBar;//页面条
	
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List getBeanList() {
		return beanList;
	}
	public void setBeanList(List beanList) {
		this.beanList = beanList;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * 总页数如何计算
	 * @return
	 */
	public int getTotalPage() {
		/**
		 * 100 10 = 10
		 * 101 10 = 11
		 * 99  10 = 10
		 */
		if(this.totalRecord%this.pageSize == 0){
			this.totalPage = this.totalRecord/this.pageSize;
		}else{
			this.totalPage = this.totalRecord/this.pageSize + 1;
		}
		return totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPrePage() {
		this.prePage = this.currentPage - 1;
		if(this.prePage <= 0){
			this.prePage = 1;
		}
		return prePage;
	}
	public int getNextPage() {
		this.nextPage = this.currentPage + 1;
		if(this.nextPage>=this.totalPage){
			this.nextPage = this.totalPage;
		}
		return nextPage;
	}
	
	public int[] getPageBar() {
		//第一步，确定页码条大小
		//第二步，给页码条赋值
		
		//一般情况
		int startIndex = this.currentPage - 5;
		int endIndex = this.currentPage + 4;
		
		//特殊情况1：总页数少于10页
		if(getTotalPage()<10){
			this.pageBar = new int[this.totalPage];//小于10的页码条   3页条
			startIndex = 1;
			endIndex = this.totalPage;
		}else{
			this.pageBar = new int[10];
			//特殊情况二：点击到前5页
			if(this.currentPage<=5){
				startIndex = 1;
				endIndex = 10;
			}
			
			//特殊情况三：点击到后四条
			if(this.currentPage>this.totalPage - 4){
				endIndex = this.totalPage;
				startIndex = this.totalPage - 9;
			}
			
		}
		
		//赋值：初始化页码条
		int index = 0;
		for(int i=startIndex;i<=endIndex;i++){
			this.pageBar[index++] = i;
		}
		
		return pageBar;
	}

}
