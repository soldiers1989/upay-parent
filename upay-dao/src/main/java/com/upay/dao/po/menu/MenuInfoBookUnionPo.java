package com.upay.dao.po.menu;

import com.pactera.dipper.po.BasePo;


public class MenuInfoBookUnionPo extends BasePo {
    private static final long serialVersionUID = 1L;
    // alias
    public static final String TABLE_ALIAS = "MenuInfoBook";
    public static final String ALIAS_ID = "PK";
    public static final String ALIAS_MENU_ID = "菜单编号";
    public static final String ALIAS_MENU_NAME = "菜单名称";
    public static final String ALIAS_MENU_TYPE = "菜单类型 1:系统菜单 0:业务菜单";
    public static final String ALIAS_PARENT_ID = "上级菜单编号 为空：表示主菜单";
    public static final String ALIAS_ICON_CLS = "节点图标CSS类名";
    public static final String ALIAS_EXPANDED = "展开状态 1:展开; 0:收缩  ";
    public static final String ALIAS_MENU_ADDR = "菜单地址";
    public static final String ALIAS_LEAF = "叶子节点 0:树枝节点; 1:叶子节点";
    public static final String ALIAS_SORT_NO = "排序号";
    public static final String ALIAS_REMARK = "备注";
    public static final String ALIAS_ICON = "节点图标";
    public static final String ALIAS_REMOVE_FLAG = "删除标志 0：未删除 1：已删除";
    public static final String ALIAS_REMOVE_TIME = "删除时间";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_CREATE_USER_ID = "创建用户";

    private java.lang.String userCertLevel;
    /**
     * 适用渠道 01-门户，02-APP，03-微信； 说明：如果适用渠道为门户和APP，则数据库保存为{01,02} db_column:
     * APPLY_CHNL_ID
     */
    private java.lang.String applyChnlId;
    /**
     * 关联时间 db_column: UPDATE_TIME
     */
    private java.util.Date updateTime;
    // columns START
    /**
     * 菜单编号 db_column: MENU_ID
     */
    private java.lang.String menuId;
    /**
     * 菜单名称 db_column: MENU_NAME
     */
    private java.lang.String menuName;
    /**
     * 菜单类型 1:系统菜单 0:业务菜单 db_column: MENU_TYPE
     */
    private java.lang.String menuType;
    /**
     * 上级菜单编号 为空：表示主菜单 db_column: PARENT_ID
     */
    private java.lang.String parentId;
    /**
     * 节点图标CSS类名 db_column: ICON_CLS
     */
    private java.lang.String iconCls;
    /**
     * 展开状态 1:展开; 0:收缩 db_column: EXPANDED
     */
    private java.lang.String expanded;
    /**
     * 菜单地址 db_column: MENU_ADDR
     */
    private java.lang.String menuAddr;
    /**
     * 叶子节点 0:树枝节点; 1:叶子节点 db_column: LEAF
     */
    private java.lang.String leaf;
    /**
     * 排序号 db_column: SORT_NO
     */
    private Integer sortNo;
    /**
     * 备注 db_column: REMARK
     */
    private java.lang.String remark;
    /**
     * 节点图标 db_column: ICON
     */
    private java.lang.String icon;
    /**
     * 删除标志 0：未删除 1：已删除 db_column: REMOVE_FLAG
     */
    private java.lang.String removeFlag;
    /**
     * 删除时间 db_column: REMOVE_TIME
     */
    private java.util.Date removeTime;
    /**
     * 创建时间 db_column: CREATE_TIME
     */
    private java.util.Date createTime;
    /**
     * 创建用户 db_column: CREATE_USER_ID
     */
    private java.lang.String createUserId;


    // columns END

    public java.lang.String getMenuId() {
        return this.menuId;
    }


    public void setMenuId(java.lang.String value) {
        this.menuId = value;
    }


    public java.lang.String getMenuName() {
        return this.menuName;
    }


    public void setMenuName(java.lang.String value) {
        this.menuName = value;
    }


    public java.lang.String getMenuType() {
        return this.menuType;
    }


    public void setMenuType(java.lang.String value) {
        this.menuType = value;
    }


    public java.lang.String getParentId() {
        return this.parentId;
    }


    public void setParentId(java.lang.String value) {
        this.parentId = value;
    }


    public java.lang.String getIconCls() {
        return this.iconCls;
    }


    public void setIconCls(java.lang.String value) {
        this.iconCls = value;
    }


    public java.lang.String getExpanded() {
        return this.expanded;
    }


    public void setExpanded(java.lang.String value) {
        this.expanded = value;
    }


    public java.lang.String getMenuAddr() {
        return this.menuAddr;
    }


    public void setMenuAddr(java.lang.String value) {
        this.menuAddr = value;
    }


    public java.lang.String getLeaf() {
        return this.leaf;
    }


    public void setLeaf(java.lang.String value) {
        this.leaf = value;
    }


    public Integer getSortNo() {
        return this.sortNo;
    }


    public void setSortNo(Integer value) {
        this.sortNo = value;
    }


    public java.lang.String getRemark() {
        return this.remark;
    }


    public void setRemark(java.lang.String value) {
        this.remark = value;
    }


    public java.lang.String getIcon() {
        return this.icon;
    }


    public void setIcon(java.lang.String value) {
        this.icon = value;
    }


    public java.lang.String getRemoveFlag() {
        return this.removeFlag;
    }


    public void setRemoveFlag(java.lang.String value) {
        this.removeFlag = value;
    }


    public java.util.Date getRemoveTime() {
        return this.removeTime;
    }


    public void setRemoveTime(java.util.Date value) {
        this.removeTime = value;
    }


    public java.util.Date getCreateTime() {
        return this.createTime;
    }


    public void setCreateTime(java.util.Date value) {
        this.createTime = value;
    }


    public java.lang.String getCreateUserId() {
        return this.createUserId;
    }


    public void setCreateUserId(java.lang.String value) {
        this.createUserId = value;
    }


    public java.lang.String getUserCertLevel() {
        return this.userCertLevel;
    }


    public void setUserCertLevel(java.lang.String value) {
        this.userCertLevel = value;
    }


    public java.lang.String getApplyChnlId() {
        return this.applyChnlId;
    }


    public void setApplyChnlId(java.lang.String value) {
        this.applyChnlId = value;
    }


    public java.util.Date getUpdateTime() {
        return this.updateTime;
    }


    public void setUpdateTime(java.util.Date value) {
        this.updateTime = value;
    }
}
