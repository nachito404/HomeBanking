package com.minhub.homebanking;

import com.minhub.homebanking.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class UtilsTest {

    @Test
    void getCVV(){
        int cvv1 = Utils.getCVV();
        int cvv2 = Utils.getCVV();
        assertNotEquals(cvv1, cvv2);
    }
    @Test
    void cvvIsTheCorrrectSize(){
        Integer cvv = Utils.getCVV();
        assertTrue(cvv.toString().length()<1000);
    }
    @Test
    void getAccountNumber(){
        int accountNumber1 = Utils.getAccountNumber();
        int accountNumber2 = Utils.getAccountNumber();
        assertNotEquals(accountNumber1, accountNumber2);
    }
    @Test
    void cardNumberIsTheCorrectSize(){
        String cardNumber = Utils.getCardNumber();
        assertEquals(19, cardNumber.length());
    }
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = Utils.getCardNumber();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
}
