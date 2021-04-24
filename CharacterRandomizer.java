package randomizer;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class CharacterRandomizer {
	
	JFrame window;
	JButton roll, create;
	JTextArea message;
	String intro, personality, background, trait;
	static String clas, race, subclass;
	JPanel messagePanel, buttonPanel;
	static int level;
	
	public CharacterRandomizer() {
		//main frame area settings
		window = new JFrame("Introduce your character:");
		window.setSize(401,310);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().setBackground(Color.black);
		window.setLayout(null);
		
		//panel where message appears
		messagePanel = new JPanel();
		messagePanel.setBounds(5,5,379,220);
		messagePanel.setBackground(Color.black);
		messagePanel.setBorder(BorderFactory.createLineBorder(Color.white));
		window.add(messagePanel);
		
		//panel for where buttons appear
		buttonPanel = new JPanel();
		buttonPanel.setBounds(90,230,205,35);
		buttonPanel.setBackground(Color.black);
		buttonPanel.setLayout(new GridLayout(1,2));
		window.add(buttonPanel);
		
		//formatting of message
		message = new JTextArea(10,26); //new thing learned: numbers are for rows,columns
		message.setBackground(Color.black);
		message.setForeground(Color.white);
		message.setEditable(false);
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		messagePanel.add(message);
		
		//settings for character feature rolling button
		roll = new JButton("Let's Get Funky!");
		roll.setBackground(Color.black);
		roll.setForeground(Color.white);
		roll.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		roll.setFocusPainted(false);
		buttonPanel.add(roll);
		
		//settings for character sheet creating button
		create = new JButton("Make It Happen!");
		create.setBackground(Color.black);
		create.setForeground(Color.white);
		create.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		create.setFocusPainted(false);
		create.setEnabled(false);
		buttonPanel.add(create);
		
		window.setVisible(true);
		
		//button actions for roll
		roll.addActionListener(e -> {
			intro = getIntro();
			clas = getClas();
			personality = getTrait();
			race = getRace();
			background = getBackground(); 
			trait = getPurpose();
			message.setText(String.format("%sA%s %s %s from %s who %s.", intro, personality, race, clas, background, trait));
			create.setEnabled(true);
		});
		
		//button actions for create
		create.addActionListener(e -> {
			//time to roll a character
			int[] stats = new int[6];
			for(int i = 0; i < 6; i++) {
				stats[i] = stat();
				if(stats[i] < 8) stats[i] = stat()+1;
				if(stats[i] > 18) stats[i] = 18;
			}
			
			//sort array for easy use later, it sorts least -> greatest
			Arrays.sort(stats);
			
			//frame for asking
			JFrame askLevel = new JFrame("Enter Level");
			askLevel.setSize(200,100);
			askLevel.setLayout(new FlowLayout());
			askLevel.getContentPane().setBackground(Color.black);
			askLevel.setLocationRelativeTo(null);
			
			JLabel lvl = new JLabel("Level: ");
			lvl.setForeground(Color.white);
			lvl.setBackground(Color.black);
			lvl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			askLevel.add(lvl);
			
			String[] choices = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
			JComboBox<String> cb = new JComboBox<String>(choices);
			cb.setBackground(Color.black);
			cb.setForeground(Color.white);
			askLevel.add(cb);
			
			JButton select = new JButton("Select");
			select.setBackground(Color.black);
			select.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			select.setBackground(Color.black);
			select.setForeground(Color.white);
			select.setFocusPainted(false);
			askLevel.add(select);
			
			askLevel.setVisible(true);
			
			JFrame charOutput = new JFrame("Here it is:");
			
			short str,dex,cha,wis,intl,con,hitDie; //for rolling stats
			str=dex=cha=wis=intl=con=10;
			final short ac;
			hitDie=8;
			
			//set up stat array
			short[] statArray = new short[6];
			
			//roll stat array
			for (int i = 0; i<6; i++) statArray[i] = statRoll();
			Arrays.sort(statArray); //is now least->greatest
			
			//properly arrange stats
			subclass = getSubclass();
			
			if(clas.equals("artificer")) {
				str = statArray[0];
				cha = statArray[1];
				wis = statArray[2];
				con = statArray[3];
				dex = statArray[4];
				intl = statArray[5];
			}
			
			if(clas.equals("barbarian")) {
				intl = statArray[0];
				cha = statArray[1];
				wis = statArray[2];
				dex = statArray[3];
				con = statArray[4];
				str = statArray[5];
			}
			
			if(clas.equals("bard")) {
				str = statArray[0];
				wis = statArray[1];
				intl = statArray[2];
				con = statArray[3];
				dex = statArray[4];
				cha = statArray[5];
			}
			
			if(clas.equals("cleric")) {
				cha = statArray[0];
				intl = statArray[1];
				str = statArray[2];
				dex = statArray[3];
				con = statArray[4];
				wis = statArray[5];
			}
			
			if(clas.equals("druid")) {
				cha = statArray[0];
				str = statArray[1];
				intl = statArray[2];
				dex = statArray[3];
				con = statArray[4];
				wis = statArray[5];
			}
			
			if(clas.equals("fighter")) {
				if(subclass.equals("psi warrior") || subclass.equals("eldritch knight")) {
					dex = statArray[0];
					cha = statArray[1];
					wis = statArray[2];
					intl = statArray[3];
					con = statArray[4];
					str = statArray[5];
				} else {
					dex = statArray[0];
					intl = statArray[1];
					cha = statArray[2];
					wis = statArray[3];
					con = statArray[4];
					str = statArray[5];
				}
			}
			
			if(clas.equals("monk")) {
				cha = statArray[0];
				intl = statArray[1];
				str = statArray[2];
				wis = statArray[3];
				con = statArray[4];
				dex = statArray[5];
			}
			
			if(clas.equals("paladin")) {
				dex = statArray[0];
				intl = statArray[1];
				wis = statArray[2];
				con = statArray[3];
				cha = statArray[4];
				str = statArray[5];
			}
			
			if(clas.equals("ranger")) {
				cha = statArray[0];
				str = statArray[1];
				intl = statArray[2];
				wis = statArray[3];
				con = statArray[4];
				dex = statArray[5];
			}
			
			if(clas.equals("rogue")) {
				if(subclass.equals("arcane trickster")) {
					str = statArray[0];
					cha = statArray[1];
					wis = statArray[2];
					con = statArray[3];
					intl = statArray[4];
					dex = statArray[5];
				} else {
					str = statArray[0];
					intl = statArray[1];
					cha = statArray[2];
					wis = statArray[3];
					con = statArray[4];
					dex = statArray[5];
				}
			}
			
			if(clas.equals("sorcerer")) {
				str = statArray[0];
				wis = statArray[1];
				intl = statArray[2];
				dex = statArray[3];
				con = statArray[4];
				cha = statArray[5];
			}
			
			if(clas.equals("warlock")) {
				if(subclass.equals("hexblade")) {
					wis = statArray[0];
					str = statArray[1];
					intl = statArray[2];
					con = statArray[3];
					dex = statArray[4];
					cha = statArray[5];
				} else {
					str = statArray[0];
					wis = statArray[1];
					intl = statArray[2];
					dex = statArray[3];
					con = statArray[4];
					cha = statArray[5];
				}
			}
			
			if(clas.equals("wizard")) {
				str = statArray[0];
				cha = statArray[1];
				wis = statArray[2];
				dex = statArray[3];
				con = statArray[4];
				intl = statArray[5];
			}
			
			//set racial ability increases
			if(race.contains("tiefling")) {
				cha += 2;
				if(race.contains("glasya")) dex += 1;
			}
			
			//set base ac
			//ac is a final int for some bug reasons, so can't edit it outside of this block
			if(clas.equals("monk")) ac = (short)(10 + ((((dex < 10) ? dex-1:dex)-10)/2) + ((((wis < 10) ? wis-1:wis)-10)/2));
			else if(clas.equals("barbarian")) ac = (short)(10 + ((((dex < 10) ? dex-1:dex)-10)/2) + ((((con < 10) ? con-1:con)-10)/2));
			else ac = (short)(10 + (((dex < 10) ? dex-1:dex)-10)/2);
			
			//arraylists for keeping track of feat names and such
			ArrayList<String> feats = new ArrayList<String>();
			ArrayList<String> choice = new ArrayList<String>();
			
			//frame to output character info
			charOutput.setSize(378,300);
			charOutput.getContentPane().setBackground(Color.black);
			charOutput.setLayout(null);
			
			//general character information
			JPanel general = new JPanel();
			general.setBounds(5,5,355,20);
			general.setBackground(Color.black);
			general.setLayout(new FlowLayout());
			
			//Printout of info
			JLabel info = new JLabel();
			info.setBackground(Color.black);
			info.setForeground(Color.white);
			info.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			info.setText(String.format("class: %s %s %s | race: %s | unarmored ac: %s", ((subclass.equals("n/a")) ? "" : subclass), 
					clas, Integer.toString(level), race, Short.toString(ac)));
			info.setBorder(BorderFactory.createLineBorder(Color.white));
			general.add(info);
			charOutput.add(general);
			
			//stats
			JPanel statList = new JPanel();
			statList.setBounds(26,30,300,50);
			statList.setLayout(new GridLayout(2,6));
			statList.setBackground(Color.black);
			
			//add all the stat names
			JLabel strLabel = new JLabel("STR");
			JLabel dexLabel = new JLabel("DEX");
			JLabel conLabel = new JLabel("CON");
			JLabel intLabel = new JLabel("INT");
			JLabel wisLabel = new JLabel("WIS");
			JLabel chaLabel = new JLabel("CHA");
			
			strLabel.setForeground(Color.white);
			dexLabel.setForeground(Color.white);
			conLabel.setForeground(Color.white);
			intLabel.setForeground(Color.white);
			wisLabel.setForeground(Color.white);
			chaLabel.setForeground(Color.white);
			
			strLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			dexLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			conLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			intLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			wisLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			chaLabel.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			
			strLabel.setBorder(BorderFactory.createLineBorder(Color.white));
			dexLabel.setBorder(BorderFactory.createLineBorder(Color.white));
			conLabel.setBorder(BorderFactory.createLineBorder(Color.white));
			intLabel.setBorder(BorderFactory.createLineBorder(Color.white));
			wisLabel.setBorder(BorderFactory.createLineBorder(Color.white));
			chaLabel.setBorder(BorderFactory.createLineBorder(Color.white));
			
			statList.add(strLabel);
			statList.add(dexLabel);
			statList.add(conLabel);
			statList.add(intLabel);
			statList.add(wisLabel);
			statList.add(chaLabel);
			
			//add space for the stats to be written
			JLabel strNum = new JLabel(Short.toString(str) + "("+ Short.toString((short)((((str < 10) ? str-1:str)-10)/2)) + ")");
			JLabel dexNum = new JLabel(Short.toString(dex) + "("+ Short.toString((short)((((dex < 10) ? dex-1:dex)-10)/2)) + ")");
			JLabel conNum = new JLabel(Short.toString(con) + "("+ Short.toString((short)((((con < 10) ? con-1:con)-10)/2)) + ")");
			JLabel intNum = new JLabel(Short.toString(intl) + "("+ Short.toString((short)((((intl < 10) ? intl-1:intl)-10)/2)) + ")");
			JLabel wisNum = new JLabel(Short.toString(wis) + "("+ Short.toString((short)((((wis < 10) ? wis-1:wis)-10)/2)) + ")");
			JLabel chaNum = new JLabel(Short.toString(cha) + "("+ Short.toString((short)((((cha < 10) ? cha-1:cha)-10)/2)) + ")");
			
			strNum.setForeground(Color.white);
			dexNum.setForeground(Color.white);
			conNum.setForeground(Color.white);
			intNum.setForeground(Color.white);
			wisNum.setForeground(Color.white);
			chaNum.setForeground(Color.white);
			
			strNum.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			dexNum.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			conNum.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			intNum.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			wisNum.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			chaNum.setFont(new Font("Times New Roman", Font.PLAIN, 12));
			
			strNum.setBorder(BorderFactory.createLineBorder(Color.white));
			dexNum.setBorder(BorderFactory.createLineBorder(Color.white));
			conNum.setBorder(BorderFactory.createLineBorder(Color.white));
			intNum.setBorder(BorderFactory.createLineBorder(Color.white));
			wisNum.setBorder(BorderFactory.createLineBorder(Color.white));
			chaNum.setBorder(BorderFactory.createLineBorder(Color.white));
			
			statList.add(strNum);
			statList.add(dexNum);
			statList.add(conNum);
			statList.add(intNum);
			statList.add(wisNum);
			statList.add(chaNum);
			
			charOutput.add(statList);
			
			//labels for feature and choices
			JPanel featLabel = new JPanel();
			featLabel.setBounds(35,85,90,20);
			featLabel.setBackground(Color.black);
			JLabel fl = new JLabel("Features:");
			fl.setBackground(Color.black);
			fl.setForeground(Color.white);
			fl.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			featLabel.add(fl);
			charOutput.add(featLabel);
			JPanel choiceLabel = new JPanel();
			choiceLabel.setBounds(215,85,90,20);
			choiceLabel.setBackground(Color.black);
			JLabel cl = new JLabel("Choices Left:");
			cl.setBackground(Color.black);
			cl.setForeground(Color.white);
			cl.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			choiceLabel.add(cl);
			charOutput.add(choiceLabel);
			
			//feature names
			JPanel featsPanel = new JPanel();
			featsPanel.setBounds(5,110,175,150);
			featsPanel.setBackground(Color.magenta);
			charOutput.add(featsPanel);
			
			//choices to make
			JPanel makeChoice = new JPanel();
			makeChoice.setBounds(185,110,175,150);
			makeChoice.setBackground(Color.blue);
			charOutput.add(makeChoice);
			
			//make the button do things
			select.addActionListener(v -> {
				level = Integer.parseInt((String)(cb.getSelectedItem()));
				askLevel.dispose();
				
				//reset info line
				info.setText(String.format("class: %s %s %s | race: %s | unarmored ac: %s", ((subclass.equals("n/a")) ? "" : subclass), 
						clas, Integer.toString(level), race, Short.toString(ac)));
				
				//wait to make it real until processing is done
				charOutput.setVisible(true);
				
				//fill out feature blocks
				if(clas.equals("artificer")) {
					
				}
				
				if(clas.equals("barbarian")) {
									
				}
				
				if(clas.equals("bard")) {
					
				}
				
				if(clas.equals("cleric")) {
					
				}
				
				if(clas.equals("druid")) {
					
				}
				
				if(clas.equals("fighter")) {
					
				}
				
				if(clas.equals("monk")) {
					
				}
				
				if(clas.equals("paladin")) {
					
				}
				
				if(clas.equals("ranger")) {
					
				}
				
				if(clas.equals("rogue")) {
					
				}
				
				if(clas.equals("sorcerer")) {
					
				}
				
				if(clas.equals("warlock")) {
					
				}
				
				if(clas.equals("wizard")) {
					
				}
				
				//wait a sec, then open webpages
				openWebpage("https://media.wizards.com/2016/dnd/downloads/5E_CharacterSheet_Fillable.pdf"); //Character sheet
			});
		});
	}
	
	public static void main(String[] args) {
		new CharacterRandomizer();
	}
	
	//roll a stat
	public static int stat() {
		int[] rolls = new int[4];
		for(int i = 0; i < 4; i++) {
			rolls[i] = (int)(Math.random()*6 + 1);
			if(rolls[i] == 1) rolls[i] = (int)(Math.random()*6 + 1);
		}
		
		//4d6 drop lowest, so first index not included
		return (rolls[1]+rolls[2]+rolls[3]);
	}
	
	//random intro statement
	public static String getIntro() {
		
		String in = "";
		
		int rand = (int)(Math.random()*5);
		
		if (rand == 0) in = "Hear me out on this: ";
		if (rand == 1) in = "Alright, I got one for you: ";
		if (rand == 2) in = "I'm excited to play: ";
		if (rand == 3) in = "Who's ready to deal with: ";
		if (rand == 4) in = "Lemme roll the stats for: ";
		
		return in + "\n\n";
	}
	
	//choose a class. artificer is less likely because it is less used
	public static String getClas() {
		int rand = (int)(Math.random()*25);
		
		if (rand == 0 || rand == 1) return "barbarian";
		if (rand == 2 || rand == 3) return "bard";
		if (rand == 4 || rand == 5) return "cleric";
		if (rand == 6 || rand == 7) return "druid";
		if (rand == 8 || rand == 9) return "fighter";
		if (rand == 10 || rand == 11) return "monk";
		if (rand == 12 || rand == 13) return "paladin";
		if (rand == 14 || rand == 15) return "ranger";
		if (rand == 16 || rand == 17) return "rogue";
		if (rand == 18 || rand == 19) return "sorcerer";
		if (rand == 20 || rand == 21) return "warlock";
		if (rand == 22 || rand == 23) return "wizard";
		else return "artificer";
	}
	
	//TODO: list all racial feats, use txt file with list of used races
	//returns an array including racial features
	public String[] getRacialFeatures() {
		ArrayList<String> features = new ArrayList<String>();
		
		if(race.contains("tiefling")) {
			features.add("-Darkvision(60ft)");
			features.add("-Speed: 30ft");
			features.add("-Hellish Resistance");
		}
		
		return (String[])(features.toArray());
	}
	
	//pick a subclass. return "n/a" if none is chosen
	public static String getSubclass() {
		
		if(clas.equals("artificer")) {
			if (level < 3) return "n/a";
			int rand = (int)(Math.random()*4);
			if(rand == 0) return "artillerist";
			if(rand == 1) return "armorer";
			if(rand == 2) return "battlesmith";
			else return "alchemist";
		}
		
		if(clas.equals("barbarian")) {
			if (level < 3) return "n/a";
			int rand = (int)(Math.random()*8);
			if(rand == 0) return "path of the ancestral guardian";
			if(rand == 1) return "path of the battlerager";
			if(rand == 2) return "path of the beast";
			if(rand == 3) return "path of the berserker";
			if(rand == 4) return "path of the storm herald";
			if(rand == 5) return "path of the totem warrior";
			if(rand == 6) return "path of the zealot";
			else return "path of wild magic";
		}
		
		if(clas.equals("bard")) {
			if(level < 3) return "n/a";
			int rand = (int)(Math.random()*7);
			if(rand == 0) return "college of whispers";
			if(rand == 1) return "college of valor";
			if(rand == 2) return "college of swords";
			if(rand == 3) return "college if lore";
			if(rand == 4) return "college of glamour";
			if(rand == 5) return "college of eloquence";
			else return "college of creation";
		}
		
		if(clas.equals("cleric")) {
			int rand = (int)(Math.random()*14);
			if(rand == 0) return "arcana domain";
			if(rand == 1) return "death domain";
			if(rand == 2) return "forge domain";
			if(rand == 3) return "grave domain";
			if(rand == 4) return "knowledge domain";
			if(rand == 5) return "life domain";
			if(rand == 6) return "light domain";
			if(rand == 7) return "nature domain";
			if(rand == 8) return "order domain";
			if(rand == 9) return "peace domain";
			if(rand == 10) return "tempest domain";
			if(rand == 11) return "trickery domain";
			if(rand == 12) return "twilight domain";
			else return "war domain";
		}
		
		if(clas.equals("druid")) {
			if(level < 2) return "n/a";
			int rand = (int)(Math.random()*7);
			if(rand == 0) return "circle of dreams";
			if(rand == 1) return "circle of spores";
			if(rand == 2) return "circle of stars";
			if(rand == 3) return "circle of the wildfire";
			if(rand == 4) return "circle of the moon";
			if(rand == 5) return "circle of the land";
			else return "circle of the shepherd";
		}
		
		if(clas.equals("fighter")) {
			if(level < 3) return "n/a";
			int rand = (int)(Math.random()*10);
			if(rand == 0) return "samurai";
			if(rand == 1) return "rune knight";
			if(rand == 2) return "purple dragon knight";
			if(rand == 3) return "eldritch knight";
			if(rand == 4) return "echo knight";
			if(rand == 5) return "champion";
			if(rand == 6) return "cavalier";
			if(rand == 7) return "battle master";
			else return "psi warrior";
		}
		
		if(clas.equals("monk")) {
			if(level < 3) return "n/a";
			String sc = "way of";
			int rand = (int)(Math.random()*9);
			if(rand == 0) return sc + "mercy";
			if(rand == 1) return sc + "shadow";
			if(rand == 2) return sc + "the astral self";
			if(rand == 3) return sc + "the drunken master";
			if(rand == 4) return sc + "the four elements";
			if(rand == 5) return sc + "the long death";
			if(rand == 6) return sc + "the open hand";
			if(rand == 7) return sc + "the kensei";
			else return sc + "the sun soul";
		}
		
		if(clas.equals("paladin")) {
			if (level < 3) return "n/a";
			int rand = (int)(Math.random()*9);
			if(rand == 0) return "oath of conquest";
			if(rand == 1) return "oath of devotion";
			if(rand == 2) return "oath of glory";
			if(rand == 3) return "oath of redemption";
			if(rand == 4) return "oath of the ancients";
			if(rand == 5) return "oath of the crown";
			if(rand == 6) return "oath of the watchers";
			if(rand == 7) return "oath of vengeance";
			else return "oathbreaker";
		}
		
		if(clas.equals("ranger")) {
			if (level < 3) return "n/a";
			int rand = (int)(Math.random()*7);
			if(rand == 0) return "beastmaster";
			if(rand == 1) return "fey wanderer";
			if(rand == 2) return "gloom stalker";
			if(rand == 3) return "horizon walker";
			if(rand == 4) return "swarm keeper";
			if(rand == 5) return "monster slayer";
			else return "hunter";
		}
		
		if(clas.equals("rogue")) {
			if (level < 3) return "n/a";
			int rand = (int)(Math.random()*9);
			if(rand == 0) return "arcane trickster";
			if(rand == 1) return "assassin";
			if(rand == 2) return "inquisitive";
			if(rand == 3) return "phantom";
			if(rand == 4) return "mastermind";
			if(rand == 5) return "scout";
			if(rand == 6) return "soul knife";
			if(rand == 7) return "swashbuckler";
			else return "thief";
		}
		
		if(clas.equals("sorcerer")) {
			int rand = (int)(Math.random()*7);
			if(rand == 0) return "aberrant mind";
			if(rand == 1) return "clockwork soul";
			if(rand == 2) return "divine soul";
			if(rand == 3) return "draconic bloodline";
			if(rand == 4) return "shadow magic";
			if(rand == 5) return "storm";
			else return "wild magic";
		}
		
		if(clas.equals("warlock")) {
			int rand = (int)(Math.random()*8);
			if(rand == 0) return "archfey patron";
			if(rand == 1) return "celestial patron";
			if(rand == 2) return "fathomless patron";
			if(rand == 3) return "fiend patron";
			if(rand == 4) return "genie patron";
			if(rand == 5) return "great old one patron";
			if(rand == 6) return "hexblade";
			else return "undying patron";
		}
		
		if(clas.equals("wizard")) {
			if(level < 2) return "n/a";
			int rand = (int)(Math.random()*13);
			if(rand == 0) return "bladesinging";
			if(rand == 1) return "chronurgy";
			if(rand == 2) return "graviturgy";
			if(rand == 3) return "order of the scribes";
			if(rand == 4) return "abjuration";
			if(rand == 5) return "conjuration";
			if(rand == 6) return "divination";
			if(rand == 7) return "enchantment";
			if(rand == 8) return "evocation";
			if(rand == 9) return "illusion";
			if(rand == 10) return "necromancy";
			if(rand == 11) return "transmutation";
			else return "war Magic";
		}
		
		return "n/a";
	}
	
	//return a race that fits the character's class
	public static String getRace() {
		
		//eladrin has extra subraces
		boolean eladrin = false;
		
		if (clas.equals("artificer")) {
			int rand = (int)(Math.random()*8);
			
			if(rand == 0) return "high elf";
			if(rand == 1) return "fire genasi";
			if(rand == 2) return "githzerai";
			if(rand == 3) return "rock gnome";
			if(rand == 4) return "hobgoblin";
			if(rand == 5) return "mepistopholes tiefling";
			if(rand == 6) return "yuan-ti pureblood";
			if(rand == 7) return "warforged";
		}
		
		if(clas.equals("barbarian")) {
			int rand = (int)(Math.random()*8);
			
			if(rand == 0) return "bugbear";
			if(rand == 1) return "dragonborn";
			if(rand == 2) return "mountain dwarf";
			if(rand == 3) return "earth genasi";
			if(rand == 4) return "githyanki";
			if(rand == 5) return "goliath";
			if(rand == 6) return "half-orc";
			if(rand == 7) return "tortle";
		}
		
		if(clas.equals("bard")) {
			int rand = (int)(Math.random()*9);
			
			if(rand == 0) eladrin = true;
			if(rand == 1) return "lightfoot halfling";
			if(rand == 2) return "tabaxi";
			if(rand == 3) return "dispater tiefling";
			if(rand == 4) return "glasya tiefling";
			if(rand == 5) return "devil's tongue tiefling";
			if(rand == 6) return "triton";
			if(rand == 7) return "verdan";
			if(rand == 8) return "yuan-ti pureblood";
		}
		
		if(clas.equals("cleric")) {
			int rand = (int)(Math.random()*5);
			
			if(rand == 0) return "protector aasimar";
			if(rand == 1) return "hill dwarf";
			if(rand == 2) return "firbolg";
			if(rand == 3) return "tortle";
			if(rand == 5) return "water genasi";
		}

		if(clas.equals("druid")) {
			int rand = (int)(Math.random()*5);
			
			if(rand == 0) return "aarakocra";
			if(rand == 1) return "wood elf";
			if(rand == 2) return "firbolg";
			if(rand == 3) return "water genasi";
			if(rand == 4) return "ghostwise halfling";
		}

		if(clas.equals("fighter")) {
			int rand = (int)(Math.random()*6);
			
			if(rand == 0) return "scourge aasimar";
			if(rand == 1) return "mountain dwarf";
			if(rand == 2) return "goliath";
			if(rand == 3) return "human";
			if(rand == 4) return "stout halfling";
			if(rand == 5) return "kobold";
		}

		if(clas.equals("monk")) {
			int rand = (int)(Math.random()*7);
			
			if(rand == 0) return "aarakocra";
			if(rand == 1) return "wood elf";
			if(rand == 2) return "goblin";
			if(rand == 3) return "kenku";
			if(rand == 4) return "lizardfolk";
			if(rand == 5) return "kobold";
			if(rand == 6) return "tabaxi";
		}

		if(clas.equals("paladin")) {
			int rand = (int)(Math.random()*6);
			
			if(rand == 0) return "fallen aasimar";
			if(rand == 1) return "scourge aasimar";
			if(rand == 2) eladrin = true;
			if(rand == 3) return "zariel tiefling";
			if(rand == 4) return "triton";
			if(rand == 5) return "yuan-ti pureblood";
		}

		if(clas.equals("ranger")) {
			int rand = (int)(Math.random()*7);
			
			if(rand == 0) return "aarakocra";
			if(rand == 1) return "shadar-kai elf";
			if(rand == 2) return "wood elf";
			if(rand == 3) return "goblin";
			if(rand == 4) return "stout halfling";
			if(rand == 5) return "kenku";
			if(rand == 6) return "kobold";
		}

		if(clas.equals("rogue")) {
			int rand = (int)(Math.random()*7);
			
			if(rand == 0) eladrin = true;
			if(rand == 1) return "high elf";
			if(rand == 2) return "lightfoot halfing";
			if(rand == 3) return "goblin";
			if(rand == 4) return "kenku";
			if(rand == 5) return "tabaxi";
			if(rand == 6) return "glasya tiefling";
		}

		if(clas.equals("sorcerer")) {
			int rand = (int)(Math.random()*7);
			
			if(rand == 0) eladrin = true;
			if(rand == 1) return "dispater tiefling";
			if(rand == 2) return "glasya tiefling";
			if(rand == 3) return "levistus tiefling";
			if(rand == 4) return "devil's tongue tiefling";
			if(rand == 5) return "verdan";
			if(rand == 6) return "yuan-ti pureblood";
		}

		if(clas.equals("warlock")) {
			int rand = (int)(Math.random()*6);
			
			if(rand == 0) eladrin = true;
			if(rand == 1) return "kobold";
			if(rand == 2) return "verdan";
			if(rand == 3) return "yuan-ti pureblood";
			if(rand == 4) return "satyr";
			if(rand == 5) return "simic hybrid";
		}

		if(clas.equals("wizard")) {
			int rand = (int)(Math.random()*5);
			
			if(rand == 0) return "githyanki";
			if(rand == 1) return "forest gnome";
			if(rand == 2) return "variant eladrin";
			if(rand == 3) return "high elf";
			if(rand == 4) return "hobgoblin";
		}
		
		//eladrin has its own subraces
		if(eladrin) {
			int rand = (int)(Math.random()*4);
			
			if(rand == 0) return "autumn eladrin";
			if(rand == 1) return "winter eladrin";
			if(rand == 2) return "spring eladrin";
			if(rand == 3) return "summer eladrin";
		}
		
		//the catch-all
		return "human";
	}
	
	public static String getTrait() {
		int rand = (int)(Math.random()*44);
		
		if(rand == 0) return "n effervescent";
		if(rand == 1) return " morally dubious";
		if(rand == 2) return "n ambitious";
		if(rand == 3) return " stuck-up";
		if(rand == 4) return "n optimistic";
		if(rand == 5) return " pessimistic";
		if(rand == 6) return " friendly";
		if(rand == 7) return " naive";
		if(rand == 8) return " conservative";
		if(rand == 9) return " childish";
		if(rand == 10) return " seductive";
		if(rand == 11) return " funky fresh";
		if(rand == 12) return " narcissistic";
		if(rand == 13) return " reckless";
		if(rand == 14) return "n inquisitive";
		if(rand == 15) return " shady";
		if(rand == 16) return "n enterprising";
		if(rand == 17) return " miserable";
		if(rand == 18) return " proud";
		if(rand == 19) return " spoiled";
		if(rand == 20) return " sassy";
		if(rand == 21) return " tired";
		if(rand == 22) return " sentimental";
		if(rand == 23) return " mom-friend";
		if(rand == 24) return " tweaking";
		if(rand == 25) return "n untrusting";
		if(rand == 26) return " courageous";
		if(rand == 27) return " cheesy";
		if(rand == 28) return "n intensely gay";
		if(rand == 29) return "n agreeable";
		if(rand == 30) return " somber";
		if(rand == 31) return " melancholy";
		if(rand == 32) return " tea-addicted";
		if(rand == 33) return "n animal-loving";
		if(rand == 34) return "n unforgivably dumb";
		if(rand == 35) return " thick-headed";
		if(rand == 36) return "n arrogant";
		if(rand == 37) return " sweet";
		if(rand == 38) return " stoner";
		if(rand == 39) return " nebulous";
		if(rand == 40) return " machiavellian";
		if(rand == 41) return "petulant";
		if(rand == 42) return "n arrogant";
		
		else return " bland";
	}
	
	//random place the person is from
	public static String getBackground() {
		
		int rand = (int)(Math.random()*33);
		
		if(rand == 0) return "the magical world of elevators";
		if(rand == 1) return "the mushroom forest, but most of those mushrooms had psilocybin in them";
		if(rand == 2) return "the Black Citadel";
		if(rand == 3) return "the paraelemental plane of salt";
		if(rand == 4) return "that hobo jungle to the northeast";
		if(rand == 5) return "the Wizarding City of Bugaboo";
		if(rand == 6) return "a farm somewhere";
		if(rand == 7) return "some archwizard's demiplane or pocket dimension";
		if(rand == 8) return "a cave in the mountains";
		if(rand == 9) return "the glorious commonwealth of Pogtopia";
		if(rand == 10) return "the Felicity Wilds";
		if(rand == 11) return "the northern bastion city";
		if(rand == 12) return "a hoity-toity magic academy";
		if(rand == 13) return "the boonies";
		if(rand == 14) return "a one-tavern town";
		if(rand == 15) return "a village near a recently-erupted volcano";
		if(rand == 16) return "the Mountains of Improbable Height";
		if(rand == 17) return "a travelling circus";
		if(rand == 18) return "a city where There Is No War";
		if(rand == 19) return "a desert metropolis";
		if(rand == 20) return "a notorious dungeon";
		if(rand == 21) return "the slums of a big port city";
		if(rand == 22) return "the Swamp of Madness";
		if(rand == 23) return "an isolated island of himbos and bimbos";
		if(rand == 24) return "the ethereal plane";
		if(rand == 25) return "a testing facility";
		if(rand == 26) return "the borderlands between two warring nations";
		if(rand == 27) return "the misty city on the peninsula";
		if(rand == 28) return "a town with the nation's only maximum-security prison";
		if(rand == 29) return "the nation's only maximum-security prison";
		if(rand == 30) return "the Pirate Isles";
		if(rand == 31) return "a shire-like town of hill houses";
		else return "a typical fantasy imperialist nation";
	}
	
	//not necessarily purpose, but returns some extra trait
	public static String getPurpose() {
		
		int rand = (int)(Math.random()*25);
		
		if(rand == 0) return "is determined to bake the best cake";
		if(rand == 1) return "was once a courrier for the empire";
		if(rand == 2) return "used to be an adventurer like you until they took an arrow to the knee";
		if(rand == 3) return "was apprentice to a discredited wizard";
		if(rand == 4) return "hails from a long line of wheat farmers";
		if(rand == 5) return "is on a quest to do every drug";
		if(rand == 6) return "makes the best damn tea you'll ever have";
		if(rand == 7) return "just wants some excitement in their life";
		if(rand == 8) return "can cook some surprisingly good monster meat dishes";
		if(rand == 9) return "dated the BBEG in highschool";
		if(rand == 10) return "got dragged along by a friend into these shenanigans";
		if(rand == 11) return "just got back from a worldwide trip and is now on a search for their lost 'lucky horseshoe'";
		if(rand == 12) return "wants to hear their favorite band play live, but their only concerts are in this one tavern across the continent";
		if(rand == 13) return "can solve a rubix cube";
		if(rand == 14) return "is a burnout gifted child";
		if(rand == 15) return "is seeking the approval of their distant father";
		if(rand == 16) return "fights better drunk";
		if(rand == 17) return "has rolled so many death saves that they are called \"Lady Luck's dice tray\"";
		if(rand == 18) return "can't flirt to save their life";
		if(rand == 19) return "sees themself as a teacher";
		if(rand == 20) return "knows every word to the Anarchist's Anthem";
		if(rand == 21) return "everyone inexplicably knows";
		if(rand == 22) return "needs a break";
		if(rand == 23) return "has a great propensity for getting themselves in too deep";
		else return "thinks the government is bees";
	}
	
	//roll a stat
	private static short statRoll() {
		short sum = 0;
		short roll = (short)(Math.random()*6+1);
		short min = roll;
		sum += roll;
		
		for(int i = 0; i<3; i++) {
			roll = (short)(Math.random()*6+1);
			sum += roll;
			if(roll<min) min = roll;
		}
		sum -= min;
		
		return sum;
	}
	
	//open a link
	public static void openWebpage(String Uri) {
		
		//get browser
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    
	    //open the link
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	        	URI uri = new URI(Uri);
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}