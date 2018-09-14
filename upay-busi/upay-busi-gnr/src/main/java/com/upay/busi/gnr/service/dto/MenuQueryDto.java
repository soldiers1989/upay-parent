package com.upay.busi.gnr.service.dto;

import java.util.List;
import java.util.Map;

import com.upay.commons.dto.BaseDto;


/**
 * 菜单查询Dto
 * 
 * @author liyulong
 * 
 */
public class MenuQueryDto extends BaseDto {

    // private String menuId;

    private String userCertLevel;

    // private String applyChnlId;

    private List<Map<String, Object>> rows;


    public List<Map<String, Object>> getRows() {
        return rows;
    }


    public void setRows(List<Map<String, Object>> rows) {
        this.rows = rows;
    }


    // public String getApplyChnlId() {
    // return applyChnlId;
    // }
    //
    //
    // public void setApplyChnlId(String applyChnlId) {
    // this.applyChnlId = applyChnlId;
    // }
    //
    //
    // public String getMenuId() {
    // return menuId;
    // }
    //
    //
    // public void setMenuId(String menuId) {
    // this.menuId = menuId;
    // }

    public String getUserCertLevel() {
        return userCertLevel;
    }


    public void setUserCertLevel(String userCertLevel) {
        this.userCertLevel = userCertLevel;
    }

    public class MenuQuerySubDto {

        private List<Map<String, Object>> rows;

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


        public java.lang.String getMenuId() {
            return menuId;
        }


        public void setMenuId(java.lang.String menuId) {
            this.menuId = menuId;
        }


        public java.lang.String getMenuName() {
            return menuName;
        }


        public void setMenuName(java.lang.String menuName) {
            this.menuName = menuName;
        }


        public java.lang.String getMenuType() {
            return menuType;
        }


        public void setMenuType(java.lang.String menuType) {
            this.menuType = menuType;
        }


        public java.lang.String getParentId() {
            return parentId;
        }


        public void setParentId(java.lang.String parentId) {
            this.parentId = parentId;
        }


        public java.lang.String getIconCls() {
            return iconCls;
        }


        public void setIconCls(java.lang.String iconCls) {
            this.iconCls = iconCls;
        }


        public java.lang.String getExpanded() {
            return expanded;
        }


        public void setExpanded(java.lang.String expanded) {
            this.expanded = expanded;
        }


        public java.lang.String getMenuAddr() {
            return menuAddr;
        }


        public void setMenuAddr(java.lang.String menuAddr) {
            this.menuAddr = menuAddr;
        }


        public java.lang.String getLeaf() {
            return leaf;
        }


        public void setLeaf(java.lang.String leaf) {
            this.leaf = leaf;
        }


        public Integer getSortNo() {
            return sortNo;
        }


        public void setSortNo(Integer sortNo) {
            this.sortNo = sortNo;
        }


        public java.lang.String getRemark() {
            return remark;
        }


        public void setRemark(java.lang.String remark) {
            this.remark = remark;
        }


        public java.lang.String getIcon() {
            return icon;
        }


        public void setIcon(java.lang.String icon) {
            this.icon = icon;
        }


        public java.lang.String getRemoveFlag() {
            return removeFlag;
        }


        public void setRemoveFlag(java.lang.String removeFlag) {
            this.removeFlag = removeFlag;
        }


        public java.util.Date getRemoveTime() {
            return removeTime;
        }


        public void setRemoveTime(java.util.Date removeTime) {
            this.removeTime = removeTime;
        }


        public java.util.Date getCreateTime() {
            return createTime;
        }


        public void setCreateTime(java.util.Date createTime) {
            this.createTime = createTime;
        }


        public java.lang.String getCreateUserId() {
            return createUserId;
        }


        public void setCreateUserId(java.lang.String createUserId) {
            this.createUserId = createUserId;
        }


        public List<Map<String, Object>> getRows() {
            return rows;
        }


        public void setRows(List<Map<String, Object>> rows) {
            this.rows = rows;
        }

    }

}
