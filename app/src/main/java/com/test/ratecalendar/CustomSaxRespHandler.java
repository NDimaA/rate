package com.test.ratecalendar;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.SaxAsyncHttpResponseHandler;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Пользователь on 13.02.2016.
 */
public class CustomSaxRespHandler extends SaxAsyncHttpResponseHandler<ValuteHandler> {
    ValuteHandler handler;

    public CustomSaxRespHandler(ValuteHandler valuteHandler) {
        super(valuteHandler);
        this.handler = valuteHandler;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, ValuteHandler valuteHandler) {

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, ValuteHandler valuteHandler) {

    }

    @Override
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream instream = entity.getContent();
            InputStreamReader inputStreamReader = null;
            if (instream != null) {
                try {
                    SAXParserFactory sfactory = SAXParserFactory.newInstance();
                    SAXParser sparser = sfactory.newSAXParser();
                    XMLReader rssReader = sparser.getXMLReader();
                    rssReader.setContentHandler(handler);
                    inputStreamReader = new InputStreamReader(instream, Charset.forName("windows-1251"));
                    rssReader.parse(new InputSource(inputStreamReader));
                } catch (SAXException e) {
                    AsyncHttpClient.log.e("CustomSaxRespHandler", "getResponseData exception", e);
                } catch (ParserConfigurationException e) {
                    AsyncHttpClient.log.e("CustomSaxRespHandler", "getResponseData exception", e);
                } finally {
                    AsyncHttpClient.silentCloseInputStream(instream);
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e) { /*ignore*/ }
                    }
                }
            }
        }
        return null;
    }
}
