package com.msg;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssertTest {

    String actualResponse = "{\"description\":\"Dummy Product 1\",\"productStatus\":\"NEW\",\"categories\":[\"Dummy Category\"]}";

    /**
     * testing JSONAssert.assertEquals(expectedResponse, actualResponse, strict);
     * strict = true -> checks for all values are enabled, if some are missing, it will fail
     *
     * @throws JSONException
     */
    @Test
    void jsonAssertTest_strictTrue() throws JSONException {
        String expectedResponse = "{\"description\":\"Dummy Product 1\",\"productStatus\":\"NEW\",\"categories\":[\"Dummy Category\"]}";
        JSONAssert.assertEquals(expectedResponse, actualResponse, true);
    }

    /**
     * testing JSONAssert.assertEquals(expectedResponse, actualResponse, strict);
     * strict = false -> checks for all values are disabled, if some are missing, but the rest are in order, it will pass
     *
     * @throws JSONException
     */
    @Test
    void jsonAssertTest_strictFalse() throws JSONException {
        String expectedResponse = "{\"description\":\"Dummy Product 1\",\"productStatus\":\"NEW\",\"categories\":[\"Dummy Category\"]}";
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        expectedResponse = "{\"categories\":[\"Dummy Category\"], \"description\":\"Dummy Product 1\"}";
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        expectedResponse = "{\"description\":\"Dummy Product 1\",\"productStatus\":\"NEW\"}";
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
        expectedResponse = "{\"description\":\"Dummy Product 2\",\"productStatus\":\"NEW\"}";
        JSONAssert.assertNotEquals(expectedResponse, actualResponse, false);
        expectedResponse = "{categories:[\"Dummy Category\"], description:\"Dummy Product 1\"}"; // we need escape characters when we have blank spaces
        JSONAssert.assertEquals(expectedResponse, actualResponse, false);
    }
}
