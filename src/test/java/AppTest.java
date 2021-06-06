import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import taxType.IncomeTaxType;
import taxType.ProgressiveTaxType;
import taxType.TaxType;
import taxType.VATTaxType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class AppTest {
    @Test
    void testIncomeTaxType() {
        double amount = 100.0;
        double expected = 13.0;

        assertThat((new IncomeTaxType()).calculateTaxFor(amount),  is(closeTo(expected,0.01)));
        assertThat((new IncomeTaxType()).calculateTaxFor(amount),   instanceOf(Double.class));
    }

    @Test
    void testVATTaxType() {
        double amount = 100.01;
        double expected = 20.0;

        assertThat((new VATTaxType()).calculateTaxFor(amount), is(closeTo(expected,0.01)));
        assertThat((new VATTaxType()).calculateTaxFor(amount), instanceOf(Double.class));
    }

    @ParameterizedTest
    @CsvSource({"100.0,10.0", "1000000.0,150000.0", "0.0,0.0", "100000.0,10000.0","100000.01,15000.0"})
    void test_ProgressiveTaxType(String input, String expected) {
        TaxType tax = new ProgressiveTaxType();
        assertThat(tax.calculateTaxFor(Double.parseDouble(input)),is(closeTo(Double.parseDouble(expected),0.01)));
        assertThat(tax.calculateTaxFor(Double.parseDouble(input)), instanceOf(Double.class));
    }

    @Test
    void testOutputTypeIncomeTaxType() {
        double amount = 100.0;
        double expected = 13.0;
        assertThat((new IncomeTaxType()).calculateTaxFor(amount),  is(closeTo(expected,0.01)));
    }


}
