package klp.com.animationdemo.data;

/**
 * Created by monkey on 17/3/1.
 */

public class ReqHeader {
    private String reqTime;
    private String version;

    public ReqHeader(String reqTime, String version) {
        this.reqTime = reqTime;
        this.version = version;
    }
}
