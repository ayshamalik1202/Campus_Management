package interfaces;

import models.Notice;
public interface NoticeObserver {
    void onNoticeReceived(Notice notice);
}
