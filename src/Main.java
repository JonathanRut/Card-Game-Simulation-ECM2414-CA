import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        LinkedList<Integer> list = new LinkedList<>();
        int num = 2;
        list.add(0); list.add(1); list.add(2);
        System.out.println(list.toString().replaceAll("[,]|[]]|[\\[]",""));
    }
}