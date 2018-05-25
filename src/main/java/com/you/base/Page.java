package com.you.base;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by shicz on 2018/5/16.
 */
public class Page<T>
{
    private Page()
    {
        
    }
    
    public Page(Integer pageNo, Integer pageSize)
    {
        if (pageNo != null && pageSize != null){
            this.pageNo = pageNo;
            this.pageSize = pageSize;
        }
    }
    
    private int pageNo = 1; // 当前页
    
    private int rowCount = 0; // 总行数
    
    private int pageSize = 20; // 页大小
    
    private int pageCount = 0; // 总页数
    
    private int pageOffset = 0;// 当前页起始记录
    
    private int pageTail = 0;// 当前页到达的记录

    private List<T> result;

    @JSONField(serialize = false)
    protected void doPage()
    {
        this.pageCount = this.rowCount / this.pageSize + 1;
        // 如果模板==0，且总数大于1，则减一
        if ((this.rowCount % this.pageSize == 0) && pageCount > 1)
        {
            this.pageCount--;
        }
        
        // Mysql 算法
        this.pageOffset = (this.pageNo - 1) * this.pageSize;
        this.pageTail = this.pageOffset + this.pageSize;
        if ((this.pageOffset + this.pageSize) > this.rowCount)
        {
            this.pageTail = this.rowCount;
        }
    }
    
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }
    
    public int getPageCount()
    {
        return pageCount;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageOffset(int pageOffset)
    {
        this.pageOffset = pageOffset;
    }
    
    public int getPageOffset()
    {
        return pageOffset;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageTail(int pageTail)
    {
        this.pageTail = pageTail;
    }
    
    public int getPageTail()
    {
        return pageTail;
    }
    
    public void setRowCount(int rowCount)
    {
        this.rowCount = rowCount;
        this.doPage();
    }
    
    public int getRowCount()
    {
        return rowCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
