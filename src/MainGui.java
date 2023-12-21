import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class MainGui implements ActionListener {
    boolean lastButtonWasEquals = false;
    JFrame frame;
    JPanel rootPanel, resultPanel, buttonsPanel, buttonsRightPanel, buttonsLeftPanel;
    JButton[] numberButton = new JButton[12]; //0-9 = numberButton, 10 = decimal, 11 = equals
    JButton[] functionButton = new JButton[11];
    JButton addition, clear, backspace, minus, leftParentheses, rightParentheses, multi, sin, division, exponent, sessionLog;
    JTextField inputField;
    Font inputFont = new Font("SansSerif",Font.PLAIN,26);

    public MainGui() {          //Constructor
        frame = new JFrame("Calculator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rootPanel = new JPanel();
            rootPanel.setLayout(new GridBagLayout());
        GridBagConstraints north = new GridBagConstraints();
            north.gridx = 1;
            north.ipady = 35;
            north.fill = GridBagConstraints.HORIZONTAL;
        GridBagConstraints south = new GridBagConstraints();
            south.gridx = 1;
            south.ipady = 50;

        resultPanel = new JPanel();
            resultPanel.setLayout(new BorderLayout());
        inputField = new JTextField();
            inputField.setHorizontalAlignment(JTextField.RIGHT);
            inputField.setEditable(false);
            inputField.setFont(inputFont);
            inputField.setText("0");
            addInputFieldKeyListener();
        resultPanel.add(inputField);

        buttonsRightPanel = new JPanel(new GridLayout(0,3,5,5));
            buttonsRightPanel.setLayout(new GridBagLayout());
        GridBagConstraints numberConstraints = new GridBagConstraints();
            numberConstraints.fill = GridBagConstraints.BOTH;
            numberConstraints.weightx = 1;
            numberConstraints.weighty = 1;
            numberConstraints.gridx = 0;
            numberConstraints.gridy = 0;
            numberConstraints.ipady = 10;
            numberConstraints.gridwidth = 1;
            numberConstraints.anchor = GridBagConstraints.PAGE_START;
        Dimension buttonSize = new Dimension(80, 25);

        for (int i = 0; i < 12; i++) {
            if (i < 10)
                numberButton[i] = new JButton(String.valueOf(i));
            if (i == 10)
                numberButton[i] = new JButton(".");
            if (i == 11)
                numberButton[i] = new JButton("=");
            numberButton[i].setFocusable(false);
            numberButton[i].addActionListener(this);
            numberButton[i].setPreferredSize(buttonSize);
            buttonsRightPanel.add(numberButton[i], numberConstraints);

            numberConstraints.gridx++;
            if(numberConstraints.gridx == 3){
                numberConstraints.gridx = 0;
                numberConstraints.gridy++;
            }
        }

        buttonsLeftPanel = new JPanel(new GridLayout(0,3,5,5));
            buttonsLeftPanel.setLayout(new GridBagLayout());
            buttonsLeftPanel.setBackground(Color.lightGray);
        GridBagConstraints functionConstraints = new GridBagConstraints();
            functionConstraints.fill = GridBagConstraints.BOTH;
            functionConstraints.weightx = 1;
            functionConstraints.weighty = 1;
            functionConstraints.gridx = 0;
            functionConstraints.gridy = 0;
            functionConstraints.ipady = 10;
            functionConstraints.gridwidth = 1;
            functionConstraints.anchor = GridBagConstraints.PAGE_START;

        functionButton[0] = addition = new JButton("+");
        functionButton[1] = clear = new JButton("C");
        functionButton[2] = backspace = new JButton("⌫");
        functionButton[3] = minus = new JButton("-");
        functionButton[4] = leftParentheses = new JButton("(");
        functionButton[5] = rightParentheses = new JButton(")");
        functionButton[6] = multi = new JButton("*");
        functionButton[7] = exponent = new JButton("x^y");
        functionButton[8] = sin = new JButton("sin(x)");
        functionButton[9] = division = new JButton("/");
        functionButton[10] = sessionLog = new JButton("Session Log");
        for(int i = 0; i < 10; i++){
            functionButton[i].addActionListener(this);
            functionButton[i].setFocusable(false);
            functionButton[i].setPreferredSize(buttonSize);
            buttonsLeftPanel.add(functionButton[i], functionConstraints);
            functionConstraints.gridx++;
            if(functionConstraints.gridx == 3){
                functionConstraints.gridx = 0;
                functionConstraints.gridy++;
            }
        }

        //SessionLog button twice as wide
        buttonSize = new Dimension(160,25);
        functionButton[10].setPreferredSize(buttonSize);
        functionButton[10].addActionListener(this);
        functionButton[10].setFocusable(false);
        functionConstraints.gridwidth = 2;
        buttonsLeftPanel.add(functionButton[10], functionConstraints);

        buttonsPanel = new JPanel();
            buttonsPanel.setLayout(new BorderLayout());
            buttonsPanel.add(buttonsRightPanel, BorderLayout.WEST);
            buttonsPanel.add(buttonsLeftPanel, BorderLayout.EAST);

        rootPanel.add(resultPanel, north);
        rootPanel.add(buttonsPanel, south);

        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        frame.setMinimumSize(new Dimension(495,305));
        frame.setMaximumSize(new Dimension(495,305));
        frame.add(rootPanel);
        frame.setVisible(true);
    }
    private void addInputFieldKeyListener(){
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyPressed = e.getKeyChar();
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    handleInput('=');
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    handleInput('⌫');
                else
                    handleInput(keyPressed);
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i=0;i<10;i++) {
            if(e.getSource() == numberButton[i]) {
                char ch = (char) ('0' + i);
                handleInput(ch);
            }
        }
        if(e.getSource()== numberButton[10])  // "."
            handleInput('.');
        if(e.getSource()==addition)
            handleInput('+');
        if(e.getSource()==multi)
            handleInput('*');
        if(e.getSource()==division)
            handleInput('/');
        if(e.getSource()==minus)
            handleInput('-');
       if(e.getSource()==exponent)
           handleInput('^');
        if(e.getSource()==clear)
            handleInput('c');    //couldn't figure out any better character
        if(e.getSource()==backspace)
            handleInput('⌫');
        if(e.getSource()== leftParentheses)
            handleInput('(');
        if(e.getSource()== rightParentheses)
            handleInput(')');
        if(e.getSource()== numberButton[11])
            handleInput('=');
        if(e.getSource()== sessionLog)
            handleInput('L');   //couldn't figure out any better character
    }
    private void handleInput(char keyPressed) {
        if (Character.isDigit(keyPressed)) {
            handleDigitInput(keyPressed);
        }
        else {
            switch (keyPressed){
                case '.':
                    handleDecimalInput();
                    break;
                case '+':
                case '*':
                case '/':
                case '-':
                case '^':
                    handleOperatorInput(keyPressed);
                    break;
                case 'c':
                    handleClearInput();
                    break;
                case '⌫':
                    handleBackspaceInput();
                    break;
                case '(':
                case ')':
                    handleParenthesisInput(keyPressed);
                    break;
                case '=':
                    handleEqualsInput();
                    break;
                case 'L':
                    SessionLogGui logWindow = new SessionLogGui();
                    break;
                default:
                    break;
            }
        }
    }
    private void handleBackspaceInput(){
        lastButtonWasEquals = false;
        if (!inputField.getText().isEmpty()) {
            if(inputField.getText().length() == 1)
                inputField.setText("0");
            else
                inputField.setText(inputField.getText().substring(0,inputField.getText().length()-1));
        }
    }
    private void handleClearInput(){
        lastButtonWasEquals = false;
        inputField.setText("0");
    }
    private void handleDecimalInput(){
        lastButtonWasEquals = false;
        if (!inputField.getText().isEmpty()) {
            int decimalCount = getDecimalCount(inputField.getText());
            if (!lastChar(inputField.getText(), '.', false) && decimalCount == 0) //Prevent numberButton such 132.4534.34
                inputField.setText(inputField.getText().concat("."));
        } else
            inputField.setText("0.");
    }
    private void handleEqualsInput(){
        if (equalParentheses(inputField.getText()) && !lastCharOperator(inputField.getText()) && !inputField.getText().isEmpty()) {
            lastButtonWasEquals = true;

            String expression = inputField.getText();
            String result = Compute.evaluate(inputField.getText());

            inputField.setText(result);
            SessionLog.addToLog(expression, result);
            if (SessionLogGui.logField != null)
                SessionLogGui.setLogField();
        }
    }
    private void handleDigitInput(char digit) {
        lastButtonWasEquals = false;
        String input = inputField.getText();

        if (input.equals("0") || lastChar(input, ')', false)) {
            inputField.setText(String.valueOf(digit));
        } else {
            inputField.setText(input + digit);
        }
    }
    private void handleOperatorInput(char operator){
        lastButtonWasEquals = false;
        if (operator != '-') {
            if (!inputField.getText().isEmpty()) {
                if (lastChar(inputField.getText(), ' ', true) || lastChar(inputField.getText(), ')', false))  //To prevent scenarios *+-/ without digits etc.
                    inputField.setText(inputField.getText().concat(String.valueOf(operator)));
            }
        }
        else{
            if(onlyDigitIsZero(inputField.getText()))
                inputField.setText("-");
            else if(!lastChar(inputField.getText(),'-', false))
                inputField.setText(inputField.getText().concat("-"));
        }
    }
    private void handleParenthesisInput(char parenthesis){
        if (lastButtonWasEquals){
            lastButtonWasEquals = false;
            inputField.setText("");
        }
        if (parenthesis == ')'){
            if(inputField.getText().length()>1){
                if (rightParenthesesCheck(inputField.getText()))
                    inputField.setText(inputField.getText().concat(")"));
            }
        }
        else{
            if(onlyDigitIsZero(inputField.getText()))
                inputField.setText("(");
            else if (!lastChar(inputField.getText(), '(', false)) //Can't add ( directly after a (
                inputField.setText(inputField.getText().concat("("));
        }

    }

    private static boolean onlyDigitIsZero(String inputField){
        return inputField.length() == 1 && inputField.charAt(0) == '0';
    }
    private static boolean equalParentheses(String inputField){
        int equalNumOfParentheses= 0;
        for (int i = inputField.length()-1; i >= 0; i--){
            if(inputField.charAt(i) == '(')
                equalNumOfParentheses++;
            if(inputField.charAt(i) == ')')
                equalNumOfParentheses--;
        }
        return equalNumOfParentheses == 0;
    }
    private static boolean rightParenthesesCheck(String inputField){
        int count = 0;
        for (int i = inputField.length()-1; i >= 0; i--){
            if(inputField.charAt(i) == '(')
                count++;
            if(inputField.charAt(i) == ')'){
                count--;
            }
        }
        return (count > 0) && (!lastChar(inputField,'(', false));
    }
    private static boolean lastChar(String inputField, char controlChar, boolean checkDigit){
        if (!inputField.isEmpty()) {
            char lastCharacter = inputField.charAt(inputField.length() - 1);
            if (checkDigit)
                return Character.isDigit(lastCharacter);
            else
                return lastCharacter == controlChar;
        }
        else
            return false;
    }
    private static boolean lastCharOperator(String inputField){
        char lastChar = inputField.charAt(inputField.length()-1);
        return String.valueOf(lastChar).matches("[-+*/^(]");
    }
    private static int getDecimalCount(String inputField){
        int count = 0;
        for (int i = inputField.length()-1; i > 0; i--){
            char ch = inputField.charAt(i);
            if (!Character.isDigit(ch)){        //To prevent scenarios with numbers such as 4566.345.345
                if(inputField.charAt(i) == '.')
                    count++;
                return count;                  //No need to check further
            }
        }
        return count;
    }
}
