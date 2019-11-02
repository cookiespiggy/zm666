package es;

import junit.framework.TestCase;

public class JavaESClientTest extends TestCase {

    private JavaESClient client;

    @Override
    public void setUp() throws Exception {
        // default
        client = new JavaESClient();
    }

    public void test00() {
        client.deleteIndex("spark");
    }

    public void test01() {
        client.createIndex("konfer-pub");
    }
}
