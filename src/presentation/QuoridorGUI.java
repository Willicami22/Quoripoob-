package presentation;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.event.*;

import domain.Log;
import domain.QuoridorGame;
import domain.QuoripoobException;
import domain.allied;
import domain.temporary;

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


    private JPanel choseOpponent;
    private JButton machine,human;

    private JPanel choseDifficult;
    private JButton begginer, intermediate, advanced;

    private JPanel choseMode;
    private JButton normalMode, timeTrial, timed; 

    private JSplitPane setPlayers;
    private JPanel player1, player2, namePanel1, namePanel2,colorPanel1,colorPanel2;
    private JTextField name1, name2;
    private JLabel nameLabel1,nameLabel2;
    private JButton ok1,ok2, selectedColorButton1, selectedColorButton2 ;
    private boolean player1Ready = false;
    private boolean player2Ready = false;
    private Color player1Color,player2Color;
    private String namePlayer1, namePlayer2;
    

    private JPanel choseSpecials;
    private JTextField normalBarriersField,temporaryBarriersField,longBarriersField, alliedBarriersField,totalSpecialTilesField,boardSizeJField;
    private ArrayList<JCheckBox> specialTileCheckBoxes;
    private ArrayList<JTextField> specialTileQuantityFields;
    private JButton submitButton;
    private JLabel amountNormalLabel, amountAlliedLabel, amountTemporaryLabel, amountLongLabel,boardSize;
    private JTextField doubleShiftField,starField,goBackField,teleporterField;
    int normalBarriers,temporaryBarriers,longBarriers, alliedBarriers, totalSpecialTiles,size,goBack,doubleShift,teleporter,star;
 
    private JPanel gameBoardPanel, QuoridorBoard, principalGBL, player2Panel, player1Panel;
    private JLabel player1Label, player2Label,barrierTypeLabel;
    private String player1Name, player2Name;
    private JButton upLeftArrowBurButton,upArrowButton ,upRightArrowButton ,leftArrowButton ,rightArrowButton ,downLeftArrowButton ,downArrowButton ,downRightArrowButton ,giveUp,putBarrier, save;
    private JTextField rowTextField,columnTextField;
    private JComboBox<String> directionComboBox,typeComboBox;


    public QuoridorGUI(){

        Quoripoob=new QuoridorGame();
        prepareElements();
        prepareActions();
    }

    /**
     * Prepares the main elements of the GUI.
     */

    private void prepareElements(){

        principal=new JPanel(new CardLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Quoripoob");

        add(principal);

        prepareElementsInit();
        prepareElementsSpecials();
        prepareElementsOpponent();
        prepareElementsDifficult();
        prepareElementsMode();
        prepareElementsSetPlayers();
        prepareElementsGameBoard();

        setSize(PREFERRED_DIMENSION);

        setLocationRelativeTo(null);
    }

        /**
     * Prepares the elements for the initial panel.
     */

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

    /**
     * Prepares the elements for the special tiles configuration panel.
     */
    
    private void prepareElementsSpecials() {
        choseSpecials = new JPanel(new GridBagLayout());
        choseSpecials.setBorder(BorderFactory.createTitledBorder("Special Tiles Configuration"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
    
        int row = 0;
        
        addLabeledField(choseSpecials, gbc, row++, "Board Size", boardSizeJField= new JTextField() );
        addLabeledField(choseSpecials, gbc, row++, "Normal Barriers:", normalBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Allied Barriers:", alliedBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Temporary Barriers:", temporaryBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Large Barriers:", longBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Total Special Tiles:", totalSpecialTilesField = new JTextField());
    
        specialTileCheckBoxes = new ArrayList<>();
        specialTileQuantityFields = new ArrayList<>();
    
        String[] tileTypes = {"Teleporter", "Go Back", "Double Shift","Star"};
        JTextField[] fields = {teleporterField = new JTextField(), goBackField = new JTextField(), doubleShiftField = new JTextField(),starField= new JTextField()};
    
        for (int i = 0; i < tileTypes.length; i++) {
            JCheckBox checkBox = new JCheckBox(tileTypes[i]);
            JTextField quantityField = fields[i];
            quantityField.setEnabled(false);
    
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    quantityField.setEnabled(checkBox.isSelected());
                }
            });
    
            specialTileCheckBoxes.add(checkBox);
            specialTileQuantityFields.add(quantityField);
    
            gbc.gridy = row;
            gbc.gridx = 0;
            choseSpecials.add(checkBox, gbc);
    
            gbc.gridx = 1;
            choseSpecials.add(quantityField, gbc);
    
            row++;
        }
    
        gbc.gridy = row;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        submitButton = new JButton("Submit");
        choseSpecials.add(submitButton, gbc);
    
        principal.add(choseSpecials, "choseSpecials");
    
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountNormalLabel.setText(normalBarriersField.getText());
                amountAlliedLabel.setText(alliedBarriersField.getText());
                amountTemporaryLabel.setText(temporaryBarriersField.getText());
                amountLongLabel.setText(longBarriersField.getText());

            }
        });
    }

    /**
     * Adds a labeled text field to a panel with specific constraints.
     * @param panel The panel to add the field to.
     * @param gbc The grid bag constraints.
     * @param row The row number.
     * @param labelText The label text.
     * @param textField The text field.
     */
    
    private void addLabeledField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField textField) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);
    }
    
      /**
     * Prepares the elements for the opponent selection panel.
     */

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

      /**
     * Prepares the elements for the difficult selection panel.
     */
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


      /**
     * Prepares the elements for the mode selection panel.
     */

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

     /**
     * Prepares the elements for setting up the players.
     */

    private void prepareElementsSetPlayers() {
        setPlayers = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        setPlayers.setDividerLocation(400);
    
        player1 = new JPanel(new GridLayout(4,0));
        player2 = new JPanel(new GridLayout(4,0));
    
        name1 = new JTextField();
        name1.setPreferredSize(new Dimension(100, 30)); 
        nameLabel1 = new JLabel("Name:");
        namePanel1 = new JPanel(new BorderLayout());
        namePanel1.add(nameLabel1, BorderLayout.WEST);
        namePanel1.add(name1, BorderLayout.CENTER);
    
        name2 = new JTextField();
        name2.setPreferredSize(new Dimension(100, 30)); 
        nameLabel2 = new JLabel("Name:");
        namePanel2 = new JPanel(new BorderLayout());
        namePanel2.add(nameLabel2, BorderLayout.WEST);
        namePanel2.add(name2, BorderLayout.CENTER);
    
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA};
        colorPanel1 = new JPanel();
        colorPanel2 = new JPanel();
        colorPanel1.setLayout(new GridLayout(1, colors.length, 5, 5)); 
        colorPanel2.setLayout(new GridLayout(1, colors.length, 5, 5)); 
    
        selectedColorButton1 = new JButton("Selected Color Player 1");
        selectedColorButton2 = new JButton("Selected Color Player 2");
    
        for (Color color : colors) {
            JButton colorButton1 = new JButton();
            colorButton1.setBackground(color);
            colorButton1.setOpaque(true);
            colorButton1.setBorderPainted(false);
            colorButton1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    player1Color = color;
                    selectedColorButton1.setBackground(player1Color);
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
    
        ok1 = new JButton("Ready");
        ok2 = new JButton("Ready");
    
        player1.add(namePanel1); 
        player1.add(colorPanel1);
        player1.add(selectedColorButton1);
        player1.add(ok1);
    
        player2.add(namePanel2); 
        player2.add(colorPanel2);
        player2.add(selectedColorButton2);
        player2.add(ok2);
    
        setPlayers.setLeftComponent(player1);
        setPlayers.setRightComponent(player2);
    
        principal.add(setPlayers, "setPlayers");

        ok1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1Label.setText(name1.getText()); // Guardar el nombre del jugador 1
            }
        });

        ok2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player2Label.setText(name2.getText()); // Guardar el nombre del jugador 2
            }
        });
    }
    

    /**
     * Prepares the elements for the game board.
     */
    private void prepareElementsGameBoard() {
        gameBoardPanel = new JPanel(new BorderLayout());

        principalGBL = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
    
        // Panel izquierdo - Información de los jugadores
    
        player2Panel = new JPanel(new FlowLayout());
        player2Label = new JLabel(player2Name != null ? player2Name : "JUGADOR 2");
        player2Panel.add(player2Label);

        //Añadir la información del player 2 al principalGBL
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.weightx = 1.0;
        principalGBL.add (player2Panel, constraints);
        constraints.weightx = 0.0;
        
        //Añadir el tablero al principalGBL
        QuoridorBoard = new QuoridorBoard(Quoripoob);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH; 
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        principalGBL.add (QuoridorBoard, constraints);
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;
        
        player1Panel = new JPanel(new FlowLayout());

        player1Label = new JLabel(player1Name != null ? player1Name : "JUGADOR 1");
        player1Panel.add(player1Label);
        
        //Añadir la información del player 1 al principalGBL
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL; 
        constraints.weightx = 1.0;
        principalGBL.add (player1Panel, constraints);
        constraints.weightx = 0.0;
        constraints.weighty = 0.0;

    
        // Panel de selección de acción (Colocar o Mover)
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
        actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centrar en el eje X

        JButton placeButton = new JButton("Put");
        JButton moveButton = new JButton("Move");

        actionPanel.add(Box.createHorizontalGlue()); // Pegamento horizontal para empujar los botones al centro
        actionPanel.add(placeButton);
        actionPanel.add(Box.createHorizontalStrut(10)); // Espacio horizontal entre botones
        actionPanel.add(moveButton);
        actionPanel.add(Box.createHorizontalGlue()); // Pegamento horizontal para empujar los botones al centro

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.NONE; 
        principalGBL.add (actionPanel, constraints);
        
        JPanel actionOptionsPanel = new JPanel(new CardLayout());
    
        // Opciones para colocar barrera
        JPanel placePanel = new JPanel(new GridLayout(5, 2));
        placePanel.add(new JLabel("Orientation:"));
        directionComboBox = new JComboBox<>(new String[]{"Horizontal", "Vertical"});
        placePanel.add(directionComboBox);
        placePanel.add(new JLabel("Row:"));
        rowTextField = new JTextField();
        placePanel.add(rowTextField);
        placePanel.add(new JLabel("Column:"));
        columnTextField = new JTextField();
        placePanel.add(columnTextField);
        placePanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Normal", "Allied", "Temporary", "Large"});
        placePanel.add(typeComboBox);
        putBarrier = new JButton("Put Barrier");
        placePanel.add(putBarrier);
    
        JPanel movePanel = new JPanel(new GridLayout(3, 3));

        // Flechas para las direcciones
        upArrowButton=createButtonWithArrow( "\u2191"); // ↑
        downArrowButton=createButtonWithArrow("\u2193"); // ↓
        leftArrowButton=createButtonWithArrow("\u2190"); // ←
        rightArrowButton=createButtonWithArrow("\u2192"); // →
        upLeftArrowBurButton=createButtonWithArrow("\u2196"); // ↖
        upRightArrowButton=createButtonWithArrow("\u2197"); // ↗
        downLeftArrowButton=createButtonWithArrow("\u2199"); // ↙
        downRightArrowButton=createButtonWithArrow("\u2198"); // ↘

        movePanel.add(upLeftArrowBurButton); 
        movePanel.add(upArrowButton); 
        movePanel.add(upRightArrowButton); 
        movePanel.add(leftArrowButton); 
        movePanel.add(new JLabel()); 
        movePanel.add(rightArrowButton); 
        movePanel.add(downLeftArrowButton); 
        movePanel.add(downArrowButton); 
        movePanel.add(downRightArrowButton); 
 


        actionOptionsPanel.add(placePanel, "Place");
        actionOptionsPanel.add(movePanel, "Move");
    
        constraints.gridx = 2;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH; 
        constraints.weighty = 1.0;
        principalGBL.add (actionOptionsPanel, constraints);
    
        // Información de barreras
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));

        JPanel barriersInfoPanel = new JPanel(new BorderLayout());
        JLabel remainingBarriersLabel = new JLabel("REMAINING BARRIERS", SwingConstants.CENTER);
        JPanel remainingBarriersGrid = new JPanel(new GridLayout(5, 2));

        JLabel barrierTypeLabel = new JLabel("Type of barrier");
        JLabel amountALabel = new JLabel("Amount");

        JLabel normalALabel = new JLabel("Normal:");
        amountNormalLabel = new JLabel();
        JLabel alliedLabel = new JLabel("Allied:");
        amountAlliedLabel = new JLabel();
        JLabel temporaryLabel = new JLabel("Temporary:");
        amountTemporaryLabel = new JLabel();
        JLabel longLabel = new JLabel("Large:");
        amountLongLabel = new JLabel();

        remainingBarriersGrid.add(barrierTypeLabel);
        remainingBarriersGrid.add(amountALabel);
        remainingBarriersGrid.add(normalALabel);
        remainingBarriersGrid.add(amountNormalLabel);
        remainingBarriersGrid.add(alliedLabel);
        remainingBarriersGrid.add(amountAlliedLabel);
        remainingBarriersGrid.add(temporaryLabel);
        remainingBarriersGrid.add(amountTemporaryLabel);
        remainingBarriersGrid.add(longLabel);
        remainingBarriersGrid.add(amountLongLabel);
        

        barriersInfoPanel.add(remainingBarriersLabel, BorderLayout.NORTH);
        barriersInfoPanel.add(remainingBarriersGrid, BorderLayout.CENTER);

        //información de casillas visitadas

        JPanel boxesInfoPanel = new JPanel(new BorderLayout());

        JLabel visitedBoxesLabel = new JLabel("VISITED BOXES", SwingConstants.CENTER);
        JPanel visitedBoxesGrid = new JPanel(new GridLayout(5, 2));

        JLabel boxesTypeLabel = new JLabel("Type of box");
        JLabel amountLabel = new JLabel("Amount");
        
        JLabel normalLabel = new JLabel("Normal:");
        JLabel amountNormalBoxesLabel = new JLabel("10");
        JLabel teleporterLabel = new JLabel("Teleporter:");
        JLabel amountTeleporterLabel = new JLabel("20");
        JLabel goBackLabel = new JLabel("Go back:");
        JLabel amountGoBackLabel = new JLabel("30");
        JLabel doubleShiftLabel = new JLabel("Double Shift:");
        JLabel amountDoubleShiftLabel = new JLabel("40");

        visitedBoxesGrid.add(boxesTypeLabel);
        visitedBoxesGrid.add(amountLabel);
        visitedBoxesGrid.add(normalLabel);
        visitedBoxesGrid.add(amountNormalBoxesLabel);
        visitedBoxesGrid.add(teleporterLabel);
        visitedBoxesGrid.add(amountTeleporterLabel);
        visitedBoxesGrid.add(goBackLabel);
        visitedBoxesGrid.add(amountGoBackLabel);
        visitedBoxesGrid.add(doubleShiftLabel);
        visitedBoxesGrid.add(amountDoubleShiftLabel);

        boxesInfoPanel.add(visitedBoxesLabel, BorderLayout.NORTH);
        boxesInfoPanel.add(visitedBoxesGrid, BorderLayout.CENTER);

        
        infoPanel.add(barriersInfoPanel);
        infoPanel.add(boxesInfoPanel);

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH; 
        principalGBL.add (infoPanel, constraints);
        constraints.weighty = 0.0;
    
        // Botón de rendirse
        giveUp = new JButton("Give Up");
        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.BOTH; 
        principalGBL.add (giveUp, constraints);
    
        gameBoardPanel.add(principalGBL, BorderLayout.CENTER);
    
        // Panel inferior - Tiempo y turno
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JLabel turnLabel = new JLabel("Turno:");
        JLabel currentPlayerLabel = new JLabel();
        JLabel timerLabel = new JLabel();
        bottomPanel.add(turnLabel);
        bottomPanel.add(currentPlayerLabel);
        bottomPanel.add(timerLabel);
        // Botón de rendirse
        save = new JButton("Save");
        gameBoardPanel.add(save, BorderLayout.NORTH);
        gameBoardPanel.add(bottomPanel, BorderLayout.SOUTH);
    
        principal.add(gameBoardPanel, "gameBoard");

    
        // Acción de botones
        placeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (actionOptionsPanel.getLayout());
                cl.show(actionOptionsPanel, "Place");
            }
        });
    
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (actionOptionsPanel.getLayout());
                cl.show(actionOptionsPanel, "Move");
            }
        });
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

        
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitConfiguration();
                
            }
        });

        giveUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                giveUpAction();
            }
        });

        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                optionSave();;
            }
        });

        upArrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("S");;
            }
        });

        downArrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("N");;
            }
        });

        rightArrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("W");;
            }
        });

        leftArrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("E");;
            }
        });
        
        upLeftArrowBurButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("SW");;
            }
        });

        upRightArrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("SE");
            }
        });

        downLeftArrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("NW");;
            }
        });

        downRightArrowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                moveAction("NE");
            }
        });

        putBarrier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev){
                putBarrierAction();
            }
        });

    }

    private void putBarrierAction() {
        try {
            // Retrieve the row and column values from text fields
            int row = Integer.parseInt(rowTextField.getText());
            int column = Integer.parseInt(columnTextField.getText());
            
            // Retrieve the orientation and type from combo boxes
            String orientation = (String) directionComboBox.getSelectedItem();
            String type = (String) typeComboBox.getSelectedItem();
    
            // Place the barrier based on the orientation
            if ("Horizontal".equals(orientation)) {
                Quoripoob.putBarrier(row, column, "H", type);
            } else {
                Quoripoob.putBarrier(row, column, "V", type);
            }
    
            // Repaint the board to reflect the changes
            QuoridorBoard.repaint();
        } catch (NumberFormatException e) {
            // Log the error and show a message if row or column inputs are not valid numbers
            Log.record(e);
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for row and column.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (QuoripoobException e) {
            // Log the error and show a message if there is an issue placing the barrier
            Log.record(e);
            JOptionPane.showMessageDialog(this, "Unable to place barrier: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void moveAction(String direction) {
        try {
            // Move the tab in the given direction
            Quoripoob.moveTab(direction);
            // Repaint the board to reflect the changes
            QuoridorBoard.repaint();
        } catch (QuoripoobException e) {
            // Log the error and show a message if there is an issue with the move
            Log.record(e);
            JOptionPane.showMessageDialog(QuoridorGUI.this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void newGameAction() {
        // Switch to the panel where the user can choose an opponent
        CardLayout cl = (CardLayout) (principal.getLayout());
        cl.show(principal, "choseOpponent");
    }
    
    private void continueGameAction() {
        // Open a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(QuoridorGUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Load the game state from the file
                QuoridorGame loadedGame = QuoridorGame.open(selectedFile);
    
                // Validate the loaded game
                if (loadedGame == null || loadedGame.getBoard() == null) {
                    throw new QuoripoobException("The file does not contain a valid game state.");
                }
    
                // Assign the loaded game to the global game variable
                Quoripoob = loadedGame;
    
                // Update the instance of QuoridorBoard with the new game
                QuoridorBoard board = (QuoridorBoard) QuoridorBoard;
                board.updateBoard(loadedGame);
    
                // Update the names and colors of the players
                player1Label.setText(Quoripoob.getPlayers()[0].getName());
                player2Label.setText(Quoripoob.getPlayers()[1].getName());
                selectedColorButton1.setBackground(Quoripoob.getPlayers()[0].getColor());
                selectedColorButton2.setBackground(Quoripoob.getPlayers()[1].getColor());
    
                // Switch to the game panel (gameBoard)
                CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "gameBoard");
    
                // Refresh the game panel
                gameBoardPanel.revalidate();
                gameBoardPanel.repaint();
    
            } catch (QuoripoobException ex) {
                // Log the error and show a message if there is an issue loading the game
                Log.record(ex);
                JOptionPane.showMessageDialog(QuoridorGUI.this, "Error opening the file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    private void optionSave() {
        // Create a file chooser dialog for saving the game
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(QuoridorGUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Save the game to the selected file
                Quoripoob.save(selectedFile);
                JOptionPane.showMessageDialog(QuoridorGUI.this, "Partida guardada correctamente!");
            } catch (QuoripoobException ex) {
                // Show error message if there is an issue saving the game
                JOptionPane.showMessageDialog(QuoridorGUI.this, "Error al guardar la partida: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Log.record(ex); // Log the exception
            } catch (SecurityException ex) {
                // Show error message if there is a security issue
                JOptionPane.showMessageDialog(QuoridorGUI.this, "Se ha denegado el acceso al archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Log.record(ex); // Log the exception
            } catch (Exception ex) {
                // Show error message for any other unknown issues
                JOptionPane.showMessageDialog(QuoridorGUI.this, "Error desconocido al guardar la partida: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                Log.record(ex); // Log the exception
            }
        }
    }
    
    private void setPlayer(String Opponent) {
        // Set the player based on the opponent type
        Quoripoob.setPlayer(Opponent);
        CardLayout cl = (CardLayout) (principal.getLayout());
        if (Opponent == "M") {
            // Show difficulty selection if the opponent is "M"
            cl.show(principal, "choseDifficult");
        } else {
            // Show mode selection otherwise
            cl.show(principal, "choseMode");
        }
    }
    
    private void setDifficult(String difficult) {
        // Set the difficulty level
        Quoripoob.setDifficult(difficult);
        // Show the mode selection screen
        CardLayout cl = (CardLayout) (principal.getLayout());
        cl.show(principal, "choseMode");
    }
    
    private void setMode(String mode) {
        // Set the game mode
        Quoripoob.setMode(mode);
        // Show the special tiles selection screen
        CardLayout cl = (CardLayout) (principal.getLayout());
        cl.show(principal, "choseSpecials");
    }
    
    private void checkReady() {
        if (player1Ready && player2Ready) {
            String player1Name = name1.getText().trim();
            String player2Name = name2.getText().trim();
    
            // Validate player names
            if (player1Name.isEmpty() || player2Name.isEmpty()) {
                player1Ready = false;
                player2Ready = false;
                JOptionPane.showMessageDialog(this, "Please enter names for both players.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (player1Name.equals(player2Name)) {
                player1Ready = false;
                player2Ready = false;
                JOptionPane.showMessageDialog(this, "Player names cannot be identical.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Validate player colors
            if (player1Color == null || player2Color == null) {
                player1Ready = false;
                player2Ready = false;
                JOptionPane.showMessageDialog(this, "Please select colors for both players.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            if (player1Color.equals(player2Color)) {
                player1Ready = false;
                player2Ready = false;
                JOptionPane.showMessageDialog(this, "Player colors cannot be identical.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Configure the game
            namePlayer1 = player1Name;
            namePlayer2 = player2Name;
            Quoripoob.setBoard(size);
            Quoripoob.setPlayers(0, player1Name, player1Color);
            Quoripoob.setPlayers(1, player2Name, player2Color);
            Quoripoob.distributeSpecialBoxes(goBack, doubleShift, teleporter, star);
            Quoripoob.setBarriers(normalBarriers, alliedBarriers, temporaryBarriers, longBarriers);
    
            JOptionPane.showMessageDialog(this, "Configuration set successfully!");
    
            // Show the game board screen
            showGameBoardScreen();
        }
    }
    
    private void submitConfiguration() {
        try {
            // Parse barrier and special tile values from text fields
            normalBarriers = parseOrDefault(normalBarriersField.getText(), 0);
            temporaryBarriers = parseOrDefault(temporaryBarriersField.getText(), 0);
            longBarriers = parseOrDefault(longBarriersField.getText(), 0);
            alliedBarriers = parseOrDefault(alliedBarriersField.getText(), 0);
            totalSpecialTiles = parseOrDefault(totalSpecialTilesField.getText(), 0);
    
            // Calculate the sum of special tiles
            int sumOfSpecialTiles = 0;
            for (int i = 0; i < specialTileCheckBoxes.size(); i++) {
                JCheckBox checkBox = specialTileCheckBoxes.get(i);
                if (checkBox.isSelected()) {
                    int quantity = parseOrDefault(specialTileQuantityFields.get(i).getText(), 0);
                    sumOfSpecialTiles += quantity;
                }
            }
    
            // Parse additional configuration values
            size = parseOrDefault(boardSizeJField.getText(), 0);
            goBack = parseOrDefault(goBackField.getText(), 0);
            doubleShift = parseOrDefault(doubleShiftField.getText(), 0);
            teleporter = parseOrDefault(teleporterField.getText(), 0);
            star = parseOrDefault(starField.getText(), 0);
    
            // Validate the configuration
            if (sumOfSpecialTiles != totalSpecialTiles) {
                JOptionPane.showMessageDialog(this, "The total quantity of special tiles does not match the specified total.");
            } else if (star > (totalSpecialTiles * 0.2)) {
                JOptionPane.showMessageDialog(this, "The total of stars can't be more than 20% of the total special tiles.");
            } else {
                int totalBarriers = normalBarriers + temporaryBarriers + longBarriers + alliedBarriers;
                if (totalBarriers > size + 1) {
                    JOptionPane.showMessageDialog(this, "The total number of barriers cannot exceed the size of the board plus one.");
                } else {
                    // Show the player setup screen
                    CardLayout cl = (CardLayout) principal.getLayout();
                    cl.show(principal, "setPlayers");
                }
            }
        } catch (NumberFormatException e) {
            // Show error message if any number parsing fails
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for all barriers and special tiles.");
        }
    }
    
    // Helper method to parse a number or return a default value if the field is empty or invalid
    private int parseOrDefault(String text, int defaultValue) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    private void showGameBoardScreen() {
        // Show the game board screen
        CardLayout cl = (CardLayout) principal.getLayout();
        cl.show(principal, "gameBoard");
    }
    
    private void giveUpAction() {
        // Give up the current game and reset
        Quoripoob.giveUp();
        CardLayout cl = (CardLayout) principal.getLayout();
        cl.show(principal, "initPanel");
        Quoripoob = new QuoridorGame();
    }
    
    private static JButton createButtonWithArrow(String arrow) {
        // Create a button with a Unicode arrow character
        JButton button = new JButton(arrow);
        button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24)); // Select a font that supports Unicode characters
        button.setFocusPainted(false); // Remove the focus border around the text
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set a border
        return button;
    }
    
    public static void main(String args[]) {
        // Create and display the Quoridor GUI
        QuoridorGUI home = new QuoridorGUI();
        home.setVisible(true);
    }
}
    

