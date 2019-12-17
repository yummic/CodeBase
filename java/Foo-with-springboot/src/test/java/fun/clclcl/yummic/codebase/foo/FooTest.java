package fun.clclcl.yummic.codebase.foo;

import fun.clclcl.yummic.codebase.foo.Bar;
import fun.clclcl.yummic.codebase.foo.Foo;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.anyString;


public class FooTest {

    public static void aa() {};

    public synchronized static void bb() {};

    synchronized public static void cc() {};


    @InjectMocks
    Foo foo;

    @Mock
    Bar bar;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(bar.getName(anyString())).thenReturn("good").thenReturn("bad");
        Mockito.when(bar.getName(null)).thenReturn(getString("Hello")).thenReturn(getString("What"));
    }

    int count = 0;

    int getNext() {
        return count++;
    }

    private String getString(String name) {
        return name + getNext();
    }

    @Test
    public void testFoo() {
        System.out.println("Test junit.");
    }

    @Test
    public void testBar() {
        System.out.println("Bar test.");
    }

    @Test
    public void testGetname() {
        System.out.println(foo.add(100, 200));
        System.out.println(foo.getBarName());
        System.out.println(foo.getBarName());
        System.out.println(foo.getBarName());
        System.out.println(foo.getBarName());
    }

}
