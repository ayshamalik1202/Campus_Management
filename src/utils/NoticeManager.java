package utils;

import interfaces.NoticeObserver;
import java.util.*;
import models.Notice;

public class NoticeManager {
    private List<NoticeObserver> observers = new ArrayList<>();

    public void addObserver(NoticeObserver o) { observers.add(o); }

    // FIX: Renamed from notifyAll() which shadows Object.notifyAll() — a reserved method
    public void broadcastNotice(Notice n) {
        for (NoticeObserver o : observers) {
            o.onNoticeReceived(n);
        }
    }
}