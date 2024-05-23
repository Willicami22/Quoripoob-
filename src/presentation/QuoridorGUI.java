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
    

    private JPanel choseSpecials;
    private JTextField normalBarriersField,temporaryBarriersField,longBarriersField, alliedBarriersField,totalSpecialTilesField;
    private ArrayList<JCheckBox> specialTileCheckBoxes;
    private ArrayList<JTextField> specialTileQuantityFields;
    private JButton submitButton;
 
    private JPanel gameBoardPanel, QuoridorBoard, principalGBL, player2Panel, player1Panel;
    private JLabel player1Label, player2Label,barrierTypeLabel;
    private String player1Name, player2Name;
    private JButton upLeftArrowBurButton,upArrowButton ,upRightArrowButton ,leftArrowButton ,rightArrowButton ,downLeftArrowButton ,downArrowButton ,downRightArrowButton ,giveUp,putBarrier, save;



    public QuoridorGUI(){

        Quoripoob= new QuoridorGame();
        prepareElements();
        prepareActions();
    }

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
    
    private void prepareElementsSpecials() {
        choseSpecials = new JPanel(new GridBagLayout());
        choseSpecials.setBorder(BorderFactory.createTitledBorder("Special Tiles Configuration"));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
    
        int row = 0;
    
        addLabeledField(choseSpecials, gbc, row++, "Normal Barriers:", normalBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Temporary Barriers:", temporaryBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Long Barriers:", longBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Allied Barriers:", alliedBarriersField = new JTextField());
        addLabeledField(choseSpecials, gbc, row++, "Total Special Tiles:", totalSpecialTilesField = new JTextField());
    
        specialTileCheckBoxes = new ArrayList<>();
        specialTileQuantityFields = new ArrayList<>();
    
        String[] tileTypes = {"Teleporter", "Go Back", "Double Shift"};
        for (String tileType : tileTypes) {
            JCheckBox checkBox = new JCheckBox(tileType);
            JTextField quantityField = new JTextField();
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
    }
    
    private void addLabeledField(JPanel panel, GridBagConstraints gbc, int row, String labelText, JTextField textField) {
        gbc.gridy = row;
        gbc.gridx = 0;
        panel.add(new JLabel(labelText), gbc);
        gbc.gridx = 1;
        panel.add(textField, gbc);
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

        JButton placeButton = new JButton("Colocar");
        JButton moveButton = new JButton("Mover");

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
        JComboBox<String> directionComboBox = new JComboBox<>(new String[]{"Horizontal", "Vertical"});
        placePanel.add(directionComboBox);
        placePanel.add(new JLabel("Row:"));
        JTextField rowTextField = new JTextField();
        placePanel.add(rowTextField);
        placePanel.add(new JLabel("Column:"));
        JTextField columnTextField = new JTextField();
        placePanel.add(columnTextField);
        placePanel.add(new JLabel("Type:"));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Normal", "Allied", "Temporary", "Large"});
        placePanel.add(typeComboBox);
        JButton placeBarrierButton = new JButton("Put Barrier");
        placePanel.add(placeBarrierButton);
    
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
        JLabel amountNormalLabel = new JLabel("10");
        JLabel alliedLabel = new JLabel("Allied:");
        JLabel amountAlliedLabel = new JLabel("20");
        JLabel temporaryLabel = new JLabel("Temporary:");
        JLabel amountTemporaryLabel = new JLabel("30");
        JLabel largeLabel = new JLabel("Large:");
        JLabel amountLargeLabel = new JLabel("40");

        remainingBarriersGrid.add(barrierTypeLabel);
        remainingBarriersGrid.add(amountALabel);
        remainingBarriersGrid.add(normalALabel);
        remainingBarriersGrid.add(amountNormalLabel);
        remainingBarriersGrid.add(alliedLabel);
        remainingBarriersGrid.add(amountAlliedLabel);
        remainingBarriersGrid.add(temporaryLabel);
        remainingBarriersGrid.add(amountTemporaryLabel);
        remainingBarriersGrid.add(largeLabel);
        remainingBarriersGrid.add(amountLargeLabel);
        

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
        JLabel timerLabel = new JLabel("Tiempo: 00:00");
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

    }

    private void newGameAction(){

        CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "choseOpponent");
        
    }

    private void continueGameAction() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(QuoridorGUI.this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Cargar el estado del juego desde el archivo
                Quoripoob = QuoridorGame.open(selectedFile);
    
                // Actualizar la interfaz de juego para reflejar el estado del juego cargado
                updateGameBoardUI();
    
                // Cambiar al panel de juego (gameBoard)
                CardLayout cl = (CardLayout) (principal.getLayout());
                cl.show(principal, "gameBoard");
    
            } catch (QuoripoobException ex) {
                JOptionPane.showMessageDialog(QuoridorGUI.this, "Error al abrir el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

private void optionSave() {
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showSaveDialog(QuoridorGUI.this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        try {
            Quoripoob.save(selectedFile);
            JOptionPane.showMessageDialog(QuoridorGUI.this, "Partida guardada correctamente!");
        } catch (QuoripoobException ex) {
            JOptionPane.showMessageDialog(QuoridorGUI.this, "Error al guardar la partida: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Log.record(ex); // Registra la excepción en el registro de errores
        } catch (SecurityException ex) {
            JOptionPane.showMessageDialog(QuoridorGUI.this, "Se ha denegado el acceso al archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Log.record(ex); // Registra la excepción en el registro de errores
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(QuoridorGUI.this, "Error desconocido al guardar la partida: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Log.record(ex); // Registra la excepción en el registro de errores
        }
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


    private void checkReady() {
        if (player1Ready && player2Ready) {
            String player1Name = name1.getText().trim();
            String player2Name = name2.getText().trim();
            
            if (!player1Name.isEmpty() && !player2Name.isEmpty() && player1Color != null && player2Color != null) {
                Quoripoob.setPlayers(0, player1Name, player1Color);
                Quoripoob.setPlayers(1, player2Name, player2Color);
                
                CardLayout cl = (CardLayout) principal.getLayout();
                    cl.show(principal, "choseSpecials");
                
            } else {
                player1Ready=false;
                player2Ready=false;
                JOptionPane.showMessageDialog(this, "Por favor ingresa nombres y selecciona colores para ambos jugadores.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void submitConfiguration() {
        try {
            int normalBarriers = Integer.parseInt(normalBarriersField.getText());
            int temporaryBarriers = Integer.parseInt(temporaryBarriersField.getText());
            int longBarriers = Integer.parseInt(longBarriersField.getText());
            int alliedBarriers = Integer.parseInt(alliedBarriersField.getText());
            int totalSpecialTiles = Integer.parseInt(totalSpecialTilesField.getText());


            int sumOfSpecialTiles = 0;
            for (int i = 0; i < specialTileCheckBoxes.size(); i++) {
                JCheckBox checkBox = specialTileCheckBoxes.get(i);
                if (checkBox.isSelected()) {
                    int quantity = Integer.parseInt(specialTileQuantityFields.get(i).getText());
                    sumOfSpecialTiles += quantity;
                    String type = checkBox.getText();
                }
            }

            if (sumOfSpecialTiles != totalSpecialTiles) {
                JOptionPane.showMessageDialog(this, "The total quantity of special tiles does not match the specified total.");
            } else {
                JOptionPane.showMessageDialog(this, "Configuration set successfully!");
                showGameBoardScreen();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for all barriers and special tiles.");
        }
    }
    

    private void showGameBoardScreen() {
    
        CardLayout cl = (CardLayout) principal.getLayout();
        cl.show(principal, "gameBoard");
    }

    private void giveUpAction(){
        Quoripoob.giveUp();
            CardLayout cl = (CardLayout) principal.getLayout();
            cl.show(principal, "initPanel");
        Quoripoob= new QuoridorGame();
    }

    public String getColorName(Color color) {
    if (color.equals(Color.BLACK)) {
        return "Negro";
    } else if (color.equals(Color.BLUE)) {
        return "Azul";
    } else if (color.equals(Color.CYAN)) {
        return "Cian";
    } else if (color.equals(Color.DARK_GRAY)) {
        return "Gris Oscuro";
    } else if (color.equals(Color.GRAY)) {
        return "Gris";
    } else if (color.equals(Color.GREEN)) {
        return "Verde";
    } else if (color.equals(Color.LIGHT_GRAY)) {
        return "Gris Claro";
    } else if (color.equals(Color.MAGENTA)) {
        return "Magenta";
    } else if (color.equals(Color.ORANGE)) {
        return "Naranja";
    } else if (color.equals(Color.PINK)) {
        return "Rosado";
    } else if (color.equals(Color.RED)) {
        return "Rojo";
    } else if (color.equals(Color.WHITE)) {
        return "Blanco";
    } else if (color.equals(Color.YELLOW)) {
        return "Amarillo";
    } else {
        return "Desconocido";
    }
}

    private static JButton createButtonWithArrow(String arrow) {
        JButton button = new JButton(arrow);
        button.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 24)); // Seleccionar una fuente que soporte los caracteres Unicode
        button.setFocusPainted(false); // Eliminar el borde alrededor del texto
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Establecer un borde
        return button;
    }
    
    private void updateGameBoardUI() {
        // Actualizar la interfaz de juego según el estado del juego cargado
        // Por ejemplo, puedes actualizar la posición de los jugadores, las barreras, etc.
    
        // Llamar a revalidate() y repaint() para asegurarse de que los cambios se apliquen
        gameBoardPanel.revalidate();
        gameBoardPanel.repaint();
    }
    
    public static void main(String args[]){
        QuoridorGUI home=new QuoridorGUI();
        home.setVisible(true);
    }    
}

