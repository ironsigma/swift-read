package com.izylab.swift;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.izylab.swift.util.TimerNotifier;
import com.izylab.swift.util.TimerNotifierListener;

/**
 * Swift Reader main class.
 */
public final class SwiftReader extends JFrame
        implements TimerNotifierListener, WindowListener {

    /** Serial Version. */
    private static final long serialVersionUID = 1L;

    /** Logger. */
    private static final Logger LOG = Logger.getLogger(SwiftReader.class);

    /** Reading Text Label. */
    private JLabel readingLine = new JLabel();

    /** Default interval. */
    private static final int INTERVAL_300_WPM = 5;

    /** Timer notifier. */
    private TimerNotifier timer = new TimerNotifier(INTERVAL_300_WPM);

    /**
     * Create the application.
     */
    public SwiftReader() {
        addWindowListener(this);
        timer.addListener(this);

        JButton startButton = new JButton(new AbstractAction() {
            private static final long serialVersionUID = 1L;
            {
                putValue(NAME, "Start");
            }

            @Override
            public void actionPerformed(final ActionEvent e) {
                run();
            }
        });

        readingLine.setText("Welcome");
        readingLine.setHorizontalAlignment(SwingConstants.CENTER);
        readingLine.setVerticalAlignment(SwingConstants.CENTER);
        readingLine.setFont(new Font(readingLine.getFont().getName(),
                Font.PLAIN, 64));

        JPanel readingPanel = new JPanel();
        readingPanel.setLayout(new BorderLayout());
        readingPanel.add(readingLine);
        readingPanel.add(startButton, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        getContentPane().add(readingPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Start the application.
     */
    public void run() {
        LOG.info("Starting...");
        timer.start();
    }

    /**
     * Main.
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        new SwiftReader();
    }

    @Override
    public void intervalElapsed() {
        readingLine.setText("ms: " + System.currentTimeMillis());
    }

    @Override
    public void windowClosing(final WindowEvent e) {
        LOG.info("Closing window");
        timer.stopNotifications();
    }

    @Override
    public void timerStopped() {
        /* empty */
    };

    @Override
    public void windowOpened(final WindowEvent e) {
        /* empty */
    }

    @Override
    public void windowClosed(final WindowEvent e) {
        /* empty */
    }

    @Override
    public void windowIconified(final WindowEvent e) {
        /* empty */
    }

    @Override
    public void windowDeiconified(final WindowEvent e) {
        /* empty */
    }

    @Override
    public void windowActivated(final WindowEvent e) {
        /* empty */
    }

    @Override
    public void windowDeactivated(final WindowEvent e) {
        /* empty */
    }

}
