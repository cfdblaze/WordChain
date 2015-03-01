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
    private WordChain wordChain = new WordChain("warm", "cold");
    Tester t = new Tester();
    //t.test();
    public void test() {
        //assertEquals("Error: unequal first words", "warm", wordChain.getFirstWord());

        wordChain.next("worm");

        //assertEquals("Error, failed to add current word", 2, wordChain.chain.size());
    }
}
