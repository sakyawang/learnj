package lean.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.*;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * fastjson不支持私有或保护属性的序列化，使用本类支持。
 * 注意：仅实现了序列化，未实现反序列化
 * Created by 张三丰 on 2015-10-14.
 */
public class MetaBeanSerializer extends JavaBeanSerializer {

    private final FieldSerializer[] getters;
    private final FieldSerializer[] sortedGetters;
    private int features = 0;

    public MetaBeanSerializer(Class<?> clazz) {
        this(clazz, null);
    }

    public MetaBeanSerializer(Class<?> clazz, Map<String, String> aliasMap) {
        super(clazz, aliasMap);
        this.features = TypeUtils.getSerializeFeatures(clazz);
        {
            List<FieldSerializer> getterList = new ArrayList<FieldSerializer>();
            List<FieldInfo> fieldInfoList = MetaTypeUtils.computeGetters(clazz, aliasMap, false);

            for (FieldInfo fieldInfo : fieldInfoList) {
                getterList.add(createFieldSerializer(fieldInfo));
            }

            getters = getterList.toArray(new FieldSerializer[getterList.size()]);
        }
        {
            List<FieldSerializer> getterList = new ArrayList<FieldSerializer>();
            List<FieldInfo> fieldInfoList = MetaTypeUtils.computeGetters(clazz, aliasMap, true);

            for (FieldInfo fieldInfo : fieldInfoList) {
                getterList.add(createFieldSerializer(fieldInfo));
            }

            sortedGetters = getterList.toArray(new FieldSerializer[getterList.size()]);
        }
    }

    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
            throws IOException {
        SerializeWriter out = serializer.getWriter();

        if (object == null) {
            out.writeNull();
            return;
        }

        if (writeReference(serializer, object, features)) {
            return;
        }

        final FieldSerializer[] getters;

        if (out.isEnabled(SerializerFeature.SortField)) {
            getters = this.sortedGetters;
        } else {
            getters = this.getters;
        }

        SerialContext parent = serializer.getContext();
        serializer.setContext(parent, object, fieldName, this.features, features);

        final boolean writeAsArray = isWriteAsArray(serializer);

        try {
            final char startSeperator = writeAsArray ? '[' : '{';
            final char endSeperator = writeAsArray ? ']' : '}';
            out.append(startSeperator);

            if (getters.length > 0 && out.isEnabled(SerializerFeature.PrettyFormat)) {
                serializer.incrementIndent();
                serializer.println();
            }

            boolean commaFlag = false;

            if (isWriteClassName(serializer, object, fieldType, fieldName)) {
                Class<?> objClass = object.getClass();
                if (objClass != fieldType) {
                    out.writeFieldName(JSON.DEFAULT_TYPE_KEY);
                    serializer.write(object.getClass());
                    commaFlag = true;
                }
            }

            char seperator = commaFlag ? ',' : '\0';

            char newSeperator = FilterUtils.writeBefore(serializer, object, seperator);
            commaFlag = newSeperator == ',';

            for (int i = 0; i < getters.length; ++i) {
                FieldSerializer fieldSerializer = getters[i];

                Field field = fieldSerializer.getField();
                if (serializer.isEnabled(SerializerFeature.SkipTransientField)) {
                    if (field != null) {
                        if (Modifier.isTransient(field.getModifiers())) {
                            continue;
                        }
                    }
                }

                if (!FilterUtils.applyName(serializer, object, fieldSerializer.getName())) {
                    continue;
                }

                Object propertyValue = fieldSerializer.getPropertyValue(object);

                if (!FilterUtils.apply(serializer, object, fieldSerializer.getName(), propertyValue)) {
                    continue;
                }

                String key = FilterUtils.processKey(serializer, object, fieldSerializer.getName(), propertyValue);

                Object originalValue = propertyValue;
                propertyValue = FilterUtils.processValue(serializer, object, fieldSerializer.getName(), propertyValue);

                if (propertyValue == null && !writeAsArray) {
                    if ((!fieldSerializer.isWriteNull())
                            && (!serializer.isEnabled(SerializerFeature.WriteMapNullValue))) {
                        continue;
                    }
                }

                if (propertyValue != null && serializer.isEnabled(SerializerFeature.NotWriteDefaultValue)) {
                    Field field1 = ReflectionUtils.findField(FieldSerializer.class, "fieldInfo");
                    ReflectionUtils.makeAccessible(field1);
                    Class<?> fieldCLass = ((FieldInfo) ReflectionUtils.getField(field1, fieldSerializer)).getFieldClass();
                    if (fieldCLass == byte.class && propertyValue instanceof Byte
                            && ((Byte) propertyValue).byteValue() == 0) {
                        continue;
                    } else if (fieldCLass == short.class && propertyValue instanceof Short
                            && ((Short) propertyValue).shortValue() == 0) {
                        continue;
                    } else if (fieldCLass == int.class && propertyValue instanceof Integer
                            && ((Integer) propertyValue).intValue() == 0) {
                        continue;
                    } else if (fieldCLass == long.class && propertyValue instanceof Long
                            && ((Long) propertyValue).longValue() == 0L) {
                        continue;
                    } else if (fieldCLass == float.class && propertyValue instanceof Float
                            && ((Float) propertyValue).floatValue() == 0F) {
                        continue;
                    } else if (fieldCLass == double.class && propertyValue instanceof Double
                            && ((Double) propertyValue).doubleValue() == 0D) {
                        continue;
                    } else if (fieldCLass == boolean.class && propertyValue instanceof Boolean
                            && !((Boolean) propertyValue).booleanValue()) {
                        continue;
                    }
                }

                if (commaFlag) {
                    out.append(',');
                    if (out.isEnabled(SerializerFeature.PrettyFormat)) {
                        serializer.println();
                    }
                }

                if (key != fieldSerializer.getName()) {
                    if (!writeAsArray) {
                        out.writeFieldName(key);
                    }
                    serializer.write(propertyValue);
                } else if (originalValue != propertyValue) {
                    if (!writeAsArray) {
                        fieldSerializer.writePrefix(serializer);
                    }
                    serializer.write(propertyValue);
                } else {
                    if (!writeAsArray) {
                        fieldSerializer.writeProperty(serializer, propertyValue);
                    } else {
                        fieldSerializer.writeValue(serializer, propertyValue);
                    }
                }

                commaFlag = true;
            }

            FilterUtils.writeAfter(serializer, object, commaFlag ? ',' : '\0');

            if (getters.length > 0 && out.isEnabled(SerializerFeature.PrettyFormat)) {
                serializer.decrementIdent();
                serializer.println();
            }

            out.append(endSeperator);
        } catch (Exception e) {
            throw new JSONException("write javaBean error", e);
        } finally {
            serializer.setContext(parent);
        }
    }
}