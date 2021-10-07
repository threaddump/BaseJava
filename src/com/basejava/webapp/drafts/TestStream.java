package com.basejava.webapp.drafts;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestStream {
    public static void main(String[] args) {
        int[] arr_1 = new int[] {1,2,3,3,2,3};
        System.out.println(minValue(arr_1));

        int[] arr_2 = new int[] {9,8};
        System.out.println(minValue(arr_2));

        List<Integer> lst_1 = Arrays.asList(1, 2, 3);
        System.out.println(oddOrEven(lst_1));

        List<Integer> lst_2 = Arrays.asList(1, 2, 4);
        System.out.println(oddOrEven(lst_2));
    }

    /*
    Реализовать метод через стрим int minValue(int[] values).
    Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
    составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно.
    Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89
     */
    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (first, second) -> first * 10 + second);
    }

    /*
    Реализовать метод List<Integer> oddOrEven(List<Integer> integers) если сумма всех чисел нечетная -
    удалить все нечетные, если четная - удалить все четные. Сложность алгоритма должна быть O(N).
    Optional - решение в один стрим.
     */
    private static List<Integer> oddOrEven(List<Integer> integers) {
        class Counter {
            public int value;
            public Counter() { value = 0; }
        }
        final Counter counter = new Counter();
        return integers.stream()
                .collect(Collectors.partitioningBy(x -> {
                    counter.value += x; return x % 2 == 0; }))
                .get(counter.value % 2 != 0);
    }
}
