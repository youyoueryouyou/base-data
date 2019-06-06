package com.you.base;

import java.util.ArrayList;
import java.util.List;


/**
 * @author shicz
 */
public class BaseCondition {
    private final String LEFT_BRACKET = "(";
    private final String RIGHT_BRACKET = ")";
    private final String EMPTY_SIGN = " ";


    private Boolean distinct;
    private String tableName;
    private Boolean unionTable = false;

    private String orderByClause;
    private List<ColumnMap> columns;
    
    protected List<Criteria> oredCriteria;
    
    private Integer offset;
    
    private Integer rows;

    private Criteria currentCriteria;
    private BaseModel model;
    private BaseCondition(){

    }

    protected void setCurrentCriteria(Criteria currentCriteria) {
        this.currentCriteria = currentCriteria;
    }

    protected BaseCondition(BaseModel model) throws Exception{
        this.model = model;
        oredCriteria = new ArrayList<Criteria>();
        initByModel(model);
    }

    public BaseModel getModel(){
        return model;
    }

    public String getOrderByClause()
    {
        return orderByClause;
    }
    
    public void setOrderByClause(String orderByClause)
    {
        this.orderByClause = orderByClause;
    }
    
    public Boolean getDistinct()
    {
        return distinct;
    }
    
    public void setDistinct(Boolean distinct)
    {
        this.distinct = distinct;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getRows()
    {
        return rows;
    }
    
    public void setRows(Integer rows)
    {
        this.rows = rows;
    }

    public void setPage(Integer offset,Integer rows){
        this.offset = offset;
        this.rows = rows;
    }

    protected List<ColumnMap> getColumns() {
        return columns;
    }

    protected void setColumns(List<ColumnMap> columns) {
        this.columns = columns;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        if (this.tableName == null || "".equals(this.tableName))
        {
            this.tableName = tableName;
        }
        else if (!tableName.trim().startsWith(LEFT_BRACKET) && tableName.split(EMPTY_SIGN).length != 1)
        {
            this.tableName = LEFT_BRACKET + tableName + RIGHT_BRACKET +" as " + this.tableName;
            this.unionTable = true;
        }
        else
        {
            this.tableName = tableName;
            this.unionTable = true;
        }
    }

    protected void initByModel(BaseModel model) throws Exception{
        if (model.getColumns() == null){
            model.initColumn();
        }
        Criteria criteria = getCriteria();
        for (ColumnMap map : model.getColumns()){
            if (map.getColumnValue() != null){
                criteria.andValueEqualTo(map.getColumnName(),map.getColumnValue());
            }
        }
        if (model.getParams() != null){
            for (BaseParam param : model.getParams()){
                if (param.getColumn() != null && !"".equals(param.getColumn()) && param.getOperator() != null){
                    String cloumn = null;
                    for (ColumnMap map : columns){
                        if (param.getColumn().equalsIgnoreCase(map.getFieldName())||param.getColumn().equalsIgnoreCase(map.getColumnName())){
                            cloumn = map.getColumnName();
                            break;
                        }
                    }
                    if (cloumn == null){
                        continue;
                    }else {
                        cloumn = criteria.getKey(cloumn);
                    }
                    if (param.getValue() != null && !"".equals(param.getValue().toString().trim())){
                        if (param.getOperator() == Operator.LK || param.getOperator() == Operator.NLK){
                            criteria.addAndCriterion(cloumn + " "+param.getOperator().getValue(),"%"+param.getValue().toString()+"%",cloumn);
                        } else {
                            criteria.addAndCriterion(cloumn + " "+param.getOperator().getValue(),param.getValue(),cloumn);
                        }
                    }else if (param.getOperator() == Operator.EP || param.getOperator() == Operator.NEP){
                        criteria.addAndCriterion(cloumn + " "+param.getOperator().getValue());
                    } 
                }
            }
        }
        if (criteria.isValid()){
            oredCriteria.add(criteria);
        }
        setTableName(model.getTableName());
        setColumns(model.getColumns());
        if (model.getPageNo() != null && model.getPageNo() > 0 && model.getPageSize() != null  && model.getPageSize() > 0)
        {
            setOffset((model.getPageNo() - 1) * model.getPageSize());
            setRows(model.getPageSize());
        }
        if (model.getOrderKey() != null && !"".equals(model.getOrderKey())){
            String orderKey = null;
            for (ColumnMap map : columns){
                if (model.getOrderKey().equalsIgnoreCase(map.getFieldName())||model.getOrderKey().equalsIgnoreCase(map.getColumnName())){
                    orderKey = map.getColumnName();
                    break;
                }
            }
            String orderType = model.getOrderType() == null ? "" : ("desc".equals(model.getOrderType().trim().toLowerCase()) ? "desc" : "");
            if (orderKey != null){
                setOrderByClause(orderKey+" "+orderType);
            }
        }
    }
    
    public Boolean addColumn(String name)
    {
        List<ColumnMap> columns = getColumns();
        if (columns == null || name == null || "".equals(name))
        {
            return false;
        }
        else
        {
            Boolean result = true;
            for (ColumnMap columnMap : columns)
            {
                if (name.equals(columnMap.getColumnName()))
                {
                    result = false;
                    break;
                }
            }
            if (result)
            {
                ColumnMap columnMap = new ColumnMap();
                columnMap.setColumnName(name);
                columns.add(columnMap);
            }
            return result;
        }
    }


    protected void setOredCriteria(List<Criteria> oredCriteria) {
        this.oredCriteria = oredCriteria;
    }


    protected List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public Criteria getCriteria() {
        if (currentCriteria == null) {
           currentCriteria = createAndCriteriaInternal();
        }
        return currentCriteria;
    }

    protected Criteria or() {
        Criteria criteria = createOrCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    protected Criteria and() {
        Criteria criteria = createAndCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }


    protected Criteria createAndCriteriaInternal() {
        Criteria criteria = new Criteria("and");
        return criteria;
    }

    protected Criteria createOrCriteriaInternal() {
        Criteria criteria = new Criteria("or");
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        rows = null;
        offset = null;
    }

    public static class Criteria extends AbstractGeneratedCriteria
    {
        
        protected Criteria(String typeHandler)
        {
            super(typeHandler);
        }
    }
    
    public static class Criterion
    {
        private String condition;
        
        private Object value;
        
        private Object secondValue;
        
        private boolean noValue;
        
        private boolean singleValue;
        
        private boolean betweenValue;
        
        private boolean listValue;
        
        private String typeHandler;
        
        public String getCondition()
        {
            return condition;
        }
        
        public Object getValue()
        {
            return value;
        }
        
        public Object getSecondValue()
        {
            return secondValue;
        }
        
        public boolean isNoValue()
        {
            return noValue;
        }
        
        public boolean isSingleValue()
        {
            return singleValue;
        }
        
        public boolean isBetweenValue()
        {
            return betweenValue;
        }
        
        public boolean isListValue()
        {
            return listValue;
        }
        
        public String getTypeHandler()
        {
            return typeHandler;
        }
        
        protected Criterion(String condition,String typeHandler)
        {
            super();
            this.condition = condition;
            this.typeHandler = typeHandler;
            this.noValue = true;
        }


        protected Criterion(String condition, Object value, String typeHandler)
        {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>)
            {
                this.listValue = true;
            }
            else
            {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler)
        {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

    }
    
    public static abstract class AbstractGeneratedCriteria
    {
        private final String REVERSE_QUOTATION = "`";
        private final String PERCENT_SIGN  = "%";
        protected List<Criterion> criteria;
        private String typeHandler;
        
        protected AbstractGeneratedCriteria(String typeHandler)
        {
            super();
            this.typeHandler = typeHandler;
            criteria = new ArrayList<Criterion>();
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        public void setTypeHandler(String typeHandler) {
            this.typeHandler = typeHandler;
        }

        public boolean isValid()
        {
            return criteria.size() > 0;
        }
        
        public List<Criterion> getAllCriteria()
        {
            return criteria;
        }

        public void removeByCondition(String newCond){
            int index = -1;
            for (int i = 0; i < getAllCriteria().size();i++){
              Criterion  criterion = getAllCriteria().get(i);
              String oldCond =  criterion.getCondition();
              if (newCond.trim().replace(" ","").equals(oldCond.trim().replace(" ",""))){
                  index = i;
              }
            }
            if (index > -1){
                getAllCriteria().remove(index);
            }
        }

        public List<Criterion> getCriteria()
        {
            return criteria;
        }
        
        protected void addAndCriterion(String condition)
    {
        if (condition == null)
        {
            return;
        }
        criteria.add(new Criterion(condition, "and"));
    }

        protected void addAndCriterion(String condition, Object value, String property)
        {
            if (value == null)
            {
                return;
            }
            criteria.add(new Criterion(condition, value, "and"));
        }

        protected void addAndCriterion(String condition, Object value1, Object value2, String property)
        {
            if (value1 == null || value2 == null)
            {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2, "and"));
        }


        protected void addOrCriterion(String condition)
        {
            if (condition == null)
            {
                return;
            }
            criteria.add(new Criterion(condition, "or"));
        }

        protected void addOrCriterion(String condition, Object value, String property)
        {
            if (value == null)
            {
                return;
            }
            criteria.add(new Criterion(condition, value, "or"));
        }

        protected void addOrCriterion(String condition, Object value1, Object value2, String property)
        {
            if (value1 == null || value2 == null)
            {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2, "or"));
        }
        
        protected String getKey(String key){
            if (!key.startsWith(REVERSE_QUOTATION) && !key.endsWith(REVERSE_QUOTATION)){
                return REVERSE_QUOTATION + key + REVERSE_QUOTATION;
            }else {
                return key;
            }
        }
        
        public Criteria andValueIsNull(String key)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " is null");
            return (Criteria)this;
        }
        
        public Criteria andValueIsNotNull(String key)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " is not null");
            return (Criteria)this;
        }
        
        public Criteria andValueEqualTo(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " =", value, key);
            return (Criteria)this;
        }
        
        public Criteria andValueNotEqualTo(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " <>", value, key);
            return (Criteria)this;
        }

        public Criteria andValueLike(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            if (value != null && !value.toString().startsWith(PERCENT_SIGN) &&  !value.toString().startsWith(PERCENT_SIGN)){
                value = PERCENT_SIGN + value + PERCENT_SIGN ;
            }
            addAndCriterion(key + " like", value, key);
            return (Criteria)this;
        }

        public Criteria andValueNotLike(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            if (value != null && !value.toString().startsWith(PERCENT_SIGN) &&  !value.toString().startsWith(PERCENT_SIGN)){
                value = PERCENT_SIGN + value + PERCENT_SIGN ;
            }
            addAndCriterion(key + " not like", value, key);
            return (Criteria)this;
        }
        
        public Criteria andValueGreaterThan(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " >", value, key);
            return (Criteria)this;
        }
        
        public Criteria andValueGreaterThanOrEqualTo(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " >=", value, key);
            return (Criteria)this;
        }
        
        public Criteria andValueLessThan(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " <", value, key);
            return (Criteria)this;
        }
        
        public Criteria andValueLessThanOrEqualTo(String key, Object value)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " <=", value, key);
            return (Criteria)this;
        }
        
        public Criteria andValueIn(String key, List<Object> values)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " in", values, key);
            return (Criteria)this;
        }
        
        public Criteria andValueNotIn(String key, List<Object> values)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " not in", values, key);
            return (Criteria)this;
        }
        
        public Criteria andValueBetween(String key, Object value1, Object value2)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " between", value1, value2, key);
            return (Criteria)this;
        }
        
        public Criteria andValueNotBetween(String key, Object value1, Object value2)
        {
            key = getKey(key);
            removeByCondition(key + " =");
            addAndCriterion(key + " not between", value1, value2, key);
            return (Criteria)this;
        }



        public Criteria orValueIsNull(String key)
        {
            key = getKey(key);
            addOrCriterion(key + " is null");
            return (Criteria)this;
        }

        public Criteria orValueIsNotNull(String key)
        {
            key = getKey(key);
            addOrCriterion(key + " is not null");
            return (Criteria)this;
        }

        public Criteria orValueEqualTo(String key, Object value)
        {
            key = getKey(key);
            addOrCriterion(key + " =", value, key);
            return (Criteria)this;
        }

        public Criteria orValueNotEqualTo(String key, Object value)
        {
            key = getKey(key);
            addOrCriterion(key + " <>", value, key);
            return (Criteria)this;
        }

        public Criteria orValueLike(String key, Object value)
        {
            key = getKey(key);
            if (value != null && !value.toString().startsWith(PERCENT_SIGN) &&  !value.toString().startsWith(PERCENT_SIGN)){
                value = PERCENT_SIGN + value + PERCENT_SIGN ;
            }
            addAndCriterion(key + " like", value, key);
            return (Criteria)this;
        }

        public Criteria orValueNotLike(String key, Object value)
        {
            key = getKey(key);
            if (value != null && !value.toString().startsWith(PERCENT_SIGN) &&  !value.toString().startsWith(PERCENT_SIGN)){
                value = PERCENT_SIGN + value + PERCENT_SIGN ;
            }
            addAndCriterion(key + " not like", value, key);
            return (Criteria)this;
        }

        public Criteria orValueGreaterThan(String key, Object value)
        {
            key = getKey(key);
            addOrCriterion(key + " >", value, key);
            return (Criteria)this;
        }

        public Criteria orValueGreaterThanOrEqualTo(String key, Object value)
        {
            key = getKey(key);
            addOrCriterion(key + " >=", value, key);
            return (Criteria)this;
        }

        public Criteria orValueLessThan(String key, Object value)
        {
            key = getKey(key);
            addOrCriterion(key + " <", value, key);
            return (Criteria)this;
        }

        public Criteria orValueLessThanOrEqualTo(String key, Object value)
        {
            key = getKey(key);
            addOrCriterion(key + " <=", value, key);
            return (Criteria)this;
        }

        public Criteria orValueIn(String key, List<Object> values)
        {
            key = getKey(key);
            addOrCriterion(key + " in", values, key);
            return (Criteria)this;
        }

        public Criteria orValueNotIn(String key, List<Object> values)
        {
            key = getKey(key);
            addOrCriterion(key + " not in", values, key);
            return (Criteria)this;
        }

        public Criteria orValueBetween(String key, Object value1, Object value2)
        {
            key = getKey(key);
            addOrCriterion(key + " between", value1, value2, key);
            return (Criteria)this;
        }

        public Criteria orValueNotBetween(String key, Object value1, Object value2)
        {
            key = getKey(key);
            addOrCriterion(key + " not between", value1, value2, key);
            return (Criteria)this;
        }
    }
}

