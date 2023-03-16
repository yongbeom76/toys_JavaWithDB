import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SimpleTestWithDB {
    public static void main(String[] args) {
        Commons commons = new Commons();
        Statement statement = commons.getStatement();
        Statement statementAnswer = commons.getStatement();

        // 설문과 답항 내용 출력
        String query = "SELECT * FROM questions_list " +
                "ORDER BY ORDERS";
        try {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // 설문 문항에 맞는 설문 답항 출력
                System.out.print(resultSet.getInt("ORDERS") + ". ");
                System.out.println(resultSet.getString("QUESTIONS"));
                String uid = resultSet.getString("QUESTIONS_UID");
                // 설문자 답 받기
                query = "SELECT example_list.EXAMPLE_UID, example_list.EXAMPLE, example_list.ORDERS " +
                        "FROM answers inner Join example_list " +
                        " on answers.EXAMPLE_UID = example_list.EXAMPLE_UID " +
                        " WHERE QUESTIONS_UID = '" + uid + "' " +
                        "ORDER BY ORDERS";
                ResultSet resultSetAnswer = statementAnswer.executeQuery(query);
                // 설문 답항 출력
                ArrayList<String> example_list = new ArrayList<String>();
                while (resultSetAnswer.next()) {
                    System.out.print(resultSetAnswer.getInt("ORDERS") + ". ");
                    System.out.println(resultSetAnswer.getString("EXAMPLE"));
                    example_list.add(resultSetAnswer.getString("EXAMPLE_UID"));
                }
                resultSetAnswer.close();
            }
            statementAnswer.close();
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
