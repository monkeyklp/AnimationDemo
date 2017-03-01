package klp.chebada.com.animationdemo.data;

/**
 * Created by monkey on 17/3/1.
 */

public class Request<T> {
    private ReqHeader header;
    private T body;

    public Request(String version, T body) {
        this.header = new ReqHeader(String.valueOf(System.currentTimeMillis()), version);
        this.body = body;
    }
}
