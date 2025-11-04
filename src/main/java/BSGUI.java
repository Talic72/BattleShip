import javax.swing.*;
import java.awt.*;

public class BSGUI extends JFrame {
    private BSGame game;
    private JButton[][] buttons;
    private JLabel lblMissCount, lblStrikeCount, lblTotalMiss, lblTotalHit;

    private ImageIcon blankIcon;
    private ImageIcon hitIcon;
    private ImageIcon missIcon;


    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    public BSGUI() {
        setTitle("Battleship | Single Player");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        game = new BSGame();
        int cellSize = 50;

        blankIcon = resizeIcon(new ImageIcon("src/images/blank.png"), cellSize, cellSize);
        hitIcon   = resizeIcon(new ImageIcon("src/images/hit.png"), cellSize, cellSize);
        missIcon  = resizeIcon(new ImageIcon("src/images/miss.png"), cellSize, cellSize);

        JPanel boardPanel = new JPanel(new GridLayout(10, 10));
        buttons = new JButton[10][10];

        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                JButton btn = new JButton(blankIcon);
                btn.setPreferredSize(new Dimension(cellSize, cellSize));
                btn.setMargin(new Insets(0, 0, 0, 0));
                btn.setBorderPainted(false);
                btn.setFocusPainted(false);
                btn.setContentAreaFilled(false);

                final int row = r, col = c;
                btn.addActionListener(e -> handleClick(row, col, btn));
                buttons[r][c] = btn;
                boardPanel.add(btn);
            }
        }

        add(boardPanel, BorderLayout.CENTER);


        JPanel infoPanel = new JPanel();
        lblMissCount = new JLabel("Miss Count: 0");
        lblStrikeCount = new JLabel("Strike Count: 0");
        lblTotalMiss = new JLabel("Total Misses: 0");
        lblTotalHit = new JLabel("Total Hits: 0");

        infoPanel.add(lblMissCount);
        infoPanel.add(lblStrikeCount);
        infoPanel.add(lblTotalMiss);
        infoPanel.add(lblTotalHit);

        add(infoPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void handleClick(int row, int col, JButton btn) {
        String result = game.handleFire(row, col);

        if (result.equals("HIT") || result.equals("SUNK") || result.equals("WIN")) {
            btn.setIcon(hitIcon);
        } else if (result.equals("MISS")) {
            btn.setIcon(missIcon);
        }
        btn.setEnabled(false);

        lblMissCount.setText("Miss Count: " + game.getMissCounter());
        lblStrikeCount.setText("Strike Count: " + game.getStrikeCounter());
        lblTotalMiss.setText("Total Misses: " + game.getTotalMisses());
        lblTotalHit.setText("Total Hits: " + game.getTotalHits());

        if (result.equals("SUNK")) {
            JOptionPane.showMessageDialog(this, "You sunk a ship!");
        } else if (result.equals("WIN")) {
            JOptionPane.showMessageDialog(this, "You win!");
            restartPrompt();
        } else if (result.equals("LOSS")) {
            JOptionPane.showMessageDialog(this, "You lost! Game over.");
            restartPrompt();
        }
    }

    private void restartPrompt() {
        int opt = JOptionPane.showConfirmDialog(this, "Play again?", "Restart", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION)
        {
            dispose();
            new BSGUI();
        }
        else
        {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new BSGUI();
    }
}