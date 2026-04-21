package cn.xiaozhou233.juiceagent.api.utils;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Deserializer {

    private final String data;
    private final HashMap<String, String> map = new HashMap<String, String>();

    public Deserializer(String data) {
        this.data = data;
        parse(data);
    }

    private void parse(String input) {
        if (input == null || input.length() == 0) {
            return;
        }

        StringBuilder key = new StringBuilder();
        StringBuilder value = new StringBuilder();

        boolean readingKey = true;
        boolean escaped = false;

        char[] chars = input.toCharArray();

        for (char c : chars) {

            if (escaped) {
                if (readingKey) {
                    key.append(c);
                } else {
                    value.append(c);
                }
                escaped = false;
                continue;
            }

            if (c == '\\') {
                escaped = true;
                continue;
            }

            if (readingKey && c == '=') {
                readingKey = false;
                continue;
            }

            if (c == ';') {
                if (key.length() != 0) {
                    map.put(key.toString(), value.toString());
                }

                key.setLength(0);
                value.setLength(0);
                readingKey = true;
                continue;
            }

            if (readingKey) {
                key.append(c);
            } else {
                value.append(c);
            }
        }

        if (key.length() != 0) {
            map.put(key.toString(), value.toString());
        }
    }

    public HashMap<String, String> deserialize() {
        return map;
    }

    public boolean has(String key) {
        return map.containsKey(key);
    }

    public String get(String key, String def) {
        String value = map.get(key);
        return value != null ? value : def;
    }

    public int getInt(String key, int def) {
        try {
            return Integer.parseInt(get(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    public long getLong(String key, long def) {
        try {
            return Long.parseLong(get(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    public double getDouble(String key, double def) {
        try {
            return Double.parseDouble(get(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    public float getFloat(String key, float def) {
        try {
            return Float.parseFloat(get(key, String.valueOf(def)));
        } catch (Exception e) {
            return def;
        }
    }

    public boolean getBoolean(String key, boolean def) {
        String value = get(key, String.valueOf(def)).toLowerCase();

        if ("true".equals(value) || "1".equals(value)) {
            return true;
        }

        if ("false".equals(value) || "0".equals(value)) {
            return false;
        }

        return def;
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public void clear() {
        map.clear();
    }

    public Map<String, String> asMap() {
        return map;
    }
}