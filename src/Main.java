public class hochu_spat {

    public static int findMin(int[] arr) {
        int min = arr[0];
        for (int num : arr) {
            if (num < min) min = num;
        }
        return min;
    }

    public static double findAverage(int[] arr) {
        int sum = 0;
        for (int num : arr) sum += num;
        return (double) sum / arr.length;
    }

    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    public static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }

    public static int fibonacci(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static int power(int a, int n) {
        if (n == 0) return 1;
        return a * power(a, n - 1);
    }

    public static void reversePrint(int[] arr, int index) {
        if (index < 0) return;
        System.out.print(arr[index] + " ");
        reversePrint(arr, index - 1);
    }

    public static boolean isAllDigits(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static int binomialCoefficient(int n, int k) {
        if (k == 0 || k == n) return 1;
        return binomialCoefficient(n - 1, k - 1) + binomialCoefficient(n - 1, k);
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{10, 1, 32, 3, 45}));
        System.out.println(findAverage(new int[]{3, 2, 4, 1}));
        System.out.println(isPrime(7));
        System.out.println(factorial(5));
        System.out.println(fibonacci(5));
        System.out.println(power(2, 10));
        reversePrint(new int[]{1, 4, 6, 2}, 3);
        System.out.println();
        System.out.println(isAllDigits("123456"));
        System.out.println(binomialCoefficient(7, 3));
        System.out.println(gcd(32, 48));
    }
}