package cn.appsys.tools;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	
	//每页显示的数量
	public static final Integer SIZE=6;
	//当前页数
	protected Integer currentPageNo=1;
	//总共数据
	protected Integer totalCount=0;
	//总页数
	protected Integer totalPageCount=1;
	
	List<T> list=new ArrayList<T>();

	
	public Integer getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(Integer currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		if(totalCount>0){
			this.totalPageCount=totalCount%SIZE==0?totalCount/SIZE:totalCount/SIZE+1;
		}
		this.totalCount = totalCount;
	}

	public Integer getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	
	
	
}
