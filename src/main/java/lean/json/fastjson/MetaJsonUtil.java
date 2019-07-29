package lean.json.fastjson;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * fastJson不支持对象中私有属性的转换
 */
public final class MetaJsonUtil {

    /**
     * 获取对象对应属性数据组成的JSON对象
     * @param target
     * @return
     */
    public static JSONObject getJSONObject(Object target) {
        return JSON.parseObject(getJSONString(target));
    }

    /**
     * 获取对象对应属性数据组成的字符串
     * @param target
     * @return
     */
    public static String getJSONString(Object target) {
        if (target != null) {
            SerializeConfig globalInstance = SerializeConfig.getGlobalInstance();
//            globalInstance.put(target.getClass(), new MetaBeanSerializer(target.getClass()));
            return JSON.toJSONString(target, globalInstance);
        }else{
            return "{}";
        }
    }
}