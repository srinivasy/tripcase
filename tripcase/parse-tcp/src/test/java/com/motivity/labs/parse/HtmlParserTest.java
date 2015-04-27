package com.motivity.labs.parse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class HtmlParserTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public HtmlParserTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( HtmlParserTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testHtml()
    {
        assertTrue( true );
    }
}
