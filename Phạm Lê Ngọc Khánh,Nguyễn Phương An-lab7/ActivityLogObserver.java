import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogObserver implements CampusEventObserver{
    private List<String> logs = new ArrayList<>();

    @Override
    public void update(String eventType, String message) {
        String log = "[" + LocalDateTime.now() + "] "
            + eventType + " - " + message;
        logs.add(log);
        System.out.println("[LOG] " + log);
    }

    public List<String> getLogs() {
        return logs;
    }
}
