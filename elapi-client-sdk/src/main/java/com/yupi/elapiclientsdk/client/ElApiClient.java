package com.yupi.elapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.yupi.elapiclientsdk.model.User;


import java.util.HashMap;
import java.util.Map;

import static com.yupi.elapiclientsdk.utils.SignUtils.genSign;

/**
 * 调用第三方接口的客户端
 * @author Elvis
 */
public class ElApiClient {
    final String URL="http://localhost:8132/api/name/";

    private String accessKey;
    private String secretKey;

    public ElApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        //使用HttpUtil工具发起get请求，并获取服务器返回的结果
        //get方法会将paramMap中的参数以查询字符串的形式附加到URL后面，并向指定的URL发送GET请求。
        String result= HttpUtil.get(URL, paramMap);
        System.out.println(result);
        //返回服务器返回的结果
        return result;
    }

    public String getNameByPost( String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        //使用HttpUtil工具发起get请求，并获取服务器返回的结果
        //get方法会将paramMap中的参数以查询字符串的形式附加到URL后面，并向指定的URL发送GET请求。
        String result= HttpUtil.get(URL, paramMap);
        System.out.println(result);
        //返回服务器返回的结果
        return result;
    }

    /**
     * 构造请求头
     * @return 构造请求头
     */
    private Map<String,String> getHeaderMap(String body){
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        //不能直接发送密钥
        //hashMap.put("secretKey",secretKey);

        //生成随机数
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        //请求体内容
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign",genSign(body,secretKey));
        return hashMap;
    }

    //使用post方法向服务器发送user对象，并获取服务器返回的结果
    public String getUserNameByPost( User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse= HttpRequest.post("http://localhost:8132/api/name/user")
                .addHeaders(getHeaderMap(json))//添加前面构造的请求头
                //设置请求体
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
}
