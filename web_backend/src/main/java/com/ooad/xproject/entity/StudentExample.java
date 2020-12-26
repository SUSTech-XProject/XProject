package com.ooad.xproject.entity;

import java.util.ArrayList;
import java.util.List;

public class StudentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudentExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        public Criteria andStdIdIsNull() {
            addCriterion("std_id is null");
            return (Criteria) this;
        }

        public Criteria andStdIdIsNotNull() {
            addCriterion("std_id is not null");
            return (Criteria) this;
        }

        public Criteria andStdIdEqualTo(Integer value) {
            addCriterion("std_id =", value, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdNotEqualTo(Integer value) {
            addCriterion("std_id <>", value, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdGreaterThan(Integer value) {
            addCriterion("std_id >", value, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("std_id >=", value, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdLessThan(Integer value) {
            addCriterion("std_id <", value, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdLessThanOrEqualTo(Integer value) {
            addCriterion("std_id <=", value, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdIn(List<Integer> values) {
            addCriterion("std_id in", values, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdNotIn(List<Integer> values) {
            addCriterion("std_id not in", values, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdBetween(Integer value1, Integer value2) {
            addCriterion("std_id between", value1, value2, "stdId");
            return (Criteria) this;
        }

        public Criteria andStdIdNotBetween(Integer value1, Integer value2) {
            addCriterion("std_id not between", value1, value2, "stdId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("role_id is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("role_id is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(Integer value) {
            addCriterion("role_id =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(Integer value) {
            addCriterion("role_id <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(Integer value) {
            addCriterion("role_id >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("role_id >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(Integer value) {
            addCriterion("role_id <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(Integer value) {
            addCriterion("role_id <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<Integer> values) {
            addCriterion("role_id in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<Integer> values) {
            addCriterion("role_id not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(Integer value1, Integer value2) {
            addCriterion("role_id between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(Integer value1, Integer value2) {
            addCriterion("role_id not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andStdNameIsNull() {
            addCriterion("std_name is null");
            return (Criteria) this;
        }

        public Criteria andStdNameIsNotNull() {
            addCriterion("std_name is not null");
            return (Criteria) this;
        }

        public Criteria andStdNameEqualTo(String value) {
            addCriterion("std_name =", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameNotEqualTo(String value) {
            addCriterion("std_name <>", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameGreaterThan(String value) {
            addCriterion("std_name >", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameGreaterThanOrEqualTo(String value) {
            addCriterion("std_name >=", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameLessThan(String value) {
            addCriterion("std_name <", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameLessThanOrEqualTo(String value) {
            addCriterion("std_name <=", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameLike(String value) {
            addCriterion("std_name like", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameNotLike(String value) {
            addCriterion("std_name not like", value, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameIn(List<String> values) {
            addCriterion("std_name in", values, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameNotIn(List<String> values) {
            addCriterion("std_name not in", values, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameBetween(String value1, String value2) {
            addCriterion("std_name between", value1, value2, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNameNotBetween(String value1, String value2) {
            addCriterion("std_name not between", value1, value2, "stdName");
            return (Criteria) this;
        }

        public Criteria andStdNoIsNull() {
            addCriterion("std_no is null");
            return (Criteria) this;
        }

        public Criteria andStdNoIsNotNull() {
            addCriterion("std_no is not null");
            return (Criteria) this;
        }

        public Criteria andStdNoEqualTo(String value) {
            addCriterion("std_no =", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoNotEqualTo(String value) {
            addCriterion("std_no <>", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoGreaterThan(String value) {
            addCriterion("std_no >", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoGreaterThanOrEqualTo(String value) {
            addCriterion("std_no >=", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoLessThan(String value) {
            addCriterion("std_no <", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoLessThanOrEqualTo(String value) {
            addCriterion("std_no <=", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoLike(String value) {
            addCriterion("std_no like", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoNotLike(String value) {
            addCriterion("std_no not like", value, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoIn(List<String> values) {
            addCriterion("std_no in", values, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoNotIn(List<String> values) {
            addCriterion("std_no not in", values, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoBetween(String value1, String value2) {
            addCriterion("std_no between", value1, value2, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdNoNotBetween(String value1, String value2) {
            addCriterion("std_no not between", value1, value2, "stdNo");
            return (Criteria) this;
        }

        public Criteria andStdClassIsNull() {
            addCriterion("std_class is null");
            return (Criteria) this;
        }

        public Criteria andStdClassIsNotNull() {
            addCriterion("std_class is not null");
            return (Criteria) this;
        }

        public Criteria andStdClassEqualTo(String value) {
            addCriterion("std_class =", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassNotEqualTo(String value) {
            addCriterion("std_class <>", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassGreaterThan(String value) {
            addCriterion("std_class >", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassGreaterThanOrEqualTo(String value) {
            addCriterion("std_class >=", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassLessThan(String value) {
            addCriterion("std_class <", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassLessThanOrEqualTo(String value) {
            addCriterion("std_class <=", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassLike(String value) {
            addCriterion("std_class like", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassNotLike(String value) {
            addCriterion("std_class not like", value, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassIn(List<String> values) {
            addCriterion("std_class in", values, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassNotIn(List<String> values) {
            addCriterion("std_class not in", values, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassBetween(String value1, String value2) {
            addCriterion("std_class between", value1, value2, "stdClass");
            return (Criteria) this;
        }

        public Criteria andStdClassNotBetween(String value1, String value2) {
            addCriterion("std_class not between", value1, value2, "stdClass");
            return (Criteria) this;
        }

        public Criteria andFlagsIsNull() {
            addCriterion("flags is null");
            return (Criteria) this;
        }

        public Criteria andFlagsIsNotNull() {
            addCriterion("flags is not null");
            return (Criteria) this;
        }

        public Criteria andFlagsEqualTo(String value) {
            addCriterion("flags =", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsNotEqualTo(String value) {
            addCriterion("flags <>", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsGreaterThan(String value) {
            addCriterion("flags >", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsGreaterThanOrEqualTo(String value) {
            addCriterion("flags >=", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsLessThan(String value) {
            addCriterion("flags <", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsLessThanOrEqualTo(String value) {
            addCriterion("flags <=", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsLike(String value) {
            addCriterion("flags like", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsNotLike(String value) {
            addCriterion("flags not like", value, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsIn(List<String> values) {
            addCriterion("flags in", values, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsNotIn(List<String> values) {
            addCriterion("flags not in", values, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsBetween(String value1, String value2) {
            addCriterion("flags between", value1, value2, "flags");
            return (Criteria) this;
        }

        public Criteria andFlagsNotBetween(String value1, String value2) {
            addCriterion("flags not between", value1, value2, "flags");
            return (Criteria) this;
        }

        public Criteria andSchIdIsNull() {
            addCriterion("sch_id is null");
            return (Criteria) this;
        }

        public Criteria andSchIdIsNotNull() {
            addCriterion("sch_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchIdEqualTo(Integer value) {
            addCriterion("sch_id =", value, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdNotEqualTo(Integer value) {
            addCriterion("sch_id <>", value, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdGreaterThan(Integer value) {
            addCriterion("sch_id >", value, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sch_id >=", value, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdLessThan(Integer value) {
            addCriterion("sch_id <", value, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdLessThanOrEqualTo(Integer value) {
            addCriterion("sch_id <=", value, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdIn(List<Integer> values) {
            addCriterion("sch_id in", values, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdNotIn(List<Integer> values) {
            addCriterion("sch_id not in", values, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdBetween(Integer value1, Integer value2) {
            addCriterion("sch_id between", value1, value2, "schId");
            return (Criteria) this;
        }

        public Criteria andSchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sch_id not between", value1, value2, "schId");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andSkillsIsNull() {
            addCriterion("skills is null");
            return (Criteria) this;
        }

        public Criteria andSkillsIsNotNull() {
            addCriterion("skills is not null");
            return (Criteria) this;
        }

        public Criteria andSkillsEqualTo(String value) {
            addCriterion("skills =", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsNotEqualTo(String value) {
            addCriterion("skills <>", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsGreaterThan(String value) {
            addCriterion("skills >", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsGreaterThanOrEqualTo(String value) {
            addCriterion("skills >=", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsLessThan(String value) {
            addCriterion("skills <", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsLessThanOrEqualTo(String value) {
            addCriterion("skills <=", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsLike(String value) {
            addCriterion("skills like", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsNotLike(String value) {
            addCriterion("skills not like", value, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsIn(List<String> values) {
            addCriterion("skills in", values, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsNotIn(List<String> values) {
            addCriterion("skills not in", values, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsBetween(String value1, String value2) {
            addCriterion("skills between", value1, value2, "skills");
            return (Criteria) this;
        }

        public Criteria andSkillsNotBetween(String value1, String value2) {
            addCriterion("skills not between", value1, value2, "skills");
            return (Criteria) this;
        }

        public Criteria andBioIsNull() {
            addCriterion("bio is null");
            return (Criteria) this;
        }

        public Criteria andBioIsNotNull() {
            addCriterion("bio is not null");
            return (Criteria) this;
        }

        public Criteria andBioEqualTo(String value) {
            addCriterion("bio =", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioNotEqualTo(String value) {
            addCriterion("bio <>", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioGreaterThan(String value) {
            addCriterion("bio >", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioGreaterThanOrEqualTo(String value) {
            addCriterion("bio >=", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioLessThan(String value) {
            addCriterion("bio <", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioLessThanOrEqualTo(String value) {
            addCriterion("bio <=", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioLike(String value) {
            addCriterion("bio like", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioNotLike(String value) {
            addCriterion("bio not like", value, "bio");
            return (Criteria) this;
        }

        public Criteria andBioIn(List<String> values) {
            addCriterion("bio in", values, "bio");
            return (Criteria) this;
        }

        public Criteria andBioNotIn(List<String> values) {
            addCriterion("bio not in", values, "bio");
            return (Criteria) this;
        }

        public Criteria andBioBetween(String value1, String value2) {
            addCriterion("bio between", value1, value2, "bio");
            return (Criteria) this;
        }

        public Criteria andBioNotBetween(String value1, String value2) {
            addCriterion("bio not between", value1, value2, "bio");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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