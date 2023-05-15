package com.test.specAndSchemaTest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class JsonData {
    private static JsonData jsonData;
    private Data data;

    public static JsonData getInstance() {
        if (jsonData == null) {
            jsonData = new JsonData();
        }
        return jsonData;
    }

    public Data getData() {
        return data;
    }

    public void loadJsonData(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Gson gson = new Gson();
            data = gson.fromJson(reader, Data.class);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getDataTypes() {
        if (data != null) {
            return data.getDataTypes();
        }
        return null;
    }

    public Object getResponseData(String dataType) {
        if (data != null && dataType != null) {
            DataType dataTypeObject = data.getDataType(dataType);
            if (dataTypeObject != null) {
            	//System.out.println(dataTypeObject);
                return dataTypeObject.getResponseData();
            }
        }
        return null;
    }

    public String getRequestURL(String dataType) {
        if (data != null && dataType != null) {
            DataType dataTypeObject = data.getDataType(dataType);
            if (dataTypeObject != null) {
                return dataTypeObject.getRequestURL();
            }
        }
        return null;
    }

    public String getType(String dataType) {
        if (data != null && dataType != null) {
            DataType dataTypeObject = data.getDataType(dataType);
            if (dataTypeObject != null) {
                return dataTypeObject.getType();
            }
        }
        return null;
    }

    public Double getResponse(String dataType) {
        if (data != null && dataType != null) {
            DataType dataTypeObject = data.getDataType(dataType);
            if (dataTypeObject != null) {
                return (double) dataTypeObject.getResponse();
            }
        }
        return null;
    }

    public static class Data {
        @SerializedName("data")
        private Map<String, DataType> dataTypeMap;

        public Map<String, DataType> getDataTypeMap() {
            return dataTypeMap;
        }

        public void setDataTypeMap(Map<String, DataType> dataTypeMap) {
            this.dataTypeMap = dataTypeMap;
        }

        public Set<String> getDataTypes() {
            if (dataTypeMap != null) {
                return dataTypeMap.keySet();
            }
            return null;
        }

        public DataType getDataType(String dataType) {
            if (dataTypeMap != null && dataType != null) {
                return dataTypeMap.get(dataType);
            }
            return null;
        }
    }

    public static class DataType {
        private String requestURL;
        private String type;
        private int response;
        private Object responseData;

        public String getRequestURL() {
            return requestURL;
        }

        public void setRequestURL(String requestURL) {
            this.requestURL = requestURL;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getResponse() {
            return response;
        }

        public void setResponse(int response) {
            this.response = response;
        }

        public Object getResponseData() {
            return responseData;
        }

        public void setResponseData(Object responseData) {
            this.responseData = responseData;
        }
    }
}
