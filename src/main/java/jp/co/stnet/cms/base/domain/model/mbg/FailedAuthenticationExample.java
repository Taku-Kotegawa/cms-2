package jp.co.stnet.cms.base.domain.model.mbg;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FailedAuthenticationExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public FailedAuthenticationExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
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
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampIsNull() {
            addCriterion("authentication_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampIsNotNull() {
            addCriterion("authentication_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampEqualTo(LocalDateTime value) {
            addCriterion("authentication_timestamp =", value, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampNotEqualTo(LocalDateTime value) {
            addCriterion("authentication_timestamp <>", value, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampGreaterThan(LocalDateTime value) {
            addCriterion("authentication_timestamp >", value, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("authentication_timestamp >=", value, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampLessThan(LocalDateTime value) {
            addCriterion("authentication_timestamp <", value, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("authentication_timestamp <=", value, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampIn(List<LocalDateTime> values) {
            addCriterion("authentication_timestamp in", values, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampNotIn(List<LocalDateTime> values) {
            addCriterion("authentication_timestamp not in", values, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("authentication_timestamp between", value1, value2, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andAuthenticationTimestampNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("authentication_timestamp not between", value1, value2, "authenticationTimestamp");
            return (Criteria) this;
        }

        public Criteria andDummyIsNull() {
            addCriterion("dummy is null");
            return (Criteria) this;
        }

        public Criteria andDummyIsNotNull() {
            addCriterion("dummy is not null");
            return (Criteria) this;
        }

        public Criteria andDummyEqualTo(String value) {
            addCriterion("dummy =", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyNotEqualTo(String value) {
            addCriterion("dummy <>", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyGreaterThan(String value) {
            addCriterion("dummy >", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyGreaterThanOrEqualTo(String value) {
            addCriterion("dummy >=", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyLessThan(String value) {
            addCriterion("dummy <", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyLessThanOrEqualTo(String value) {
            addCriterion("dummy <=", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyLike(String value) {
            addCriterion("dummy like", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyNotLike(String value) {
            addCriterion("dummy not like", value, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyIn(List<String> values) {
            addCriterion("dummy in", values, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyNotIn(List<String> values) {
            addCriterion("dummy not in", values, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyBetween(String value1, String value2) {
            addCriterion("dummy between", value1, value2, "dummy");
            return (Criteria) this;
        }

        public Criteria andDummyNotBetween(String value1, String value2) {
            addCriterion("dummy not between", value1, value2, "dummy");
            return (Criteria) this;
        }

        public Criteria andUsernameLikeInsensitive(String value) {
            addCriterion("upper(username) like", value.toUpperCase(), "username");
            return (Criteria) this;
        }

        public Criteria andDummyLikeInsensitive(String value) {
            addCriterion("upper(dummy) like", value.toUpperCase(), "dummy");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_failed_authentication
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_failed_authentication
     *
     * @mbg.generated
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