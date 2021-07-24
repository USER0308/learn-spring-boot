import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class JsonParse {



    public static void main(String[] args) {
//        boolean a = true;
//        JSONObject.parse("index");
        S s = new S();
        s.set("aa", "bb");
        JSONObject.parse(s.toString());


    }

}

class S implements Serializable {
    private String name;
    private String age;

    public void set(String name, String age) {
        this.name = name;
        this.age = age;
    }
    public S() {

    }

    @Override
    public String toString() {
        return name + age;
    }
}