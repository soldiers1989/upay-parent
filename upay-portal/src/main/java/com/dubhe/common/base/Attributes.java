package com.dubhe.common.base; 

import com.dubhe.common.datastructure.impl.BaseDto;


/** 
 * 数机构自定义属性
 * @author zhouzhuanshu 
 * @version create time：2014年7月1日 下午2:56:41 
 * 
 */
public class Attributes extends BaseDto {

     public void Attributes(int level){
        this.level = level;
    } 
    
    /**
     * 
     */
    private static final long serialVersionUID = -3283477782014101210L;
    /** 级别 */
    int level;
  
    public Attributes(int level){
        this.level = level;
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
