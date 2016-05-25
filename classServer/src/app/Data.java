package app;

/**
 * Created by SARROCHE Nicolas on 10/05/16.
 */
public class Data implements IData {

    private static final long serialVersionUID = 1L;
    private String data;

    public Data(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String getDataAsString() {
        return data;
    }
}
