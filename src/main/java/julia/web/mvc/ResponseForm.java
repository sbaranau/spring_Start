package julia.web.mvc;


public class ResponseForm {

    private Object data;

    private Integer resultCode = ControllerConstants.RESULT_SUCCESS;

    public ResponseForm() {
    }

    public ResponseForm(Object data, Integer resultCode) {
        this.data = data;
        this.resultCode = resultCode;
    }

    public ResponseForm(Object data) {
        this.data = data;
        this.resultCode = ControllerConstants.RESULT_SUCCESS;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

}
