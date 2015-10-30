package com.coep.puneet.boilerplate.Global;

import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;

public class SendUnicodeSms {

    public static void sendSms(final String n)
    {
        AsyncTask.execute(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    send(n);
                }
                catch (NoSuchFieldException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (UnsupportedEncodingException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    static void send(String number) throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException,
            UnsupportedEncodingException {
        HttpClient client = new DefaultHttpClient();
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("From", "09243422233"));
        postParameters.add(new BasicNameValuePair("To", number));
        String body = "Magic. Do not touch.";
        String out = new String(body.getBytes("UTF-8"), "ISO-8859-1");
        postParameters.add(new BasicNameValuePair("Body", out));

        //Replace <sid> with your account sid
        String sid = "student162";
        //Replace <token> with your secret token
        String authStr = sid + ":" + "3e92cd2389adca5d954dcd01bd52bd341a374bce";
        String url = "https://" +
                authStr + "@twilix.exotel.in/v1/Accounts/" +
                sid + "/Sms/send";
        byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
        String authStringEnc = new String(authEncBytes);
        HttpPost post = new HttpPost(url);
        post.setHeader("Authorization", "Basic " + authStringEnc);
        try {
            post.setEntity(new UrlEncodedFormEntity(postParameters));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            HttpResponse response = client.execute(post);
            int httpStatusCode = response.getStatusLine().getStatusCode();
            System.out.println(httpStatusCode + "is the status code");
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}