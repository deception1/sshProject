package com.shop.www.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Paging implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	private long          count;       //总数据行
	private int           pageSize;    //页面数据行数
	private int           pageIndex;   //当前页码
	private int           totalPage;   //总页码
	private int           pageNum;     //页码数
	private List<Integer> pageNumList; //页码集合
	private boolean       isPageNum;   //是否计算页码
	@SuppressWarnings("rawtypes")
	private Map           session;     //缓存值栈对象
	private String        pageKey;     //分页KEY缓存名
	
	public Paging(){
		this.count=0;
		this.pageSize=0;
		this.pageIndex=1;
		this.totalPage=0;
		this.isPageNum=false;
	}
	
	public Paging(int pageIndex,int pageSize){
		this.count=0;
		this.pageSize=pageSize;
		this.pageIndex=pageIndex;
		this.totalPage=0;
		this.isPageNum=false;
	}
	
	public Paging(int pageIndex,int pageSize,int pageNum){
		this.count=0;
		this.pageSize=pageSize;
		this.pageIndex=pageIndex;
		this.totalPage=0;
		this.pageNum=pageNum;
		this.pageNumList=new ArrayList<Integer>();
		this.isPageNum=pageNum>0?true:false;
	}
	
	public void countPageNum(){//计算页码
		this.pageNumList=new ArrayList<Integer>();
		if(this.getTotalPage()<=this.getPageNum()){
			for(int i=1;i<=this.getTotalPage();i++){
				this.pageNumList.add(i);
			}
		}else{
			int adjustIndex=0;
			if(this.getPageIndex()==1){
				adjustIndex=1;
			}else if(this.getPageIndex()==this.getTotalPage()){
				adjustIndex=this.getTotalPage()-(this.getPageNum()-1);
			}else{
				int middle=(int)(this.getPageNum()/2);
				if(this.getPageIndex()<=this.getPageNum()){
					adjustIndex=1;
				}else if(this.getPageIndex()>(this.getTotalPage()-this.getPageNum())){
					adjustIndex=this.getTotalPage()-(this.getPageNum()-1);
				}else{
					adjustIndex=this.getPageIndex()-middle;
				}
			}
			for(int i=adjustIndex;i<(adjustIndex+this.getPageNum());i++){
				this.pageNumList.add(i);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static Paging getInstance(Map session,String key,Paging pg,int pageIndex,int pageSize,int pageNum){
		Paging p=(Paging)session.get(key);
		if(p==null){
			p=new Paging(pageIndex, pageSize, pageNum);
		}
		if(pg!=null){
			if(pg.getPageIndex()!=0) p.setPageIndex(pg.getPageIndex());
			if(pg.getPageSize()!=0) p.setPageSize(pg.getPageSize());
			if(pg.getPageNum()!=0) p.setPageNum(pg.getPageNum());
		}
		p.session=session;
		p.pageKey=key;
		return p;
	}
	
	@SuppressWarnings("unchecked")
	public void saveInstance(){
		this.session.put(this.pageKey, this);
	}
	
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
		this.setTotalPage((int)(this.getCount()%this.getPageSize()==0?this.getCount()/this.getPageSize():this.getCount()/this.getPageSize()+1));
		if(this.getPageIndex()<1){
			this.setPageIndex(1);
		}
		if(this.getPageIndex()>this.getTotalPage()){
			this.setPageIndex(this.getTotalPage());
		}
		if(this.isPageNum()) this.countPageNum();
		this.saveInstance();
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		this.isPageNum=pageNum>0?true:false;
	}
	public List<Integer> getPageNumList() {
		return pageNumList;
	}
	public void setPageNumList(List<Integer> pageNumList) {
		this.pageNumList = pageNumList;
	}
	public boolean isPageNum() {
		return isPageNum;
	}
	public void setPageNum(boolean isPageNum) {
		this.isPageNum = isPageNum;
	}
}
