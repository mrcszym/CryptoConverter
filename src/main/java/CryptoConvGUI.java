import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CryptoConvGUI extends JFrame{

    private JPanel mainPanel;
    private JTextField passValueTextField;
    private JButton convertBtn;
    private JLabel passValueLabel;
    private JLabel resultLabel;
    private JLabel oneBtcValueLabel;
    private JButton reverseBtn;

    private double result;
    private String calculatingCurrency = "toBTC";

    public CryptoConvGUI(String title) {
        super(title);

        Image icon = Toolkit.getDefaultToolkit().getImage("images/logo.png");
        this.setIconImage(icon);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(true);
        this.pack();

        convertBtn.addActionListener(e -> {

            if(calculatingCurrency.equals("toPLN")) {
                result = parseAndCalculateResult(fetchBtcValue(), passValueTextField.getText(), calculatingCurrency);

                if(result > 0.0) {
                    resultLabel.setText(result + " PLN");
                    oneBtcValueLabel.setText(fetchBtcValue() + " PLN = 1 BTC.");
                } else {
                    resultLabel.setText("Wrong data.");
                }
            } else if(calculatingCurrency.equals("toBTC")) {
                result = parseAndCalculateResult(fetchBtcValue(), passValueTextField.getText(), calculatingCurrency);

                if(result > 0.0) {
                    resultLabel.setText(result + " BTC");
                    oneBtcValueLabel.setText("1 BTC = " + fetchBtcValue() + " PLN.");
                } else {
                    resultLabel.setText("Wrong data.");
                }
            }

        });
        reverseBtn.addActionListener(e -> {
            resultLabel.setText("Reversed!");
            oneBtcValueLabel.setText("Enter your value and hit Convert button.");

            if(calculatingCurrency.equals("toBTC")) {
                passValueLabel.setText("BTC");
                calculatingCurrency = "toPLN";
            }
            else if(calculatingCurrency.equals("toPLN")) {
                passValueLabel.setText("BTC");
                calculatingCurrency = "toBTC";
            }
            else System.out.println("Error at reverseBtn ActionListener.");
        });
    }

    public static void main(String[] args) {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new CryptoConvGUI("Crypto Currency Converter");

        frame.setBounds(0,0,(int)size.getWidth()/2, (int)size.getHeight()/2);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static String fetchBtcValue() {
        try {
            Document document = Jsoup.
                    connect("https://www.google.com/search?q=1+Bitcoin+to+PLN&client=opera-gx&hs=Wq9&sxsrf=APq-WBvyQOspTN1J1KV9PvwhSrNMEf-Law%3A1645201187665&ei=I8cPYs6BKIrfrgTy7YGICg&oq=1+btc&gs_lcp=Cgdnd3Mtd2l6EAEYAjIHCCMQsAMQJzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzISCC4QxwEQ0QMQyAMQsAMQQxgAMhIILhDHARDRAxDIAxCwAxBDGABKBAhBGABKBAhGGABQAFgAYPEQaAJwAXgAgAEAiAEAkgEAmAEAyAELwAEB2gEECAAYCA&sclient=gws-wiz").get();
            Elements elements = document.getElementsByClass("pclqee");

            return elements.get(0).text();

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    double parseAndCalculateResult(String fetchedValue, String passedValue, String calculatedCurrency) {
        String fetchedValueComaModified = fetchedValue.replace(',', '.');
        String finalFetchedValue = fetchedValueComaModified.replaceAll("\\s+",""); //no spaces

        String passedValueComaModified = passedValue.replace(',', '.');
        String finalPassedValue = passedValueComaModified.replaceAll("\\s+",""); //no spaces

        if(calculatedCurrency.equals("toBTC"))
            return Double.parseDouble(finalPassedValue) / Double.parseDouble(finalFetchedValue);
        else if(calculatedCurrency.equals("toPLN"))
            return Double.parseDouble(finalPassedValue) * Double.parseDouble(finalFetchedValue);
        else return -1;
    }
}
