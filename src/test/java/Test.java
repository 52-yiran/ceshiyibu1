import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;




/*
*  ImmutableList是一个不可变、线程安全的列表集合，它只会获取传入对象的一个副本，而不会影响到原来的变量或者对象，如下代码：

          int a = 23;
          ImmutableList<Integer> list = ImmutableList.of(a, 12);
          System.out.println(list);
          a = 232;
          System.out.println(list);
        1
        2
        3
        4
        5
        输出结果：

        [23, 12]
        [23, 12]
*
*
*
* */
public class Test {

  public static void main(String[] args) {
    String a ="100";
    String join = StringUtils.join(a + "_", "c", "E");
    System.out.println(join);// 100_cE

    ImmutableList<String> of = ImmutableList.of(join);
    System.out.println(of); // [100_cE]
    a = "99";
    System.out.println(of);// [100_cE]    要表达的意思就是a 重新赋值没影响ImmutableList 的对象的结果


  }
}
