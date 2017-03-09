package diplomat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Diplomat {


    public static String[] args;

    public static void main(String[] args) {
        Diplomat.args = args;
        SpringApplication.run(Diplomat.class, args);
    }
}
