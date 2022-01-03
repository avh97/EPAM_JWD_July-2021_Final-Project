package by.khaletski.platform.service.util;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EncoderTest {

    @DataProvider
    public Object[][] encodeData() {
        return new Object[][]{
                {"password", "6347467a63336476636d513d"},
                {"12345678", "4d54497a4e4455324e7a673d"},
                {"4d54497a4e44553", "4e4751314e4451354e3245305a5451304e54557a"},
                {"&(*99^&*@85-i23", "4a6967714f546c654a6970414f4455746154497a"}
        };
    }


    @Test(dataProvider = "encodeData")
    public void testEncode(String string, String expected) {
        String actual = Encoder.encode(string);
        assertEquals(actual, expected);
    }
}