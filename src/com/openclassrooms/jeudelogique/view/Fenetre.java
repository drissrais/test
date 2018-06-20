package com.openclassrooms.jeudelogique.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import com.openclassrooms.jeudelogique.controler.SearchChallengerControler;
import com.openclassrooms.jeudelogique.observer.Observer;

public class Fenetre extends JFrame implements Observer {

	private static final long serialVersionUID = 3397622752660620083L;

	private JMenuBar menubar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenu aPropos = new JMenu("À Propos");
	private JMenu param = new JMenu("Paramètres");
	private JMenuItem nouveau = new JMenuItem("Nouveau Jeu");
	private JMenuItem quitter = new JMenuItem("Quitter");
	private JMenuItem regles = new JMenuItem("Règles du jeu");
	private JMenuItem parametres = new JMenuItem("Paramètres");

	private JToolBar toolbar = new JToolBar(JToolBar.HORIZONTAL);
	private JButton newGameButton = new JButton(new ImageIcon(getClass().getResource("/resources/newGame.png")));
	private JButton exitButton = new JButton(new ImageIcon(getClass().getResource("/resources/quitGame.png")));

	BorderLayout layout = new BorderLayout();
	private JPanel conteneur = new JPanel(layout);

	private Dimension size;
	private SearchChallengerControler controler;

	private Properties properties;
	private int nbCoupsRecherchePlusMoins, nbCasesRecherchePlusMoins;
	private String developerModeString = "";
	private boolean developerMode;

	public Fenetre(SearchChallengerControler controler, boolean developerMode) {
		setTitle("Jeux de Logique");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.developerMode = developerMode;
		if (developerMode == true) {
			developerModeString = "true";
		} else {
			developerModeString = "false";
		}

		this.controler = controler;
		size = new Dimension(this.getWidth(), this.getHeight());

		initMenu();
		initToolBar();

		conteneur.setBackground(Color.WHITE);
		conteneur.setPreferredSize(size);
		conteneur.add(new AccueilPanel().getPanel());

		add(toolbar, BorderLayout.NORTH);
		add(conteneur, BorderLayout.CENTER);
		
		properties = new Properties();
		try (InputStream input = new FileInputStream("src/resources/config.properties");
				OutputStream output = new FileOutputStream("src/resources/config.properties")) {
			properties.load(input);
			properties.setProperty("param.nbCoupsRecherchePlusMoins", "10");
			properties.setProperty("param.nbCasesRecherchePlusMoins", "4");

			properties.setProperty("param.modeDeveloppeur", this.developerModeString);
			properties.store(output, "Fichier de configuration config.properties");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	// Méthode permettant d'initialiser la barre d'outils de la fenêtre principale.
	private void initToolBar() {
		toolbar.setBorder(BorderFactory.createEmptyBorder());
		toolbar.setBackground(Color.WHITE);
		newGameButton.setFocusable(false);
		exitButton.setFocusable(false);
		toolbar.add(newGameButton);
		toolbar.add(exitButton);

		newGameButton.addActionListener(new newGameListener());
		exitButton.addActionListener((e) -> {
			properties = new Properties();
			try (InputStream input = new FileInputStream("src/resources/config.properties");
					OutputStream output = new FileOutputStream("src/resources/config.properties")) {
				properties.load(input);

				properties.setProperty("param.nbCoupsRecherchePlusMoins", "10");
				properties.setProperty("param.nbCasesRecherchePlusMoins", "4");
				properties.setProperty("param.modeDeveloppeur", "false");

				properties.store(output, "Fichier de configuration config.properties");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});
	}

	// Méthode permettant d'initialiser le menu de la fenêtre principale.
	private void initMenu() {
		// Définition des accélérateurs et mnémoniques
		nouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
		quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
		regles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		parametres.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK));
		fichier.setMnemonic('F');
		aPropos.setMnemonic('O');
		param.setMnemonic('P');

		// Construction du menu
		fichier.add(nouveau);
		fichier.addSeparator();
		fichier.add(quitter);
		aPropos.add(regles);
		param.add(parametres);
		menubar.add(fichier);
		menubar.add(aPropos);
		menubar.add(param);

		setJMenuBar(menubar);

		// Définition des listeners
		nouveau.addActionListener(new newGameListener());

		quitter.addActionListener((e) -> {
			properties = new Properties();
			try (InputStream input = new FileInputStream("src/resources/config.properties");
					OutputStream output = new FileOutputStream("src/resources/config.properties")) {
				properties.load(input);

				properties.setProperty("param.nbCoupsRecherchePlusMoins", "10");
				properties.setProperty("param.nbCasesRecherchePlusMoins", "4");
				properties.setProperty("param.modeDeveloppeur", "false");

				properties.store(output, "Fichier de configuration config.properties");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				properties = new Properties();
				try (InputStream input = new FileInputStream("src/resources/config.properties");
						OutputStream output = new FileOutputStream("src/resources/config.properties")) {
					properties.load(input);

					properties.setProperty("param.nbCoupsRecherchePlusMoins", "10");
					properties.setProperty("param.nbCasesRecherchePlusMoins", "4");
					properties.setProperty("param.modeDeveloppeur", "false");

					properties.store(output, "Fichier de configuration config.properties");
					System.exit(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	public Dimension getPreferredSize() {
		Dimension dim = new Dimension(900, 600);
		return dim;
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public class newGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			BoiteDialogueDebutDePartie boite = new BoiteDialogueDebutDePartie(null, "Nouveau Jeu", true);
			// On récupère les données enregistrées dans le fichier config.properties
			properties = new Properties();
			try (InputStream input = new FileInputStream("src/resources/config.properties")) {
				properties.load(input);
				nbCoupsRecherchePlusMoins = Integer.parseInt(properties.getProperty("param.nbCoupsRecherchePlusMoins"));
				nbCasesRecherchePlusMoins = Integer.parseInt(properties.getProperty("param.nbCasesRecherchePlusMoins"));
				developerMode = Boolean.parseBoolean(properties.getProperty("param.modeDeveloppeur"));
				if ((!boite.getzInfo().getGame().equals("")) && (!boite.getzInfo().getMode().equals(""))) {
					if (boite.getzInfo().getGame().equals("Recherche +/-")
							&& boite.getzInfo().getMode().equals("CHALLENGER")) {
						param.setEnabled(false);
						conteneur.removeAll();
						SearchChallengerPanel scp = new SearchChallengerPanel(controler.getSearchModel(), nbCoupsRecherchePlusMoins,
								nbCasesRecherchePlusMoins, developerMode);
						controler.getSearchModel().addObserver(scp);
						conteneur.add(scp.getPanel(), BorderLayout.CENTER);
						conteneur.revalidate();
						initModel();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

		// Réinitialiser les modèles
		private void initModel() {
			// this.searchModel = new SearchModel();
			// this.searchModel.addObserver(this);
		}

		public JPanel getConteneur() {
			return conteneur;
		}

	}

	@Override
	public void update(String proposition, String reponse) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accueil() {
		conteneur.removeAll();
		conteneur.add(new AccueilPanel().getPanel());
		conteneur.revalidate();
		// newGameButton.doClick();
		param.setEnabled(true);
	}

	@Override
	public void exitApplication() {
		properties = new Properties();
		try (InputStream input = new FileInputStream("src/resources/config.properties");
				OutputStream output = new FileOutputStream("src/resources/config.properties")) {
			properties.load(input);

			// Traitement pour le jeu RecherchePlusMoins
			properties.setProperty("param.nbCoupsRecherchePlusMoins", "10");
			properties.setProperty("param.nbCasesRecherchePlusMoins", "4");
			properties.setProperty("param.modeDeveloppeur", "false");

			properties.store(output, "Fichier de configuration config.properties");
			System.exit(0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
