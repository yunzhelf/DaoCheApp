package com.yifactory.daocheapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StsToken {

    /**
     * data : [{"Access Key Secret":"Dyr4iJPC391NRKTmSUKBdLvEYtMF6t3PwdhsaQz1bmoa","RequestId":"57455BC6-6A4F-474D-B058-90AD8DEA3659","Expiration":"2018-03-23T03:12:28Z","Security Token":"CAISgwJ1q6Ft5B2yfSjIra7zJInQhbcThZGyZ0PFhVcSOvZPvIPekDz2IHFEdXZtBeEdvvQ3lWhR5/kZlqVoRoReREvCKM1565kPV6MWjUeF6aKP9rUhpMCPKwr6UmzGvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AFZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO7gEa4K+kkMqH8Uic3h+oiM1t/tmoesn7MJIzY8svCo/rjdYbLPSRjHRijDFR77pzgaB+/jPKg8qQGVE54W/dYrqFrYAzfVQpNvBrQ/cV86Pm9fpjoeXfkoXx25PHsSeg/Ju1GoABP0vzXgupU6NNAhWn5Wa+uBeUssAfePI8ICwXuBcvCsu2I+uB9O8QdXvMFy6cV3YmK/2EwaDAl7ej0js7CYL70qQY3rwBG2OQoZ8Ct5AM65ThRzvarxTBhj3CCgIRKxh3j69ThvUGDlJEad7sDwH64GqHWoWsXpDYOtNcTdHkjmY=","Access Key Id":"STS.LmFo3dhh22SYeetaWG6ycSFur"}]
     * msg : 获取成功
     * responseState : 1
     */

    private String msg;
    private String responseState;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResponseState() {
        return responseState;
    }

    public void setResponseState(String responseState) {
        this.responseState = responseState;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String accessKeySecret;
        private String requestId;
        private String expiration;
        private String securityToken;
        private String accessKeyId;

        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        public void setAccessKeySecret(String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        public String getRequestId() {
            return requestId;
        }

        public void setRequestId(String requestId) {
            this.requestId = requestId;
        }

        public String getExpiration() {
            return expiration;
        }

        public void setExpiration(String expiration) {
            this.expiration = expiration;
        }

        public String getSecurityToken() {
            return securityToken;
        }

        public void setSecurityToken(String securityToken) {
            this.securityToken = securityToken;
        }

        public String getAccessKeyId() {
            return accessKeyId;
        }

        public void setAccessKeyId(String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }
    }
}
