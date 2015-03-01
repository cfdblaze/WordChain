package com.example.blaze.wordchain;

import junit.framework.Assert;

/**
 * Created by eachee on 2/27/2015.
 */
public class Tester {


    private WordChain wordChain = new WordChain("warm", "cold");
    public boolean testXML(String XMLVerify){
        Assert.assertNotNull(XMLVerify);
        return true;
    }
    public void responseTest(int code) {
        Assert.assertEquals(200, code);
        return;
    }
    
    public void test() {
        Assert.assertEquals("Error: unequal first words", "warm", wordChain.getFirstWord());

        wordChain.next("worm");

        Assert.assertEquals("Error, failed to add current word", 2, wordChain.chain.size());

        wordChain.undo();

        Assert.assertEquals("Error, failed to delete current word", 1, wordChain.chain.size());

        wordChain.clear();

        Assert.assertEquals("Error, failed to clear list", 1, wordChain.chain.size());
    }
}
