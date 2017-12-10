import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Label;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import oracle.net.aso.e;

import javax.swing.JLabel;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class JFrameTP3 extends JFrame {

	private static final long serialVersionUID = -5406745872685262568L;// pour empêcher un warning
	
	//Définir les variables
		Statement stmt;
		Statement stmt2;
		Connection con;
		ResultSet rs;
		ResultSet rs2;
		String [] statutString = {" ", "Accepté","Préliminaire","Intermédiaire","Final","Terminé"};
		
	
	private JPanel contentPane;
	private JLabel labelProjets;
	private JButton buttonRechercher;
	private JLabel labelProjetTrouver;
	private JList listeProjets;
	private JLabel labelNom;
	private JTextField textFieldNom;
	private JLabel labelMembres;
	private JTextArea textAreaMembre;
	private JLabel labelStatut;
	private JComboBox<?> comboBoxStatut;
	private JLabel labelMontantAlloue;
	private JTextField textFieldMontantAlloue;
	private JTextField textFieldDateDebut;
	private JLabel labelDateDbut;
	private JLabel labelDateFin;
	private JTextField textFieldDateFin;
	private JButton buttonOk;
	private JButton buttonAnnuler;
	private JButton buttonArchiver;
	private JButton buttonMettreAJour;
	private JButton buttonAjouter;
	private JButton buttonQuitter;
	private JScrollPane scrollListeProjets;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameTP3 frame = new JFrameTP3();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public JFrameTP3() throws ClassNotFoundException, SQLException {
		setTitle("CRIPÉ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 664, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		labelProjets = new JLabel("Projets :");
		labelProjets.setBounds(6, 11, 51, 16);
		contentPane.add(labelProjets);
		
		buttonRechercher = new JButton("Rechercher...");
		buttonRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			buttonRechercherClick();
			
			}
		});
		buttonRechercher.setBounds(89, 6, 117, 29);
		buttonRechercher.setEnabled(true);
		contentPane.add(buttonRechercher);
		
		labelProjetTrouver = new JLabel("No.         Nom.                                                                           Montant        Statut             Date début");
		labelProjetTrouver.setBounds(6, 61, 641, 16);
		contentPane.add(labelProjetTrouver);
		
		{
		scrollListeProjets = new JScrollPane();
		scrollListeProjets.setBounds(6, 78, 641, 73);
		contentPane.add(scrollListeProjets); {	
			
			//Liste de projets dans le scrollPane
			listeProjets = new JList();
			
			listeProjets.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JList theList = (JList) e.getSource();
					if (e.getClickCount() == 1) 
			        {
			            int index = theList.locationToIndex(e.getPoint());
			            if (index >= 0) 
			            {
			                Object o = listeProjets.getModel().getElementAt(index);
			                textFieldNom.setText(o.toString());
			                textFieldMontantAlloue.setText(o.toString());
			                textFieldDateDebut.setText(o.toString());
			                textFieldDateFin.setText(o.toString());
			                comboBoxStatut.setToolTipText(o.toString());
			            }
			        }
				}
			});
			scrollListeProjets.setViewportView(listeProjets);
			listeProjets.setFont(new Font("Courier New",Font.PLAIN ,10));
			
			}
		}
		
		labelNom = new JLabel("Nom :");
		labelNom.setBounds(6, 164, 61, 16);
		contentPane.add(labelNom);
		
		textFieldNom = new JTextField();
		textFieldNom.setBounds(72, 159, 586, 26);
		textFieldNom.setEditable(false);
		contentPane.add(textFieldNom);
		textFieldNom.setColumns(10);
		
		labelMembres = new JLabel("Membres :");
		labelMembres.setBounds(6, 201, 71, 16);
		contentPane.add(labelMembres);
		
		textAreaMembre = new JTextArea();
		textAreaMembre.setBounds(77, 197, 311, 97);
		textAreaMembre.setEditable(false);
		contentPane.add(textAreaMembre);
		
		labelStatut = new JLabel("Statut : ");
		labelStatut.setBounds(396, 197, 51, 16);
		contentPane.add(labelStatut);
		
		
		comboBoxStatut = new JComboBox(statutString);
		comboBoxStatut.setBounds(502, 193, 123, 27);
		comboBoxStatut.setEnabled(false);
		contentPane.add(comboBoxStatut);
		
		labelMontantAlloue = new JLabel("Montant alloué :");
		labelMontantAlloue.setBounds(395, 225, 109, 16);
		contentPane.add(labelMontantAlloue);
		
		textFieldMontantAlloue = new JTextField();
		textFieldMontantAlloue.setEditable(false);
		textFieldMontantAlloue.setBounds(502, 220, 156, 26);
		contentPane.add(textFieldMontantAlloue);
		textFieldMontantAlloue.setColumns(10);
		
		textFieldDateDebut = new JTextField();
		textFieldDateDebut.setEditable(false);
		textFieldDateDebut.setBounds(502, 248, 156, 26);
		contentPane.add(textFieldDateDebut);
		textFieldDateDebut.setColumns(10);
		
		labelDateDbut = new JLabel("Date début : ");
		labelDateDbut.setBounds(395, 253, 81, 16);
		contentPane.add(labelDateDbut);
		
		labelDateFin = new JLabel("Date fin :");
		labelDateFin.setBounds(396, 281, 61, 16);
		contentPane.add(labelDateFin);
		
		textFieldDateFin = new JTextField();
		textFieldDateFin.setEditable(false);
		textFieldDateFin.setBounds(502, 276, 156, 26);
		contentPane.add(textFieldDateFin);
		textFieldDateFin.setColumns(10);
		
		buttonOk = new JButton("Ok");
		buttonOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			buttonOkClick();
			
			}
		});
		buttonOk.setBounds(581, 309, 77, 29);
		buttonOk.setEnabled(false);
		contentPane.add(buttonOk);
		
		buttonAnnuler = new JButton("Annuler");
		buttonAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			buttonAnnulerClick();
			
			}
		});
		buttonAnnuler.setBounds(497, 309, 88, 29);
		buttonAnnuler.setEnabled(false);
		contentPane.add(buttonAnnuler);
		
		buttonArchiver = new JButton("Archiver...");
		buttonArchiver.setBounds(308, 306, 88, 29);
		contentPane.add(buttonArchiver);
		
		buttonMettreAJour = new JButton("Mettre à jour");
		buttonMettreAJour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			buttonMettreAJourClick();
			
			}
		});
		buttonMettreAJour.setBounds(206, 306, 109, 29);
		buttonMettreAJour.setEnabled(true);
		contentPane.add(buttonMettreAJour);
		
		buttonAjouter = new JButton("Ajouter");
		buttonAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			buttonAjouterClick();
			
			}
		});
		buttonAjouter.setBounds(124, 306, 88, 29);
		contentPane.add(buttonAjouter);
		
		buttonQuitter = new JButton("Quitter");
		buttonQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			buttonQuitterClick();
			
			}
		});
		buttonQuitter.setBounds(37, 306, 88, 29);
		contentPane.add(buttonQuitter);
		
		//Connection au serveur de l'université
		String login="C##VASEN4";
	    String passwd="bd111158802";
	    
	    try {
		
			//initialisation de la connexion.
			Class.forName("oracle.jdbc.driver.OracleDriver");
		    con = DriverManager.getConnection ("jdbc:oracle:thin:@ift-p-ora12c.fsg.ulaval.ca:1521:ora12c", login, passwd);
		    
		    //Création de la requéte pour envoie de l'instruction.
			stmt = con.createStatement();
			   
			 //les requêtes SQL DML qui retournent des données 
			//ResultSet rs = stmt.executeQuery("select P.NO_PROJET, NOM_PRO, MNT_ALLOUE_PRO, STATUT_PRO, DATE_DEBUT_PRO, DATE_FIN_PRO, NOM_MEM, PRENOM_MEM "
					//+ "from TP2_PROJET P left join TP2_EQUIPE_PROJET E on P.NO_PROJET = E.NO_PROJET left join TP2_MEMBRE M on E.NO_MEMBRE = M.NO_MEMBRE "
					//+ "order by DATE_DEBUT_PRO desc");
			
			ResultSet rs = stmt.executeQuery("select NO_PROJET, NOM_PRO, MNT_ALLOUE_PRO, STATUT_PRO, DATE_DEBUT_PRO, DATE_FIN_PRO from TP2_PROJET order by DATE_DEBUT_PRO desc");
			
			//une liste dont la longueur est dynamique comme on ne sait pas le nombre d'item dans la BD
			List<String> Projets = new ArrayList<String>(6);
			
		Projets.clear();
		
		while (rs.next()) {
				
				//Ajoute chaque projet à la liste
				Projets.add(rs.getString("NO_PROJET") + " " + rs.getString("NOM_PRO")+ " " + rs.getString("MNT_ALLOUE_PRO")+ " " + rs.getString("STATUT_PRO") + " " + rs.getDate("DATE_DEBUT_PRO"));
				//Place le nom du projet dans les détails
				String NoProjet = rs.getString(1); 
				textFieldNom.setText(rs.getString(2));
				//Place le montant alloué dans les détails
				textFieldMontantAlloue.setText(rs.getString(3) + "$");
				//Place la date de début dans les détails
				textFieldDateDebut.setText(String.valueOf(rs.getDate(5)));
				//Place la date de fin dans les détails
				textFieldDateFin.setText(String.valueOf(rs.getDate(6)));	
				//Choisi et affiche dans le comboBox le statut du projet
				comboBoxStatut.setSelectedItem(rs.getString(4));
				
				//Ajoute les membres relié a chaque projet dans les détails
				//ResultSet rs2 = stmt.executeQuery("select PRENOM_MEM, NOM_MEM from TP2_EQUIPE_PROJET E, TP2_MEMBRE M where  NO_PROJET ='"+ NoProjet +"' and M.NO_MEMBRE = E.NO_MEMBRE");
					
				//while (rs2.next()) {
						
						//textAreaMembre.append(rs2.getString(1) + " " + rs2.getString(2) + "\n");
				//}
		}
				
		String[] tableauBidon = new String[Projets.size()];
        //on converti la liste en tableau
        Projets.toArray(tableauBidon);
        
        listeProjets.setModel(new AbstractListModel() {
			//on ajoute les éléments du tableau au JList
			String[] values = tableauBidon;
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		listeProjets.setSelectedIndex(0); { //Sélectionne le premier item par défaut
			
		}
		
	    } catch (Exception e) {
	    	
			if (e.getMessage().indexOf("invalid username/password")>=0) {
				textFieldNom.setText("La combinaison mot de passe usager est invalide\nFermez le programme et recommencez\nVous assurez d'écrire votre usager et mot de passe dans le code là où indiqué");
		    	}
		    else if(e.getMessage().indexOf("OracleDriver")>=0){
		    	textFieldNom.setText("Il semble que vous n'avez pas ajouté\nla librairie Oracle au projet");
		        }
	        else if(e.getMessage().indexOf("Table ou vue inexistante")>=0){
	        	textFieldNom.setText("La table n'existe pas, vous assurez de créer la table LAB06_BIDON avec les attributs NO_BIDON et TITRE_BIDON");
	        	}
	        else if(e.getMessage().indexOf("Nom de colonne non valide")>=0){
	        	textFieldNom.setText("La colonne NO_BIDON ou la colonne TITRE_BIDON n'existe pas dans la table LAB06_BIDON. Elles doivent être présentes pour que la liste fonctionne");
	        	}
	        else
		      //Pour toutes les autres erreurs qu'on ne gère pas, on affiche le message anglais
		      	{
		    	textFieldNom.setText(e.getMessage());		      
		  		}
	}


	}
	//Gestion du click sur le bouton Quitter
	private void buttonQuitterClick() {
		try {
			System.exit(0);	
			}
		
		catch(Exception ex) {
			//en cas d'erreur, affiche l'erreur retournée par Oracle ou par le pilote de connexion à Oracle
			textAreaMembre.setText(ex.getMessage());
		}
}
	//Gestion du click sur le bouton Ajouter
	private void buttonAjouterClick() {
		try {
			textFieldNom.setText("");
			textAreaMembre.setText("");
			textFieldDateDebut.setText("");
			textFieldDateFin.setText("");
			textFieldMontantAlloue.setText("");
			textAreaMembre.setEditable(true);
			textFieldNom.setEditable(true);
			textFieldDateDebut.setEditable(true);
			textFieldDateFin.setEditable(true);
			textFieldMontantAlloue.setEditable(true);
			comboBoxStatut.setEnabled(true);
			comboBoxStatut.setSelectedItem(" ");
			buttonMettreAJour.setEnabled(true);
			buttonOk.setEnabled(true);
			
		}catch(Exception ex) {
			//en cas d'erreur, affiche l'erreur retournée par Oracle ou par le pilote de connexion à Oracle
			textAreaMembre.setText(ex.getMessage());
		}
	String srtMAJouAJ = "AJ";
}
	//Gestion du click sur le bouton Mettre à jour
	private void buttonMettreAJourClick() {
		try {
			textAreaMembre.setEditable(false);
			textFieldNom.setEditable(true);
			textFieldDateFin.setEditable(true);
			textFieldDateDebut.setEditable(true);
			textFieldMontantAlloue.setEditable(true);
			comboBoxStatut.setEnabled(true);
			buttonAnnuler.setEnabled(true);
			buttonOk.setEnabled(true);
			buttonQuitter.setEnabled(false);
			buttonMettreAJour.setEnabled(false);
			buttonArchiver.setEnabled(false);
			buttonAjouter.setEnabled(false);
		
			}
		
		catch(Exception ex) {
			//en cas d'erreur, affiche l'erreur retournée par Oracle ou par le pilote de connexion à Oracle
			textAreaMembre.setText(ex.getMessage());
		}
	String strMAJouAJ = "MAJ";
}
	//Gestion du click sur le boutton Ok
	private void buttonOkClick() {
		try {
			//if (strMAJouAJ.equals("AJ")); {
			String insert = "INSERT INTO TP2_PROJET(NO_PROJET, NOM_PRO, MNT_ALLOUE_PRO, STATUT_PRO, DATE_DEBUT_PRO, DATE_FIN_PRO) "
				    + "VALUES (NO_PROJET_SEQ.nextval, ?, ?, ?, ?, ?)";
			
				try (PreparedStatement pstm = con.prepareStatement(insert)) {
				    pstm.setString(1, textFieldNom.getText());
				    pstm.setString(2, textFieldMontantAlloue.getText());
				    String statut = (String) comboBoxStatut.getSelectedItem();
				    pstm.setString(3, statut);
				    pstm.setString(4, textFieldDateDebut.getText());
				    pstm.setString(5, textFieldDateFin.getText());
	
				    pstm.executeUpdate();
	
				    JOptionPane.showMessageDialog(null, "Projet ajouté :'" +textFieldNom.getText()+ "'");		   
				    } 
			//}
			//else if (strMAJouAJ.equals("MAJ")); {
			String update = "UPDATE TP2_PROJET SET NOM_PRO ='"+textFieldNom.getText() + "', MNT_ALLOUE_PRO = '"+textFieldMontantAlloue.getText() 
			+ "',STATUT_PRO='"+comboBoxStatut.getSelectedItem() + "', DATE_DEBUT_PRO='"+textFieldDateDebut.getText() + "', DATE_FIN_PRO='"
					+textFieldDateFin.getText() +"' WHERE NO_PROJET ='"+ rs.getString("NO_PROJET") +"'" ;
				
				try (PreparedStatement pstm = con.prepareStatement(update)) {
				    pstm.setString(1, textFieldNom.getText());
				    pstm.setString(2, textFieldMontantAlloue.getText());
				    String statut = (String) comboBoxStatut.getSelectedItem();
				    pstm.setString(3, statut);
				    pstm.setString(4, textFieldDateDebut.getText());
				    pstm.setString(5, textFieldDateFin.getText());
	
				    pstm.executeUpdate();
	
				    JOptionPane.showMessageDialog(null, "Projet mis à jour :'" +textFieldNom.getText()+ "'");		   
				    } 
			//}
		}catch(Exception ex) {
				//en cas d'erreur, affiche l'erreur retournée par Oracle ou par le pilote de connexion à Oracle
				textAreaMembre.setText(ex.getMessage());
			}		
		
	}
	//Gestion du click sur le bouton Annuler
		private void buttonAnnulerClick() {
			try {
					
				}
			
			catch(Exception ex) {
				//en cas d'erreur, affiche l'erreur retournée par Oracle ou par le pilote de connexion à Oracle
				textAreaMembre.setText(ex.getMessage());
			}
	}
	//Gestion du click sur le bouton Rechercher...
		private void buttonRechercherClick() {
			try {
				String rechercheProjet = JOptionPane.showInputDialog(null, "Quel est le nom du projet rechercher? :");
				String rechercheNom = JOptionPane.showInputDialog(null, "Quel est le nom du membre rechercher ? :");
				String recherchePrenom = JOptionPane.showInputDialog(null, "Quel est le prenom du membre rechercher ? :");
				
						
					if (rechercheNom != null && recherchePrenom != null && recherchePrenom != null); {
							ResultSet rsrecherche = stmt.executeQuery("select P.NO_PROJET, NOM_PRO, MNT_ALLOUE_PRO, STATUT_PRO, DATE_DEBUT_PRO, DATE_FIN_PRO, NOM_MEM, PRENOM_MEM "
									+ "from TP2_PROJET P, TP2_EQUIPE_PROJET E, TP2_MEMBRE M where P.NO_PROJET = E.NO_PROJET and E.NO_MEMBRE = M.NO_MEMBRE and NOM_PRO ='" +rechercheProjet+ "'"
									+ "and NOM_MEM ='"+rechercheNom+"' and PRENOM_MEM ='" +recherchePrenom+"'");
					
							while (rs.next()) {
								textFieldNom.setText(rsrecherche.getString(2));
								textFieldMontantAlloue.setText(rsrecherche.getString(3));
								textFieldDateDebut.setText(rsrecherche.getString(5));
								textFieldDateFin.setText(rsrecherche.getString(6)); }
								
					}
				}
			
			catch(Exception ex) {
				//en cas d'erreur, affiche l'erreur retournée par Oracle ou par le pilote de connexion à Oracle
				textAreaMembre.setText(ex.getMessage());
			}
	}
}
