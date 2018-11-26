package lean.json.fastjson;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class MetaTypeUtils extends TypeUtils {

    public static List<FieldInfo> computeGetters(Class<?> clazz, Map<String, String> aliasMap, boolean sorted) {
        Map<String, FieldInfo> fieldInfoMap = new LinkedHashMap<String, FieldInfo>();

        for (Method method : clazz.getMethods()) {
            String methodName = method.getName();
            int ordinal = 0, serialzeFeatures = 0;
            String label = null;

            if (Modifier.isStatic(method.getModifiers())) {
                continue;
            }

            if (method.getReturnType().equals(Void.TYPE)) {
                continue;
            }

            if (method.getParameterTypes().length != 0) {
                continue;
            }

            if (method.getReturnType() == ClassLoader.class) {
                continue;
            }

            if (method.getName().equals("getMetaClass")
                    && method.getReturnType().getName().equals("groovy.lang.MetaClass")) {
                continue;
            }

            JSONField annotation = method.getAnnotation(JSONField.class);

            if (annotation == null) {
                annotation = getSupperMethodAnnotation(clazz, method);
            }

            if (annotation != null) {
                if (!annotation.serialize()) {
                    continue;
                }

                ordinal = annotation.ordinal();
                serialzeFeatures = SerializerFeature.of(annotation.serialzeFeatures());

                if (annotation.name().length() != 0) {
                    String propertyName = annotation.name();

                    if (aliasMap != null) {
                        propertyName = aliasMap.get(propertyName);
                        if (propertyName == null) {
                            continue;
                        }
                    }

                    fieldInfoMap.put(propertyName, new FieldInfo(propertyName, method, null, ordinal, serialzeFeatures));
                    continue;
                }

                if (annotation.name().length() != 0) {
                    label = annotation.name();
                }
            }

            if (methodName.startsWith("get")) {
                if (methodName.length() < 4) {
                    continue;
                }

                if (methodName.equals("getClass")) {
                    continue;
                }

                char c3 = methodName.charAt(3);

                String propertyName;
                if (Character.isUpperCase(c3)) {
                    if (compatibleWithJavaBean) {
                        propertyName = decapitalize(methodName.substring(3));
                    } else {
                        propertyName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                    }
                } else if (c3 == '_') {
                    propertyName = methodName.substring(4);
                } else if (c3 == 'f') {
                    propertyName = methodName.substring(3);
                } else if (methodName.length() >= 5 && Character.isUpperCase(methodName.charAt(4))) {
                    propertyName = decapitalize(methodName.substring(3));
                } else {
                    continue;
                }

                boolean ignore = isJSONTypeIgnore(clazz, propertyName);

                if (ignore) {
                    continue;
                }

                Field field = ParserConfig.getField(clazz, propertyName);

                if (field != null) {
                    JSONField fieldAnnotation = field.getAnnotation(JSONField.class);

                    if (fieldAnnotation != null) {
                        if (!fieldAnnotation.serialize()) {
                            continue;
                        }

                        ordinal = fieldAnnotation.ordinal();
                        serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());

                        if (fieldAnnotation.name().length() != 0) {
                            propertyName = fieldAnnotation.name();

                            if (aliasMap != null) {
                                propertyName = aliasMap.get(propertyName);
                                if (propertyName == null) {
                                    continue;
                                }
                            }
                        }

                        if (fieldAnnotation.name().length() != 0) {
                            label = fieldAnnotation.name();
                        }
                    }
                }

                if (aliasMap != null) {
                    propertyName = aliasMap.get(propertyName);
                    if (propertyName == null) {
                        continue;
                    }
                }

                fieldInfoMap.put(propertyName, new FieldInfo(propertyName, method, field, ordinal, serialzeFeatures));
            }

            if (methodName.startsWith("is")) {
                if (methodName.length() < 3) {
                    continue;
                }

                char c2 = methodName.charAt(2);

                String propertyName;
                if (Character.isUpperCase(c2)) {
                    if (compatibleWithJavaBean) {
                        propertyName = decapitalize(methodName.substring(2));
                    } else {
                        propertyName = Character.toLowerCase(methodName.charAt(2)) + methodName.substring(3);
                    }
                } else if (c2 == '_') {
                    propertyName = methodName.substring(3);
                } else if (c2 == 'f') {
                    propertyName = methodName.substring(2);
                } else {
                    continue;
                }

                Field field = ParserConfig.getField(clazz, propertyName);

                if (field == null) {
                    field = ParserConfig.getField(clazz, methodName);
                }

                if (field != null) {
                    JSONField fieldAnnotation = field.getAnnotation(JSONField.class);

                    if (fieldAnnotation != null) {
                        if (!fieldAnnotation.serialize()) {
                            continue;
                        }

                        ordinal = fieldAnnotation.ordinal();
                        serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());

                        if (fieldAnnotation.name().length() != 0) {
                            propertyName = fieldAnnotation.name();

                            if (aliasMap != null) {
                                propertyName = aliasMap.get(propertyName);
                                if (propertyName == null) {
                                    continue;
                                }
                            }
                        }

                        if (fieldAnnotation.name().length() != 0) {
                            label = fieldAnnotation.name();
                        }
                    }
                }

                if (aliasMap != null) {
                    propertyName = aliasMap.get(propertyName);
                    if (propertyName == null) {
                        continue;
                    }
                }

                fieldInfoMap.put(propertyName, new FieldInfo(propertyName, method, field, ordinal, serialzeFeatures));
            }
        }
//此处改动：获取所有属性
        for (Field field : clazz.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            JSONField fieldAnnotation = field.getAnnotation(JSONField.class);

            int ordinal = 0, serialzeFeatures = 0;
            String propertyName = field.getName();
            String label = null;
            if (fieldAnnotation != null) {
                if (!fieldAnnotation.serialize()) {
                    continue;
                }

                ordinal = fieldAnnotation.ordinal();
                serialzeFeatures = SerializerFeature.of(fieldAnnotation.serialzeFeatures());

                if (fieldAnnotation.name().length() != 0) {
                    propertyName = fieldAnnotation.name();
                }

                if (fieldAnnotation.name().length() != 0) {
                    label = fieldAnnotation.name();
                }
            }

            if (aliasMap != null) {
                propertyName = aliasMap.get(propertyName);
                if (propertyName == null) {
                    continue;
                }
            }

            if (!fieldInfoMap.containsKey(propertyName)) {
                fieldInfoMap.put(propertyName, new FieldInfo(propertyName, null, field, ordinal, serialzeFeatures));
            }
        }

        List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();

        boolean containsAll = false;
        String[] orders = null;

        JSONType annotation = clazz.getAnnotation(JSONType.class);
        if (annotation != null) {
            orders = annotation.orders();

            if (orders != null && orders.length == fieldInfoMap.size()) {
                containsAll = true;
                for (String item : orders) {
                    if (!fieldInfoMap.containsKey(item)) {
                        containsAll = false;
                        break;
                    }
                }
            } else {
                containsAll = false;
            }
        }

        if (containsAll) {
            for (String item : orders) {
                FieldInfo fieldInfo = fieldInfoMap.get(item);
                fieldInfoList.add(fieldInfo);
            }
        } else {
            for (FieldInfo fieldInfo : fieldInfoMap.values()) {
                fieldInfoList.add(fieldInfo);
            }

            if (sorted) {
                Collections.sort(fieldInfoList);
            }
        }
        return fieldInfoList;
    }

    private static boolean isJSONTypeIgnore(Class<?> clazz, String propertyName) {
        JSONType jsonType = clazz.getAnnotation(JSONType.class);

        if (jsonType != null) {
            // 1、新增 includes 支持，如果 JSONType 同时设置了includes 和 ignores 属性，则以includes为准。
            // 2、个人认为对于大小写敏感的Java和JS而言，使用 equals() 比 equalsIgnoreCase() 更好，改动的唯一风险就是向后兼容性的问题
            // 不过，相信开发者应该都是严格按照大小写敏感的方式进行属性设置的
            String[] fields = jsonType.ignores();
            if (fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    if (propertyName.equals(fields[i])) {
                        return false;
                    }
                }
                return true;
            } else {
                fields = jsonType.ignores();
                for (int i = 0; i < fields.length; i++) {
                    if (propertyName.equals(fields[i])) {
                        return true;
                    }
                }
            }
        }

        if (clazz.getSuperclass() != Object.class && clazz.getSuperclass() != null) {
            if (isJSONTypeIgnore(clazz.getSuperclass(), propertyName)) {
                return true;
            }
        }

        return false;
    }
}