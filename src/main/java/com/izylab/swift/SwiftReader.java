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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

import com.izylab.swift.file.FileReader;
import com.izylab.swift.file.TextFileReader;
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

    /** Current position label. */
    private JLabel positionLabel = new JLabel();

    /** Default interval. */
    private static final int WORDS_PER_MINUTE = 300;

    /** Timer notifier. */
    private TimerNotifier timer = new TimerNotifier();

    /** File reader. */
    private FileReader reader;

    /**
     * Create the application.
     */
    public SwiftReader() {
        addWindowListener(this);
        timer.setInterval(WORDS_PER_MINUTE);
        timer.addListener(this);

        JButton startButton = new JButton(new AbstractAction() {
            private static final long serialVersionUID = 1L;
            {
                putValue(NAME, "Start");
            }

            @Override
            public void actionPerformed(final ActionEvent e) {
                if (timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            }
        });

        reader = new TextFileReader(
                "/Through the Looking-Glass - Carroll Lewis.txt");

        readingLine.setText("Swift Reader");
        readingLine.setHorizontalAlignment(SwingConstants.CENTER);
        readingLine.setVerticalAlignment(SwingConstants.CENTER);
        readingLine.setFont(new Font(readingLine.getFont().getName(),
                Font.PLAIN, 64));

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem quitMenuItem = new JMenuItem(new AbstractAction() {
            private static final long serialVersionUID = 1L;
            {
                putValue(NAME, "Quit");
            }

            @Override
            public void actionPerformed(final ActionEvent e) {
                dispose();
            }
        });
        fileMenu.add(quitMenuItem);
        setJMenuBar(menuBar);

        JPanel statusPanel = new JPanel();
        statusPanel.add(positionLabel);
        statusPanel.add(startButton);

        JPanel readingPanel = new JPanel();
        readingPanel.setLayout(new BorderLayout());
        readingPanel.add(readingLine);
        readingPanel.add(statusPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        getContentPane().add(readingPanel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800, 200));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
        if (!reader.hasNext()) {
            timer.stop();
            readingLine.setText("-- End --");
            return;
        }
        String word = reader.getNext();

        // delay an extra 50 ms for longer words
        // TODO: compute??? beggining 
        if (word.length() > 10) {
            timer.delayNext(50);
        }

        readingLine.setText(word);
        positionLabel.setText(reader.getPosition().toString());
    }

    @Override
    public void windowClosing(final WindowEvent e) {
        LOG.info("Closing window");
        timer.shutdown();
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
