package presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import domain.QuoridorGame;

import java.util.*;
import javax.swing.*;


public class QuoridorGUI extends JFrame {
    public static final int Widht=800, Height=800;
    private static final Dimension PREFERRED_DIMENSION =
                         new Dimension(Widht,Height);
    private JPanel principal;

    private QuoridorGame Quoripoob;
    
    private JPanel initPanel; 
    private JButton newGame,continueGame,exit;

    private JPanel choseSpace;
    private JButton game1,game2,game3;

    private JPanel choseOpponent;
    private JButton machine,human;

    private JPanel choseDifficult;
    private JButton begginer, intermediate, advanced;

    private JPanel choseMode;
    private JButton normalMode, timeTrial, timed; 

    private JSplitPane setPlayers;
    private JPanel player1, player2;
    private JTextField name1, name2;
    private JButton ok1,ok2 ;
    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private Color player1Color;
    private Color player2Color;

    private JPanel gameBoardPanel;
    private JButton forward,left,right,back,leftDiagonal,rightDiagonal,giveUp,putBarrier;






    public QuoridorGUI(){

        prepareElements();
        prepareActions();
    }

    private void prepareElements(){

        Quoripoob=new QuoridorGame();

        principal=new JPanel(new CardLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Quoripoob");

        add(principal);

        prepareElementsInit();
        prepareElementsSpace();
        prepareElementsOpponent();
        prepareElementsDifficult();
        prepareElementsMode();
        prepareElementsSetPlayers();
        prepareElementsGameBoard();

        setSize(PREFERRED_DIMENSION);

        setLocationRelativeTo(null);
    }

    private void prepareElementsInit(){
        initPanel= new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;gbc.gridy = 0;gbc.weightx = 1.0;gbc.weighty = 1.0;gbc.fill = GridBagConstraints.BOTH;

        newGame=new JButton("New Game");
        continueGame= new JButton("Continue Game");
        exit= new JButton("Exit");

        gbc.insets = new Insets(10, 10, 10, 10); 
        initPanel.add(newGame, gbc);
        gbc.gridy++;
        initPanel.add(continueGame, gbc);
        gbc.gridy++;
        initPanel.add(exit, gbc);

        principal.add(initPanel,"initPanel");

        initPanel.setSize(PREFERRED_DIMENSION);
    }
    
    private void prepareElementsSpace(){

        choseSpace=new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;gbc.gridy = 0;gbc.weightx = 1.0;gbc.weighty = 1.0;gbc.fill = GridBagConstraints.BOTH;

        game1=new JButton("Game 1");
        game2= new JButton("Game 2");
        game3= new JButton("Game 3");

        gbc.insets = new Insets(10, 10, 10, 10); 

        choseSpace.add(game1,gbc);
        gbc.gridy++;
        choseSpace.add(game2,gbc);
        gbc.gridy++;
        choseSpace.add(game3,gbc);
        principal.add(choseSpace, "choseSpace");
        choseSpace.setSize(PREFERRED_DIMENSION);
    }

    private void prepareElementsOpponent(){

        choseOpponent= new JPanel();
        choseOpponent.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;gbc.gridy = 0;gbc.weightx = 1.0;gbc.weighty = 1.0;gbc.fill = GridBagConstraints.BOTH;

        machine=new JButton("1 VS AI");
        human= new JButton("1 VS 1");

        gbc.insets = new Insets(10, 10, 10, 10); 
        choseOpponent.add(machine,gbc);
        gbc.gridy++;
        choseOpponent.add(human,gbc);

        principal.add(choseOpponent, "choseOpponent");

        choseOpponent.setSize(PREFERRED_DIMENSION);
    }

    private void prepareElementsDifficult(){
        
        choseDifficult= new JPanel();

        choseDifficult.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;gbc.gridy = 0;gbc.weightx = 1.0;gbc.weighty = 1.0;gbc.fill = GridBagConstraints.BOTH;

        begginer = new JButton("Begginer");
        intermediate= new JButton("Intermediate");
        advanced = new JButton("Advanced");
        
        gbc.insets = new Insets(10, 10, 10, 10); 

        choseDifficult.add(begginer,gbc);
        gbc.gridy++;
        choseDifficult.add(intermediate,gbc);
        gbc.gridy++;choseDifficult.add(advanced,gbc);
        principal.add(choseDifficult, "choseDifficult");

        choseDifficult.setSize(PREFERRED_DIMENSION);
    }

    private void prepareElementsMode(){
        choseMode= new JPanel();

        choseMode.setLayout(new GridLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;gbc.gridy = 0;gbc.weightx = 1.0;gbc.weighty = 1.0;gbc.fill = GridBagConstraints.BOTH;

        normalMode= new JButton("Normal");
        timeTrial= new JButton("Time Trial");
        timed= new JButton("Timed");

        gbc.insets = new Insets(10, 10, 10, 10);
        
        choseMode.add(normalMode,gbc);
        gbc.gridy++;
        choseMode.add(timeTrial,gbc);
        gbc.gridy++;
        choseMode.add(timed,gbc);

        principal.add(choseMode, "choseMode");

        choseMode.setSize(PREFERRED_DIMENSION);

    }

    private void prepareElementsSetPlayers() {
        setPlayers = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        setPlayers.setDividerLocation(400);
    
        player1 = new JPanel(new GridLayout(4,0));
        player2 = new JPanel(new GridLayout(4,0));

        name1= new JTextField();
        name2= new JTextField();
    
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA};
        JPanel colorPanel1 = new JPanel();
        JPanel colorPanel2 = new JPanel();
        colorPanel1.setLayout(new GridLayout(1, colors.length, 5, 5)); 
        colorPanel2.setLayout(new GridLayout(1, colors.length, 5, 5)); 
    
        JButton selectedColorButton1 = new JButton("Selected Color Player 1");
        JButton selectedColorButton2 = new JButton("Selected Color Player 2");
    
        for (Color color : colors) {
            JButton colorButton1 = new JButton();
            colorButton1.setBackground(color);
            colorButton1.setOpaque(true);
            colorButton1.setBorderPainted(false);
            colorButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player1Color = color;
                    selectedColorButton1.setBackground(color);
                }
            });
            colorPanel1.add(colorButton1);
    
            JButton colorButton2 = new JButton();
            colorButton2.setBackground(color);
            colorButton2.setOpaque(true);
            colorButton2.setBorderPainted(false);
            colorButton2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player2Color = color;
                    selectedColorButton2.setBackground(color);
                }
            });
            colorPanel2.add(colorButton2);
        }
    
        selectedColorButton1.setEnabled(false); 
        selectedColorButton1.setPreferredSize(new Dimension(200, 50));
        selectedColorButton2.setEnabled(false); 
        selectedColorButton2.setPreferredSize(new Dimension(200, 50));

        ok1= new JButton("Ready");
        ok2= new JButton("Ready");

        player1.add(name1);
        player1.add(colorPanel1, BorderLayout.CENTER);
        player1.add(selectedColorButton1, BorderLayout.SOUTH);
        player1.add(ok1);

        player2.add(name2);
        player2.add(colorPanel2, BorderLayout.CENTER);
        player2.add(selectedColorButton2, BorderLayout.SOUTH);
        player2.add(ok2);
    
        setPlayers.setLeftComponent(player1);
        setPlayers.setRightComponent(player2);
    
        principal.add(setPlayers, "setPlayers");
    }

    

    private void prepareElementsGameBoard() {
        gameBoardPanel = new JPanel(new BorderLayout());
        
        JPanel boardPanel = new JPanel(new GridLayout(11, 9));
        JButton[][] boardButtons = new JButton[9][9];
        
        JLabel player1Label = new JLabel();
        player1Label.setHorizontalAlignment(SwingConstants.CENTER);
        boardPanel.add(player1Label);
        
        for (int col = 0; col < 9; col++) {
            boardPanel.add(new JLabel());
        }
        
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60));
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                    }
                });
                boardButtons[row][col] = button;
                boardPanel.add(button);
            }
        }
        
        JLabel player2Label = new JLabel();
        player2Label.setHorizontalAlignment(SwingConstants.CENTER);
        boardPanel.add(player2Label);
        
        gameBoardPanel.add(boardPanel, BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        
        JPanel barrierSelectionPanel = new JPanel(new GridLayout(4, 1));
        JLabel columnLabel = new JLabel("Column:");
        JTextField columnTextField = new JTextField();
        JLabel rowLabel = new JLabel("Row:");
        JTextField rowTextField = new JTextField();
        JLabel barrierTypeLabel = new JLabel("Barrier Type:");
        JComboBox<String> barrierTypeComboBox = new JComboBox<>(new String[]{"Normal", "Allied", "Temporary", "Large"});
        JButton placeBarrierButton = new JButton("Place Barrier");
        barrierSelectionPanel.add(columnLabel);
        barrierSelectionPanel.add(columnTextField);
        barrierSelectionPanel.add(rowLabel);
        barrierSelectionPanel.add(rowTextField);
        barrierSelectionPanel.add(barrierTypeLabel);
        barrierSelectionPanel.add(barrierTypeComboBox);
        barrierSelectionPanel.add(placeBarrierButton);
        rightPanel.add(barrierSelectionPanel);
        
        JPanel barrierInformationPanel = new JPanel(new BorderLayout());
        JLabel remainingBarriersLabel = new JLabel("Remaining Barriers:");
        JTable barrierTable = new JTable(new String[][]{{"Normal", "10"}, {"Allied", "5"}, {"Temporary", "3"}, {"Large", "2"}}, new String[]{"Barrier Type", "Count"});
        JScrollPane scrollPane = new JScrollPane(barrierTable);
        barrierInformationPanel.add(remainingBarriersLabel, BorderLayout.NORTH);
        barrierInformationPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(barrierInformationPanel);
        
        gameBoardPanel.add(rightPanel, BorderLayout.EAST);
        
        JButton surrenderButton = new JButton("Surrender");
        gameBoardPanel.add(surrenderButton, BorderLayout.NORTH);
        
        JPanel turnPanel = new JPanel(new FlowLayout());
        JLabel turnLabel = new JLabel();
        JLabel timerLabel = new JLabel("Time: 00:00");
        turnPanel.add(turnLabel);
        turnPanel.add(timerLabel);
        gameBoardPanel.add(turnPanel, BorderLayout.SOUTH);
        
        principal.add(gameBoardPanel, "gameBoard");
    }

    private void checkReady(){
        if (player1Ready && player2Ready){

            String player1Name = name1.getText();
            String player2Name = name2.getText();

            showGameBoardScreen(player1Name, player2Name, player1Color, player2Color);
    }
} 

    private void prepareActions()
    {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev){
                setVisible(false);
                System.exit(0);
            }
        });

        newGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                newGameAction();
            }
        });

        continueGame.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                continueGameAction();
            }
        });

        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setVisible(false);
                System.exit(0);
            }
        });

        game1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                fileAction();
            }
        });

        game2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                fileAction();
            }
        });

        game3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                fileAction();
            }
        });

        machine.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setPlayer("M");
            }
        });

        human.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setPlayer("H");
            }
        });

        begginer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setDifficult("B");
            }
        });

        intermediate.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setDifficult("I");
            }
        });

        advanced.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setDifficult("A");
            }
        });

        normalMode.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setMode("N");
            }
        });

        timeTrial.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setMode("T");
            }
        });

        timed.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                setMode("TT");
            }
        });

        ok1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                player1Ready = true;
                checkReady();
            }
        });

        ok2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                player2Ready = true;
                checkReady();
            }
        });
    }

    private void newGameAction(){

        CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "choseSpace");
        
    }

    private void continueGameAction(){

      
        CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "choseSpace");
    }

    private void fileAction(){
        if (Quoripoob.isNew()){
            CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "choseOpponent");
        }
        else{
            CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "game");
        }
    }

    private void setPlayer(String Opponent){

        Quoripoob.setPlayer(Opponent);
        if (Opponent=="M"){
            CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "choseDifficult");
        }
        else{
            CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "choseMode");
        }
    }
 

    private void setDifficult(String difficult){

        Quoripoob.setDifficult(difficult);
        CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "choseMode");

    }

    private void setMode(String mode){

        Quoripoob.setMode(mode);
        CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "setPlayers");

    }

    private void showGameBoardScreen(String player1Name, String player2Name, Color player1Color, Color player2Color) {
    
        CardLayout cl = (CardLayout) principal.getLayout();
        cl.show(principal, "gameBoard");
    }


    public static void main(String args[]){
        QuoridorGUI home=new QuoridorGUI();
        home.setVisible(true);
    }    
}
