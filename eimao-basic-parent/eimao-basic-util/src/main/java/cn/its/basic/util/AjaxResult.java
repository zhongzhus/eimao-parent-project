package cn.its.basic.util;

public class AjaxResult {
    private boolean success=true;
    private String msg = "";
    private Integer stateCode = null;
    private  Object object;

    private AjaxResult(){}

    public static AjaxResult me(){
        return new AjaxResult();
    }

    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public AjaxResult setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
        return this;
    }

    public Object getObject() {
        return object;
    }

    public AjaxResult setObject(Object object) {
        this.object = object;
        return  this;
    }
}
