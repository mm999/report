package com.weshare.thunder.model;

import java.util.ArrayList;
import java.util.List;

public class UserInfoQueryExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_info
     *
     * @mbggenerated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_info
     *
     * @mbggenerated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table user_info
     *
     * @mbggenerated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public UserInfoQueryExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_info
     *
     * @mbggenerated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("date is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("date is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Integer value) {
            addCriterion("date =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Integer value) {
            addCriterion("date <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Integer value) {
            addCriterion("date >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("date >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Integer value) {
            addCriterion("date <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Integer value) {
            addCriterion("date <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Integer> values) {
            addCriterion("date in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Integer> values) {
            addCriterion("date not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Integer value1, Integer value2) {
            addCriterion("date between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Integer value1, Integer value2) {
            addCriterion("date not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Integer value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Integer value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Integer value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Integer value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Integer value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Integer> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Integer> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Integer value1, Integer value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andNotActivitedIsNull() {
            addCriterion("not_activited is null");
            return (Criteria) this;
        }

        public Criteria andNotActivitedIsNotNull() {
            addCriterion("not_activited is not null");
            return (Criteria) this;
        }

        public Criteria andNotActivitedEqualTo(Integer value) {
            addCriterion("not_activited =", value, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedNotEqualTo(Integer value) {
            addCriterion("not_activited <>", value, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedGreaterThan(Integer value) {
            addCriterion("not_activited >", value, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedGreaterThanOrEqualTo(Integer value) {
            addCriterion("not_activited >=", value, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedLessThan(Integer value) {
            addCriterion("not_activited <", value, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedLessThanOrEqualTo(Integer value) {
            addCriterion("not_activited <=", value, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedIn(List<Integer> values) {
            addCriterion("not_activited in", values, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedNotIn(List<Integer> values) {
            addCriterion("not_activited not in", values, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedBetween(Integer value1, Integer value2) {
            addCriterion("not_activited between", value1, value2, "notActivited");
            return (Criteria) this;
        }

        public Criteria andNotActivitedNotBetween(Integer value1, Integer value2) {
            addCriterion("not_activited not between", value1, value2, "notActivited");
            return (Criteria) this;
        }

        public Criteria andActivitedIsNull() {
            addCriterion("activited is null");
            return (Criteria) this;
        }

        public Criteria andActivitedIsNotNull() {
            addCriterion("activited is not null");
            return (Criteria) this;
        }

        public Criteria andActivitedEqualTo(Integer value) {
            addCriterion("activited =", value, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedNotEqualTo(Integer value) {
            addCriterion("activited <>", value, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedGreaterThan(Integer value) {
            addCriterion("activited >", value, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedGreaterThanOrEqualTo(Integer value) {
            addCriterion("activited >=", value, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedLessThan(Integer value) {
            addCriterion("activited <", value, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedLessThanOrEqualTo(Integer value) {
            addCriterion("activited <=", value, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedIn(List<Integer> values) {
            addCriterion("activited in", values, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedNotIn(List<Integer> values) {
            addCriterion("activited not in", values, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedBetween(Integer value1, Integer value2) {
            addCriterion("activited between", value1, value2, "activited");
            return (Criteria) this;
        }

        public Criteria andActivitedNotBetween(Integer value1, Integer value2) {
            addCriterion("activited not between", value1, value2, "activited");
            return (Criteria) this;
        }

        public Criteria andBindMobileIsNull() {
            addCriterion("bind_mobile is null");
            return (Criteria) this;
        }

        public Criteria andBindMobileIsNotNull() {
            addCriterion("bind_mobile is not null");
            return (Criteria) this;
        }

        public Criteria andBindMobileEqualTo(Integer value) {
            addCriterion("bind_mobile =", value, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileNotEqualTo(Integer value) {
            addCriterion("bind_mobile <>", value, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileGreaterThan(Integer value) {
            addCriterion("bind_mobile >", value, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileGreaterThanOrEqualTo(Integer value) {
            addCriterion("bind_mobile >=", value, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileLessThan(Integer value) {
            addCriterion("bind_mobile <", value, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileLessThanOrEqualTo(Integer value) {
            addCriterion("bind_mobile <=", value, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileIn(List<Integer> values) {
            addCriterion("bind_mobile in", values, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileNotIn(List<Integer> values) {
            addCriterion("bind_mobile not in", values, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileBetween(Integer value1, Integer value2) {
            addCriterion("bind_mobile between", value1, value2, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andBindMobileNotBetween(Integer value1, Integer value2) {
            addCriterion("bind_mobile not between", value1, value2, "bindMobile");
            return (Criteria) this;
        }

        public Criteria andCertifiedIsNull() {
            addCriterion("certified is null");
            return (Criteria) this;
        }

        public Criteria andCertifiedIsNotNull() {
            addCriterion("certified is not null");
            return (Criteria) this;
        }

        public Criteria andCertifiedEqualTo(Integer value) {
            addCriterion("certified =", value, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedNotEqualTo(Integer value) {
            addCriterion("certified <>", value, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedGreaterThan(Integer value) {
            addCriterion("certified >", value, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedGreaterThanOrEqualTo(Integer value) {
            addCriterion("certified >=", value, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedLessThan(Integer value) {
            addCriterion("certified <", value, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedLessThanOrEqualTo(Integer value) {
            addCriterion("certified <=", value, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedIn(List<Integer> values) {
            addCriterion("certified in", values, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedNotIn(List<Integer> values) {
            addCriterion("certified not in", values, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedBetween(Integer value1, Integer value2) {
            addCriterion("certified between", value1, value2, "certified");
            return (Criteria) this;
        }

        public Criteria andCertifiedNotBetween(Integer value1, Integer value2) {
            addCriterion("certified not between", value1, value2, "certified");
            return (Criteria) this;
        }

        public Criteria andBindCardIsNull() {
            addCriterion("bind_card is null");
            return (Criteria) this;
        }

        public Criteria andBindCardIsNotNull() {
            addCriterion("bind_card is not null");
            return (Criteria) this;
        }

        public Criteria andBindCardEqualTo(Integer value) {
            addCriterion("bind_card =", value, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardNotEqualTo(Integer value) {
            addCriterion("bind_card <>", value, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardGreaterThan(Integer value) {
            addCriterion("bind_card >", value, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardGreaterThanOrEqualTo(Integer value) {
            addCriterion("bind_card >=", value, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardLessThan(Integer value) {
            addCriterion("bind_card <", value, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardLessThanOrEqualTo(Integer value) {
            addCriterion("bind_card <=", value, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardIn(List<Integer> values) {
            addCriterion("bind_card in", values, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardNotIn(List<Integer> values) {
            addCriterion("bind_card not in", values, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardBetween(Integer value1, Integer value2) {
            addCriterion("bind_card between", value1, value2, "bindCard");
            return (Criteria) this;
        }

        public Criteria andBindCardNotBetween(Integer value1, Integer value2) {
            addCriterion("bind_card not between", value1, value2, "bindCard");
            return (Criteria) this;
        }

        public Criteria andDepositedIsNull() {
            addCriterion("deposited is null");
            return (Criteria) this;
        }

        public Criteria andDepositedIsNotNull() {
            addCriterion("deposited is not null");
            return (Criteria) this;
        }

        public Criteria andDepositedEqualTo(Integer value) {
            addCriterion("deposited =", value, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedNotEqualTo(Integer value) {
            addCriterion("deposited <>", value, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedGreaterThan(Integer value) {
            addCriterion("deposited >", value, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedGreaterThanOrEqualTo(Integer value) {
            addCriterion("deposited >=", value, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedLessThan(Integer value) {
            addCriterion("deposited <", value, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedLessThanOrEqualTo(Integer value) {
            addCriterion("deposited <=", value, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedIn(List<Integer> values) {
            addCriterion("deposited in", values, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedNotIn(List<Integer> values) {
            addCriterion("deposited not in", values, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedBetween(Integer value1, Integer value2) {
            addCriterion("deposited between", value1, value2, "deposited");
            return (Criteria) this;
        }

        public Criteria andDepositedNotBetween(Integer value1, Integer value2) {
            addCriterion("deposited not between", value1, value2, "deposited");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalIsNull() {
            addCriterion("not_follow_total is null");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalIsNotNull() {
            addCriterion("not_follow_total is not null");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalEqualTo(Integer value) {
            addCriterion("not_follow_total =", value, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalNotEqualTo(Integer value) {
            addCriterion("not_follow_total <>", value, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalGreaterThan(Integer value) {
            addCriterion("not_follow_total >", value, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("not_follow_total >=", value, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalLessThan(Integer value) {
            addCriterion("not_follow_total <", value, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalLessThanOrEqualTo(Integer value) {
            addCriterion("not_follow_total <=", value, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalIn(List<Integer> values) {
            addCriterion("not_follow_total in", values, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalNotIn(List<Integer> values) {
            addCriterion("not_follow_total not in", values, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalBetween(Integer value1, Integer value2) {
            addCriterion("not_follow_total between", value1, value2, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andNotFollowTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("not_follow_total not between", value1, value2, "notFollowTotal");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesIsNull() {
            addCriterion("total_finance_peoples is null");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesIsNotNull() {
            addCriterion("total_finance_peoples is not null");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesEqualTo(Integer value) {
            addCriterion("total_finance_peoples =", value, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesNotEqualTo(Integer value) {
            addCriterion("total_finance_peoples <>", value, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesGreaterThan(Integer value) {
            addCriterion("total_finance_peoples >", value, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_finance_peoples >=", value, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesLessThan(Integer value) {
            addCriterion("total_finance_peoples <", value, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesLessThanOrEqualTo(Integer value) {
            addCriterion("total_finance_peoples <=", value, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesIn(List<Integer> values) {
            addCriterion("total_finance_peoples in", values, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesNotIn(List<Integer> values) {
            addCriterion("total_finance_peoples not in", values, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesBetween(Integer value1, Integer value2) {
            addCriterion("total_finance_peoples between", value1, value2, "totalFinancePeoples");
            return (Criteria) this;
        }

        public Criteria andTotalFinancePeoplesNotBetween(Integer value1, Integer value2) {
            addCriterion("total_finance_peoples not between", value1, value2, "totalFinancePeoples");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_info
     *
     * @mbggenerated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table user_info
     *
     * @mbggenerated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}