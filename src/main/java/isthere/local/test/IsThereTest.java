package isthere.local.test;

import java.io.IOException;

/**
 * 이써? 에 사용될 알고리즘 테스트용 클래스.
 * 아직 알고리즘 이전에 테스트클래스를 테스트해야한다.
 *
 * @author Hyunje
 * @since 1.0
 *        Date: 12. 10. 21
 *        Time: 오후 5:30
 */
public class IsThereTest {
    public static void main(String[] args) throws IOException {
        IsThereFrame isThere = new IsThereFrame(800, 600);
        isThere.setVisible(true);
        isThere.setDefaultCloseOperation(isThere.EXIT_ON_CLOSE);
    }
}
