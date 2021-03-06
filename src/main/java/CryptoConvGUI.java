import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CryptoConvGUI extends JFrame {

    private JPanel mainPanel;
    private JTextField passValueTextField;
    private JButton convertBtn;
    private JLabel passValueLabel;
    private JLabel resultLabel;
    private JLabel oneValueLabel;
    private JButton reverseBtn;
    private JRadioButton btcRadio;
    private JRadioButton ethereumRadio;
    private JRadioButton tetherRadio;

    private double result;

    public CryptoConvGUI(String title) {
        super(title);

        Image icon = Toolkit.getDefaultToolkit().getImage("images/logo.png");
        this.setIconImage(icon);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setResizable(false);
        this.pack();

        oneValueLabel.setText("Here you'll see real-time cryptocurrency to PLN value.");

        btcRadio.addActionListener(e -> {
            passValueLabel.setText("PLN");
            resultLabel.setText("BTC");
            oneValueLabel.setText("1 BTC = " + fetchCryptoValue("BTC") + " PLN.");

        });
        ethereumRadio.addActionListener(e -> {
            passValueLabel.setText("PLN");
            resultLabel.setText("ETH");
            oneValueLabel.setText("1 ETH = " + fetchCryptoValue("ETH")+ " PLN.");

        });
        tetherRadio.addActionListener(e -> {
            passValueLabel.setText("PLN");
            resultLabel.setText("USDT");
            oneValueLabel.setText("1 USDT = " + fetchCryptoValue("USDT") + " PLN.");

        });

        convertBtn.addActionListener(e -> {
            checkBeforeConvert();

            switch (passValueLabel.getText()) {
                case "PLN" -> convertCalculatingCurrency();
                case "BTC" -> {
                    result = parseAndCalculateResult(fetchCryptoValue("BTC"), passValueTextField.getText(), "PLN");
                    resultLabel.setText(result + " PLN");
                }
                case "ETH" -> {
                    result = parseAndCalculateResult(fetchCryptoValue("ETH"), passValueTextField.getText(), "PLN");
                    resultLabel.setText(result + " PLN");
                }
                case "USDT" -> {
                    result = parseAndCalculateResult(fetchCryptoValue("USDT"), passValueTextField.getText(), "PLN");
                    resultLabel.setText(result + " PLN");
                }
                default -> System.out.println("Error at calculatingCurrency switch statement.");
            }
        });

        reverseBtn.addActionListener(e -> {
            if (resultLabel.getText().length() > 4) // to erase "Result will show up here." or "Wrong value."
                resultLabel.setText("BTC");

            reverseCurrencies();
        });
    }

    void reverseCurrencies() {
        String tempCurrencyValue = passValueLabel.getText();
        passValueLabel.setText(resultLabel.getText());
        resultLabel.setText(tempCurrencyValue);
        passValueTextField.setText("");
    }

    void checkBeforeConvert() {
        if (resultLabel.getText().length() > 4) // to erase "Result will show up here." and "Wrong value."
            resultLabel.setText("BTC");
        if (Double.parseDouble(passValueTextField.getText()) <= 0.0) // sure you will try:)
            passValueTextField.setText("1");
        if (passValueTextField.getText().isBlank())
            passValueTextField.setText("1");
    }

    void convertCalculatingCurrency() {

        switch (resultLabel.getText()) {
            case "PLN" -> {
                result = parseAndCalculateResult(fetchCryptoValue("BTC"), passValueTextField.getText(), "PLN");
                resultLabel.setText(result + " PLN");
                oneValueLabel.setText(fetchCryptoValue("BTC") + " PLN = 1 BTC.");
            }
            case "BTC" -> {
                result = parseAndCalculateResult(fetchCryptoValue("BTC"), passValueTextField.getText(), "BTC");
                resultLabel.setText(result + " BTC");
                oneValueLabel.setText(fetchCryptoValue("BTC") + " PLN = 1 BTC.");
            }
            case "ETH" -> {
                result = parseAndCalculateResult(fetchCryptoValue("ETH"), passValueTextField.getText(), "ETH");
                resultLabel.setText(result + " ETH");
                oneValueLabel.setText(fetchCryptoValue("ETH") + " PLN = 1 ETH.");
            }
            case "USDT" -> {
                result = parseAndCalculateResult(fetchCryptoValue("USDT"), passValueTextField.getText(), "USDT");
                resultLabel.setText(result + " USDT");
                oneValueLabel.setText(fetchCryptoValue("USDT") + " PLN = 1 USDT.");
            }
            default -> System.out.println("Error at convertCalculatingCurrency() switch");
        }
    }

    public static void main(String[] args) {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new CryptoConvGUI("Cryptocurrency Converter");

        frame.setBounds(0,0,(int)size.getWidth()/2, (int)size.getHeight()/2);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    static String fetchCryptoValue(String cryptoName) {
        String realTimeValueLink;
        switch (cryptoName) {
            case "BTC" -> realTimeValueLink = "https://www.google.com/search?q=1+Bitcoin+to+PLN&client=opera-gx&hs=Wq9&sxsrf=APq-WBvyQOspTN1J1KV9PvwhSrNMEf-Law%3A1645201187665&ei=I8cPYs6BKIrfrgTy7YGICg&oq=1+btc&gs_lcp=Cgdnd3Mtd2l6EAEYAjIHCCMQsAMQJzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzIHCAAQRxCwAzISCC4QxwEQ0QMQyAMQsAMQQxgAMhIILhDHARDRAxDIAxCwAxBDGABKBAhBGABKBAhGGABQAFgAYPEQaAJwAXgAgAEAiAEAkgEAmAEAyAELwAEB2gEECAAYCA&sclient=gws-wiz";
            case "ETH" -> realTimeValueLink = "https://www.google.com/search?q=1+ethereum+to+pln&client=opera-gx&sxsrf=APq-WBvl2QzFlq9r86qx-DJK2CsGmOchVg%3A1645260428750&ei=jK4QYqSbLerirgTx3KLoAg&oq=1+Ehterium+to+PLN&gs_lcp=Cgdnd3Mtd2l6EAEYADIJCAAQDRBGEIICOgcIABBHELADOgcIABCwAxBDOgYIABAHEB46BAgAEA06CAgAEA0QBRAeOggIABAIEA0QHkoECEEYAEoECEYYAFD3CVifHmCZLWgCcAF4AIAB0wGIAewGkgEFOC4wLjGYAQCgAQHIAQrAAQE&sclient=gws-wiz";
            case "USDT"-> realTimeValueLink = "https://www.google.com/search?q=1+tether+to+pln&client=opera-gx&sxsrf=APq-WBsFRlZRTFDRwXKv7YthCyAKUPBMBg%3A1645260437431&ei=la4QYrPIGa2MrwS8pIrICg&ved=0ahUKEwjz5r3fsIv2AhUtxosKHTySAqkQ4dUDCA0&uact=5&oq=1+tether+to+pln&gs_lcp=Cgdnd3Mtd2l6EAMyBAgAEA06BwgAEEcQsAM6BwgAELADEEM6BggAEAcQHjoICAAQCBAHEB46CggAEAgQBxAKEB46CAgAEA0QBRAeOggIABAIEA0QHkoECEEYAEoECEYYAFD0B1jdDWCQEGgCcAF4AIABV4gB5wOSAQE2mAEAoAEByAEJwAEB&sclient=gws-wiz";

            default -> throw new IllegalStateException("Unexpected value: " + cryptoName);
        }
                try {
                    Document document = Jsoup.
                            connect(realTimeValueLink).get();
                    Elements elements = document.getElementsByClass("pclqee");

                    return elements.get(0).text();

                } catch (IOException e) {
                    e.printStackTrace();
                    return "";
                }
            }

    double parseAndCalculateResult(String fetchedValue, String passedValue, String calculatedCurrency) {
        String fetchedValueComaModified = fetchedValue.replace(',', '.');
        String finalFetchedValue = fetchedValueComaModified.replaceAll("\\s+", ""); //no spaces

        String passedValueComaModified = passedValue.replace(',', '.');
        String finalPassedValue = passedValueComaModified.replaceAll("\\s+", ""); //no spaces

        if (calculatedCurrency.equals("BTC") || calculatedCurrency.equals("ETH") || calculatedCurrency.equals("USDT"))
            return Double.parseDouble(finalPassedValue) / Double.parseDouble(finalFetchedValue);
        else if (calculatedCurrency.equals("PLN"))
            return Double.parseDouble(finalPassedValue) * Double.parseDouble(finalFetchedValue);
        else return -1;
    }
}


