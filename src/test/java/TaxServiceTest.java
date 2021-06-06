import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.MethodSource;
import taxType.IncomeTaxType;
import taxType.ProgressiveTaxType;
import taxType.VATTaxType;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class TaxServiceTest {

    static Stream<Bill> getBill() {
        TaxService taxService = new TaxService();
        ProgressiveTaxType progressiveTaxType = new ProgressiveTaxType();
        List<Bill> bills = Arrays.asList(
                new Bill (100, new IncomeTaxType(), taxService),
                new Bill (100,new VATTaxType(),taxService),
                new Bill (100,progressiveTaxType,taxService),
                new Bill (1_000_000.1,progressiveTaxType,taxService));
        return bills.stream();
    }

    protected static ByteArrayOutputStream output;
    private static PrintStream old;

    @BeforeAll
    static void setUpStreams() {
        old = System.out;
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterAll
    static void cleanUpStreams() {
        System.setOut(old);
    }

    @BeforeEach
    void resetStreams() {
        output.reset();
    }
    @Test
    void testTaxServiceTest() {
        double input = 100.0;

        String result = String.format("Уплачен налог в размере %,.2f%n", input);

        TaxService taxService = new TaxService();
        taxService.payOut(input);
        assertThat(output.toString(), equalTo(result));
    }

    @Test
    void testPayTaxes(){
        double amount = 100.0;
        Bill bill = new Bill (amount, new IncomeTaxType(), new TaxService());
        double taxPayed = (new IncomeTaxType()).calculateTaxFor(amount);

        String result = String.format("Уплачен налог в размере %,.2f%n", taxPayed);

        bill.payTaxes();

        assertThat(output.toString(), equalTo(result));
    }

    @ParameterizedTest
    @MethodSource ("getBill")
    void testPayTaxesReturnsSomething(Bill bill){
        bill.payTaxes();
        assertThat(output, is(anything()));
        assertThat(output.toString(), notNullValue());
    }

    @ParameterizedTest
    @MethodSource ("getBill")
    void testPayTaxesReturnsString(Bill bill){
        bill.payTaxes();

        assertThat(output.toString(), containsString("Уплачен налог в размере "));
    }










}
