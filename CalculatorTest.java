import StaticClassesEnumsExceptions.Calculator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

//Add public class
public class CalculatorTest {

    @BeforeAll
    public static void classSetup(){
        System.out.println("Before all Method");
    }

    @AfterAll
    public static void classTearDown(){
        System.out.println("After all method");
    }

    @BeforeEach
    public void beforeTest(){
        System.out.println("Before each method");
    }
    @AfterEach
    public void afterTest(){
        System.out.println("After each method");
    }

    static Stream<Arguments>dataProvider(){
        return Stream.of(
                Arguments.of(2,2,4,Calculator.Type.SUMMARY),
                Arguments.of(4,2,2,Calculator.Type.DIVISION),
                Arguments.of(2,2,4,Calculator.Type.MULTIPLICATION),
                Arguments.of(5,2,3,Calculator.Type.SUBTRACTION)
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    public void methodSourceTest(double a, double b, double expectedResult, Calculator.Type type){
        double actualResult = Calculator.calculate(a,b,type);
        assertEquals(expectedResult, actualResult);

    }

    @ParameterizedTest
    @CsvSource({"2,2, 4, SUMMARY", "4,2,2, DIVISION"})
    public void csvParamsTest(double a, double b, double expectedResult, Calculator.Type type){
        double actualResult = Calculator.calculate(a,b,type);
        assertEquals(expectedResult, actualResult);

    }


    @ParameterizedTest
    @ValueSource(ints = {-10,0,27, 645235576, -645235576})
    public void summaryParamsTest(int a){
        double b = 6;
        double expectedResult = b+a;

        double actualResult = Calculator.calculate(a,b, Calculator.Type.SUMMARY);

        assertEquals(expectedResult, actualResult, "Summary result is incorrect");
    }

    @ParameterizedTest
    @EnumSource(Calculator.Type.class)
    public void calculationTypesTest(Calculator.Type type){
        double a = 25;
        double b = 6;


        double actualResult = Calculator.calculate(a,b, type);
        assertNotNull(actualResult);
        assertTrue(actualResult>0);

    }

    @Test
    public void summaryTest(){
        double a = 5;
        double b = 6;
        double expectedResult = 11;

        double actualResult = Calculator.calculate(a,b, Calculator.Type.SUMMARY);

        assertEquals(expectedResult, actualResult, "Summary result is incorrect");

        }
    }


