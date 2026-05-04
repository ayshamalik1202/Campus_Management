package interfaces;

import models.Notice;

// FIX: This interface was referenced in NoticeManager but never created
public interface NoticeObserver {
    void onNoticeReceived(Notice notice);
}