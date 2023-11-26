package videojuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Videojuego extends JFrame {
    private int puntaje = 0;
    private JPanel panelJuego;
    private JLabel puntajeLabel;
    private Timer timer;
    private Timer vidaObjetivo;

    public Videojuego() {
        setTitle("Juego de Disparo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        JPanel panelMenu = new JPanel(new GridLayout(3, 1));

        JButton iniciarBtn = new JButton("Iniciar Juego");
        iniciarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
                getContentPane().remove(panelMenu);
            }
        });
        panelMenu.add(iniciarBtn);

        JButton dificultadBtn = new JButton("Cambiar Dificultad");
        dificultadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarDificultad();
            }
        });
        panelMenu.add(dificultadBtn);

        JButton salirBtn = new JButton("Salir");
        salirBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelMenu.add(salirBtn);

        add(panelMenu, BorderLayout.CENTER);
        setVisible(true);
    }

    private void iniciarJuego() {
        setTitle("Juego de Disparo");
        getContentPane().removeAll();

        puntajeLabel = new JLabel("Puntaje: " + puntaje, JLabel.CENTER);
        add(puntajeLabel, BorderLayout.NORTH);

        panelJuego = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Component c : getComponents()) {
                    if (c instanceof JLabel) {
                        JLabel label = (JLabel) c;
                        int width = label.getWidth();
                        int height = label.getHeight();
                        g.setColor(Color.RED);
                        g.fillOval(label.getX(), label.getY(), width, height);
                    }
                }
            }
        };
        panelJuego.setLayout(null);
        add(panelJuego, BorderLayout.CENTER);

        panelJuego.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();

                for (Component c : panelJuego.getComponents()) {
                    if (c.getBounds().contains(x, y)) {
                        panelJuego.remove(c);
                        puntaje += 10;
                        puntajeLabel.setText("Puntaje: " + puntaje);
                        panelJuego.repaint();
                        break;
                    }
                }
            }
        });

        panelJuego.setFocusable(true);
        panelJuego.requestFocusInWindow();
        panelJuego.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    regresarAlMenu();
                }
            }
        });

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarObjetivos();
            }
        });
        timer.start();

        setVisible(true);
    }

    private void generarObjetivos() {
        Random random = new Random();
        int size = random.nextInt(50) + 10;
        int posX = random.nextInt(panelJuego.getWidth() - size);
        int posY = random.nextInt(panelJuego.getHeight() - size);

        JLabel objetivo = new JLabel(" +");
        objetivo.setFont(new Font("Arial", Font.PLAIN, size));
        objetivo.setBounds(posX, posY, size, size);
        panelJuego.add(objetivo);
        panelJuego.repaint();

        vidaObjetivo = new Timer(4500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelJuego != null) {
                    if (!objetivo.isVisible()) { // Verificar si el objetivo fue clicado
                        panelJuego.remove(objetivo);
                        panelJuego.repaint();
                    }
                    vidaObjetivo.stop(); // Detener el temporizador una vez que el objetivo desaparece
                }
            }
        });
        vidaObjetivo.start();

        if (timer.getDelay() == 2000) {
            vidaObjetivo.setDelay(2000);
        }
    }

    private void cambiarDificultad() {
        String[] opciones = {"Fácil", "Intermedio", "Difícil"};
        int opcionElegida = JOptionPane.showOptionDialog(
            null,
            "Selecciona la dificultad",
            "Cambiar Dificultad",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            opciones,
            opciones[0]
        );

        switch (opcionElegida) {
            case 0:
                cambiarTiempoDesaparicion(0); // Tiempo 0 indica que no desaparecen
                break;
            case 1:
                cambiarTiempoDesaparicion(4500); // 4500 milisegundos = 4.5 segundos
                break;
            case 2:
                cambiarTiempoDesaparicion(2000); // 2000 milisegundos = 2 segundos
                break;
            default:
                break;
        }

        regresarAlMenu();
    }

    private void cambiarTiempoDesaparicion(int tiempo) {
        if (timer != null) {
            timer.stop();
        }

        timer = new Timer(tiempo, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarObjetivos();
            }
        });

        timer.start();
    }

    private void regresarAlMenu() {
        getContentPane().removeAll();
        puntaje = 0;
        panelJuego = null;
        puntajeLabel = null;
        timer.stop();
        getContentPane().revalidate();
        getContentPane().repaint();

        JPanel panelMenu = new JPanel(new GridLayout(3, 1));

        JButton iniciarBtn = new JButton("Iniciar Juego");
        iniciarBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
                getContentPane().remove(panelMenu);
            }
        });
        panelMenu.add(iniciarBtn);

        JButton dificultadBtn = new JButton("Cambiar Dificultad");
        dificultadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarDificultad();
            }
        });
        panelMenu.add(dificultadBtn);

        JButton salirBtn = new JButton("Salir");
        salirBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panelMenu.add(salirBtn);

        add(panelMenu, BorderLayout.CENTER);
        setTitle("Juego de Disparo");
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Videojuego());
    }
}
