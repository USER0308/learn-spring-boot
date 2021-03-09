
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParse {



    public static void main(String[] args) {
        List<KeyValueStore> list = new ArrayList<>();
        list.add(new KeyValueStore("1", "001"));
        list.add(new KeyValueStore("2", "002"));
        list.add(new KeyValueStore("3", "003"));

        List<KeyAnotherValueStore> anotherList = new ArrayList<>();
        anotherList.add(new KeyAnotherValueStore("3", "10"));
        anotherList.add(new KeyAnotherValueStore("4", "10"));
        anotherList.add(new KeyAnotherValueStore("5", "10"));

        List<String> allFields = getFieldNames(list.get(0).getClass());
        allFields.addAll(getFieldNames(anotherList.get(0).getClass()));

        List<JSONObject> jsonObjects = new ArrayList<>();
        for (KeyValueStore keyValueStore : list) {
            Map<String, String> map = new HashMap<>();
//            map.put()
        }

    }

    private static List<String> getFieldNames(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<String> allFields = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            allFields.add(field.getName());
        }
        return allFields;
    }
}


@Data
@AllArgsConstructor
class KeyValueStore {
    String name;
    String No;

}

@Data
@AllArgsConstructor
class KeyAnotherValueStore {
    String name;
    String age;
}