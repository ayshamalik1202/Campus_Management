package views;

import java.awt.Color;
import java.awt.Font;

// NOTE: Use these constants in your dashboards instead of redefining colors locally.
// Example: panel.setBackground(UITheme.NAVY);  instead of new Color(28, 40, 65) each time.
public class UITheme {
    public static final Color NAVY      = new Color(28, 40, 65);
    public static final Color LAVENDER  = new Color(230, 230, 250);
    public static final Color DARK_PINK = new Color(170, 50, 90);
    public static final Color OFF_WHITE = new Color(248, 248, 248);
    public static final Color DARK_BG   = new Color(13, 11, 23);
    public static final Color NEON_PINK = new Color(236, 21, 137);
    public static final Color NEON_CYAN = new Color(0, 169, 157);

    public static final Font MAIN_FONT  = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font BOLD_FONT  = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font TITLE_FONT = new Font("Georgia", Font.BOLD, 22);
}