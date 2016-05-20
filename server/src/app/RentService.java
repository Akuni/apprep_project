package app;

/**
 * Created by SARROCHE Nicolas on 20/05/16.
 */
public class RentService implements IService {

    static final long serialVersionUID = 1L;
    private String teamName;

    public RentService(String name){
        teamName = name;
    }


    @Override
    public String getInfo() {
        return "This service is brought to you by " + teamName;
    }
}
