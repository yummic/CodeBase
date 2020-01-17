package fun.clclcl.yummic.codebase.sample.lombok;

public class Foo {
    public static void main(String[] args) {
        FooBean bean = new FooBean();
        bean.setName("good");

        System.out.println(bean.getName());
        System.out.println(bean.toString());
        System.out.println(bean.canEqual(null));
    }
}
