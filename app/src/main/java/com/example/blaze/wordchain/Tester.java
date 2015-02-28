package com.example.blaze.wordchain;

import junit.framework.Assert;

/**
 * Created by eachee on 2/27/2015.
 */
public class Tester {
    private WordChain wordChain = new WordChain();
    public boolean testXML(String XMLVerify){
        Assert.assertNotNull(XMLVerify);
        return true;
    }
    public void responseTest(int code) {
        Assert.assertEquals(200, code);
        return;
    }
}
