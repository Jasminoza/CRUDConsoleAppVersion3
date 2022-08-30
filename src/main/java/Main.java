import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.flywaydb.core.Flyway;
import view.MainView;

public class Main {
    public static void main(String[] args) {
        migrateFlyway();

        MainView mainView = new MainView();
        mainView.mainMenu();
    }

    private static void migrateFlyway() {
        Config config = ConfigFactory.load();
        Flyway flyway = Flyway.configure().dataSource(config.getString("db.url"), config.getString("db.user"), config.getString("db.password")).load();
        flyway.repair();
        flyway.migrate();
    }
}